package gov.uspto.trademark.cms.repo.webscripts.mark;

import java.util.Map;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.MarkRenditions;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.nodelocator.CaseNodeLocator;
import gov.uspto.trademark.cms.repo.services.BehaviorImageMarkService;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype.MarkDocService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * A webscript to get Mark Rendition Content for a given serial number.
 *
 * @author bgummadi
 */
public class MarkRenditionWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(MarkRenditionWebScript.class);

    /** The image mark service. */
    private BehaviorImageMarkService imageMarkService;

    /** The cms node locator. */
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CaseNodeLocator caseNodeLocator;

    @Autowired
    @Qualifier(value = "MarkDocService")
    MarkDocService markDocService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    public void executeService(WebScriptRequest webRequest, WebScriptResponse webResponse) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webRequest);
        String serialNumber = urlParameters.get(SERIAL_NUMBER_PATH_PARAMETER);
        String originalFileName = urlParameters.get(FILE_NAME_PARAM);
        String renditionName = urlParameters.get(RENDITION_FORMAT);

        try {
            // Validate if rendition name is supported
            MarkRenditions.fromRenditionName(renditionName);
        } catch (IllegalArgumentException e) {
            if (log.isInfoEnabled()) {
                log.info(e.getMessage(), e);
            }
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        NodeRef mrkNodeRef = null;
        String fileNameDataModFix = null;
        try {
            if (StringUtils.equalsIgnoreCase("MOR", originalFileName)) {
                mrkNodeRef = caseNodeLocator.retrieveMarkOfRecordsFileName(serialNumber);
            } else {
                mrkNodeRef = caseNodeLocator.locateNode(serialNumber, originalFileName, TradeMarkModel.MARK_QNAME);
            }
        } catch (TmngCmsException.DocumentDoesNotExistException e) {
            log.debug(e.getLocalizedMessage(), e);
            fileNameDataModFix = markDocService.getMarkFileNameWithDataModFix(originalFileName, serialNumber);
            try {
                mrkNodeRef = caseNodeLocator.locateNode(serialNumber, fileNameDataModFix, TradeMarkModel.MARK_QNAME);
            } catch (TmngCmsException.DocumentDoesNotExistException e1) {
                throw new TmngCmsException.DocumentDoesNotExistException("Requested File \"" + originalFileName + "\" Not Found",
                        e1);
            }
        }

        if (mrkNodeRef == null) {
            if (log.isDebugEnabled()) {
                log.debug("Mark doesn't exist for {}", serialNumber);
            }
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NO_CONTENT, "Mark not found");
        }
        ContentReader contentReaderader = null;
        try {
            contentReaderader = imageMarkService.getMarkRenditionReader(mrkNodeRef, renditionName);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
            contentReaderader = markDocService.retrieveContent(caseNodeLocator, TradeMarkModel.MARK_QNAME, serialNumber,
                    originalFileName, null);
        }
        webResponse.setContentType(contentReaderader.getMimetype());
        webResponse.setContentEncoding(contentReaderader.getEncoding());
        webResponse.addHeader(HttpHeaders.CONTENT_LENGTH, " " + contentReaderader.getSize());
        webResponse.addHeader("Content-Disposition", "inline; filename=" + imageMarkService.getMarkName(mrkNodeRef));

        try {
            FileCopyUtils.copy(contentReaderader.getContentInputStream(), webResponse.getOutputStream());
        } catch (Exception e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Sets the image mark service.
     *
     * @param imageMarkService
     *            the new image mark service
     */
    public void setImageMarkService(BehaviorImageMarkService imageMarkService) {
        this.imageMarkService = imageMarkService;
    }

}