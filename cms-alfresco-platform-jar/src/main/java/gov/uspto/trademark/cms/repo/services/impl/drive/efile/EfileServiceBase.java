package gov.uspto.trademark.cms.repo.services.impl.drive.efile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.EfileNodeCreator;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.model.drive.efile.Efile;
import gov.uspto.trademark.cms.repo.nodedelete.BehaviorDocumentDelete;
import gov.uspto.trademark.cms.repo.nodelocator.EfileNodeLocator;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.EfileService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.EfileDocumentMetadataResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * The Class EfileServiceBase.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
@Component("EfileServiceBase")
public class EfileServiceBase extends AbstractEfileCommonService implements EfileService {

    /** The case service. */
    @Autowired
    protected CaseService caseService;

    @Autowired
    CmsNodeCreator caseNodeCreator;

    @Autowired
    @Qualifier("DocumentHardDelete")
    BehaviorDocumentDelete documentHardDelete;

    /**
     * Instantiates a new abstract common service.
     *
     * @param eFileNodeLocator
     *            the case node creator
     * @param eFileNodeCreator
     *            the cms node locator
     */
    @Autowired
    public EfileServiceBase(EfileNodeCreator eFileNodeCreator, EfileNodeLocator eFileNodeLocator) {
        super(eFileNodeCreator, eFileNodeLocator);
    }

    /**
     * Gets the efile all document properties.
     *
     * @param globalId
     *            the globalId
     * @return the efile all document properties
     */
    @Override
    public List<EfileDocumentMetadataResponse> getDocumentList(String globalId) {
        NodeRef globalIdFolderNodeRef = this.cmsNodeLocator.locateNode(globalId);
        List<ChildAssociationRef> eFileTemporaryFiles = serviceRegistry.getNodeService().getChildAssocs(globalIdFolderNodeRef);
        List<EfileDocumentMetadataResponse> documentMetadataList = new ArrayList<EfileDocumentMetadataResponse>();
        for (ChildAssociationRef childAssociationRef : eFileTemporaryFiles) {
            Map<QName, Serializable> repositoryProperties = this.serviceRegistry.getNodeService()
                    .getProperties(childAssociationRef.getChildRef());
            EfileDocumentMetadataResponse efileDocumentMetadataResponse = new EfileDocumentMetadataResponse();
            efileDocumentMetadataResponse.setId(globalId);
            efileDocumentMetadataResponse.setMetadata(JacksonHelper.generateOutGoingDTO(repositoryProperties, Efile.class));
            efileDocumentMetadataResponse.setVersion(efileDocumentMetadataResponse.getMetadata().getVersion());
            efileDocumentMetadataResponse.setDocumentId(globalId,
                    this.serviceRegistry.getNodeService().getType(childAssociationRef.getChildRef()).getLocalName(),
                    (String) repositoryProperties.get(ContentModel.PROP_NAME));
            documentMetadataList.add(efileDocumentMetadataResponse);
        }
        return documentMetadataList;
    }

    /**
     * This method is used to create the document. This will be invoked from the
     * CreateWebScript and has to be implemented in the doctype specific service
     * class.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param properties
     *            the properties
     * @return NodeRef - created NodeRef
     */
    @Override
    public TmngAlfResponse create(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.EFILE_QNAME, properties, Efile.class);
    }

    /**
     * This method is used to update the document. This will be invoked from the
     * UpdateWebScript and has to be implemented in the doc type specific
     * service.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param content
     *            the content
     * @param properties
     *            the properties
     * @return updated noderef
     */
    @Override
    public TmngAlfResponse update(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        throw new UnsupportedOperationException("Operation not permitted");
    }

    /**
     * This method is used to update the document metadata . This will be
     * invoked from the UpdateWebScript and has to be implemented in the doc
     * type specific service.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param properties
     *            the properties
     * @return updated noderef
     */
    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        throw new UnsupportedOperationException("Operation not permitted");
    }

    /**
     * This method is used to retrieve the content for a document. This will be
     * invoked from RetrieveContentWebScript and will be implemented in doc type
     * specific service class.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param versionNumber
     *            the version number
     * @return content reader
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(cmsNodeLocator, TradeMarkModel.EFILE_QNAME, id, fileName, versionNumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent(
     * java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return null;
    }

    /**
     * This method is used to retrieve the metadata for the given serial number
     * and file name.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param versionNumber
     *            the version number
     * @return metadata json
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.EFILE_QNAME, Efile.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata(
     * java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return null;
    }
}
