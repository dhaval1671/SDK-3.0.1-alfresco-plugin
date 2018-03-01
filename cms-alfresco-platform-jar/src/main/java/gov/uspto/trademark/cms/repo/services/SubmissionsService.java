package gov.uspto.trademark.cms.repo.services;

import java.util.List;

import gov.uspto.trademark.cms.repo.webscripts.beans.TmngAlfResponse;
import gov.uspto.trademark.cms.repo.webscripts.efile.vo.SubmissionJson;

/**
 * Created by bgummadi on 8/7/2015.
 */
public interface SubmissionsService {

    /**
     * Efile submission. Moving files from temporary storage to a case folder
     *
     * @param submissionJsonArray
     *            the source node ref list
     * @return the list
     */
    List<TmngAlfResponse> submitEfile(SubmissionJson[] submissionJsonArray);

}
