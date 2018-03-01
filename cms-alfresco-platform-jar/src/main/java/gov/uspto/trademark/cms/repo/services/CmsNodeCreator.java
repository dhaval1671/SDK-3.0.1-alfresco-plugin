package gov.uspto.trademark.cms.repo.services;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

/**
 * This interface defines functions to locate the node based on the given
 * parameters.
 * 
 * @author vnondapaka
 *
 */
@Component
public interface CmsNodeCreator {

    /**
     * Create a folder for the given id.
     *
     * @param id
     *            the id
     * @return the node ref
     */
    NodeRef createNode(final String id);

    /**
     * Creates the folder node.
     *
     * @param parentNodeRef
     *            the parent node ref
     * @param folderName
     *            the folder name
     * @return the node ref
     */
    NodeRef createFolderNode(NodeRef parentNodeRef, String folderName);
}
