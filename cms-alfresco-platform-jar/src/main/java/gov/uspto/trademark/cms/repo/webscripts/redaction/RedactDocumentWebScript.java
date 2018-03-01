package gov.uspto.trademark.cms.repo.webscripts.redaction;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
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
import gov.uspto.trademark.cms.repo.TmngCmsException.CMSRuntimeException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.RedactionLevel;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.CmsValidator;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CmsRedactionService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This is the common web script used for all future services.
 * 
 * @author vnondapaka
 *
 */
public class RedactDocumentWebScript extends AbstractCmsCommonWebScript {

    /** The Constant REDACTION_LEVEL. */
    private static final String REDACTION_LEVEL = "redactionLevel";

    /** The cms redaction service. */
    @Autowired
    @Qualifier(value = "documentRedactionService")
    private CmsRedactionService cmsRedactionService;

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
            String metadata = webScriptRequest.getParameter(METADATA_QS_PARAM);

            // Parse the Json and if that fails system throws bad request.
            Map<String, Serializable> metadataMap = CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(metadata);

            String redactionLevel = (String) metadataMap.get(REDACTION_LEVEL);
            if (redactionLevel == null || RedactionLevel.getRedactionLevel(redactionLevel) == null
                    || RedactionLevel.getRedactionLevel(redactionLevel).toString().equals(RedactionLevel.NONE.toString())) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Redact Level is mandatory and should be either FULL or PARTIAL");
            }

            WrappingWebScriptRequest wrappingWebScriptRequest = (WrappingWebScriptRequest) webScriptRequest;
            WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) wrappingWebScriptRequest.getNext();
            FormField content = webScriptServletRequest.getFileField(CONTENT);
            if (content == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Please verify 'content' parameter, it should NOT be empty.");
            }
            // Invoke the doctype specific service which creates the document
            TmngAlfResponse postResponse = cmsRedactionService.redactDocument(id, fileName, ContentItem.getInstance(content),
                    docType, metadataMap);
            if (postResponse != null) {
                webScriptResponse.setStatus(Status.STATUS_OK);
                webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
                webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(postResponse));
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Redact request failed.");
            }
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (DocumentDoesNotExistException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (CMSRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (AlfrescoRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

}
