package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Creates a case folder for a given id. Maintains the strategy of creating
 * folder structure for a given case id.
 */
@Component("legalProceedingNodeCreator")
public class LegalProceedingNodeCreator extends AbstractNodeCreator {

    /** The case folder path. */
    private String[] legalProceedingFolderPath = new String[] { TradeMarkModel.CABINET_FOLDER_NAME,
            TradeMarkModel.LEGAL_PROCEEDING_FOLDER_NAME };

    /**
     * Create a folder for the given id inside 'cabinet --> legal-proceeding'
     * folder
     * 
     * @param id
     *            the id
     * @return the node ref
     */
    @Override
    public synchronized NodeRef createNode(final String id) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                NodeRef parentNodeRef = cmsNodeLocator.locateNodeRef(legalProceedingFolderPath);
                parentNodeRef = createFolderNode(parentNodeRef, id.substring(TMConstants.ZERO, TMConstants.THREE));
                parentNodeRef = createFolderNode(parentNodeRef, id.substring(TMConstants.THREE, TMConstants.SIX));
                parentNodeRef = createFolderNode(parentNodeRef, id, TradeMarkModel.PROCEEDING_NUMBER_FOLDER_QNAME);
                return parentNodeRef;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }
}