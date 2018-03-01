package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class Result.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ Result.DOCUMENT_ID, Result.METADATA2 })
public class Result {

    /** The Constant METADATA2. */
    static final String METADATA2 = "metadata";

    /** The Constant DOCUMENT_ID. */
    static final String DOCUMENT_ID = "documentId";

    /** The document id. */
    @JsonProperty(DOCUMENT_ID)
    private String documentId;

    /** The metadata. */
    @JsonProperty(METADATA2)
    private Metadata metadata;

    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * Gets the metadata.
     *
     * @return The metadata
     */
    @JsonProperty(METADATA2)
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata
     *            The metadata
     */
    @JsonProperty(METADATA2)
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets the additional properties.
     *
     * @return the additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Sets the additional property.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}