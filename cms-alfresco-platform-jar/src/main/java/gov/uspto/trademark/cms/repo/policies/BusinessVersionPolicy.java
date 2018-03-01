package gov.uspto.trademark.cms.repo.policies;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.domain.node.ContentDataWithId;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Created by bgummadi on 4/15/2014.
 */
public class BusinessVersionPolicy
        implements NodeServicePolicies.OnUpdatePropertiesPolicy, NodeServicePolicies.OnCreateNodePolicy {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(BusinessVersionPolicy.class);

    /** The policy component. */
    private PolicyComponent policyComponent;

    /** The service registry. */
    private ServiceRegistry serviceRegistry;

    /**
     * Initializes the behavior and binds the behavior to the policy component.
     * This binding acts as a callback and calls the associated update method
     */
    public void init() {
        Behaviour onUpdateProperties = new JavaBehaviour(this, "onUpdateProperties",
                Behaviour.NotificationFrequency.TRANSACTION_COMMIT);
        Behaviour onCreateNode = new JavaBehaviour(this, "onCreateNode", Behaviour.NotificationFrequency.TRANSACTION_COMMIT);
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnUpdatePropertiesPolicy.QNAME, TradeMarkModel.MARK_QNAME,
                onUpdateProperties);
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnCreateNodePolicy.QNAME, TradeMarkModel.MARK_QNAME,
                onCreateNode);
    }

    /**
     * Called after a node's properties have been changed.
     *
     * @param nodeRef
     *            reference to the updated node
     * @param before
     *            the node's properties before the change
     * @param after
     *            the node's properties after the change
     */
    @Override
    public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before, Map<QName, Serializable> after) {
        if (log.isDebugEnabled()) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
            log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
            log.debug("before {}", before);
            log.debug("after {}", after);
        }
        if (before.get(ContentModel.PROP_CONTENT) != null) {
            if (contentChanged(before, after) || effectiveStartDateChanged(before, after)) {
                this.serviceRegistry.getNodeService().setProperty(nodeRef,
                        QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, "isBusinessVersion"), true);
                if (!isDataMigration(after)) {
                    // Initialize the load date to the current date if the
                    // upload is not from data migration
                    this.serviceRegistry.getNodeService().setProperty(nodeRef,
                            QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, TradeMarkModel.LOAD_DATE), new Date());
                }
            } else {
                // This is just a metadata update
                this.serviceRegistry.getNodeService().setProperty(nodeRef,
                        QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, "isBusinessVersion"), false);
            }
        } else {
            // This is the first time create
            if (!isDataMigration(after)) {
                // Initialize the load date to the current date if the upload is
                // not from data migration
                this.serviceRegistry.getNodeService().setProperty(nodeRef,
                        QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, TradeMarkModel.LOAD_DATE), new Date());
            }
        }
    }

    /**
     * Called when a new node has been created.
     *
     * @param childAssocRef
     *            the created child association reference
     */
    @Override
    public void onCreateNode(ChildAssociationRef childAssocRef) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        log.debug("Making {} a business version", childAssocRef.getChildRef());
    }

    /**
     * Checks if is data migration.
     *
     * @param after
     *            the after
     * @return true, if is data migration
     */
    private boolean isDataMigration(Map<QName, Serializable> after) {
        String migrationMethod = (String) after
                .get(QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI, TradeMarkModel.MIGRATION_METHOD));
        return "BLK".equals(migrationMethod) || "LZL".equals(migrationMethod);
    }

    /**
     * Content changed.
     *
     * @param before
     *            the before
     * @param after
     *            the after
     * @return true, if successful
     */
    private boolean contentChanged(Map<QName, Serializable> before, Map<QName, Serializable> after) {
        ContentDataWithId contentBefore = (ContentDataWithId) before.get(ContentModel.PROP_CONTENT);
        ContentDataWithId contentAfter = (ContentDataWithId) after.get(ContentModel.PROP_CONTENT);

        if (contentBefore == null) {
            // First time upload. Previous content will be null
            return true;
        }
        return !contentAfter.getContentUrl().equals(contentBefore.getContentUrl());
    }

    /**
     * Effective start date changed.
     *
     * @param before
     *            the before
     * @param after
     *            the after
     * @return true, if successful
     */
    private boolean effectiveStartDateChanged(Map<QName, Serializable> before, Map<QName, Serializable> after) {
        Date effectiveStartDateBefore = null;
        Date effectiveStartDateAfter = null;
        boolean status = false;
        QName effectiveStartDateQN = QName.createQName(TradeMarkModel.TRADEMARK_MODEL_1_0_URI,
                TradeMarkModel.EFFECTIVE_START_DATE);

        if (before.containsKey(effectiveStartDateQN)) {
            effectiveStartDateBefore = (Date) before.get(effectiveStartDateQN);
        }
        if (after.containsKey(effectiveStartDateQN)) {
            effectiveStartDateAfter = (Date) after.get(effectiveStartDateQN);
        }

        if (null != effectiveStartDateAfter) {
            status = !effectiveStartDateAfter.equals(effectiveStartDateBefore);
        }

        return status;
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

    /**
     * Sets the service registry.
     *
     * @param serviceRegistry
     *            the new service registry
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}