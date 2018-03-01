package gov.uspto.trademark.cms.repo.webscripts.doclib.webcapture;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.WrappingWebScriptRequest;
import org.springframework.extensions.webscripts.servlet.FormData.FormField;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRequest;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.WebcaptureService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.WebcaptureResponse;

/**
 * A webscript to create user Content inside Document_Library -> Webcapture folder
 * @author stank
 */
public class CreateWebcaptureContentWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());

    @Autowired
    @Qualifier(value = "WebcaptureImplService")
    private WebcaptureService webcaptureService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    public void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        log.debug("### Executing " + Thread.currentThread().getStackTrace()[TMConstants.ONE].getMethodName() + " ####");
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        final String userid = urlParameters.get(TMConstants.USER_ID);
        final String fileName = urlParameters.get(FILE_NAME_PARAM);

        WrappingWebScriptRequest wrappingWebScriptRequest = (WrappingWebScriptRequest) webScriptRequest;
        WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) wrappingWebScriptRequest.getNext();
        FormField content = webScriptServletRequest.getFileField(CONTENT);

        WebcaptureResponse response = (WebcaptureResponse) webcaptureService.createWebcapture(userid, fileName, content);

        // Construct and Send Response
        if (response != null) {
            webScriptResponse.setStatus(Status.STATUS_CREATED);
            webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
            try {
                response.setDocumentId(
                        TMConstants.FORWARD_SLASH + TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase()
                                + TMConstants.FORWARD_SLASH + userid + TMConstants.FORWARD_SLASH + fileName);
                webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(response));
            } catch (IOException e) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
            }
        } else {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Create request failed.");
        }
    }

    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        // validate user id and path.
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String errorMsg;
        errorMsg = StringUtils.isBlank(urlParameters.get(TMConstants.USER_ID))? "Please verify 'userid' parameter it should NOT be blank." : "";
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))? "Please verify 'fileName' parameter it should NOT be blank." : errorMsg;

        WrappingWebScriptRequest wrappingWebScriptRequest = (WrappingWebScriptRequest) webScriptRequest;
        WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) wrappingWebScriptRequest.getNext();
        FormField content = webScriptServletRequest.getFileField(CONTENT);
        errorMsg = content == null ? "Please verify 'content' parameter, it should NOT be empty." : errorMsg;

        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }

    }

}