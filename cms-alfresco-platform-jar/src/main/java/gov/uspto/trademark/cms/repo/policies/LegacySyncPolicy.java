package gov.uspto.trademark.cms.repo.policies;

import org.alfresco.repo.action.ActionImpl;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeRef.Status;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.uspto.trademark.cms.repo.action.DeleteLegacySyncManager;
import gov.uspto.trademark.cms.repo.action.LegacySyncManager;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This class will be registered as policy and will be invoked whenever there is
 * a node created of type tm:document.
 */
public class LegacySyncPolicy implements NodeServicePolicies.OnCreateNodePolicy, NodeServicePolicies.OnMoveNodePolicy {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(LegacySyncPolicy.class);

    /** The policy component. */
    private PolicyComponent policyComponent;

    /** The legacy sync manager. */
    private LegacySyncManager legacySyncManager;

    private DeleteLegacySyncManager deleteLegacySyncManager;

    /** The node service. */
    private NodeService nodeService;

    /**
     * Initializes the behavior and binds the behavior to the policy component.
     * This binding acts as a callback and calls the associated update method
     */
    public void init() {
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnCreateNodePolicy.QNAME, TradeMarkModel.DOCUMENT_QNAME,
                new JavaBehaviour(this, "onCreateNode", NotificationFrequency.TRANSACTION_COMMIT));

        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnMoveNodePolicy.QNAME, TradeMarkModel.DOCUMENT_QNAME,
                new JavaBehaviour(this, "onMoveNode", NotificationFrequency.TRANSACTION_COMMIT));
    }

    /**
     * Sets the policy component.
     *
     * @param policyComponent
     *            the new policy component
     */
    public void setpolicyComponent(PolicyComponent policyComponent) {
        this.policyComponent = policyComponent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.repo.node.NodeServicePolicies.OnCreateNodePolicy#
     * onCreateNode (org.alfresco.service.cmr.repository.ChildAssociationRef)
     */
    @Override
    public void onCreateNode(ChildAssociationRef childAssocRef) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        NodeRef node = childAssocRef.getChildRef();
        Status status = nodeService.getNodeStatus(node);
        if (status != null && !status.isDeleted()) {
            String migrSource = (String) nodeService.getProperty(node, TradeMarkModel.PROP_MIGRATION_SOURCE);
            QName docType = nodeService.getType(node);
            if (migrSource != null && migrSource.equals(TradeMarkModel.TICRS_SOURCE) && docType != null
                    && !(docType.getLocalName()).equals(TradeMarkModel.TYPE_TICRS_DOCUMENT)) {
                ActionImpl action = new ActionImpl(node, LegacySyncManager.LEGACYSYNCMANAGER_ID, null);
                action.setParameterValue(LegacySyncManager.MIGRATION_SOURCE, migrSource);
                action.setParameterValue(LegacySyncManager.SCAN_DATE,
                        nodeService.getProperty(node, TradeMarkModel.PROP_SCAN_DATE));
                action.setParameterValue(LegacySyncManager.SERIAL_NUM,
                        nodeService.getProperty(node, TradeMarkModel.SERIAL_NUMBER_QNAME));
                legacySyncManager.execute(action, node);
            }
        }
    }

    @Override
    public void onMoveNode(ChildAssociationRef oldChildAssocRef, ChildAssociationRef newChildAssocRef) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        NodeRef node = oldChildAssocRef.getChildRef();
        Status status = nodeService.getNodeStatus(node);
        if (status != null && !status.isDeleted()) {
            String migrSource = (String) nodeService.getProperty(node, TradeMarkModel.PROP_MIGRATION_SOURCE);
            QName docType = nodeService.getType(node);
            if (migrSource != null && migrSource.equals(TradeMarkModel.TICRS_SOURCE) && docType != null
                    && !(docType.getLocalName()).equals(TradeMarkModel.TYPE_TICRS_DOCUMENT)) {
                ActionImpl action = new ActionImpl(node, DeleteLegacySyncManager.DELETELEGACYSYNCMANAGER_ID, null);
                action.setParameterValue(LegacySyncManager.SERIAL_NUM,
                        nodeService.getProperty(node, TradeMarkModel.SERIAL_NUMBER_QNAME));
                deleteLegacySyncManager.execute(action, node);
            }
        }
    }

    /**
     * Sets the legacy sync manager.
     *
     * @param legacySyncManager
     *            the new legacy sync manager
     */
    public void setLegacySyncManager(LegacySyncManager legacySyncManager) {
        this.legacySyncManager = legacySyncManager;
    }

    public void setDeleteLegacySyncManager(DeleteLegacySyncManager deleteLegacySyncManager) {
        this.deleteLegacySyncManager = deleteLegacySyncManager;
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