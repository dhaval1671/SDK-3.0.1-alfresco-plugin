package gov.uspto.trademark.cms.repo.webscripts.beans;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * Created by bgummadi on 5/29/2014.
 */
public class PostResponse extends TmngAlfResponse {

    /** The document id. */
    private DocumentId documentId;

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
     * @param serialNumber
     *            the serial number
     * @param type
     *            the type
     * @param fileName
     *            the file name
     */
    public void setDocumentId(String serialNumber, String type, String fileName) {
        setDocumentId(new DocumentId(TradeMarkModel.CASE_FOLDER_NAME, serialNumber, type, fileName));
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
