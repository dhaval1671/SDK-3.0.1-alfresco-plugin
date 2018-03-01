package gov.uspto.trademark.cms.repo.services.impl;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This class is used to retrieve the document based on file name without
 * document type. This functionality should only be used internally and should
 * not be used by any other external applications. This is used to access links
 * from pdf documents.
 * 
 * @author vnondapaka
 *
 */
@Component("PsuedoNodeService")
public class PsuedoNodeService extends AbstractCaseCommonService {

    /** The psuedo node locator. */
    @Autowired
    CmsNodeLocator psuedoNodeLocator;

    /**
     * Instantiates a new psuedo node service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param psuedoNodeLocator
     *            the psuedo node locator
     */
    @Autowired
    public PsuedoNodeService(CmsNodeCreator caseNodeCreator, CmsNodeLocator psuedoNodeLocator) {
        super(caseNodeCreator, psuedoNodeLocator);
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
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
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
        return retrieveContent(psuedoNodeLocator, TradeMarkModel.ATTACHMENT_QNAME, id, fileName, versionNumber);
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
        return retrieveMetadata(psuedoNodeLocator, id, fileName, versionNumber, TradeMarkModel.ATTACHMENT_QNAME,
                Attachment.class);
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#updateMetadata
     * (java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public TmngAlfResponse updateMetadata(String id, String fileName, Map<String, Serializable> properties) {
        throw new UnsupportedOperationException();
    }
}