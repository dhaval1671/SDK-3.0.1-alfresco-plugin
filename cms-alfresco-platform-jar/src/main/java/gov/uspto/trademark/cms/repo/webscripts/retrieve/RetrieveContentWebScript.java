package gov.uspto.trademark.cms.repo.webscripts.retrieve;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.filters.AccessLevelFilter;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.DocumentServiceFactory;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This is the common web script used to retrieve the content for all documents.
 *
 * @author vnondapaka
 */
public class RetrieveContentWebScript extends AbstractCmsCommonWebScript {

    /** The document service map. */
    @Resource(name = "documentServiceMap")
    private Map<String, String> documentServiceMap;

    /** The document service factory. */
    @Autowired
    @Qualifier(value = "documentServiceFactory")
    private DocumentServiceFactory documentServiceFactory;

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
            String accessLevelValue = webScriptRequest.getParameter(AccessLevel.ACCESS_LEVEL_KEY);

            AccessLevelFilter accessLevelFilter = new AccessLevelFilter(accessLevelValue);
            ContentReader contentReader = documentServiceFactory.getDocumentService(documentServiceMap.get(docType))
                    .retrieveContent(id, fileName, versionNumber, accessLevelFilter);
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
            throw new WebScriptException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e);
        } catch (AlfrescoRuntimeException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
