package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.impl.AbstractCommonService;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;

/**
 * Created by bgummadi on 8/7/2015.
 */
public abstract class AbstractCaseCommonService extends AbstractCommonService {

    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService; 	
	
    /**
     * Instantiates a new abstract common service.
     *
     * @param cmsNodeCreator
     *            the case node creator
     * @param cmsNodeLocator
     *            the cms node locator
     */
    public AbstractCaseCommonService(CmsNodeCreator cmsNodeCreator, CmsNodeLocator cmsNodeLocator) {
        super(cmsNodeCreator, cmsNodeLocator);
    }

    /**
     * Used to set Id and other mandatory parameters during creation process
     *
     * @param repositoryProperties
     * @param id
     */
    @Override
    protected void setMandatoryProperties(Map<QName, Serializable> repositoryProperties, String id) {
        repositoryProperties.put(TradeMarkModel.SERIAL_NUMBER_QNAME, id);
    }

    /**
     * Used to buildResponse after creation
     *
     * @param id
     * @param type
     * @param fileName
     * @param versionNumber
     *            @return
     */
    @Override
    protected TmngAlfResponse buildResponse(String id, String type, String fileName, String versionNumber) {
        PostResponse postResponse = new PostResponse();
        postResponse.setDocumentId(id, type, fileName);
        postResponse.setVersion(versionNumber);
        postResponse.setSerialNumber(id);
        return postResponse;
    }

    public boolean removeAssociation(final NodeRef source, final NodeRef target, final QName type) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<Boolean> callback = new RetryingTransactionCallback<Boolean>() {
            @Override
            public Boolean execute() throws Throwable {
                NodeService ns = serviceRegistry.getNodeService();
                boolean success = false;
                try {
                    if (!ns.exists(source)) {
                        throw new IllegalArgumentException("Source node does not exist: " + source);
                    }
                    if (!ns.exists(target)) {
                        throw new IllegalArgumentException("Target node does not exist: " + target);
                    }
                    ns.removeAssociation(source, target, type);
                    success = true;
                } catch (Exception e) {
                    String msg = "Could not remove association:\n" + "Source Node: " + source + "\n" + "Target Node: " + target
                            + "\n" + "Type: " + type + "\n";
                    if (StringUtils.isNotBlank(e.getMessage())) {
                        msg += "Caused by: " + e.getMessage();
                    }
                    throw new TmngCmsException.CMSRuntimeException(msg, e);
                }
                return success;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }

}
