package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;
import gov.uspto.trademark.cms.repo.nodedelete.BehaviorDocumentDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * The Class AttachmentService.
 */
@Component("AttachmentDocService")
public class AttachmentDocService extends AbstractCaseCommonService {

    @Autowired
    @Qualifier(value = "DocumentSoftDelete")
    protected BehaviorDocumentDelete behaviorDocumentDelete;

    /**
     * Instantiates a new attachment service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param caseNodeLocator
     *            the case node locator
     */
    @Autowired
    public AttachmentDocService(CmsNodeCreator caseNodeCreator, CmsNodeLocator caseNodeLocator) {
        super(caseNodeCreator, caseNodeLocator);
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
     * @return the tmng alf response
     */
    @Override
    public TmngAlfResponse create(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.ATTACHMENT_QNAME, properties, Attachment.class);
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
     * @return the tmng alf response
     */
    @Override
    public TmngAlfResponse update(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return updateDocument(id, fileName, content, TradeMarkModel.ATTACHMENT_QNAME, properties, Attachment.class);
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
     * @return the content reader
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(cmsNodeLocator, TradeMarkModel.ATTACHMENT_QNAME, id, fileName, versionNumber);
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
        return retrieveContent(cmsNodeLocator, TradeMarkModel.ATTACHMENT_QNAME, id, fileName, versionNumber, dataFilter);
    }

    /**
     * Retrieve metadata.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param versionNumber
     *            the version number
     * @return the byte[]
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.ATTACHMENT_QNAME, Attachment.class);
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
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.ATTACHMENT_QNAME, Attachment.class,
                dataFilter);
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
        return updateDocument(id, fileName, null, TradeMarkModel.ATTACHMENT_QNAME, properties, Attachment.class);
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
        PostResponse postResponse = null;
        NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName, TradeMarkModel.ATTACHMENT_QNAME);
        Boolean result = behaviorDocumentDelete.delete(documentNodeRef);

        if (result) {
            postResponse = new PostResponse();
            postResponse.setDocumentId(id, TradeMarkModel.ATTACHMENT_QNAME.getLocalName(), fileName);
        }

        return postResponse;
    }
}