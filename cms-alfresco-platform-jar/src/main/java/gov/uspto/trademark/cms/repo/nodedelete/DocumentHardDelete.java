/**
 *
 */
package gov.uspto.trademark.cms.repo.nodedelete;

import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * The Class DocumentHardDelete.
 *
 * @author stank
 */
@Component("DocumentHardDelete")
public class DocumentHardDelete extends CommonDelete implements BehaviorDocumentDelete {

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.uspto.trademark.cms.repo.nodedelete.AlfrescoDocDelete#delete(org.
     * alfresco.service.cmr. repository.NodeRef)
     */
    @Override
    public Boolean delete(final NodeRef documentNodeRef) {
        return delete(documentNodeRef, Boolean.FALSE);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.uspto.trademark.cms.repo.nodedelete.AlfrescoDocDelete#delete(org.
     * alfresco.service.cmr. repository.NodeRef)
     */
    @Override
    public Boolean delete(final NodeRef documentNodeRef, final Boolean deleteParentFolderIfEmpty) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<Boolean> callback = new RetryingTransactionCallback<Boolean>() {
            @Override
            public Boolean execute() throws Throwable {

                if (documentNodeRef != null) {
                    String accessLevel = (String) serviceRegistry.getNodeService().getProperty(documentNodeRef,
                            TradeMarkModel.ACCESS_LEVEL_QNAME);
                    if (accessLevel != null && accessLevel.equalsIgnoreCase(TMConstants.PUBLIC)) {
                        throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                                "Public documents cannot be deleted");
                    }

                    NodeRef deleteParentFolder = null;
                    if (deleteParentFolderIfEmpty && checkIfParentFolderNeedsToBeDeleted(documentNodeRef)) {
                        deleteParentFolder = getParentFolderNodeRef(documentNodeRef);
                    }
                    deleteNodeRef(documentNodeRef);
                    if (null != deleteParentFolder) {
                        deleteNodeRef(deleteParentFolder);
                    }
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        };
        return txnHelper.doInTransaction(callback, false, false);

    }

    /**
     * Check if parent folder needs to be deleted.
     *
     * @param nodeRef
     *            the node ref
     * @return true, if successful
     */
    private boolean checkIfParentFolderNeedsToBeDeleted(NodeRef nodeRef) {
        boolean checkIfParentFolderNeedsToBeDeleted = false;
        String fileName = (String) serviceRegistry.getNodeService().getProperty(nodeRef, ContentModel.PROP_NAME);
        NodeRef parentNodeRef = getParentFolderNodeRef(nodeRef);
        List<ChildAssociationRef> carList = serviceRegistry.getNodeService().getChildAssocs(parentNodeRef);
        int childSize = carList.size();
        if (childSize == 1) {
            ChildAssociationRef car = carList.get(0);
            NodeRef noderefTwo = car.getChildRef();
            String fileNameTwo = (String) serviceRegistry.getNodeService().getProperty(noderefTwo, ContentModel.PROP_NAME);
            if (fileName.equals(fileNameTwo)) {
                checkIfParentFolderNeedsToBeDeleted = true;
            }
        }
        return checkIfParentFolderNeedsToBeDeleted;
    }

    public Boolean hardDeleteNoChecks(final NodeRef documentNodeRef, final Boolean deleteParentFolderIfEmpty) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<Boolean> callback = new RetryingTransactionCallback<Boolean>() {
            @Override
            public Boolean execute() throws Throwable {
                if (documentNodeRef != null) {
                    NodeRef deleteParentFolder = null;
                    if (deleteParentFolderIfEmpty && checkIfParentFolderNeedsToBeDeleted(documentNodeRef)) {
                        deleteParentFolder = getParentFolderNodeRef(documentNodeRef);
                    }
                    deleteNodeRef(documentNodeRef);
                    if (null != deleteParentFolder) {
                        deleteNodeRef(deleteParentFolder);
                    }
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        };
        return txnHelper.doInTransaction(callback, false, false);
    }

}
