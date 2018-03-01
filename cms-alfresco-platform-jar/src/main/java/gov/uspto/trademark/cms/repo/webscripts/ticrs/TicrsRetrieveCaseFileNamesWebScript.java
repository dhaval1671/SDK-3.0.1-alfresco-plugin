package gov.uspto.trademark.cms.repo.webscripts.ticrs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;

/**
 * The Class TicrsRetrieveCaseFileNamesWebScript.
 */
public class TicrsRetrieveCaseFileNamesWebScript extends AbstractCmsCommonWebScript {

    private static final Logger log = LoggerFactory.getLogger(TicrsRetrieveCaseFileNamesWebScript.class);

    private CaseService caseService;

    public void setCaseService(CaseService caseService) {
        this.caseService = caseService;
    }

    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String id = urlParameters.get(ID_PARAMETER);
        List<String> fileNameList = caseService.retrieveTicrsCaseFileNames(id);
        byte[] fileNameByteArray = JacksonHelper.generateClientJsonFrDTO(fileNameList);
        if (fileNameByteArray == null) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "TICRS file names NOT found.");
        } else {
            webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
            try {
                webScriptResponse.getOutputStream().write(fileNameByteArray);
            } catch (IOException e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage(), e);
                }
            }
        }
    }
}