package gov.uspto.trademark.cms.repo.services;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

/**
 * Service to hadle trademarks. Each trademark is associated with a serial
 * number which uniquely identifies a mark. Mark can have a predefined set of
 * renditions which are supported.
 * 
 * @author bgummadi
 *
 */
public interface MultimediaMarkService extends MarkService {

    /**
     * Returns the name of the multimedia mark.
     *
     * @param markNodeRef
     *            the mark node ref
     * @return String
     */
    String getMultimediaMarkName(NodeRef markNodeRef);

    /**
     * Create a multimedia mark for a given serial number with given properties.
     *
     * @param serialNumber
     *            the serial number
     * @param fileName
     *            the file name
     * @param inputStream
     *            the input stream
     * @param properties
     *            the properties
     * @return NodeRef
     */
    NodeRef createMultimediaMark(String serialNumber, String fileName, InputStream inputStream,
            Map<QName, Serializable> properties);

    /**
     * Update a mark content and metadata for a given serial number.
     *
     * @param serialNumber
     *            the serial number
     * @param fileName
     *            the file name
     * @param inputStream
     *            the input stream
     * @param properties
     *            the properties
     * @return NodeRef
     */
    NodeRef updateMultimediaMark(String serialNumber, String fileName, InputStream inputStream,
            Map<QName, Serializable> properties);

}