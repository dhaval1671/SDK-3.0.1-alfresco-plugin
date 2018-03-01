package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This method is used to locate the legal-proceeding id folder.
 * 
 * @author stank
 *
 */
@Component("legalProceedingNodeLocator")
public class LegalProceedingNodeLocator extends AbstractNodeLocator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java
     * .lang.String)
     */
    @Override
    public NodeRef locateNode(String id) {
        NodeRef s = null;
        if (id.length() < TMConstants.FOUR) {
            s = locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.LEGAL_PROCEEDING_FOLDER_NAME, id);
        } else if (id.length() < TMConstants.SEVEN) {
            s = locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.LEGAL_PROCEEDING_FOLDER_NAME,
                    id.substring(TMConstants.ZERO, TMConstants.THREE), id);
        } else if (id.length() >= TMConstants.SEVEN) {
            s = locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.LEGAL_PROCEEDING_FOLDER_NAME,
                    id.substring(TMConstants.ZERO, TMConstants.THREE), id.substring(TMConstants.THREE, TMConstants.SIX), id);
        }
        return s;
    }

}
