package gov.uspto.trademark.cms.repo.webscripts.evidence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
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
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.EvidenceLibraryService;
import gov.uspto.trademark.cms.repo.services.EvidenceService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.EvidencePostResponse;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * A webscript to copy All evidence files from source folder to target serial
 * number folder.
 *
 * @author stank
 */
public class CopyEvidencesWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOGGER. */
    private static final Logger log = LoggerFactory.getLogger(CopyEvidencesWebScript.class);

    @Autowired
    @Qualifier(value = "caseServiceBase")
    private CaseService caseService;

    @Autowired
    @Qualifier(value = "evidenceServiceBase")
    private EvidenceService evidenceService;

    @Autowired
    @Qualifier(value = "EvidenceLibraryImplService")
    private EvidenceLibraryService evidenceLibraryService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    public void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webResponse) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        final String serialNumber = urlParameters.get(SERIAL_NUMBER_PATH_PARAMETER);
        final String requestBody = getContentAsString(webScriptRequest);

        CopyEvidenceRequest[] lclCopyEvidenceRequests = null;
        // Check for request body
        if (StringUtils.isBlank(requestBody)) {
            log.error("Missing metadata ");
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_GATEWAY, "Missing metadata");
        } else {
            try {
                lclCopyEvidenceRequests = JacksonHelper.unMarshall(requestBody, CopyEvidenceRequest[].class);
            } catch (JsonParseException e) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } catch (JsonMappingException e) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } catch (IOException e) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }
        final CopyEvidenceRequest[] copyEvidenceRequests = lclCopyEvidenceRequests;
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionHelper.RetryingTransactionCallback<List<EvidencePostResponse>> callbackOne = new RetryingTransactionHelper.RetryingTransactionCallback<List<EvidencePostResponse>>() {
            @Override
            public List<EvidencePostResponse> execute() throws Throwable {
                // Check if case exists
                NodeRef caseFolderNodeRef = caseService.getCaseFolderNodeRef(serialNumber, true);

                // Check if all source files exist
                Map<NodeRef, CopyEvidenceRequest> sourceNodeRefList = new HashMap<NodeRef, CopyEvidenceRequest>();
                for (CopyEvidenceRequest copyEvidenceRequest : copyEvidenceRequests) {
                    if (StringUtils.isNotBlank(copyEvidenceRequest.getDocumentId())) {
                        String[] repositoryPath = PathResolver.resolveDocumentIdToPath(copyEvidenceRequest.getDocumentId())
                                .split("/");
                        List<QName> allowedFileTypes = new ArrayList<QName>();
                        allowedFileTypes.add(TradeMarkModel.EVIDENCE_QNAME);
                        allowedFileTypes.add(ContentModel.TYPE_CONTENT);
                        NodeRef nodeRefOfSourceFile = evidenceService.fileExists(allowedFileTypes, repositoryPath);
                        if (nodeRefOfSourceFile != null) {
                            CopyEvidenceRequest newCopyEvidenceRequest = evidenceLibraryService
                                    .processEvidenceBankFiles(nodeRefOfSourceFile, copyEvidenceRequest);
                            sourceNodeRefList.put(nodeRefOfSourceFile, newCopyEvidenceRequest);
                        } else {
                            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND,
                                    copyEvidenceRequest.getDocumentId() + " doesn't exist");
                        }
                    } else {
                        throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, "Server recieved DocumentId as :"
                                + copyEvidenceRequest.getDocumentId() + " and can't proceed with this request.");
                    }
                }
                List<EvidencePostResponse> responses = evidenceService.copyEvidences(serialNumber, caseFolderNodeRef, sourceNodeRefList);
                return responses;
            }
        };
        List<EvidencePostResponse> postResponseList = txnHelper.doInTransaction(callbackOne, false, true);
        webResponse.setContentType(TMConstants.APPLICATION_JSON);
        try {
            webResponse.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(postResponseList));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
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

    /**
     * Sets the evidence service.
     *
     * @param evidenceService
     *            the new evidence service
     */
    public void setEvidenceService(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

}