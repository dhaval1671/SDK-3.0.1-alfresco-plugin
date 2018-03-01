package gov.uspto.trademark.cms.repo.webscripts.update;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

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
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.CmsValidator;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.DocumentServiceFactory;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This is the common web script used to update metadata.
 *
 * @author vnondapaka
 */
public class UpdateMetadataWebScript extends UpdateWebScript {

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
     * @see gov.uspto.trademark.cms.repo.webscripts.update.UpdateWebScript#
     * executeService(org.springframework.extensions.webscripts.
     * WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            String serialNumber = urlParameters.get(ID_PARAMETER);
            String docType = urlParameters.get(DOC_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String metadata = webScriptRequest.getParameter(METADATA_QS_PARAM);

            // Parse the Json and if that fails system throws bad request.
            Map<String, Serializable> metadataMap = CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(metadata);
            // Invoke the doctype specific service which creates the document
            TmngAlfResponse response = documentServiceFactory.getDocumentService(documentServiceMap.get(docType))
                    .updateMetadata(serialNumber, fileName, metadataMap);
            if (response != null) {
                // Construct and Send Response
                webScriptResponse.setStatus(Status.STATUS_OK);
                webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
                webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(response));
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Update request failed.");
            }
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
        } catch (Exception e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
