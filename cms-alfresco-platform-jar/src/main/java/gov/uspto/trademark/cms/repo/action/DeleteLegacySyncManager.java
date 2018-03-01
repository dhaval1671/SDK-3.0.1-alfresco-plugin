package gov.uspto.trademark.cms.repo.action;

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.services.CaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This is an action class which will be invoked through policy on any document
 * created under the case folder. This updates the last ticrs sync date and
 * number of documents loaded from Legacy Source.
 * 
 *
 */
public class DeleteLegacySyncManager extends ActionExecuterAbstractBase {
    
    private static final Logger log = LoggerFactory.getLogger(org.apache.log4j.Logger.class.getName());

    /** The Constant NAME. */
    public static final String DELETELEGACYSYNCMANAGER_ID = "deleteLegacySyncManager";

    /** The Constant SERIAL_NUM. */
    public static final String SERIAL_NUM = "serialNum";

    /** The Constant ZERO_INT. */
    private static final Integer ZERO_INT = 0;

    /** The node service. */
    private NodeService nodeService;

    /** The case service. */
    private CaseService caseService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.alfresco.repo.action.executer.ActionExecuterAbstractBase#executeImpl
     * (org.alfresco.service.cmr.action.Action,
     * org.alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
        log.debug("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
        String serialNum = (String) action.getParameterValue(SERIAL_NUM);
        NodeRef caseFolder = caseService.getCaseFolderNodeRef(serialNum);
        Integer ticrsDocCount = (Integer) nodeService.getProperty(caseFolder, TradeMarkModel.TICRS_DOC_COUNT_QNAME);
        ticrsDocCount = (ticrsDocCount == null) ? ZERO_INT : ticrsDocCount;
        nodeService.setProperty(caseFolder, TradeMarkModel.TICRS_DOC_COUNT_QNAME, ticrsDocCount - 1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.repo.action.ParameterizedItemAbstractBase#
     * addParameterDefinitions(java.util.List)
     */
    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
        paramList.add(new ParameterDefinitionImpl(SERIAL_NUM, DataTypeDefinition.TEXT, false, SERIAL_NUM));

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
