package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;

/**
 * Creates submissions folder for Global Id for e-file submissions
 * 
 * @author vnondapaka
 *
 */
@Component("submissionNodeCreator")
public class SubmissionNodeCreator extends AbstractNodeCreator {

    /** The eFile folder path. */
    private static final String[] SUBMISSIONS_FOLDER_PATH = new String[] { TradeMarkModel.CABINET_FOLDER_NAME,
            TradeMarkModel.SUBMISSIONS_FOLDER_NAME };

    public static final String ID_SEPARATOR = ":";

    @Autowired
    @Qualifier(value = "submissionNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    /**
     * Create a folder for the given global id.
     * 
     * Eg: abc:777:123 will be created as abc/777/123
     *
     * @param id
     *            the id
     * @return the node ref
     */
    @Override
    public NodeRef createNode(final String id) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                NodeRef parentNodeRef = cmsNodeLocator.locateNodeRef(SUBMISSIONS_FOLDER_PATH);
                String[] idPart = id.split(ID_SEPARATOR);
                parentNodeRef = createFolderNode(parentNodeRef, idPart[TMConstants.ZERO]);
                parentNodeRef = createFolderNode(parentNodeRef, idPart[TMConstants.ONE]);
                parentNodeRef = createFolderNode(parentNodeRef, idPart[TMConstants.TWO]);
                return parentNodeRef;
            }
        };
        return txnHelper.doInTransaction(callback, false, false);
    }

}