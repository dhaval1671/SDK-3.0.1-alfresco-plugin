package gov.uspto.trademark.cms.repo.webscripts.beans;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * The Class Submission Response is used to form the response object for e-file
 * submission.
 *
 * 
 */
public class SubmissionResponse extends TmngAlfResponse {

    /** The document id. */
    private DocumentId documentId;

    /** The version. */
    private String version;

    /** The id. */
    private String id;

    /**
     * Gets the document id.
     *
     * @return the document id
     */
    public String getDocumentId() {
        return documentId.getId();
    }

    /**
     * Sets the document id.
     *
     * @param documentId
     *            the new document id
     */
    public void setDocumentId(DocumentId documentId) {
        this.documentId = documentId;
    }

    /**
     * Sets the document id.
     *
     * @param efileId
     *            the efile id
     * @param type
     *            the type
     * @param fileName
     *            the file name
     */
    public void setDocumentId(String efileId, String type, String fileName) {
        setDocumentId(new DocumentId(TradeMarkModel.SUBMISSIONS_FOLDER_NAME, efileId, type, fileName));
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the new version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

}
