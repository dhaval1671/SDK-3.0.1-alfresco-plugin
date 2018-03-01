package gov.uspto.trademark.cms.repo.model.aspects;

/**
 * The Class LegacyDocumentTypeAspect.
 */
public class LegacyDocumentTypeAspect {

    /** The doc code. */
    private String docCode;

    /** The legacy category. */
    private String legacyCategory;

    /**
     * Gets the doc code.
     *
     * @return the doc code
     */
    public String getDocCode() {
        return docCode;
    }

    /**
     * Sets the doc code.
     *
     * @param docCode
     *            the new doc code
     */
    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    /**
     * Gets the legacy category.
     *
     * @return the legacy category
     */
    public String getLegacyCategory() {
        return legacyCategory;
    }

    /**
     * Sets the legacy category.
     *
     * @param legacyCategory
     *            the new legacy category
     */
    public void setLegacyCategory(String legacyCategory) {
        this.legacyCategory = legacyCategory;
    }

}
