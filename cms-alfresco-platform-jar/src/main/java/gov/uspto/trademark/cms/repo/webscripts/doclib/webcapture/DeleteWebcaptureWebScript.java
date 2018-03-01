package gov.uspto.trademark.cms.repo.webscripts.doclib.webcapture;

import java.io.IOException;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.AccessLevelRuleViolationException;
import gov.uspto.trademark.cms.repo.TmngCmsException.CMSRuntimeException;
import gov.uspto.trademark.cms.repo.TmngCmsException.CorruptedDocIdException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.WebcaptureService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.WebcaptureResponse;

/**
 *
 * @author stank
 */
public class DeleteWebcaptureWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOGGER. */
    private static final Logger log = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());

    @Autowired
    @Qualifier(value = "WebcaptureImplService")
    private WebcaptureService webcaptureService;

    @Override
    public void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webResponse) {
        log.debug("### Executing " + Thread.currentThread().getStackTrace()[TMConstants.ONE].getMethodName() + " ####");
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            final String userid = urlParameters.get(TMConstants.USER_ID);
            final String fileName = urlParameters.get(FILE_NAME_PARAM);

            // Invoke the doctype specific service which creates the document
            WebcaptureResponse response = (WebcaptureResponse) webcaptureService.deleteWebcapture(userid, fileName);

            // Construct and Send Response
            if (response != null) {
                webResponse.setStatus(Status.STATUS_OK);
                webResponse.setContentType(TMConstants.APPLICATION_JSON);
                webResponse.getOutputStream().write(WebScriptHelper.toJson(response));
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Delete request failed.");
            }

        } catch (DuplicateChildNodeNameException dcnne) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.CONFLICT, dcnne.getMessage(), dcnne);
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DocumentDoesNotExistException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (CMSRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (AlfrescoRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (CorruptedDocIdException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (AccessLevelRuleViolationException e) {
            throw new TmngCmsException.CMSWebScriptException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }        
        
    }
    
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String errorMsg;
        errorMsg = StringUtils.isBlank(urlParameters.get(TMConstants.USER_ID))? "Please verify 'userid' parameter it should NOT be blank." : "";
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))? "Please verify 'fileName' parameter it should NOT be blank." : errorMsg;

        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }    

}