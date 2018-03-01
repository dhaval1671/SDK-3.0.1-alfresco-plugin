package gov.uspto.trademark.cms.repo.webscripts.madrid;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.MadridServiceFactory;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.madridib.AbstractMadridBaseCommonService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This is the common web script used to retrieve the content for all documents.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class RetrieveMadridContentWebScript extends AbstractCmsCommonWebScript {

    /** The publication service map. */
    @Resource(name = "madridServiceMap")
    private Map<String, String> madridServiceMap;

    /** The publication service factory. */
    @Autowired
    @Qualifier(value = "madridServiceFactory")
    private MadridServiceFactory madridServiceFactory;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.CmsCommonWebScript#executeService
     * (org.springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            String id = urlParameters.get(ID_PARAMETER);
            String docType = urlParameters.get(DOC_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String versionNumber = webScriptRequest.getParameter(VERSION_NUMBER_QS_PARAM);

            AbstractMadridBaseCommonService absPubSrvc = (AbstractMadridBaseCommonService) madridServiceFactory
                    .getMadridService(madridServiceMap.get(docType));
            ContentReader contentReader = absPubSrvc.retrieveContent(id, fileName, versionNumber);
            String msg = "Filename Not found: " + fileName;
            if (StringUtils.isNotBlank(versionNumber)) {
                msg = msg + " for version " + versionNumber;
            }
            if (contentReader == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, msg);
            }
            webScriptResponse.setContentType(contentReader.getMimetype());
            webScriptResponse.setContentEncoding(contentReader.getEncoding());
            webScriptResponse.addHeader(HttpHeaders.CONTENT_LENGTH, "" + contentReader.getSize());
            webScriptResponse.addHeader("Content-Disposition", "inline; filename=" + fileName);
            FileCopyUtils.copy(contentReader.getContentInputStream(), webScriptResponse.getOutputStream());
        } catch (FileCheckFailedException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        } catch (SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DocumentDoesNotExistException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ContentIOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (AlfrescoRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.webscripts.CmsCommonWebScript#
     * validateRequest
     * (org.springframework.extensions.webscripts.WebScriptRequest)
     */
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String errorMsg = null;
        // move the error messages to a property file
        // see if we can implement the configurable validation framework
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))
                ? "Please verify 'fileName' parameter it should NOT be blank.." : "";

        String docType = urlParameters.get(DOC_TYPE);
        errorMsg = StringUtils.isBlank(docType) ? "'docType' is mandatory parameter." : errorMsg;

        errorMsg = StringUtils.isNotBlank(webScriptRequest.getParameter(METADATA_QS_PARAM))
                ? "'metadata' request parameter should NOT be supplied. Please remove it and re-try." : errorMsg;

        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }

}
