package gov.uspto.trademark.cms.repo.webscripts.redaction;

import java.io.IOException;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CmsRedactionService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This class is used to retrieve metadata for a redacted document.
 * 
 * @author vnondapaka
 *
 */
public class RetrieveRedactMetadataWebScript extends AbstractCmsCommonWebScript {

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

            byte[] metadata = cmsRedactionService.retrieveRedactionMetadata(id, fileName, docType);

            if (metadata == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "Metadata NOT found.");
            }
            webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
            webScriptResponse.getOutputStream().write(metadata);
        } catch (FileCheckFailedException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        } catch (SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DocumentDoesNotExistException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (AlfrescoRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}
