package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This method is used to locate the case id(serialnumber) folder.
 * 
 * @author vnondapaka
 *
 */
@Component("idmNodeLocator")
public class IdmNodeLocator extends AbstractNodeLocator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java.
     * lang.String)
     */
    @Override
    public NodeRef locateNode(String id) {
        NodeRef nodeRef = null;
        nodeRef = locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.PUBLICATION_FOLDER_NAME,
                TradeMarkModel.IDM_FOLDER_NAME, id);
        return nodeRef;
    }

}
