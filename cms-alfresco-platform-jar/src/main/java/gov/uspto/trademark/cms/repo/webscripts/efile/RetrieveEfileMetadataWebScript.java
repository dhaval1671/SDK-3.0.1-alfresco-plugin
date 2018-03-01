package gov.uspto.trademark.cms.repo.webscripts.efile;

import java.io.IOException;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.identifiers.BehaviorIdStrategy;
import gov.uspto.trademark.cms.repo.services.EfileService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This is the common web script used to retrieve metadata for all the
 * documentsR.
 *
 * @author vnondapaka
 */
public class RetrieveEfileMetadataWebScript extends AbstractCmsCommonWebScript {

    /** The efile service. */
    @Autowired
    private EfileService efileService;

    /** The global id. */
    @Autowired
    private BehaviorIdStrategy globalId;

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
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String versionNumber = webScriptRequest.getParameter(VERSION_NUMBER_QS_PARAM);

            byte[] metadata = efileService.retrieveMetadata(id, fileName, versionNumber);
            if (metadata == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "Metadata NOT found.");
            }
            if (metadata.length == 0) {
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

    /**
     * This method validates the request parameters.
     *
     * @param webScriptRequest
     *            the web script request
     */
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        String id = WebScriptHelper.getUrlParameters(webScriptRequest).get(ID_PARAMETER);
        try {
            globalId.isValid(id);
        } catch (TmngCmsException.InvalidGlobalIdException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}