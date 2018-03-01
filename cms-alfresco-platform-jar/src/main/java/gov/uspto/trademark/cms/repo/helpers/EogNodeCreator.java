package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This class is used to create eog folder structure for a given publication.
 * This inherits the abstractnodecreator and implements the functionality
 * specific to publication folders creation.
 * 
 * @author vnondapaka
 *
 */
@Component("eogNodeCreator")
public class EogNodeCreator extends AbstractNodeCreator {

    /** The eog folder path. */
    private String[] eogFolderPath = new String[] { TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.PUBLICATION_FOLDER_NAME,
            TradeMarkModel.EOG_FOLDER_NAME };

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.helpers.CmsNodeCreator#createNode(java.lang
     * .String)
     */
    @Override
    public synchronized NodeRef createNode(final String id) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                NodeRef parentNodeRef = eogNodeLocator.locateNodeRef(eogFolderPath);
                parentNodeRef = createFolderNode(parentNodeRef, id, TradeMarkModel.EOG_FOLDER_QNAME);
                return parentNodeRef;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }

}
