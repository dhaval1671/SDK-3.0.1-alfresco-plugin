/**
 * 
 */
package gov.uspto.trademark.cms.repo.nodedelete;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * The Class DocumentSoftDelete.
 *
 * @author stank
 */
@Component("DocumentSoftDelete")
public class DocumentSoftDelete extends CommonDelete implements BehaviorDocumentDelete {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodedelete.AlfrescoDocDelete#delete(org.
     * alfresco.service.cmr. repository.NodeRef)
     */
    @Override
    public Boolean delete(final NodeRef nodeRefOfFileToBeDeleted) {
        return delete(nodeRefOfFileToBeDeleted, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodedelete.AlfrescoDocDelete#delete(org.
     * alfresco.service.cmr. repository.NodeRef)
     */
    @Override
    public Boolean delete(final NodeRef nodeRefOfFileToBeDeleted, Boolean deleteParentFolderIfEmpty) {

        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<Boolean> callback = new RetryingTransactionCallback<Boolean>() {
            @Override
            public Boolean execute() throws Throwable {

                if (nodeRefOfFileToBeDeleted != null) {

                    String accessLevel = (String) serviceRegistry.getNodeService().getProperty(nodeRefOfFileToBeDeleted,
                            TradeMarkModel.ACCESS_LEVEL_QNAME);
                    if (accessLevel != null && accessLevel.equalsIgnoreCase(TMConstants.PUBLIC)) {
                        throw new TmngCmsException.AccessLevelRuleViolationException(HttpStatus.FORBIDDEN,
                                "Public documents cannot be deleted");
                    }

                    Map<QName, Serializable> properties = serviceRegistry.getNodeService()
                            .getProperties(nodeRefOfFileToBeDeleted);
                    String fn = (String) properties.get(ContentModel.PROP_NAME);

                    NodeRef nodeRefOfParentFolder = getParentFolderNodeRef(nodeRefOfFileToBeDeleted);

                    NodeRef deleteFolderNodeRef = cmsNodeCreator.createFolderNode(nodeRefOfParentFolder,
                            TMConstants.DELETED_FOLDER_NAME);

                    String baseName = FilenameUtils.getBaseName(fn);
                    String ext = FilenameUtils.getExtension(fn);
                    if (StringUtils.isNotBlank(ext)) {
                        ext = "'." + ext + "'";
                    }
                    String partName = new SimpleDateFormat("'_'yyyyMMddhhmmss_SSS" + ext).format(new Date());
                    String newFileName = baseName + partName;
                    // Append time stamp to NodeRef name.
                    serviceRegistry.getNodeService().setProperty(nodeRefOfFileToBeDeleted, ContentModel.PROP_NAME, newFileName);

                    // move the evidence file to delete folder.
                    serviceRegistry.getNodeService().moveNode(nodeRefOfFileToBeDeleted, deleteFolderNodeRef,
                            ContentModel.ASSOC_CONTAINS, null);
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);

    }

    public NodeRef ticrsSoftDelete(final String id, final String fileName, final String strDocType) {

        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            @Override
            public NodeRef execute() throws Throwable {
                
                NodeRef nodeRefOfFileToBeDeleted;
                QName qn;
                if (StringUtils.isNotBlank(strDocType)) {
                    qn = TradeMarkDocumentTypes.getTradeMarkQName(strDocType);
                    nodeRefOfFileToBeDeleted = cmsNodeLocator.locateNode(id, fileName, qn);
                } else {
                    NodeRef caseFolderNodeRef = cmsNodeLocator.locateNode(id);
                    nodeRefOfFileToBeDeleted = cmsNodeLocator.locateNodeRef(caseFolderNodeRef, fileName);
                    if (null != nodeRefOfFileToBeDeleted) {
                        qn = serviceRegistry.getFileFolderService().getFileInfo(nodeRefOfFileToBeDeleted).getType();
                    } else {
                        throw new TmngCmsException.DocumentDoesNotExistException("Requested File '" + fileName + "' Not Found");
                    }
                }                

                if (nodeRefOfFileToBeDeleted != null) {

                    Map<QName, Serializable> properties = serviceRegistry.getNodeService()
                            .getProperties(nodeRefOfFileToBeDeleted);
                    String fn = (String) properties.get(ContentModel.PROP_NAME);

                    NodeRef nodeRefOfParentFolder = getParentFolderNodeRef(nodeRefOfFileToBeDeleted);

                    NodeRef deleteFolderNodeRef = cmsNodeCreator.createFolderNode(nodeRefOfParentFolder,
                            TMConstants.DELETED_FOLDER_NAME);

                    String baseName = FilenameUtils.getBaseName(fn);
                    String ext = FilenameUtils.getExtension(fn);
                    if (StringUtils.isNotBlank(ext)) {
                        ext = "'." + ext + "'";
                    }
                    String partName = new SimpleDateFormat("'_'yyyyMMddhhmmss_SSS" + ext).format(new Date());
                    String newFileName = baseName + partName;
                    // Append time stamp to NodeRef name.
                    serviceRegistry.getNodeService().setProperty(nodeRefOfFileToBeDeleted, ContentModel.PROP_NAME, newFileName);

                    // move the evidence file to delete folder.
                    serviceRegistry.getNodeService().moveNode(nodeRefOfFileToBeDeleted, deleteFolderNodeRef,
                            ContentModel.ASSOC_CONTAINS, null);
                }
                return nodeRefOfFileToBeDeleted;
            }
        };
        return txnHelper.doInTransaction(callback, false, true);

    }

}
