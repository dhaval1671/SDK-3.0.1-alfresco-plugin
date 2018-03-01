package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Summary;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the Summary document type functionality.
 * 
 * @author vnondapaka
 *
 */
@Component("SummaryDocService")
public class SummaryDocService extends AbstractCaseCommonService {

    /**
     * Instantiates a new summary doc service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param caseNodeLocator
     *            the case node locator
     */
    @Autowired
    public SummaryDocService(CmsNodeCreator caseNodeCreator, CmsNodeLocator caseNodeLocator) {
        super(caseNodeCreator, caseNodeLocator);
    }

    /*
     * This method is used to create the summary document type
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
        return createDocument(id, fileName, content, TradeMarkModel.SUMMARY_QNAME, properties, Summary.class);
    }

    /*
     * This method is used to update the Summary document type
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
        return updateDocument(id, fileName, content, TradeMarkModel.SUMMARY_QNAME, properties, Summary.class);
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
        return retrieveContent(cmsNodeLocator, TradeMarkModel.SUMMARY_QNAME, id, fileName, versionNumber);
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
        return retrieveContent(cmsNodeLocator, TradeMarkModel.SUMMARY_QNAME, id, fileName, versionNumber, dataFilter);
    }

    /*
     * This method is used to retrieve the Summary document metadata
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
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.SUMMARY_QNAME, Summary.class);
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
        return retrieveMetadata(cmsNodeLocator, id, fileName, versionNumber, TradeMarkModel.SUMMARY_QNAME, Summary.class,
                dataFilter);
    }

    /*
     * This method is used to update the legacy document metadata.
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
        return updateDocument(id, fileName, null, TradeMarkModel.SUMMARY_QNAME, properties, Summary.class);

    }
}
