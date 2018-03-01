package gov.uspto.trademark.cms.repo.webscripts.legacy.ticrs;

import java.io.IOException;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.LegacyTicrsService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * 
 * The Class TicrsRetrieveCaseFileNamesWebScript.
 * 
 */
public class LegacyTicrsRetrieveContentWebScript extends AbstractCmsCommonWebScript {

    private static final String VERSION_FOLDER = "versionFolder";
    @Autowired
    @Qualifier(value = "LegacyTicrsImplService")
    private LegacyTicrsService legacyTicrsService;

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
            String docType = urlParameters.get(DOC_TYPE);
            String versionFolder = urlParameters.get(VERSION_FOLDER);
            String fileName = urlParameters.get(FILE_NAME_PARAM);

            ContentReader contentReader = legacyTicrsService.retrieveLegacyTicrsDocContent(docType, versionFolder, fileName);
            if (contentReader == null) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "Filename Not found: " + fileName);
            }
            webScriptResponse.setContentType(contentReader.getMimetype());
            webScriptResponse.setContentEncoding(contentReader.getEncoding());
            webScriptResponse.addHeader(HttpHeaders.CONTENT_LENGTH, "" + contentReader.getSize());
            webScriptResponse.addHeader("Content-Disposition", "inline; filename=" + fileName);
            FileCopyUtils.copy(contentReader.getContentInputStream(), webScriptResponse.getOutputStream());
        } catch (ContentIOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IOException e) {
            throw new WebScriptException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e);
        }
    }
    
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        // validate user id and path.
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String errorMsg;
        errorMsg = StringUtils.isBlank(urlParameters.get(DOC_TYPE))? "Please verify '"+DOC_TYPE+"' parameter it should NOT be blank." : "";
        errorMsg = StringUtils.isBlank(urlParameters.get(VERSION_FOLDER))? "Please verify '"+VERSION_FOLDER+"' parameter it should NOT be blank." : errorMsg;
        errorMsg = StringUtils.isBlank(urlParameters.get(FILE_NAME_PARAM))? "Please verify '"+FILE_NAME_PARAM+"' parameter it should NOT be blank." : errorMsg;
        if (StringUtils.isNotBlank(errorMsg)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, errorMsg);
        }

    }    
}