package gov.uspto.trademark.cms.repo.services;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

/**
 * Service to hadle trademarks. Each trademark is associated with a serial
 * number which uniquely identifies a mark. Mark can have a predefined set of
 * renditions which are supported.
 *
 * @author stank
 */
public interface MarkService extends BaseService {

    /**
     * Gets the trade mark node ref.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumber
     *            the version number
     * @param documentType
     *            the document type
     * @return the trade mark node ref
     */
    NodeRef getTradeMarkNodeRef(String serialNumber, String versionNumber, QName documentType);

    /**
     * Gets the trade mark node ref.
     *
     * @param serialNumber
     *            the serial number
     * @param documentType
     *            the document type
     * @param aspectType
     *            the aspect type
     * @return the trade mark node ref
     */
    NodeRef getTradeMarkNodeRef(String serialNumber, QName documentType);

}