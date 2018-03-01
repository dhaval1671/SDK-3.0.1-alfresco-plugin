package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This class is used to locate the eogTemplate drive folder for the
 * serialnumber.
 * 
 * @author vnondapaka
 *
 */
@Component("eogTemplateNodeLocator")
public class EogTemplateNodeLocator extends AbstractNodeLocator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java
     * .lang.String)
     */
    @Override
    public NodeRef locateNode(String id) {
        return locateNodeRef(TradeMarkModel.EFILE_DRIVE, TradeMarkModel.TYPE_EOG_TEMPLATE_FOLDER, id);

    }
}