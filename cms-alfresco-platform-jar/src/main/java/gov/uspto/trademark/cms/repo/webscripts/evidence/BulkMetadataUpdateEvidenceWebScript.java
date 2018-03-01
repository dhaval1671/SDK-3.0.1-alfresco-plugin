package gov.uspto.trademark.cms.repo.webscripts.evidence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
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
import gov.uspto.trademark.cms.repo.services.EvidenceService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;
import gov.uspto.trademark.cms.repo.webscripts.beans.EvidencePostResponse;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * The Class BulkMetadataUpdateEvidenceWebScript.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk} on Oct/16/2014
 */
public class BulkMetadataUpdateEvidenceWebScript extends AbstractCmsCommonWebScript {

    /** The evidence service. */
    private EvidenceService evidenceService;

    /** The case service. */
    private CaseService caseService;

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

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.webscripts.BaseWebscript#executeAction(org
     * .springframework.extensions.webscripts.WebScriptRequest,
     * org.springframework.extensions.webscripts.WebScriptResponse)
     */
    @Override
    protected void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webResponse) {

        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        final String serialNumber = urlParameters.get(SERIAL_NUMBER_PATH_PARAMETER);
        final String requestBody = getContentAsString(webScriptRequest);

        // Check for request body
        if (StringUtils.isBlank(requestBody)) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_GATEWAY, "Missing metadata");
        }

        // Check if case exists
        NodeRef caseFolderNodeRef = caseService.getCaseFolderNodeRef(serialNumber);

        // Check if all source files exist
        CopyEvidenceRequest[] copyEvidenceRequests = null;
        try {
            copyEvidenceRequests = JacksonHelper.unMarshall(requestBody, CopyEvidenceRequest[].class);
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        Map<NodeRef, CopyEvidenceRequest> sourceNodeRefList = new HashMap<NodeRef, CopyEvidenceRequest>();
        for (CopyEvidenceRequest copyEvidenceRequest : copyEvidenceRequests) {
            if (StringUtils.isNotBlank(copyEvidenceRequest.getDocumentId())) {

                // Verify if URL serial number matches the json serial number.
                DocumentId documentIdObj = DocumentId.createDocumentId(copyEvidenceRequest.getDocumentId());
                if (!(serialNumber.equals(documentIdObj.getDocumentId()))) {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                            "URL serial no.: " + serialNumber + " has to match the Serial no. in: " + documentIdObj.getId());
                }

                String[] repositoryPath = PathResolver.resolveDocumentIdToPath(copyEvidenceRequest.getDocumentId()).split("/");
                List<QName> allowedFileTypes = new ArrayList<QName>();
                allowedFileTypes.add(TradeMarkModel.EVIDENCE_QNAME);
                NodeRef nodeRef = evidenceService.fileExists(allowedFileTypes, repositoryPath);
                if (nodeRef != null) {
                    sourceNodeRefList.put(nodeRef, copyEvidenceRequest);
                } else {
                    throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND,
                            copyEvidenceRequest.getDocumentId() + " doesn't exist");
                }
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Incoming json field 'documentId', cannot be null or empty string.");
            }
        }
        List<EvidencePostResponse> responses = evidenceService.bulkMetadataUpdateEvidences(serialNumber, caseFolderNodeRef,
                sourceNodeRefList);

        List<EvidencePostResponse> postResponseList = responses;
        webResponse.setContentType(TMConstants.APPLICATION_JSON);
        try {
            webResponse.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(postResponseList));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}