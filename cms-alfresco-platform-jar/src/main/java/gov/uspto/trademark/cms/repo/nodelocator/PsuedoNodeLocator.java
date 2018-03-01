package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This method is used to locate the case id(serialnumber) folder.
 * 
 * @author vnondapaka
 *
 */
@Component
public class PsuedoNodeLocator extends AbstractNodeLocator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java.
     * lang.String)
     */
    @Override
    public NodeRef locateNode(String id) {
        NodeRef nodeRef;
        nodeRef = locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME,
                id.substring(TMConstants.ZERO, TMConstants.THREE), id.substring(TMConstants.THREE, TMConstants.SIX), id);
        return nodeRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.nodelocator.AbstractNodeLocator#
     * locateNodeRef(org.alfresco.service.cmr.repository.NodeRef,
     * org.alfresco.service.namespace.QName, java.lang.String[])
     */
    @Override
    public NodeRef locateNodeRef(NodeRef nodeRef, QName docType, String... paths) {
        NodeRef psuedoNodeRef = super.locateNodeRef(nodeRef, paths);
        if (psuedoNodeRef != null
                && this.serviceRegistry.getNodeService().hasAspect(psuedoNodeRef, TradeMarkModel.ASPECT_MIGRATED)) {
            return psuedoNodeRef;
        }
        throw new TmngCmsException.FileCheckFailedException(HttpStatus.BAD_REQUEST,
                "Invalid document type, not a migrated document type");
    }

}