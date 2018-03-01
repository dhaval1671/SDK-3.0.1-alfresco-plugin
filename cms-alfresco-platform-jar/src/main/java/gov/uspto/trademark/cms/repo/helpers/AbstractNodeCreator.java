package gov.uspto.trademark.cms.repo.helpers;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileExistsException;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.EogNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.IdmNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.LegalProceedingNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.MadridNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;

/**
 * Base class defining the strategy to create folders. Sub classes has the
 * knowledge to create and navigate folders in/to the right location.
 */
public abstract class AbstractNodeCreator implements CmsNodeCreator {

    /** The cms node locator. */
    @Autowired
    @Qualifier(value = "caseNodeLocator")
    CmsNodeLocator cmsNodeLocator;

    /** The eog node locator. */
    @Autowired
    @Qualifier(value = "eogNodeLocator")
    EogNodeLocator eogNodeLocator;

    /** The idm node locator. */
    @Autowired
    @Qualifier(value = "idmNodeLocator")
    IdmNodeLocator idmNodeLocator;

    /** The madridib node locator. */
    @Autowired
    @Qualifier(value = "madridNodeLocator")
    MadridNodeLocator madridNodeLocator;

    /** The legal-proceeding node locator. */
    @Autowired
    @Qualifier(value = "legalProceedingNodeLocator")
    LegalProceedingNodeLocator legalProceedingNodeLocator;

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    /**
     * Creates a folder with a generic Alfresco folder type.
     *
     * @param parentNodeRef
     *            the parent node ref
     * @param folderName
     *            the folder name
     * @return the node ref
     */
    @Override
    public NodeRef createFolderNode(NodeRef parentNodeRef, String folderName) {
        return createFolderNode(parentNodeRef, folderName, ContentModel.TYPE_FOLDER);
    }

    /**
     * Creates a folder for a given Type with the given parameters.
     *
     * @param parentNodeRef
     *            the parent node ref
     * @param folderName
     *            the folder name
     * @param caseFolderQname
     *            the case folder qname
     * @return the node ref
     */
    protected NodeRef createFolderNode(NodeRef parentNodeRef, String folderName, QName caseFolderQname) {
        NodeRef nodeRef = cmsNodeLocator.locateNodeRef(parentNodeRef, folderName);
        if (nodeRef == null) {
            try {
                FileInfo fileInfo = serviceRegistry.getFileFolderService().create(parentNodeRef, folderName, caseFolderQname);
                nodeRef = fileInfo.getNodeRef();
            } catch (FileExistsException exception) {
                nodeRef = cmsNodeLocator.locateNodeRef(parentNodeRef, folderName);
            }

        }
        return nodeRef;
    }

}
