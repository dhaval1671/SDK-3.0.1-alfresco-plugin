package gov.uspto.trademark.cms.repo.action;

import java.util.Date;
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

/**
 * This is an action class which will be invoked through policy on any document
 * created under the case folder. This updates the last ticrs sync date and
 * number of documents loaded from Legacy Source.
 * 
 *
 */
public class LegacySyncManager extends ActionExecuterAbstractBase {

    /** The Constant NAME. */
    public static final String LEGACYSYNCMANAGER_ID = "legacySyncManager";

    /** The Constant MIGRATION_SOURCE. */
    public static final String MIGRATION_SOURCE = "migrationSource";

    /** The Constant SCAN_DATE. */
    public static final String SCAN_DATE = "scanDate";

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
        Date scanDate = (Date) action.getParameterValue(SCAN_DATE);
        String serialNum = (String) action.getParameterValue(SERIAL_NUM);
        NodeRef caseFolder = caseService.getCaseFolderNodeRef(serialNum);
        Integer ticrsDocCount = (Integer) nodeService.getProperty(caseFolder, TradeMarkModel.TICRS_DOC_COUNT_QNAME);
        ticrsDocCount = (ticrsDocCount == null) ? ZERO_INT : ticrsDocCount;
        nodeService.setProperty(caseFolder, TradeMarkModel.LAST_TICRS_SYNC_DATE_QNAME, scanDate);
        nodeService.setProperty(caseFolder, TradeMarkModel.TICRS_DOC_COUNT_QNAME, ticrsDocCount + 1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.repo.action.ParameterizedItemAbstractBase#
     * addParameterDefinitions(java.util.List)
     */
    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
        paramList.add(new ParameterDefinitionImpl(MIGRATION_SOURCE, DataTypeDefinition.TEXT, false, MIGRATION_SOURCE));
        paramList.add(new ParameterDefinitionImpl(SCAN_DATE, DataTypeDefinition.DATETIME, false, SCAN_DATE));
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
