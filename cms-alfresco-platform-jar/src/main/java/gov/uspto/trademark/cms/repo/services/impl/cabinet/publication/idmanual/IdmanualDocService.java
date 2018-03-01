package gov.uspto.trademark.cms.repo.services.impl.cabinet.publication.idmanual;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual.Idm;
import gov.uspto.trademark.cms.repo.nodedelete.BehaviorDocumentDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.publication.AbstractPublicationCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.PublicationResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the note document type functionality.
 * 
 * @author vnondapaka
 *
 */
@Component("IdmanualDocService")
public class IdmanualDocService extends AbstractPublicationCommonService {

    /** The idm node locator. */
    private final CmsNodeLocator idmNodeLocator;

    /** The behavior document delete. */
    @Autowired
    @Qualifier(value = "DocumentHardDelete")
    protected BehaviorDocumentDelete behaviorDocumentDelete;

    /**
     * Instantiates a new idmanual doc service.
     *
     * @param idmNodeCreator
     *            the idm node creator
     * @param idmNodeLocator
     *            the idm node locator
     */
    @Autowired
    public IdmanualDocService(CmsNodeCreator idmNodeCreator, CmsNodeLocator idmNodeLocator) {
        super(idmNodeCreator, idmNodeLocator);
        this.idmNodeLocator = idmNodeLocator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(idmNodeLocator, TradeMarkModel.IDMANUAL_QNAME, id, fileName, versionNumber);
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return retrieveMetadata(idmNodeLocator, id, fileName, versionNumber, TradeMarkModel.IDMANUAL_QNAME, Idm.class);
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
        return  new byte[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateMetadata(
     * java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return updateDocument(id, fileName, null, TradeMarkModel.IDMANUAL_QNAME, properties, Idm.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.impl.AbstractCommonService#delete(
     * java.lang.String, java.lang.String)
     */
    @Override
    public TmngAlfResponse delete(String id, String fileName) {
        NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName, TradeMarkModel.IDMANUAL_QNAME);
        Boolean deleteParentIfEmpty = Boolean.TRUE;
        Boolean result = behaviorDocumentDelete.delete(documentNodeRef, deleteParentIfEmpty);
        PublicationResponse publResponse = null;
        if (result) {
            publResponse = new PublicationResponse();
            publResponse.setDocumentId(id, TradeMarkModel.IDMANUAL_QNAME.getLocalName(), fileName);
            publResponse.setId(id);
        }
        return publResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.impl.PublicationCommonService#
     * create(java.lang.String, java.lang.String, java.io.InputStream,
     * java.util.Map)
     */
    @Override
    public TmngAlfResponse create(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.IDMANUAL_QNAME, properties, Idm.class);

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.impl.PublicationCommonService#
     * update(java.lang.String, java.lang.String, java.io.InputStream,
     * java.util.Map)
     */
    @Override
    public TmngAlfResponse update(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return updateDocument(id, fileName, TradeMarkModel.IDMANUAL_QNAME, properties, Idm.class, content);
    }
}
