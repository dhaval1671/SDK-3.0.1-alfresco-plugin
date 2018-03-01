package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This method is used to locate the case id(serialnumber) folder.
 * 
 * @author bgummadi
 *
 */
@Component("ticrsDocumentNodeLocator")
public class TicrsDocumentNodeLocator extends AbstractNodeLocator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java
     * .lang.String)
     */
    @Override
    public NodeRef locateNode(String id) {
        return locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.CASE_FOLDER_NAME,
                id.substring(TMConstants.ZERO, TMConstants.THREE), id.substring(TMConstants.THREE, TMConstants.SIX), id,
                TradeMarkModel.TICRS_FOLDER_NAME);
    }

}
