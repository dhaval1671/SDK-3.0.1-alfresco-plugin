/**
 * 
 */
package gov.uspto.trademark.cms.repo.nodedelete;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;

/**
 * The Class CommonDelete.
 *
 * @author stank
 */
public class CommonDelete {

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;
    
    @Autowired
    @Qualifier("TransactionService")    
    protected TransactionService transactionService;    

    /** The case node creator. */
    @Autowired
    @Qualifier(value = "caseNodeCreator")
    protected CmsNodeCreator cmsNodeCreator;
    
    @Autowired
    @Qualifier(value = "caseNodeLocator")    
    protected CmsNodeLocator cmsNodeLocator;

    /**
     * Gets the parent folder node ref.
     *
     * @param nodeRef
     *            the node ref
     * @return the parent folder node ref
     */
    protected NodeRef getParentFolderNodeRef(NodeRef nodeRef) {
        ChildAssociationRef car = serviceRegistry.getNodeService().getPrimaryParent(nodeRef);
        return car.getParentRef();
    }

    /**
     * Delete node ref.
     *
     * @param nodeRef
     *            the node ref
     */
    protected void deleteNodeRef(NodeRef nodeRef) {
        serviceRegistry.getNodeService().addAspect(nodeRef, ContentModel.ASPECT_TEMPORARY, null);
        serviceRegistry.getNodeService().deleteNode(nodeRef);
    }

}
