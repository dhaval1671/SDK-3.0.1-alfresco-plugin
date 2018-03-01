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
import org.springframework.extensions.webscripts.WrappingWebScriptRequest;
import org.springframework.extensions.webscripts.servlet.FormData.FormField;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRequest;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.CMSRuntimeException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.PublicationServiceFactory;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.publication.AbstractPublicationBaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This is the update webscript used to update the documents.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class UpdatePublicationWebScript extends AbstractCmsCommonWebScript {

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
            String serialNumber = urlParameters.get(ID_PARAMETER);
            String docType = urlParameters.get(PUB_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);

            // get the content from the request
            WrappingWebScriptRequest wrappingWebScriptRequest = (WrappingWebScriptRequest) webScriptRequest;
            WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) wrappingWebScriptRequest.getNext();
            FormField content = webScriptServletRequest.getFileField(CONTENT);

            if (content == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Please verify 'content' parameter, it should NOT be empty.");
            }

            String bean = publicationServiceMap.get(docType);
            AbstractPublicationBaseCommonService cmsCommonSrvc = (AbstractPublicationBaseCommonService) publicationServiceFactory
                    .getPublicationService(bean);

            // Invoke the doctype specific service which creates the document
            TmngAlfResponse response = cmsCommonSrvc.update(serialNumber, fileName, ContentItem.getInstance(content), null);
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

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.webscripts.CmsCommonWebScript#
     * validateRequest(org.springframework.extensions.webscripts.
     * WebScriptRequest)
     */
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {

        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String errorMsg = null;
        // move the error messages to a property file
        // see if we can implement the configurable validation framework
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))
                ? "Please verify 'fileName' parameter it should NOT be blank.." : "";

        String docType = urlParameters.get(PUB_TYPE);
        errorMsg = StringUtils.isBlank(docType) ? "'docType' is mandatory parameter." : errorMsg;

        errorMsg = StringUtils.isNotBlank(webScriptRequest.getParameter(METADATA_QS_PARAM))
                ? "'metadata' request parameter should NOT be supplied. Please remove it and re-try." : errorMsg;

        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }

}
