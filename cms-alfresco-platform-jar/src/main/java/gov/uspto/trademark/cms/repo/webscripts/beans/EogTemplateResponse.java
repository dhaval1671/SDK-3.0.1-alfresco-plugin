package gov.uspto.trademark.cms.repo.webscripts.beans;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * The Class EogTemplateResponse.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class EogTemplateResponse extends TmngAlfResponse {

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
    public void setDocumentId(String id, String type, String fileName) {
        setDocumentId(new DocumentId(TradeMarkModel.EFILE_DRIVE, "eogtemplate", id, fileName));
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
