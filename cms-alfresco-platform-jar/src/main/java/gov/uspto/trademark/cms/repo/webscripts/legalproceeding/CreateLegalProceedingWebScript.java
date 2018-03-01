package gov.uspto.trademark.cms.repo.webscripts.legalproceeding;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
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
import gov.uspto.trademark.cms.repo.TmngCmsException.CorruptedDocIdException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.CmsValidator;
import gov.uspto.trademark.cms.repo.helpers.LegalProceedingValidator;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.LegalProceedingServiceFactory;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.legalproceeding.AbstractLegalProceedingBaseCommonService;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This class is used to create all the document types.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */
public class CreateLegalProceedingWebScript extends AbstractCmsCommonWebScript {

    /** The Constant FILE_NAME_PARAM. */

    @Resource(name = "legalProceedingServiceMap")
    private Map<String, String> legalProceedingServiceMap;

    /** The legal-proceeding service factory. */
    @Autowired
    @Qualifier(value = "legalProceedingServiceFactory")
    private LegalProceedingServiceFactory legalProceedingServiceFactory;

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
            String docType = urlParameters.get(DOC_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String metadata = webScriptRequest.getParameter(METADATA_QS_PARAM);

            // Parse the Json and if that fails system throws bad request.
            Map<String, Serializable> metadataMap = CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(metadata);
            LegalProceedingValidator.validateProperties(metadataMap);

            // get the content from the request
            WrappingWebScriptRequest wrappingWebScriptRequest = (WrappingWebScriptRequest) webScriptRequest;
            WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) wrappingWebScriptRequest.getNext();
            FormField content = webScriptServletRequest.getFileField(CONTENT);

            if (content == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Please verify 'content' parameter, it should NOT be empty.");
            }

            AbstractLegalProceedingBaseCommonService madridCommonSrvc = (AbstractLegalProceedingBaseCommonService) legalProceedingServiceFactory
                    .getLegalProceedingService(legalProceedingServiceMap.get(docType));

            // Invoke the doctype specific service which creates the document
            TmngAlfResponse response = madridCommonSrvc.create(cmsId, fileName, ContentItem.getInstance(content), metadataMap);

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
        String idParam = WebScriptHelper.getUrlParameters(webScriptRequest).get(ID_PARAMETER);

        String errorMsg = null;

        if ((idParam != null) && WebScriptHelper.isBadCaseSerialNumber(idParam)) {
            // move error messages to a property file
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Please verify incoming proceeding number: '"
                    + ID_PARAMETER + "', it has to match, greater than or equal to eight character alpha numeric pattern.");
        }

        // move the error messages to a property file
        // see if we can implement the configurable validation framework
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))
                ? "Please verify 'fileName' parameter it should NOT be blank.." : "";

        String docType = urlParameters.get(DOC_TYPE);
        errorMsg = StringUtils.isBlank(docType) ? "'docType' is mandatory parameter." : errorMsg;

        errorMsg = StringUtils.isBlank(webScriptRequest.getParameter(METADATA_QS_PARAM))
                ? "Please verify 'metadata' parameter it should NOT be blank." : errorMsg;

        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }
}
