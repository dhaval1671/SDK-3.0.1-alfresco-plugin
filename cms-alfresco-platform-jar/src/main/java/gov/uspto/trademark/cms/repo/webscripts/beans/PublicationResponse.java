package gov.uspto.trademark.cms.repo.webscripts.beans;

import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * This class is used to build the response object for publication services.
 * 
 * @author stank
 *
 */
public class PublicationResponse extends TmngAlfResponse {

    /** The document id. */
    private DocumentId documentId;

    /** The serial number. */
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
     * @param serialNumber
     *            the serial number
     * @param type
     *            the type
     * @param fileName
     *            the file name
     */
    public void setDocumentId(String serialNumber, String type, String fileName) {
        setDocumentId(new DocumentId(TradeMarkModel.PUBLICATION_FOLDER_NAME, serialNumber, type, fileName));
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
