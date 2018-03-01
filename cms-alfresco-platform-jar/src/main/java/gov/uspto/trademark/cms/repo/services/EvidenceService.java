package gov.uspto.trademark.cms.repo.services;

import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.webscripts.beans.EvidencePostResponse;
import gov.uspto.trademark.cms.repo.webscripts.beans.PostResponse;
import gov.uspto.trademark.cms.repo.webscripts.evidence.vo.CopyEvidenceRequest;

/**
 * Created by bgummadi on 5/9/2014.
 */
@Component
public interface EvidenceService extends BaseService {

    /**
     * Deletes Evidence file names supplied for given serial number.
     *
     * @param serialNumber
     *            the serial number
     * @param deleteFileList
     *            the delete file list
     * @return List<EvidencePostResponse>
     * @throws EvidenceDeleteFailedException
     *             the evidence delete failed exception
     */
    List<PostResponse> deleteEvidences(String serialNumber, List<NodeRef> deleteFileList)
            throws TmngCmsException.AccessLevelRuleViolationException;

    /**
     * Copies given evidence files into the case(serial number).
     *
     * @param serialNumber
     *            the serial number
     * @param caseFolderNodeRef
     *            the case folder node ref
     * @param sourceData
     *            the source data
     * @return List<NodeRef>
     */
    List<EvidencePostResponse> copyEvidences(String serialNumber, NodeRef caseFolderNodeRef,
            Map<NodeRef, CopyEvidenceRequest> sourceData);

    /**
     * Applies given metadata for all evidence files inside given case folder.
     *
     * @param serialNumber
     *            the serial number
     * @param caseFolderNodeRef
     *            the case folder node ref
     * @param sourceData
     *            the source data
     * @return List<NodeRef>
     */
    List<EvidencePostResponse> bulkMetadataUpdateEvidences(String serialNumber, NodeRef caseFolderNodeRef,
            Map<NodeRef, CopyEvidenceRequest> sourceData);
}
