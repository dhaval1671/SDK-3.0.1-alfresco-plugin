package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This method is used to locate the madridib id folder.
 * 
 * @author stank
 *
 */
@Component("madridNodeLocator")
public class MadridNodeLocator extends AbstractNodeLocator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java
     * .lang.String)
     */
    @Override
    public NodeRef locateNode(String id) {

        return locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.MADRIDIB_FOLDER_NAME, id);
    }

}
