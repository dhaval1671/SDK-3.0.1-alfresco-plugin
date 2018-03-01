package gov.uspto.trademark.cms.repo.model.aspects.mixin;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The Interface EvidenceAspectMixIn.
 */
public interface EvidenceAspectMixIn {

    /**
     * Gets the evidence source url.
     *
     * @return the evidence source url
     */
    @JsonProperty("sourceURL")
    String getEvidenceSourceUrl();

    /**
     * Gets the evidence source type.
     *
     * @return the evidence source type
     */
    @JsonProperty("sourceType")
    String getEvidenceSourceType();

    /**
     * Gets the evidence source type id.
     *
     * @return the evidence source type id
     */
    @JsonProperty("sourceReference")
    String getEvidenceSourceTypeId();

    /**
     * Gets the evidence group names.
     *
     * @return the evidence group names
     */
    @JsonProperty("groupName")
    String getEvidenceGroupNames();

}