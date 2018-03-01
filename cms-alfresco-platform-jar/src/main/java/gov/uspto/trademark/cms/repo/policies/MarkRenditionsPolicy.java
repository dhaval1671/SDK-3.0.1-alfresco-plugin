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

import gov.uspto.trademark.cms.repo.constants.MarkRenditions;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.services.BehaviorImageMarkService;

/**
 * The Class MarkRenditionsPolicy.
 *
 * @author bgummadi
 */
public class MarkRenditionsPolicy extends TransactionListenerAdapter
        implements ContentServicePolicies.OnContentPropertyUpdatePolicy {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(MarkRenditionsPolicy.class);

    /** The policy component. */
    private PolicyComponent policyComponent;

    /** The image mark service. */
    private BehaviorImageMarkService imageMarkService;

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    /**
     * Initializes the behavior and binds the behavior to the policy component.
     * This binding acts as a callback and calls the associated update method
     */
    public void init() {
        Behaviour onContentPropertyUpdate = new JavaBehaviour(this, "onContentPropertyUpdate",
                NotificationFrequency.TRANSACTION_COMMIT);
        this.policyComponent.bindClassBehaviour(TradeMarkModel.ON_CONTENT_PROPERTY_UPDATE_QNAME, TradeMarkModel.MARK_QNAME,
                onContentPropertyUpdate);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.repo.content.ContentServicePolicies.
     * OnContentPropertyUpdatePolicy
     * #onContentPropertyUpdate(org.alfresco.service.cmr.repository.NodeRef,
     * org.alfresco.service.namespace.QName,
     * org.alfresco.service.cmr.repository.ContentData,
     * org.alfresco.service.cmr.repository.ContentData)
     */
    @Override
    public void onContentPropertyUpdate(NodeRef nodeRef, QName propertyQName, ContentData beforeValue, ContentData afterValue) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
        log.debug("### Executing " + ste.getClassName() + "." + ste.getMethodName() + " ####");
        if (afterValue != null) {
            ContentReader incomingMediaTypeFileReader = serviceRegistry.getContentService().getReader(nodeRef,ContentModel.PROP_CONTENT);
            String oldMediaFileName = (String) serviceRegistry.getNodeService().getProperty(nodeRef,ContentModel.PROP_NAME);
            String oldMediaNewGuessMimeType = serviceRegistry.getMimetypeService().guessMimetype(oldMediaFileName,incomingMediaTypeFileReader);        	
            boolean incomingMimeTypeIsVideoType = StringUtils.startsWith(oldMediaNewGuessMimeType, TMConstants.VIDEO) || StringUtils.startsWith(oldMediaNewGuessMimeType, TMConstants.AUDIO);
            if (!incomingMimeTypeIsVideoType) {
                AlfrescoTransactionSupport.bindListener(this);
                AlfrescoTransactionSupport.bindResource("NODE_REF", nodeRef);
            }        	
        }
        if (log.isDebugEnabled()) {
            log.debug("Before Values {}", beforeValue);
            log.debug("After Values {}", afterValue);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.alfresco.repo.transaction.TransactionListenerAdapter#afterCommit()
     */
	@Override
	public void afterCommit() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		log.debug("### Execution " + ste.getClassName() + "." + ste.getMethodName() + ":START ####");
		final NodeRef incomingMediaTypeNodeRef = AlfrescoTransactionSupport.getResource("NODE_REF");
		final BehaviorImageMarkService imageMarkServiceLcl = this.imageMarkService;
		transactionService.getRetryingTransactionHelper().doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
					@Override
					public Void execute() throws Throwable {
						AuthenticationUtil.runAs(new RunAsWork<Void>() {
							@Override
                            public Void doWork() throws Exception {
								try {
									if (null != incomingMediaTypeNodeRef) {
										ContentData cd = (ContentData) serviceRegistry.getNodeService().getProperty(incomingMediaTypeNodeRef, ContentModel.PROP_CONTENT);
										String mimeType = cd.getMimetype();
										boolean isRenditionSupported = imageMarkServiceLcl.isRenditionSupported(mimeType);
										if (isRenditionSupported) {
											imageMarkServiceLcl.createRendition(incomingMediaTypeNodeRef,MarkRenditions.IMAGE_TO_PNG);
											log.debug("Created IMAGE_TO_PNG rendition");
											imageMarkServiceLcl.createRendition(incomingMediaTypeNodeRef,MarkRenditions.MARK_TINY_80X65);
											log.debug("Created MARK_TINY_80X65 rendition");
											imageMarkServiceLcl.createRendition(incomingMediaTypeNodeRef,MarkRenditions.MARK_SMALL_100X100);
											log.debug("Created MARK_SMALL_100X100 rendition");
											imageMarkServiceLcl.createRendition(incomingMediaTypeNodeRef,MarkRenditions.MARK_THUMBNAIL_160X130);
											log.debug("Created MARK_THUMBNAIL_160X130 rendition");
											imageMarkServiceLcl.createRendition(incomingMediaTypeNodeRef,MarkRenditions.MARK_LARGE_320X260);
											log.debug("Created MARK_LARGE_320X260 rendition");
										}
									}
								} catch (Exception e) {
									log.debug("MarkRenditionsPolicy.afterCommit() failed: ", e);
								}
								return null;
							}
						}, AuthenticationUtil.SYSTEM_USER_NAME);
						return null;
					}
				}, false, true);
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
     * Sets the image mark service.
     *
     * @param imageMarkService
     *            the new image mark service
     */
    public void setImageMarkService(BehaviorImageMarkService imageMarkService) {
        this.imageMarkService = imageMarkService;
    }

}