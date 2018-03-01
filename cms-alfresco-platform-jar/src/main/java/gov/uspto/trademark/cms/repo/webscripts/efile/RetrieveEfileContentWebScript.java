package gov.uspto.trademark.cms.repo.webscripts.efile;

import java.io.IOException;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.identifiers.BehaviorIdStrategy;
import gov.uspto.trademark.cms.repo.services.EfileService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This is the common web script used to retrieve the content for all documents.
 *
 * @author vnondapaka
 */
public class RetrieveEfileContentWebScript extends AbstractCmsCommonWebScript {

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

            ContentReader contentReader = efileService.retrieveContent(id, fileName, versionNumber);
            if (contentReader == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "Filename Not found: " + fileName);
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
