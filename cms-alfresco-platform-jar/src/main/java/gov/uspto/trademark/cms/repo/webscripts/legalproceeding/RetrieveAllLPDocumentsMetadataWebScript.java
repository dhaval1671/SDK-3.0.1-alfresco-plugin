package gov.uspto.trademark.cms.repo.webscripts.legalproceeding;

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
import gov.uspto.trademark.cms.repo.TmngCmsException.SerialNumberNotFoundException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.filters.AccessLevelFilter;
import gov.uspto.trademark.cms.repo.filters.CmsDataFilter;
import gov.uspto.trademark.cms.repo.filters.DocumentMetadataFilter;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.identifiers.BehaviorIdStrategy;
import gov.uspto.trademark.cms.repo.services.LegalProceedingService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.LegalProceedingDocumentMetadataResponse;

/**
 * Returns a list of all documents with complete metadata for a given proceeding
 * number number
 * <p/>
 * Created by Sanjay Tank {linkedin.com/in/sanjaytaunk} on 01/17/2017.
 */
public class RetrieveAllLPDocumentsMetadataWebScript extends AbstractCmsCommonWebScript {

    /** FILTER KEY PARAM. */
    private static final String FILTER_KEY_QS_PARAM = "filterKey";

    /** FILTER VALUE PARAM. */
    private static final String FILTER_VALUE_QS_PARAM = "filterValue";

    /** TYPE. */
    private static final String TYPE = "LP";

    /** LOG. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveAllLPDocumentsMetadataWebScript.class);

    @Autowired
    @Qualifier(value = "proceedingNumberId")
    protected BehaviorIdStrategy proceedingNumberId;

    /** The service registry. */
    @Autowired
    @Qualifier(value = "legalProceedingServiceBase")
    protected LegalProceedingService legalProceedingService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse response) {
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            String serialNumber = urlParameters.get(ID_PARAMETER);

            String filterKey = webScriptRequest.getParameter(FILTER_KEY_QS_PARAM);
            String filterValue = webScriptRequest.getParameter(FILTER_VALUE_QS_PARAM);
            String accessLevelValue = webScriptRequest.getParameter(AccessLevel.ACCESS_LEVEL_KEY);
            CmsDataFilter documentMetadataFilter = null;

            LOGGER.debug("*****filterKey****" + filterKey);
            LOGGER.debug("*****filterValue****" + filterValue);

            if (filterKey != null && filterValue != null) {
                documentMetadataFilter = new DocumentMetadataFilter(filterKey, filterValue, TYPE);
            }

            AccessLevelFilter accessLevelFilter = new AccessLevelFilter(accessLevelValue, documentMetadataFilter);
            List<LegalProceedingDocumentMetadataResponse> documentMetadataList = legalProceedingService
                    .getAllDocumentsProperties(serialNumber, accessLevelFilter);
            response.setContentType(TMConstants.APPLICATION_JSON);
            response.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(documentMetadataList));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (SerialNumberNotFoundException e) {
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
            proceedingNumberId.isValid(id);
        } catch (TmngCmsException.InvalidGlobalIdException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}