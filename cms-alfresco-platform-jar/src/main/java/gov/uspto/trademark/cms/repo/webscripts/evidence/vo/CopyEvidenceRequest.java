package gov.uspto.trademark.cms.repo.webscripts.evidence.vo;

import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * Created by bgummadi on 9/12/2014.
 */
public class CopyEvidenceRequest {

    /** The document id. */
    private String documentId;

    /** The metadata. */
    private Evidence metadata;

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

    /**
     * Gets the metadata.
     *
     * @return the metadata
     */
    public Evidence getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata
     *            the new metadata
     */
    public void setMetadata(Evidence metadata) {
        this.metadata = metadata;
    }
}
