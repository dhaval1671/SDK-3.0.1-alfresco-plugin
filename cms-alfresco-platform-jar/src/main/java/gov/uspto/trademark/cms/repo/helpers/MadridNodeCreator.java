package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Creates a case folder for a given id. Maintains the strategy of creating
 * folder structure for a given case id.
 */
@Component("madridNodeCreator")
public class MadridNodeCreator extends AbstractNodeCreator {

    /** The case folder path. */
    private String[] madridFolderPath = new String[] { TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.MADRIDIB_FOLDER_NAME };

    /**
     * Create a folder for the given id inside 'cabinet --> madridib' folder
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
                NodeRef madridIBNodeRef = madridNodeLocator.locateNodeRef(madridFolderPath);
                madridIBNodeRef = createFolderNode(madridIBNodeRef, id, ContentModel.TYPE_FOLDER);
                return madridIBNodeRef;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }
}