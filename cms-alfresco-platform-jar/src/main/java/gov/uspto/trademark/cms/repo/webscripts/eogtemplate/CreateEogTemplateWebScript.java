/**
 * 
 */
package gov.uspto.trademark.cms.repo.webscripts.eogtemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.WrappingWebScriptRequest;
import org.springframework.extensions.webscripts.servlet.FormData.FormField;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRequest;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.CMSRuntimeException;
import gov.uspto.trademark.cms.repo.TmngCmsException.CorruptedDocIdException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.CmsValidator;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.EogTemplateDocService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This class is used
 * 
 * @author vnondapaka
 *
 */
public class CreateEogTemplateWebScript extends AbstractCmsCommonWebScript {

    @Autowired
    private EogTemplateDocService eogTemplateDocService;

    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            String id = urlParameters.get(ID_PARAMETER);
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String metadata = webScriptRequest.getParameter(METADATA_QS_PARAM);

            // Parse the Json and if that fails system throws bad request.
            Map<String, Serializable> metadataMap = CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(metadata);

            // get the content from the request
            WrappingWebScriptRequest wrappingWebScriptRequest = (WrappingWebScriptRequest) webScriptRequest;
            WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) wrappingWebScriptRequest.getNext();
            FormField content = webScriptServletRequest.getFileField(CONTENT);

            if (content == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Please verify 'content' parameter, it should NOT be empty.");
            }

            // Invoke the doctype specific service which creates the document
            TmngAlfResponse response = eogTemplateDocService.create(id, fileName, ContentItem.getInstance(content), metadataMap);

            // Construct and Send Response
            if (response != null) {
                webScriptResponse.setStatus(Status.STATUS_CREATED);
                webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
                webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(response));
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Create request failed.");
            }

        } catch (DuplicateChildNodeNameException dcnne) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.CONFLICT, dcnne.getMessage(), dcnne);
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
        } catch (CorruptedDocIdException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);

        String idParam = urlParameters.get(ID_PARAMETER);
        if (StringUtils.isBlank(idParam)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Id should NOT be blank");
        }
        String errorMsg = null;
        // move the error messages to a property file
        // see if we can implement the configurable validation framework
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))
                ? "Please verify 'fileName' parameter it should NOT be blank.." : "";
        errorMsg = StringUtils.isBlank(webScriptRequest.getParameter(METADATA_QS_PARAM))
                ? "Please verify 'metadata' parameter it should NOT be blank." : errorMsg;
        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }

}
