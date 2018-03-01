package gov.uspto.trademark.cms.repo.webscripts.efile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.identifiers.BehaviorIdStrategy;
import gov.uspto.trademark.cms.repo.services.EfileService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.EfileDocumentMetadataResponse;

/**
 * Returns a list of all Efile documents with complete metadata for a given
 * serial number.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class RetrieveAllEfileDocsFrCaseWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(RetrieveAllEfileDocsFrCaseWebScript.class);

    /** The Constant EFILE_ID. */
    private final static String EFILE_ID = "id";

    @Autowired
    @Qualifier(value = "globalId")
    protected BehaviorIdStrategy efileGlobalId;

    /** The efile service. */
    private EfileService efileService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) {
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            String efileId = urlParameters.get(EFILE_ID);
            List<EfileDocumentMetadataResponse> caseDocumentList = efileService.getDocumentList(efileId);
            webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
            webScriptResponse.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(caseDocumentList));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (TmngCmsException.SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * This method validates the request parameters.
     *
     * @param webScriptRequest
     *            the web script request
     */
    @Override
    public void validateRequest(WebScriptRequest webScriptRequest) {
        String id = WebScriptHelper.getUrlParameters(webScriptRequest).get(ID_PARAMETER);
        try {
            efileGlobalId.isValid(id);
        } catch (TmngCmsException.InvalidGlobalIdException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * Sets the efile service.
     *
     * @param efileService
     *            the new efile service
     */
    public void setEfileService(EfileService efileService) {
        this.efileService = efileService;
    }

}