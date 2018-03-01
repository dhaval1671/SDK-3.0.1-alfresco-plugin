package gov.uspto.trademark.cms.repo.webscripts.publication;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.error.AlfrescoRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.CMSRuntimeException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CmsCommonService;
import gov.uspto.trademark.cms.repo.services.PublicationServiceFactory;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This is the update webscript used to delete the documents.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class DeletePublicationWebScript extends AbstractCmsCommonWebScript {

    /** The publication service map. */
    @Resource(name = "publicationServiceMap")
    private Map<String, String> publicationServiceMap;

    /** The publication service factory. */
    @Autowired
    @Qualifier(value = "publicationServiceFactory")
    private PublicationServiceFactory publicationServiceFactory;

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
            String cmsId = urlParameters.get(ID_PARAMETER);
            String docType = urlParameters.get(PUB_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);

            CmsCommonService pubCommonSrvc = publicationServiceFactory.getPublicationService(publicationServiceMap.get(docType));

            // Invoke the doctype specific service which creates the document
            TmngAlfResponse response = pubCommonSrvc.delete(cmsId, fileName);
            if (response != null) {
                webScriptResponse.setStatus(Status.STATUS_OK);
                webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
                webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(response));
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Update request failed.");
            }
        } catch (FileCheckFailedException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.FORBIDDEN, e.getMessage(), e);
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

    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String errorMsg = null;

        errorMsg = StringUtils.isBlank(urlParameters.get(ID_PARAMETER)) ? "Serial Number and TrademarkId both found blank" : "";

        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))
                ? "Please verify 'fileName' parameter it should NOT be blank.." : "";

        String docType = urlParameters.get(PUB_TYPE);
        errorMsg = StringUtils.isBlank(docType) ? "'docType' is mandatory parameter." : errorMsg;

        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }

}
