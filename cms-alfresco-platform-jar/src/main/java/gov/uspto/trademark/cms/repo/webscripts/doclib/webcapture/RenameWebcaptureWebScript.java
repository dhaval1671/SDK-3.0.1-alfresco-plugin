package gov.uspto.trademark.cms.repo.webscripts.doclib.webcapture;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.AccessLevelRuleViolationException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.CmsValidator;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.WebcaptureService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.WebcaptureResponse;

/**
 *
 * @author stank
 */
public class RenameWebcaptureWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOGGER. */
    private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());

    @Autowired
    @Qualifier(value = "WebcaptureImplService")
    private WebcaptureService webcaptureService;


    @Override
    public void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webResponse) {
        log.info("### Executing " + Thread.currentThread().getStackTrace()[TMConstants.ONE].getMethodName() + " ####");
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            final String userid = urlParameters.get(TMConstants.USER_ID);
            final String oldFileName = urlParameters.get(FILE_NAME_PARAM);
            String metadata = webScriptRequest.getParameter(METADATA_QS_PARAM);
            Map<String, Serializable> metadataMap = CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(metadata);
            final String newFileName = (String) metadataMap.get(TMConstants.NEW_FILENAME);
            
            if(StringUtils.isBlank(newFileName)){
                throw new TmngCmsException.PropertyNotFoundException("Incoming JSON Property '" + TMConstants.NEW_FILENAME + "' or it's value" + " Not Found"); 
            }
            
            // Invoke the doctype specific service which creates the document
            WebcaptureResponse response = webcaptureService.renameWebcapture(userid, oldFileName, newFileName);

            // Construct and Send Response
            if (response != null) {
                webResponse.setStatus(Status.STATUS_OK);
                webResponse.setContentType(TMConstants.APPLICATION_JSON);
                webResponse.getOutputStream().write(WebScriptHelper.toJson(response));
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Rename webcapture request failed.");
            }

        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (AccessLevelRuleViolationException e) {
            throw new TmngCmsException.CMSWebScriptException(e.getStatusCode(), e.getMessage(), e);
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