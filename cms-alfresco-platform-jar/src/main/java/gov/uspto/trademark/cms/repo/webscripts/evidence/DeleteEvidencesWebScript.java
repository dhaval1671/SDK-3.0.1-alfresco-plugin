package gov.uspto.trademark.cms.repo.webscripts.evidence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.EvidenceService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.DocumentId;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.DeleteEvidenceRequest;

/**
 * A webscript to copy All evidence files from source folder to target serial
 * number folder.
 *
 * @author stank
 * @author bgummadi
 */
public class DeleteEvidencesWebScript extends AbstractCmsCommonWebScript {

    /** The Constant LOGGER. */
    private static final Logger log = LoggerFactory.getLogger(DeleteEvidencesWebScript.class);

    /** The evidence service. */
    private EvidenceService evidenceService;

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
    public void executeService(WebScriptRequest webScriptRequest, WebScriptResponse webResponse) {
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        final String serialNumber = urlParameters.get(SERIAL_NUMBER_PATH_PARAMETER);
        final String requestBody = getContentAsString(webScriptRequest);

        // Check for request body
        if (StringUtils.isBlank(requestBody)) {
            log.error("Missing metadata ");
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_GATEWAY, "Missing metadata");
        }

        final DeleteEvidenceRequest[] evidencesToDelete;
        try {
            evidencesToDelete = JacksonHelper.unMarshall(requestBody, DeleteEvidenceRequest[].class);
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionHelper.RetryingTransactionCallback<Void> callbackOne = new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
            @Override
            public Void execute() throws Throwable {
                List<NodeRef> deleteEvidenceList = new ArrayList<NodeRef>();
                for (DeleteEvidenceRequest deleteEvidenceRequest : evidencesToDelete) {
                    String[] repositoryPath = PathResolver.resolveDocumentIdToPath(deleteEvidenceRequest.getDocumentId())
                            .split("/");
                    DocumentId documentId = DocumentId.createDocumentId(deleteEvidenceRequest.getDocumentId());
                    if (serialNumber.equals(documentId.getDocumentId())) {
                        List<QName> allowedFileTypes = new ArrayList<QName>();
                        allowedFileTypes.add(TradeMarkModel.EVIDENCE_QNAME);
                        NodeRef nodeRef = evidenceService.fileExists(allowedFileTypes, repositoryPath);
                        if (nodeRef != null) {
                            deleteEvidenceList.add(nodeRef);
                        } else {
                            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND,
                                    deleteEvidenceRequest.getDocumentId() + " doesn't exist");
                        }
                    } else {
                        throw new TmngCmsException.CMSWebScriptException(HttpStatus.FORBIDDEN,
                                deleteEvidenceRequest.getDocumentId() + " doesn't match with the resource");
                    }
                }
                evidenceService.deleteEvidences(serialNumber, deleteEvidenceList);
                return null;
            }
        };
        txnHelper.doInTransaction(callbackOne, false, true);
        webResponse.setContentType(TMConstants.APPLICATION_JSON);
        try {
            webResponse.getOutputStream().write(JacksonHelper.generateClientJsonFrDTO(evidencesToDelete));
        } catch (IOException e) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}