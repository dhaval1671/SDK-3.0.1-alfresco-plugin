package gov.uspto.trademark.cms.repo.policies;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This is alfresco policy which gets executed on every update or before delete.
 * This restricts the user to update or delete any document which has access
 * level as public.
 * 
 * @author bgummadi
 *
 */
public class AccessLevelPolicy
        implements NodeServicePolicies.OnUpdatePropertiesPolicy, NodeServicePolicies.BeforeDeleteNodePolicy {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(AccessLevelPolicy.class);

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
        Behaviour beforeDeleteNode = new JavaBehaviour(this, "beforeDeleteNode",
                Behaviour.NotificationFrequency.TRANSACTION_COMMIT);

        // Update properties behaviors
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnUpdatePropertiesPolicy.QNAME,
                TradeMarkModel.TYPE_TRADEMARK_CONTENT, onUpdateProperties);

        // Delete properties behaviors
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.BeforeDeleteNodePolicy.QNAME,
                TradeMarkModel.TYPE_TRADEMARK_CONTENT, beforeDeleteNode);
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
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        // Check if old value is public, throw exception if new value is not
        // public
        String accessLevelBefore = (String) before.get(TradeMarkModel.ACCESS_LEVEL_QNAME);
        String accessLevelAfter = (String) after.get(TradeMarkModel.ACCESS_LEVEL_QNAME);
        if (AccessLevel.PUBLIC.toString().equals(accessLevelBefore) && !AccessLevel.PUBLIC.toString().equals(accessLevelAfter)) {
            log.debug("Attempted to change the value of accessLevel from public");
            throw new TmngCmsException.AccessLevelRuleViolationException("Cannot change access level on public documents");
        }
    }

    /**
     * Called before a node is deleted.
     *
     * @param nodeRef
     *            the node reference
     */
    @Override
    public void beforeDeleteNode(NodeRef nodeRef) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        // Public documents cannot be deleted. Check access level and throw
        // exception for public
        String accessLevel = (String) serviceRegistry.getNodeService().getProperty(nodeRef, TradeMarkModel.ACCESS_LEVEL_QNAME);
        if (AccessLevel.PUBLIC.toString().equals(accessLevel)) {
            log.debug("Attempted to delete a public document");
            throw new TmngCmsException.AccessLevelRuleViolationException("Cannot delete public documents");
        }
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
