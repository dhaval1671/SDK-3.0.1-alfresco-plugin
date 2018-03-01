package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.springframework.stereotype.Component;

/**
 * This interface defines functions to locate the node based on the given
 * parameters.
 * 
 * @author vnondapaka
 *
 */
@Component
public interface CmsNodeLocator {

    /**
     * This method is used to locate the node based on the given ID, File Name
     * and Document Type.
     *
     * @param id
     *            the id
     * @param fileName
     *            the file name
     * @param docType
     *            the doc type
     * @return nodeRef
     */
    NodeRef locateNode(String id, String fileName, QName docType);

    /**
     * This method is used to locate the node in the given node based on the
     * path and doc type.
     *
     * @param nodeRef
     *            the node ref
     * @param docType
     *            the doc type
     * @param paths
     *            the paths
     * @return nodeRef
     */
    NodeRef locateNodeRef(NodeRef nodeRef, QName docType, String... paths);

    /**
     * This method is used to locate the node based on the path.
     *
     * @param paths
     *            the paths
     * @return nodeRef
     */
    NodeRef locateNodeRef(String... paths);

    /**
     * This method is used to locate the node based on the document type and the
     * path.
     *
     * @param docType
     *            the doc type
     * @param paths
     *            the paths
     * @return nodeRef
     */
    NodeRef locateNodeRef(QName docType, String... paths);

    /**
     * This method is used to locate the node of an id folder.
     *
     * @param id
     *            the id
     * @return nodeRef
     */
    NodeRef locateNode(String id);

    /**
     * This method is used to locate the node in the given node based on path.
     *
     * @param nodeRef
     *            the node ref
     * @param paths
     *            the paths
     * @return nodeRef
     */
    NodeRef locateNodeRef(NodeRef nodeRef, String... paths);

    /**
     * This method is used to locate the node in the given node based on
     * version, doc type and path.
     *
     * @param nodeRef
     *            the node ref
     * @param version
     *            the version
     * @param docType
     *            the doc type
     * @param paths
     *            the paths
     * @return the node ref
     */
    NodeRef locateNodeRef(NodeRef nodeRef, String version, QName docType, String... paths);

}
