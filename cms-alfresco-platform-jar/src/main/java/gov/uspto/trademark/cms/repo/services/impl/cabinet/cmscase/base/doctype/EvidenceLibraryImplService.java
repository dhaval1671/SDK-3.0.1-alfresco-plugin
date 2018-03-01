package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.DateFormatter;
import gov.uspto.trademark.cms.repo.model.aspects.EvidenceAspect;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.services.EvidenceLibraryService;
import gov.uspto.trademark.cms.repo.services.impl.AbstractBaseService;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * Created by stank on 5/9/2014.
 * 
 * @author stank
 */
@Component("EvidenceLibraryImplService")
public class EvidenceLibraryImplService extends AbstractBaseService implements EvidenceLibraryService {

    /** The Constant LOG. */
    private static final Logger log = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.EvidenceService#
     * getEvidenceLibFolderNodeRef()
     */
    @Override
    public NodeRef getEvidenceBankFolderNodeRef() {
        return getFolderNodeRef(TradeMarkModel.DOCUMENT_LIBRARY_FOLDER_NAME, TradeMarkModel.EVIDENCE_BANK_FOLDER_NAME);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.EvidenceService#
     * get2aEvidenceFileContent(org.alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    public ContentReader get2aEvidenceFileContent(NodeRef fileNodeRef) {
        return getContentReader(fileNodeRef, ContentModel.PROP_CONTENT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.EvidenceService#
     * get2aEvidenceFolderContents(java.util.List, java.util.List)
     */
    @Override
    public Map<String, List<Map<String, String>>> get2aEvidenceFolderContents(List<FileInfo> fileList,
            List<FileInfo> folderList) {
        log.debug("### Executing " + Thread.currentThread().getStackTrace()[TMConstants.ONE].getMethodName() + " ####");
        Map<String, List<Map<String, String>>> results = new HashMap<String, List<Map<String, String>>>();

        List<Map<String, String>> resultFileList = new ArrayList<Map<String, String>>();
        for (FileInfo child : fileList) {
            Map<String, String> fileProperties = new HashMap<String, String>();
            fileProperties.put("name", child.getName());
            fileProperties.put("mimetype", child.getContentData().getMimetype());
            fileProperties.put("size", Long.toString(child.getContentData().getSize()));
            fileProperties.put("modificationTime", DateFormatter.getUTCString(child.getModifiedDate()));

            Map<QName, Serializable> propMap = child.getProperties();
            String source = (String) propMap.get(TradeMarkModel.PROP_EVIDENCE_BANK_TWO_A_LIB_SOURCE);
            if (StringUtils.isNotBlank(source)) {
                fileProperties.put("source", source);
            }

            resultFileList.add(fileProperties);
        }
        results.put("files", resultFileList);

        List<Map<String, String>> resultFolderList = new ArrayList<Map<String, String>>();
        for (FileInfo child : folderList) {
            Map<String, String> folderProperties = new HashMap<String, String>();
            folderProperties.put("name", child.getName());
            folderProperties.put("modificationTime", DateFormatter.getUTCString(child.getModifiedDate()));
            resultFolderList.add(folderProperties);
        }
        results.put("folders", resultFolderList);

        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.uspto.trademark.cms.repo.services.EvidenceService#
     * processEvidenceBankFiles(org.alfresco.service.cmr.repository.NodeRef,
     * gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest)
     */
    @Override
    public CopyEvidenceRequest processEvidenceBankFiles(NodeRef nodeRefOfSourceFile, CopyEvidenceRequest copyEvidenceRequest) {
        Map<QName, Serializable> properties = serviceRegistry.getNodeService().getProperties(nodeRefOfSourceFile);
        String sourceProp = (String) properties.get(TradeMarkModel.PROP_EVIDENCE_BANK_TWO_A_LIB_SOURCE);
        if (StringUtils.isNotBlank(sourceProp)) {
            Evidence evi = copyEvidenceRequest.getMetadata();
            EvidenceAspect eviAsp = evi.getEvidenceAspect();
            eviAsp.setEvidenceSourceUrl(sourceProp);
        }
        return copyEvidenceRequest;
    }

}
