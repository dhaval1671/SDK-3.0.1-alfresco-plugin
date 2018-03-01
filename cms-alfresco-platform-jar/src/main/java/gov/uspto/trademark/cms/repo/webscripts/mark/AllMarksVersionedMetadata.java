package gov.uspto.trademark.cms.repo.webscripts.mark;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import gov.uspto.trademark.cms.repo.model.AbstractBaseType;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "documentId", "version", "serialNumber", "metadata" })
public class AllMarksVersionedMetadata {

    @JsonProperty("documentId")
    private String documentId;
    @JsonProperty("version")
    private String version;
    @JsonProperty("serialNumber")
    private String serialNumber;
    @JsonProperty("metadata")
    private List<AbstractBaseType> metadata = new ArrayList<AbstractBaseType>();

    /**
     *
     * @return The documentId
     */
    @JsonProperty("documentId")
    public String getDocumentId() {
        return documentId;
    }

    /**
     *
     * @param documentId
     *            The documentId
     */
    @JsonProperty("documentId")
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     *
     * @return The version
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     *            The version
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return The serialNumber
     */
    @JsonProperty("serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     *
     * @param serialNumber
     *            The serialNumber
     */
    @JsonProperty("serialNumber")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @JsonProperty("metadata")
    public List<AbstractBaseType> getMetadata() {
        return metadata;
    }

    /**
     *
     * @param metadata
     *            The metadata
     */
    @JsonProperty("metadata")
    public void setMetadata(List<AbstractBaseType> metadata) {
        this.metadata = metadata;
    }

}