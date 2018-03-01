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
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.RegistrationCertificate;
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
@Component("RegistrationCertificateDocService")
public class RegistrationCertificateDocService extends AbstractCaseCommonService {

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
    public RegistrationCertificateDocService(CmsNodeCreator caseNodeCreator, CmsNodeLocator caseNodeLocator) {
        super(caseNodeCreator, caseNodeLocator);
    }

    /*
     * This method is used to create the notice document in Alfresco
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#create(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
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
        return this.createOrUpdateDocument(id, fileName, content, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME, properties,
                RegistrationCertificate.class);
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
        return updateDocument(id, fileName, content, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME, properties,
                RegistrationCertificate.class);
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
        return this.updateDocument(id, fileName, null, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME, properties,
                RegistrationCertificate.class);
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
        return retrieveContent(cmsNodeLocator, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME, id, fileName, versionNumber);
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
        return retrieveContent(cmsNodeLocator, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME, id, fileName, versionNumber,
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
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME,
                RegistrationCertificate.class);
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
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME,
                RegistrationCertificate.class, dataFilter);
    }

    /*
     * This method is used to delete the registration certificate for EOG
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.impl.AbstractCommonService#delete
     * (java.lang.String, java.lang.String)
     */
    @Override
    public TmngAlfResponse delete(String id, String fileName) {
        NodeRef rcNodeRef = cmsNodeLocator.locateNode(id, fileName, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME);

        PostResponse postResponse = null;
        Boolean result = behaviorDocumentDelete.delete(rcNodeRef);
        if (result) {
            postResponse = new PostResponse();
            postResponse.setDocumentId(id, TradeMarkModel.REGISTRATION_CERTIFICATE_QNAME.getLocalName(), fileName);
            postResponse.setSerialNumber(id);
        }
        return postResponse;
    }

}
