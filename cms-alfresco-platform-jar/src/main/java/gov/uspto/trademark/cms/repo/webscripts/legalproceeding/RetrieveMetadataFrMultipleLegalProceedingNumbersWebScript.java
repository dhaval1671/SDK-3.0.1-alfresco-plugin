package gov.uspto.trademark.cms.repo.webscripts.legalproceeding;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import gov.uspto.trademark.cms.repo.identifiers.BehaviorIdStrategy;
import gov.uspto.trademark.cms.repo.services.LegalProceedingService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.LegalProceedingDocumentMetadataResponse;

/**
 * Returns metadata for all the documents present in the supplied list of proceeding numbers.
 * <p/>
 * Created by Sanjay Tank {linkedin.com/in/sanjaytaunk} on May/26/2017.
 */
public class RetrieveMetadataFrMultipleLegalProceedingNumbersWebScript extends AbstractCmsCommonWebScript {

    private static final Logger log = LoggerFactory.getLogger(RetrieveMetadataFrMultipleLegalProceedingNumbersWebScript.class);
    /** FILTER KEY PARAM. */
    private static final String FILTER_KEY_QS_PARAM = "filterKey";

    /** FILTER VALUE PARAM. */
    private static final String FILTER_VALUE_QS_PARAM = "filterValue";

    /** TYPE. */
    private static final String TYPE = "LP";

    /** LOG. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveMetadataFrMultipleLegalProceedingNumbersWebScript.class);

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
            final String requestBody = getContentAsString(webScriptRequest);
            String[] arrayOfProceedingNumbers = null;
            // Check for request body
            if (StringUtils.isBlank(requestBody)) {
                log.error("Missing metadata ");
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_GATEWAY, "Missing metadata");
            } else {
                try {
                    arrayOfProceedingNumbers = JacksonHelper.unMarshall(requestBody, String[].class);
                    for(String proceedingNumber: arrayOfProceedingNumbers){
                        proceedingNumberId.isValid(proceedingNumber);
                    }
                } catch (JsonParseException e) {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                } catch (JsonMappingException e) {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                } catch (TmngCmsException.InvalidGlobalIdException e) {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                } catch (IOException e) {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
            }            

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
            List<LegalProceedingDocumentMetadataResponse> documentMetadataList = legalProceedingService.retrieveMetadataFromMultipleProceedingNumbers(arrayOfProceedingNumbers, accessLevelFilter);
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

    }

}