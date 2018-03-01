package gov.uspto.trademark.cms.repo.webscripts.beans;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;

/**
 * The Class BaseMetadataResponse.
 */
public class BaseMetadataResponse {

    /** The document id. */
    private String documentId;

    /** The document type. */
    private String documentType;

    /** The version. */
    private String version;

    /** The metadata. */
    private AbstractBaseType metadata;

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
     * Gets the document type.
     *
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the document type.
     *
     * @param documentType
     *            the new document type
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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
     * Gets the metadata.
     *
     * @return the metadata
     */
    public AbstractBaseType getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata
     *            the new metadata
     */
    public void setMetadata(AbstractBaseType metadata) {
        this.metadata = metadata;
    }

}
