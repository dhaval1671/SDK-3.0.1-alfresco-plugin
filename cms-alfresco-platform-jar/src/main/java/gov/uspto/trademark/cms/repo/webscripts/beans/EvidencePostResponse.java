package gov.uspto.trademark.cms.repo.webscripts.beans;

/**
 * The Class EvidencePostResponse.
 */
public class EvidencePostResponse extends PostResponse {

    /** The access level. */
    private String accessLevel;

    /**
     * Gets the access level.
     *
     * @return the access level
     */
    public String getAccessLevel() {
        return accessLevel;
    }

    /**
     * Sets the access level.
     *
     * @param accessLevel
     *            the new access level
     */
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

}
