package gov.uspto.trademark.cms.repo.webscripts.redaction;

import java.io.IOException;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.CMSRuntimeException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.RedactionRestoreOriginalException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CmsRedactionService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This webscript class is used to restore the original version for a redacted
 * document.
 * 
 * @author vnondapaka
 *
 */
public class RestoreOriginalWebScript extends AbstractCmsCommonWebScript {

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

            // Invoke the doctype specific service which creates the document
            TmngAlfResponse postResponse = cmsRedactionService.restoreToOriginalVersion(id, fileName, docType);
            if (postResponse != null) {
                // Construct and Send Response
                webScriptResponse.setStatus(Status.STATUS_OK);
                webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
                webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(postResponse));
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Restore Original request failed.");
            }
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (DocumentDoesNotExistException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RedactionRestoreOriginalException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (CMSRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AlfrescoRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}
