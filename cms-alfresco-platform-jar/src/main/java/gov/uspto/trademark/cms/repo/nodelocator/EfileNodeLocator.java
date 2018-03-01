package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.EfileNodeCreator;

@Component("eFileNodeLocator")
public class EfileNodeLocator extends AbstractNodeLocator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator#locateNode(java.
     * lang.String)
     */
    @Override
    public NodeRef locateNode(String id) {
        String idPart[] = id.split(EfileNodeCreator.ID_SEPARATOR);
        return locateNodeRef(TradeMarkModel.EFILE_DRIVE, TradeMarkModel.TYPE_EFILE_FOLDER, idPart[TMConstants.ZERO],
                idPart[TMConstants.ONE], idPart[TMConstants.TWO]);
    }
}