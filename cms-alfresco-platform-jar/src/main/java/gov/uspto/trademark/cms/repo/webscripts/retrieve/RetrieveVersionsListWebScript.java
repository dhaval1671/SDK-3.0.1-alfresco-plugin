package gov.uspto.trademark.cms.repo.webscripts.retrieve;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.filters.AccessLevelFilter;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.DocumentServiceFactory;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * This is the common web script used to retrieve metadata for all the
 * documentsR.
 *
 * @author vnondapaka
 */
public class RetrieveVersionsListWebScript extends AbstractCmsCommonWebScript {

    /** The document service map. */
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
        try {
            Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
            String id = urlParameters.get(ID_PARAMETER);
            String docType = urlParameters.get(DOC_TYPE);
            String fileName = urlParameters.get(FILE_NAME_PARAM);
            String accessLevelValue = webScriptRequest.getParameter(AccessLevel.ACCESS_LEVEL_KEY);

            AccessLevelFilter accessLevelFilter = new AccessLevelFilter(accessLevelValue);
            List<? extends TmngAlfResponse> versionList = documentServiceFactory
                    .getDocumentService(documentServiceMap.get(docType))
                    .retrieveVersionsList(id, fileName, TradeMarkDocumentTypes.getTradeMarkQName(docType), accessLevelFilter);

            if (versionList == null || versionList.isEmpty()) {
                throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                        "Server intercepted request for secured document.");
            }
            webScriptResponse.setContentType(TMConstants.APPLICATION_JSON);
            webScriptResponse.getOutputStream().write(WebScriptHelper.toJson(versionList));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}