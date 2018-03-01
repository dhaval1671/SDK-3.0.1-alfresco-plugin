package gov.uspto.trademark.cms.repo.policies;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeRef.Status;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.DataDictionaryHelper;
import gov.uspto.trademark.cms.repo.nodedelete.DocumentHardDelete;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype.MultimediaService;

/**
 * This class will be registered as policy and will be invoked whenever there is
 * a node created of type tm:document.
 */
public class MultimediaDeletePolicy implements NodeServicePolicies.OnMoveNodePolicy {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(MultimediaDeletePolicy.class);

    /** The policy component. */
    @Autowired
    @Qualifier(value = "policyComponent")
    private PolicyComponent policyComponent;

    @Autowired
    @Qualifier(value = "ServiceRegistry")
    private ServiceRegistry serviceRegistry;

    @Autowired
    @Qualifier(value = "multimediaService")
    private MultimediaService multimediaService;

    @Autowired
    @Qualifier(value = "caseNodeLocator")
    private CmsNodeLocator cmsNodeLocator;

    @Autowired
    @Qualifier("DocumentHardDelete")
    private DocumentHardDelete documentHardDelete;

    /**
     * Initializes the behavior and binds the behavior to the policy component.
     * This binding acts as a callback and calls the associated update method
     */
    public void init() {
        Behaviour onMoveNode = new JavaBehaviour(this, "onMoveNode", NotificationFrequency.TRANSACTION_COMMIT);
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnMoveNodePolicy.QNAME, TradeMarkModel.LEGACY_QNAME,
                onMoveNode);
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnMoveNodePolicy.QNAME, TradeMarkModel.EVIDENCE_QNAME,
                onMoveNode);
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnMoveNodePolicy.QNAME, TradeMarkModel.SPECIMEN_QNAME,
                onMoveNode);
        this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnMoveNodePolicy.QNAME, TradeMarkModel.MARK_QNAME,
                onMoveNode);
    }

    @Override
    public void onMoveNode(ChildAssociationRef oldChildAssocRef, ChildAssociationRef newChildAssocRef) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        NodeRef caseSerialNumberNodeRef = oldChildAssocRef.getParentRef();
        NodeRef nodeRefOfFileToBeDeleted = newChildAssocRef.getChildRef();
        Status status = serviceRegistry.getNodeService().getNodeStatus(nodeRefOfFileToBeDeleted);
        if (status != null && !status.isDeleted()) {
            QName docType = serviceRegistry.getNodeService().getType(nodeRefOfFileToBeDeleted);
            ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(nodeRefOfFileToBeDeleted,
                    ContentModel.PROP_CONTENT);
            String mimeType = cd.getMimetype();
            Map<QName, Serializable> properties = serviceRegistry.getNodeService().getProperties(nodeRefOfFileToBeDeleted);
            String fn = (String) properties.get(DataDictionaryHelper.getTradeMarkPropertyQName(TradeMarkModel.DOCUMENT_NAME));
            if (multimediaService.isMultimediaContentChangeInsideCaseOrMulFolder(mimeType, caseSerialNumberNodeRef, docType,
                    false)) {
                processMultimediaFile(caseSerialNumberNodeRef, fn, nodeRefOfFileToBeDeleted);
            }
        }
    }

    private void processMultimediaFile(NodeRef caseSerialNumberFolderNodeRef, String fn, NodeRef nodeRefOfFileToBeDeleted) {
        NodeRef documentNodeRef = cmsNodeLocator.locateNodeRef(caseSerialNumberFolderNodeRef, TMConstants.MULTIMEDIA_FOLDER_NAME,
                fn);
        if (null != documentNodeRef) {
            log.debug("### Hard Delete " + fn + " ####");
            documentHardDelete.hardDeleteNoChecks(documentNodeRef, Boolean.TRUE);
        }
    }

}