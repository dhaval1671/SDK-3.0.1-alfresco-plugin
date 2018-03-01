package gov.uspto.trademark.cms.repo.services.impl;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.helpers.TicrsDocumentNodeCreator;
import gov.uspto.trademark.cms.repo.model.TicrsDocument;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Specimen;
import gov.uspto.trademark.cms.repo.nodelocator.TicrsDocumentNodeLocator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the Legacy document type functionality.
 * 
 * @author vnondapaka
 *
 */
@Component("TicrsDocumentDocService")
public class TicrsDocumentDocService extends AbstractCaseCommonService {

    @Autowired
    @Qualifier(value = "transformToPngService")
    private TransformToPngService transformToPngService;

    /**
     * Instantiates a new specimen doc service.
     *
     * @param ticrsDocumentNodeCreator
     *            the case node creator
     * @param ticrsDocumentNodeLocator
     *            the case node locator
     */
    @Autowired
    public TicrsDocumentDocService(TicrsDocumentNodeCreator ticrsDocumentNodeCreator,
            TicrsDocumentNodeLocator ticrsDocumentNodeLocator) {
        super(ticrsDocumentNodeCreator, ticrsDocumentNodeLocator);
    }

    /*
     * This method is used to create the Signature document type
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#create(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#create(java.lang.
     * String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.TICRS_DOCUMENT_QNAME, properties, TicrsDocument.class);
    }

    /*
     * This method is used to update the Signature document type
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#update(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#update(java.lang.
     * String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse update(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return updateDocument(id, fileName, content, TradeMarkModel.TICRS_DOCUMENT_QNAME, properties, TicrsDocument.class);
    }

    /*
     * This method is used to retrieve the Summary document content
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent(
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ContentReader retrieveContent(String id, String fileName, String versionNumber) {
        return retrieveContent(cmsNodeLocator, TradeMarkModel.TICRS_DOCUMENT_QNAME, id, fileName, versionNumber);
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
        // Ignore the data filter as the accessLevel is not needed for AS-IS
        // TicrsDocuments
        return retrieveContent(cmsNodeLocator, TradeMarkModel.TICRS_DOCUMENT_QNAME, id, fileName, versionNumber);
    }

    /*
     * This method is used to retrieve the Signature document metadata
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata(
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public byte[] retrieveMetadata(String id, String fileName, String versionNumber) {
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.TICRS_DOCUMENT_QNAME,
                TicrsDocument.class);
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
        // Ignore the data filter as the accessLevel is not needed for AS-IS
        // TicrsDocuments
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.TICRS_DOCUMENT_QNAME,
                TicrsDocument.class);
    }

    /*
     * This method is used to update the Signature document metadata.
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateMetadata
     * (java.lang.String, java.lang.String, java.util.Map)
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateMetadata(
     * java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        return updateDocument(id, fileName, null, TradeMarkModel.TICRS_DOCUMENT_QNAME, properties, Specimen.class);

    }

    /**
     * This will retrieve the attachments for Office Action.
     */
    public ContentReader retrieveAttachmentContent(String id, String fileName, String versionNumber) {
        ContentReader lclContentReader = null;
        ContentReader contentReader = retrieveContent(cmsNodeLocator, TradeMarkModel.TICRS_DOCUMENT_QNAME, id, fileName,
                versionNumber);
        lclContentReader = transformToPngService.transformImageToPNG(fileName, contentReader);
        return lclContentReader;
    }

}
