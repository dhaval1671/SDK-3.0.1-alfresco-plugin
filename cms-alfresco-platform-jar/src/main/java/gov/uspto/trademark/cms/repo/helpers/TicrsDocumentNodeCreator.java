package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.model.ContentModel;
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
@Component("ticrsDocumentNodeCreator")
public class TicrsDocumentNodeCreator extends AbstractNodeCreator {

    /** The case folder path. */
    private final String[] caseFolderPath = new String[] { TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME };

    /**
     * Create a folder for the given case id. Current implementation is for a
     * serial number Creates a folder tree with first 3 letters as the folder
     * name followed by a 3 letter name followed by the complete serial number.
     * 
     * Eg: 12345678 will be created as 123/456/12345678
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
                NodeRef parentNodeRef = cmsNodeLocator.locateNodeRef(caseFolderPath);
                parentNodeRef = createFolderNode(parentNodeRef, id.substring(TMConstants.ZERO, TMConstants.THREE));
                parentNodeRef = createFolderNode(parentNodeRef, id.substring(TMConstants.THREE, TMConstants.SIX));
                parentNodeRef = createFolderNode(parentNodeRef, id, TradeMarkModel.CASE_FOLDER_QNAME);
                parentNodeRef = createFolderNode(parentNodeRef, TradeMarkModel.TICRS_FOLDER_NAME, ContentModel.TYPE_FOLDER);
                return parentNodeRef;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }
}