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
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.UpdatedRegistrationCertificate;
import gov.uspto.trademark.cms.repo.nodedelete.BehaviorDocumentDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the note document type functionality.
 * 
 * @author stank
 *
 */
@Component("UpdatedRegistrationCertificateDocService")
public class UpdatedRegistrationCertificateDocService extends AbstractCaseCommonService {

    /** The behavior document delete. */
    @Autowired
    @Qualifier(value = "DocumentHardDelete")
    protected BehaviorDocumentDelete behaviorDocumentDelete;

    /**
     * Instantiates a new notice doc service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param caseNodeLocator
     *            the case node locator
     */
    @Autowired
    public UpdatedRegistrationCertificateDocService(CmsNodeCreator caseNodeCreator, CmsNodeLocator caseNodeLocator) {
        super(caseNodeCreator, caseNodeLocator);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#create(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return this.createOrUpdateDocument(id, fileName, content, TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME,
                properties, UpdatedRegistrationCertificate.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#update(java.lang
     * .String, java.lang.String,
     * gov.uspto.trademark.cms.repo.services.util.ContentItem, java.util.Map)
     */
    @Override
    public TmngAlfResponse update(String id, String fileName, ContentItem content, Map<String, Serializable> properties) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateMetadata
     * (java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return this.updateDocument(id, fileName, null, TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME, properties,
                UpdatedRegistrationCertificate.class);
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
        return retrieveContent(cmsNodeLocator, TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME, id, fileName,
                versionNumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent
     * (java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return retrieveContent(cmsNodeLocator, TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME, id, fileName, versionNumber,
                dataFilter);
    }

    /*
     * This method is used to retrieve the metadata of the registration
     * certificate for eog
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber,
                TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME, UpdatedRegistrationCertificate.class);
    }

    /*
     * This method is used to retrieve the metadata of the registration
     * certificate for eog
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber, CmsDataFilter dataFilter) {
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber,
                TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME, UpdatedRegistrationCertificate.class, dataFilter);
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
        NodeRef documentNodeRef = cmsNodeLocator.locateNode(id, fileName, TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME);
        Boolean result = behaviorDocumentDelete.delete(documentNodeRef, Boolean.TRUE);
        PostResponse postResponse = null;
        if (result) {
            postResponse = new PostResponse();
            postResponse.setDocumentId(id, TradeMarkModel.UPDATED_REGISTRATION_CERTIFICATE_QNAME.getLocalName(), fileName);
            postResponse.setSerialNumber(id);
        }
        return postResponse;
    }
}
