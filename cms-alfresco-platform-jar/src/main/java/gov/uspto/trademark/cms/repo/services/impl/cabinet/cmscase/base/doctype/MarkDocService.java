package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.TransformToPngService;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.AbstractCaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This service will implement the mark document type functionality.
 *
 * @author vnondapaka
 */
@Component("MarkDocService")
public class MarkDocService extends AbstractCaseCommonService {

    private static final Logger log = LoggerFactory.getLogger(MarkDocService.class);
    @Autowired
    @Qualifier(value = "transformToPngService")
    private TransformToPngService transformToPngService;

    /**
     * Instantiates a new mark doc service.
     *
     * @param caseNodeCreator
     *            the case node creator
     * @param caseNodeLocator
     *            the case node locator
     */
    @Autowired
    public MarkDocService(CmsNodeCreator caseNodeCreator, CmsNodeLocator caseNodeLocator) {
        super(caseNodeCreator, caseNodeLocator);
    }

    /*
     * (non-Javadoc) This method is used to create the officeAction Document and
     * also attach the evidences provided in the request.
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#create(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse create(final String id, final String fileName, final ContentItem content,
            Map<String, Serializable> properties) {
        return createDocument(id, fileName, content, TradeMarkModel.MARK_QNAME, properties, MarkDoc.class);
    }

    /*
     * (non-Javadoc) This method is used to update the office Action Document
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#update(java.lang
     * .String, java.lang.String, byte[], java.util.Map)
     */
    @Override
    public TmngAlfResponse update(final String id, final String fileName, final ContentItem content,
            final Map<String, Serializable> properties) {
        return updateDocument(id, fileName, content, TradeMarkModel.MARK_QNAME, properties, MarkDoc.class);
    }

    /*
     * (non-Javadoc) This method is used to retrieve the content of an office
     * action document.
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveContent
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ContentReader retrieveContent(String id, String originalFileName, String versionNumber) {
        ContentReader contentReader = null;
        String fileNameDataModFix = null;
        try {
            contentReader = retrieveContent(cmsNodeLocator, TradeMarkModel.MARK_QNAME, id, originalFileName, versionNumber);
        } catch (TmngCmsException.DocumentDoesNotExistException e) {
            log.debug(e.getLocalizedMessage(), e);
            fileNameDataModFix = getMarkFileNameWithDataModFix(originalFileName, id);
            try {
                contentReader = retrieveContent(cmsNodeLocator, TradeMarkModel.MARK_QNAME, id, fileNameDataModFix, versionNumber);
            } catch (TmngCmsException.DocumentDoesNotExistException e1) {
                throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + originalFileName + "\" Not Found",
                        e1);
            }
        }
        if (null != contentReader) {
            contentReader = transformToPngService.transformImageToPNG(originalFileName, contentReader);
        }
        return contentReader;
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
    public ContentReader retrieveContent(String id, String originalFileName, String versionNumber, CmsDataFilter dataFilter) {
        ContentReader contentReader = null;
        String fileNameDataModFix = null;
        try {
            contentReader = retrieveContent(cmsNodeLocator, TradeMarkModel.MARK_QNAME, id, originalFileName, versionNumber,
                    dataFilter);
        } catch (TmngCmsException.DocumentDoesNotExistException e) {
            log.debug(e.getLocalizedMessage(), e);
            fileNameDataModFix = getMarkFileNameWithDataModFix(originalFileName, id);
            try {
                contentReader = retrieveContent(cmsNodeLocator, TradeMarkModel.MARK_QNAME, id, fileNameDataModFix, versionNumber,
                        dataFilter);
            } catch (TmngCmsException.DocumentDoesNotExistException e1) {
                throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + originalFileName + "\" Not Found",
                        e1);
            }
        }
        if (null != contentReader) {
            contentReader = transformToPngService.transformImageToPNG(originalFileName, contentReader);
        }
        return contentReader;
    }

    /*
     * (non-Javadoc) This method is used to retrieve the metadata of office
     * action document.
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public byte[] retrieveMetadata(String id, String originalFileName, String versionNumber) {
        byte[] metadata = null;
        String fileNameDataModFix = null;
        try {
            metadata = retrieveMetadata(cmsNodeLocator, id, originalFileName, versionNumber, TradeMarkModel.MARK_QNAME,
                    MarkDoc.class);
        } catch (TmngCmsException.DocumentDoesNotExistException e) {
            log.debug(e.getLocalizedMessage(), e);
            fileNameDataModFix = getMarkFileNameWithDataModFix(originalFileName, id);
            try {
                metadata = retrieveMetadata(cmsNodeLocator, id, fileNameDataModFix, versionNumber, TradeMarkModel.MARK_QNAME,
                        MarkDoc.class);
            } catch (TmngCmsException.DocumentDoesNotExistException e1) {
                throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + originalFileName + "\" Not Found",
                        e1);
            }
        }
        return metadata;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.CmsCommonService#retrieveMetadata
     * (java.lang.String, java.lang.String, java.lang.String,
     * gov.uspto.trademark.cms.repo.filters.CmsDataFilter)
     */
    @Override
    public byte[] retrieveMetadata(String id, String originalFileName, String versionNumber, CmsDataFilter dataFilter) {
        byte[] metadata = null;
        String fileNameDataModFix = null;
        try {
            metadata = retrieveMetadata(cmsNodeLocator, id, originalFileName, versionNumber, TradeMarkModel.MARK_QNAME,
                    MarkDoc.class, dataFilter);
        } catch (TmngCmsException.DocumentDoesNotExistException e) {
            log.debug(e.getLocalizedMessage(), e);
            fileNameDataModFix = getMarkFileNameWithDataModFix(originalFileName, id);
            try {
                metadata = retrieveMetadata(cmsNodeLocator, id, fileNameDataModFix, versionNumber, TradeMarkModel.MARK_QNAME,
                        MarkDoc.class, dataFilter);
            } catch (TmngCmsException.DocumentDoesNotExistException e1) {
                throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + originalFileName + "\" Not Found",
                        e1);
            }
        }
        return metadata;
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
        return update(id, fileName, null, properties);
    }

    //
    public String getMarkFileNameWithDataModFix(String fileName, String id) {
        String newFileName = fileName.toUpperCase();
        if (newFileName.startsWith("USPTO-IMAGE-MARK")) {
            newFileName = "MRK_" + id.substring(TMConstants.SIX) + "." + FilenameUtils.getExtension(fileName);
        } else {
            newFileName = "USPTO-IMAGE-MARK." + FilenameUtils.getExtension(fileName);
        }
        return newFileName;
    }

}
