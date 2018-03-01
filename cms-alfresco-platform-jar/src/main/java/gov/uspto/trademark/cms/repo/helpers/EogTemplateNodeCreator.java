package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;

/**
 * Creates a case folder for a given id. Maintains the strategy of creating
 * folder structure for a given case id.
 */
@Component("eogTemplateNodeCreator")
public class EogTemplateNodeCreator extends AbstractNodeCreator {

    public static final String ID_SEPARATOR = ":";

    /** The eFile folder path. */
    private static final String[] eogTemplateFolderPath = new String[] { TradeMarkModel.EFILE_DRIVE,
            TradeMarkModel.TYPE_EOG_TEMPLATE_FOLDER };

    @Autowired
    @Qualifier(value = "eogTemplateNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    /**
     * Create a folder for the given id.
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
                NodeRef parentNodeRef = cmsNodeLocator.locateNodeRef(eogTemplateFolderPath);
                parentNodeRef = createFolderNode(parentNodeRef, id, TradeMarkModel.EOG_FOLDER_QNAME);
                return parentNodeRef;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);
    }

}