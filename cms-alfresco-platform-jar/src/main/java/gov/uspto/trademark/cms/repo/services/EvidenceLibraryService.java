package gov.uspto.trademark.cms.repo.services;

import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * Created by bgummadi on 5/9/2014.
 */
@Component
public interface EvidenceLibraryService extends BaseService {

    /**
     * Gets the folder node ref.
     *
     * @param documentType
     *            the document type
     * @param rootNodeRef
     *            the root node ref
     * @param paths
     *            the paths
     * @return the folder node ref
     */
    NodeRef getFolderNodeRef(QName documentType, NodeRef rootNodeRef, String... paths);

    /**
     * Returns the folder contents for a given folder path under 2aEvidence
     * library.
     *
     * @param fileList
     *            the file list
     * @param folderList
     *            the folder list
     * @return Map<String, List<Map<String, String>>>
     */
    Map<String, List<Map<String, String>>> get2aEvidenceFolderContents(List<FileInfo> fileList, List<FileInfo> folderList);

    /**
     * Returns ContentReader of file name path supplied in String Array.
     *
     * @param fileNodeRef
     *            the file node ref
     * @return ContentReader
     */
    ContentReader get2aEvidenceFileContent(NodeRef fileNodeRef);

    /**
     * Returns the root NodeRef of 2A Evidence Library inside alfresco.
     * 
     * @return NodeRef
     */
    NodeRef getEvidenceBankFolderNodeRef();
    
    /**
     * Process evidence bank files.
     *
     * @param nodeRef
     *            the node ref
     * @param copyEvidenceRequest
     *            the copy evidence request
     * @return CopyEvidenceRequest
     * @Title: processEvidenceBankFiles
     * @Description:
     */
    CopyEvidenceRequest processEvidenceBankFiles(NodeRef nodeRef, CopyEvidenceRequest copyEvidenceRequest);

}
