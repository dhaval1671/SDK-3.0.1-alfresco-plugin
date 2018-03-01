package gov.uspto.trademark.cms.repo.model.aspects;

import java.util.List;

/**
 * Evidence aspect representation in the repository
 *
 * Created by bgummadi on 6/2/2014.
 */
public class EvidenceAspect extends AbstractBaseAspect {

    /** The evidence source url. */
    private String evidenceSourceUrl;

    /** The evidence source type. */
    private String evidenceSourceType;

    /** The evidence source type id. */
    private String evidenceSourceTypeId;

    /** The evidence group names. */
    private List<String> evidenceGroupNames;

    /** The evidence category. */
    private String evidenceCategory;

    /**
     * Gets the evidence source url.
     *
     * @return the evidence source url
     */
    public String getEvidenceSourceUrl() {
        return evidenceSourceUrl;
    }

    /**
     * Sets the evidence source url.
     *
     * @param evidenceSourceUrl
     *            the new evidence source url
     */
    public void setEvidenceSourceUrl(String evidenceSourceUrl) {
        this.evidenceSourceUrl = evidenceSourceUrl;
    }

    /**
     * Gets the evidence source type.
     *
     * @return the evidence source type
     */
    public String getEvidenceSourceType() {
        return evidenceSourceType;
    }

    /**
     * Sets the evidence source type.
     *
     * @param evidenceSourceType
     *            the new evidence source type
     */
    public void setEvidenceSourceType(String evidenceSourceType) {
        this.evidenceSourceType = evidenceSourceType;
    }

    /**
     * Gets the evidence source type id.
     *
     * @return the evidence source type id
     */
    public String getEvidenceSourceTypeId() {
        return evidenceSourceTypeId;
    }

    /**
     * Sets the evidence source type id.
     *
     * @param evidenceSourceTypeId
     *            the new evidence source type id
     */
    public void setEvidenceSourceTypeId(String evidenceSourceTypeId) {
        this.evidenceSourceTypeId = evidenceSourceTypeId;
    }

    /**
     * Gets the evidence group names.
     *
     * @return the evidence group names
     */
    public List<String> getEvidenceGroupNames() {
        return evidenceGroupNames;
    }

    /**
     * Sets the evidence group names.
     *
     * @param evidenceGroupNames
     *            the new evidence group names
     */
    public void setEvidenceGroupNames(List<String> evidenceGroupNames) {
        this.evidenceGroupNames = evidenceGroupNames;
    }

    /**
     * Gets the evidence category.
     *
     * @return the evidence category
     */
    public String getEvidenceCategory() {
        return evidenceCategory;
    }

    /**
     * Sets the evidence category.
     *
     * @param evidenceCategory
     *            the new evidence category
     */
    public void setEvidenceCategory(String evidenceCategory) {
        this.evidenceCategory = evidenceCategory;
    }

}
