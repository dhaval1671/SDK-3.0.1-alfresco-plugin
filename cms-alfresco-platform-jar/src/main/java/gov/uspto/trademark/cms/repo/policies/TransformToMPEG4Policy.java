package gov.uspto.trademark.cms.repo.policies;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.ContentServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.transaction.AlfrescoTransactionSupport;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.util.transaction.TransactionListenerAdapter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype.MultimediaService;

/**
 * This is alfresco policy which gets executed on every create or update of
 * Mark/Evidence/Specimen docTypes. This enable feature of transforming any
 * incoming video media type to converted to MPEG-4 for HTML5 complaint
 * rendering on TMNG-UI screens.
 * 
 * @author stank
 *
 */
public class TransformToMPEG4Policy extends TransactionListenerAdapter
        implements ContentServicePolicies.OnContentPropertyUpdatePolicy {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(TransformToMPEG4Policy.class);

    /** The policy component. */
    private PolicyComponent policyComponent;

    /** The service registry. */
    ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    @Autowired
    @Qualifier(value = "multimediaService")
    protected MultimediaService multimediaService;

    /**
     * Initializes the behavior and binds the behavior to the policy component.
     * This binding acts as a callback and calls the associated update method
     */
    public void init() {
        Behaviour onContentPropertyUpdate = new JavaBehaviour(this, "onContentPropertyUpdate",
                NotificationFrequency.TRANSACTION_COMMIT);
        this.policyComponent.bindClassBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME, TradeMarkModel.LEGACY_QNAME,
                onContentPropertyUpdate);
        this.policyComponent.bindClassBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME, TradeMarkModel.EVIDENCE_QNAME,
                onContentPropertyUpdate);
        this.policyComponent.bindClassBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME, TradeMarkModel.SPECIMEN_QNAME,
                onContentPropertyUpdate);
        this.policyComponent.bindClassBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME, TradeMarkModel.MARK_QNAME,
                onContentPropertyUpdate);
    }

    @Override
    public void onContentPropertyUpdate(NodeRef incomingMediaTypeNodeRef, QName propertyQName, ContentData beforeValue,
            ContentData afterValue) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        AlfrescoTransactionSupport.bindListener(this);
        AlfrescoTransactionSupport.bindResource("IncomingMediaTypeNodeRef", incomingMediaTypeNodeRef);
        AlfrescoTransactionSupport.bindResource("BeforeValue", beforeValue);
        AlfrescoTransactionSupport.bindResource("AfterValue", afterValue);
    }

	@Override
	public void afterCommit() {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		log.debug("### Execution " + ste.getClassName() + "." + ste.getMethodName() + ":START ####");
		final NodeRef incomingMediaTypeNodeRef = AlfrescoTransactionSupport.getResource("IncomingMediaTypeNodeRef");
		final ContentData beforeValue = AlfrescoTransactionSupport.getResource("BeforeValue");
		ContentData afterValue = AlfrescoTransactionSupport.getResource("AfterValue");
		if (afterValue != null) {
			transactionService.getRetryingTransactionHelper().doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
						@Override
						public Void execute() throws Throwable {
							AuthenticationUtil.runAs(new RunAsWork<Void>() {
								@Override
                                public Void doWork() throws Exception {
									try {
										ChildAssociationRef childAssocRef = serviceRegistry.getNodeService().getPrimaryParent(incomingMediaTypeNodeRef);
										ContentReader incomingMediaTypeFileReader = serviceRegistry.getContentService().getReader(incomingMediaTypeNodeRef, ContentModel.PROP_CONTENT);
										String oldMediaFileName = (String) serviceRegistry.getNodeService().getProperty(incomingMediaTypeNodeRef, ContentModel.PROP_NAME);
										String oldMediaNewGuessMimeType = serviceRegistry.getMimetypeService().guessMimetype(oldMediaFileName, incomingMediaTypeFileReader);
										QName type = serviceRegistry.getNodeService().getType(incomingMediaTypeNodeRef);
										boolean isPolicyMakingChanges = true;
										if (multimediaService.isMultimediaContentChangeInsideCaseOrMulFolder(oldMediaNewGuessMimeType, childAssocRef.getParentRef(), type,isPolicyMakingChanges)) {
											/*
											 * Use the incoming file name as the new file name, even though we are converting the file to a different mime-type.
											 * Only the mime-type will be updated/changed.
											 */
											String newMediaFileName = oldMediaFileName;
											String newMimeType = TMConstants.VIDEO_MP4;
											if (StringUtils.startsWith(oldMediaNewGuessMimeType, TMConstants.AUDIO)) {
												newMimeType = TMConstants.AUDIO_MPEG;
											}
											log.debug("Transform Policy Update: Convert the media file " + oldMediaFileName + " of type: " + oldMediaNewGuessMimeType + " to " + newMediaFileName + " of type: " + newMimeType);
											multimediaService.processMediaConversion(childAssocRef, oldMediaFileName, oldMediaNewGuessMimeType, newMediaFileName, newMimeType, null != beforeValue);
											log.debug("Transform Policy applied: Converted/copied the media file " + oldMediaFileName + "; type " + oldMediaNewGuessMimeType + " as " + newMediaFileName + " or " + oldMediaFileName);
										}
									} catch (Exception e) {
										log.debug("TransformToMPEG4Policy.afterCommit() failed: ", e);
									}
									return null;
								}
							}, AuthenticationUtil.SYSTEM_USER_NAME);
							return null;
						}
					}, false, true);
		}
		log.debug("### Execution " + ste.getClassName() + "." + ste.getMethodName() + ":END ####");
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
