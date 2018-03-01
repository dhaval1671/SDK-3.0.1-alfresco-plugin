package gov.uspto.trademark.cms.repo.webscripts.beans;

/**
 * The Class MarkVersionRetrieval.
 */
public class MarkVersionRetrieval {

    /** The document id. */
    private String documentId;

    /** The version. */
    private String version;

    /** The serial number. */
    private String serialNumber;

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
     * Gets the serial number.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the serial number.
     *
     * @param serialNumber
     *            the new serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
