package gov.uspto.trademark.cms.repo.webscripts.publication;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.TmngCmsException.DocumentDoesNotExistException;
import gov.uspto.trademark.cms.repo.TmngCmsException.FileCheckFailedException;
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog;
import gov.uspto.trademark.cms.repo.services.PublicationServiceFactory;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.publication.AbstractPublicationBaseCommonService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * This is the common web script used to retrieve the content for all documents.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class RetrievePublicationContentWebScript extends AbstractCmsCommonWebScript {

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
            String id = urlParameters.get(ID_PARAMETER);
            String docType = urlParameters.get(PUB_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String versionNumber = webScriptRequest.getParameter(VERSION_NUMBER_QS_PARAM);

            AbstractPublicationBaseCommonService absPubSrvc = (AbstractPublicationBaseCommonService) publicationServiceFactory
                    .getPublicationService(publicationServiceMap.get(docType));
            ContentReader contentReader = absPubSrvc.retrieveContent(id, fileName, versionNumber);
            String msg = "Filename Not found: " + fileName;
            if (StringUtils.isNotBlank(versionNumber)) {
                msg = msg + " for version " + versionNumber;
            }
            if (contentReader == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, msg);
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

        if (Eog.TYPE.equalsIgnoreCase(docType)) {
            String cmsId = urlParameters.get(ID_PARAMETER);
            String strMsg = "Incoming date, '" + cmsId + "' is NOT compliant for the Date instance of format "
                    + WebScriptHelper.EOG_DATE_FORMAT;
            errorMsg = !(WebScriptHelper.isValidEogDate(cmsId)) ? strMsg : errorMsg;
        }

        errorMsg = StringUtils.isNotBlank(webScriptRequest.getParameter(METADATA_QS_PARAM))
                ? "'metadata' request parameter should NOT be supplied. Please remove it and re-try." : errorMsg;

        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }
    }

}
