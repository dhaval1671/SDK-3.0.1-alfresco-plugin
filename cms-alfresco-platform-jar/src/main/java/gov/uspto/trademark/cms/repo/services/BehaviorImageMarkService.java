package gov.uspto.trademark.cms.repo.services;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;

import gov.uspto.trademark.cms.repo.constants.MarkRenditions;

/**
 * Service to hadle trademarks. Each trademark is associated with a serial
 * number which uniquely identifies a mark. Mark can have a predefined set of
 * renditions which are supported.
 *
 * @author bgummadi
 */
public interface BehaviorImageMarkService extends MarkService {

    /**
     * Returns the content reader for a given version of the mark.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumber
     *            the version number
     * @return the mark reader
     */
    ContentReader getMarkReader(String serialNumber, String versionNumber);

    /**
     * Returns the name of the mark.
     *
     * @param markNodeRef
     *            the mark node ref
     * @return the mark name
     */
    String getMarkName(NodeRef markNodeRef);

    /**
     * Checks if renditions can be supported for a given mimeType
     *
     * @param mimeType
     * @return
     */
    boolean isRenditionSupported(String mimeType);

    /**
     * Returns a rendition of the mark for a NodeRef and rendition name.
     *
     * @param nodeRef
     *            the node ref
     * @param renditionName
     *            the rendition name
     * @return the mark rendition reader
     */
    ContentReader getMarkRenditionReader(NodeRef nodeRef, String renditionName);

    /**
     * Creates rendition for a given Mark. Set of Renditions are defined in
     * MarkRenditions which is validated before a rendition is created
     *
     * @param sourceNode
     *            the source node
     * @param renditionName
     *            the rendition name
     * @return the child association ref
     */
    ChildAssociationRef createRendition(NodeRef sourceNode, MarkRenditions renditionName);

}