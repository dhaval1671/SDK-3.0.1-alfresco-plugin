package gov.uspto.trademark.cms.repo.webscripts.evidence.vo;

/**
 * Created by bgummadi on 9/19/2014.
 */
public class DeleteEvidenceRequest {

    /** The document id. */
    private String documentId;

    /**
     * Gets the document id.
     *
     * @return the document id
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document id.
     *
     * @param documentId
     *            the new document id
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
