package gov.uspto.trademark.cms.repo.webscripts.legacy.ticrs;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.DocumentServiceFactory;
import gov.uspto.trademark.cms.repo.services.util.ContentItem;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This class is used to create all the document types.
 * 
 * @author vnondapaka
 *
 */
public class LegacyTicrsCreateWebScript extends AbstractCmsCommonWebScript {

    private static final Logger log = LoggerFactory.getLogger(LegacyTicrsCreateWebScript.class);
    /** The Constant FILE_NAME_PARAM. */

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
        FormField content = null;
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            String serialNumber = urlParameters.get(ID_PARAMETER);
            String docType = urlParameters.get(DOC_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String metadata = webScriptRequest.getParameter(METADATA_QS_PARAM);

            // Parse the Json and if that fails system throws bad request.
            Map<String, Serializable> metadataMap = CmsValidator.parseJsonReturningMapWithCaseSensitiveKeys(metadata);
            CmsValidator.validateProperties(metadataMap);
            // get the content from the request
            WrappingWebScriptRequest wrappingWebScriptRequest = (WrappingWebScriptRequest) webScriptRequest;
            WebScriptServletRequest webScriptServletRequest = (WebScriptServletRequest) wrappingWebScriptRequest.getNext();
            content = webScriptServletRequest.getFileField(CONTENT);

            log.debug("Create " + fileName + " request received.");
            if (content == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Please verify 'content' parameter, it should NOT be empty.");
            }

            // Invoke the doctype specific service which creates the document
            TmngAlfResponse response = documentServiceFactory.getDocumentService(documentServiceMap.get(docType))
                    .create(serialNumber, fileName, ContentItem.getInstance(content), metadataMap);

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
        } finally {
            /*
             * The temporary file that is used to stream in the content is not
             * being properly cleaned up. So this logic should take care of
             * that.
             */
            if (content != null && content.getIsFile()) {
                content.cleanup();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.webscripts.CmsCommonWebScript#
     * validateRequest
     * (org.springframework.extensions.webscripts.WebScriptRequest)
     */
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        super.validateRequest(webScriptRequest);
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String errorMsg;
        // move the error messages to a property file
        // see if we can implement the configurable validation framework
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))
                ? "Please verify 'fileName' parameter it should NOT be blank." : "";
        errorMsg = StringUtils.isBlank(webScriptRequest.getParameter(METADATA_QS_PARAM))
                ? "Please verify 'metadata' parameter it should NOT be blank." : errorMsg;
        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }
}
