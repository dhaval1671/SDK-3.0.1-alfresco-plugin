package gov.uspto.trademark.cms.repo.services;

import java.util.List;

import gov.uspto.trademark.cms.repo.webscripts.beans.EfileDocumentMetadataResponse;

/**
 * Created by stank on 7/23/2014.
 */
public interface EfileService extends CmsCommonService {

    /**
     * Returns all documents from the temporary storage. Files are stored in a
     * temporary storage before those files are submitted. Submission moves
     * files from temporary storage to the case folder
     *
     * @param globalId
     *            the serial number
     * @return the efile all document properties
     */
    List<EfileDocumentMetadataResponse> getDocumentList(String globalId);

}
