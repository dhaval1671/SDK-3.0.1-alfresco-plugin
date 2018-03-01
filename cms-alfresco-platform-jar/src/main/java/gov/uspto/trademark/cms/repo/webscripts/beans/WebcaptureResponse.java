package gov.uspto.trademark.cms.repo.webscripts.beans;

/**
 * This class is used to build the response object for EvidenceBank-Webcapture
 * content creation.
 * 
 * @author stank
 *
 */
public class WebcaptureResponse extends TmngAlfResponse {

    /** The document id. */
    private String documentId;

    /** The serial number. */
    private String size;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
