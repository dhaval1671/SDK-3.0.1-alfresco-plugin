package gov.uspto.trademark.cms.repo.nodelocator;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Created by stank on May/19/2017.
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */
@Component("legacyTicrsDocumentNodeLocator")
public class LegacyTicrsDocumentNodeLocator extends AbstractNodeLocator {

    
    private static final String _1_0 = "1.0";
    private static final String TICRSIMAGERETRIEVAL = "TICRSIMAGERETRIEVAL";

    public NodeRef getXmlXslDtdFolderNodeRef() {
        return locateNodeRef(TradeMarkModel.CABINET_FOLDER_NAME, TradeMarkModel.XML_XSL_DTD_FOLDER_NAME);
    }

    public NodeRef getExportPathFolderNodeRef() {
        return locateNodeRefWithException(getXmlXslDtdFolderNodeRef(), TradeMarkModel.EXPORTPATH_FOLDER_NAME);
    }

    public NodeRef getDtdXslFolderNodeRef() {
        return locateNodeRefWithException(getExportPathFolderNodeRef(), TradeMarkModel.DTD_XSL_FOLDER_NAME);
    }    
    
    public NodeRef locateLegacyTicrsXslDocumentNode(String docType, String versionFolder, String fileName) {
        return locateNodeRefWithException(getDtdXslFolderNodeRef(), docType, versionFolder, TICRSIMAGERETRIEVAL, _1_0, fileName);
    }

    @Override
    public NodeRef locateNode(String id) {
        return null;
    }



}
