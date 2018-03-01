/**
 * 
 */
package gov.uspto.trademark.cms.repo.nodedelete;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * The Interface IDocumentDelete.
 *
 * @author stank
 */
public interface BehaviorDocumentDelete {

    /**
     * Delete.
     *
     * @param nodeRefOfFileToBeDeleted
     *            the node ref of file to be deleted
     * @return the boolean
     */
    Boolean delete(NodeRef nodeRefOfFileToBeDeleted);

    /**
     * Delete.
     *
     * @param nodeRefOfFileToBeDeleted
     *            the node ref of file to be deleted
     * @param deleteParentFolderIfEmpty
     *            the delete parent folder if empty
     * @return the boolean
     */
    Boolean delete(NodeRef nodeRefOfFileToBeDeleted, Boolean deleteParentFolderIfEmpty);

}
