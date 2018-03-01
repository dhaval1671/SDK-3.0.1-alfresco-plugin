package gov.uspto.trademark.cms.repo.webscripts.cases.fetchalldocs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.CaseDocumentMetadataResponse;

/**
 * Returns a list of all documents with complete metadata for a given serial
 * number
 * <p/>
 * Created by bgummadi on 9/5/2014.
 */
public class CaseDocumentsMetadataWebScript extends AbstractCmsCommonWebScript {

    /** FILTER KEY PARAM. */
    private static final String FILTER_KEY_QS_PARAM = "filterKey";

    /** FILTER VALUE PARAM. */
    private static final String FILTER_VALUE_QS_PARAM = "filterValue";

    /** TYPE. */
    private static final String TYPE = "CASE";

    /** The case service. */
    private CaseService caseService;

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
            String serialNumber = urlParameters.get(SERIAL_NUMBER_PATH_PARAMETER);

            String filterKey = webScriptRequest.getParameter(FILTER_KEY_QS_PARAM);
            String filterValue = webScriptRequest.getParameter(FILTER_VALUE_QS_PARAM);
            String accessLevelValue = webScriptRequest.getParameter(AccessLevel.ACCESS_LEVEL_KEY);
            CmsDataFilter documentMetadataFilter = null;

            if (filterKey != null && filterValue != null) {
                documentMetadataFilter = new DocumentMetadataFilter(filterKey, filterValue, TYPE);
            }
            AccessLevelFilter accessLevelFilter = new AccessLevelFilter(accessLevelValue, documentMetadataFilter);
            List<CaseDocumentMetadataResponse> documentMetadataList = caseService.getAllDocumentsProperties(serialNumber,
                    accessLevelFilter);
            response.setContentType(TMConstants.APPLICATION_JSON);
            response.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(documentMetadataList));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (SerialNumberNotFoundException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }

    /**
     * Gets the case service.
     *
     * @return the case service
     */
    public CaseService getCaseService() {
        return caseService;
    }

    /**
     * Sets the case service.
     *
     * @param caseService
     *            the new case service
     */
    public void setCaseService(CaseService caseService) {
        this.caseService = caseService;
    }
}