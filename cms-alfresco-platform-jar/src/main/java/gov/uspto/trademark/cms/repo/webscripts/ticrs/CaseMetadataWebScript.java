package gov.uspto.trademark.cms.repo.webscripts.ticrs;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.JacksonHelper;
import gov.uspto.trademark.cms.repo.helpers.WebScriptHelper;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.webscripts.AbstractCmsCommonWebScript;
import gov.uspto.trademark.cms.repo.webscripts.beans.CaseMetadataResponse;

/**
 * Returns the metadata json for a case folder
 * <p/>
 * .
 */
public class CaseMetadataWebScript extends AbstractCmsCommonWebScript {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(CaseMetadataWebScript.class);

    /** The case service. */
    private CaseService caseService;

    /** The node service. */
    private NodeService nodeService;

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
        Map<String, String> urlParameters = WebScriptHelper.getUrlParameters(webScriptRequest);
        String serialNumber = urlParameters.get(SERIAL_NUMBER_PATH_PARAMETER);
        NodeRef caseFolder = caseService.getCaseFolderNodeRef(serialNumber);
        Map<String, Serializable> caseProperties = new HashMap<String, Serializable>();
        caseProperties.put(TradeMarkModel.NAME, nodeService.getProperty(caseFolder, TradeMarkModel.NAME_QNAME));
        caseProperties.put(TradeMarkModel.TICRS_DOC_COUNT,
                nodeService.getProperty(caseFolder, TradeMarkModel.TICRS_DOC_COUNT_QNAME));
        caseProperties.put(TradeMarkModel.LAST_TICRS_SYNC_DATE,
                nodeService.getProperty(caseFolder, TradeMarkModel.LAST_TICRS_SYNC_DATE_QNAME));

        CaseMetadataResponse caseMetadataObj = JacksonHelper.generateDTOFrAlfrescoRepoProps(caseProperties,
                CaseMetadataResponse.class);
        byte[] caseMetadata = JacksonHelper.generateClientJsonFrDTO(caseMetadataObj);
        if (caseMetadata == null) {
            throw new TmngCmsException.CMSWebScriptException(HttpStatus.NOT_FOUND, "Metadata NOT found.");
        } else {
            response.setContentType(TMConstants.APPLICATION_JSON);
            try {
                response.getOutputStream().write(caseMetadata);
            } catch (IOException e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage(), e);
                }
            }
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
     * Sets the node service.
     *
     * @param nodeService
     *            the new node service
     */
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

}