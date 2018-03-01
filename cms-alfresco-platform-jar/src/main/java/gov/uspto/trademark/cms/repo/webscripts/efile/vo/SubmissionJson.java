package gov.uspto.trademark.cms.repo.webscripts.efile.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Document;

/**
 * The Class SubmissionIncomingJson.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ SubmissionJson.DOCUMENT_ID, SubmissionJson.SERIAL_NUMBERS, SubmissionJson.DOCUMENT_TYPE,
        SubmissionJson.METADATA })
public class SubmissionJson {

    /** The Constant METADATA. */
    static final String METADATA = "metadata";

    /** The Constant DOCUMENT_TYPE. */
    static final String DOCUMENT_TYPE = "documentType";

    /** The Constant SERIAL_NUMBERS. */
    static final String SERIAL_NUMBERS = "serialNumbers";

    /** The Constant DOCUMENT_ID. */
    static final String DOCUMENT_ID = "documentId";

    /** The document id. */
    @JsonProperty(DOCUMENT_ID)
    private String documentId;

    /** The serial numbers. */
    @JsonProperty(SERIAL_NUMBERS)
    private List<String> serialNumbers = new ArrayList<String>();

    /** The document type. */
    @JsonProperty(DOCUMENT_TYPE)
    private String documentType;

    /** The metadata. */
    @JsonProperty(METADATA)
    private Document metadata;

    /**
     * Gets the document id.
     *
     * @return The documentId
     */
    @JsonProperty(DOCUMENT_ID)
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document id.
     *
     * @param documentId
     *            The documentId
     */
    @JsonProperty(DOCUMENT_ID)
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Gets the serial numbers.
     *
     * @return The serialNumbers
     */
    @JsonProperty(SERIAL_NUMBERS)
    public List<String> getSerialNumbers() {
        return serialNumbers;
    }

    /**
     * Sets the serial numbers.
     *
     * @param serialNumbers
     *            The serialNumbers
     */
    @JsonProperty(SERIAL_NUMBERS)
    public void setSerialNumbers(List<String> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    /**
     * Gets the document type.
     *
     * @return The documentType
     */
    @JsonProperty(DOCUMENT_TYPE)
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the document type.
     *
     * @param documentType
     *            The documentType
     */
    @JsonProperty(DOCUMENT_TYPE)
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the metadata.
     *
     * @return The metadata
     */
    @JsonProperty(METADATA)
    public Document getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata
     *            The metadata
     */
    @JsonProperty(METADATA)
    public void setMetadata(Document metadata) {
        this.metadata = metadata;
    }

}