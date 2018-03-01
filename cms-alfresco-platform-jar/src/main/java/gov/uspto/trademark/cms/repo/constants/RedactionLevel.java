package gov.uspto.trademark.cms.repo.constants;

/**
 * The Enum RedactionLevel.
 */
public enum RedactionLevel {

    /** The none. */
    NONE("None"),

    /** The partial. */
    PARTIAL("Partial"),

    /** The full. */
    FULL("Full");

    /** The Constant REDACTION_LEVEL_KEY. */
    public static final String REDACTION_LEVEL_KEY = "redactionLevel";

    /** The redaction level. */
    private String redactionLevel;

    /**
     * Instantiates a new redaction level.
     *
     * @param redactionLevel
     *            the redaction level
     */
    RedactionLevel(String redactionLevel) {
        this.redactionLevel = redactionLevel;
    }

    /**
     * Gets the redaction level.
     *
     * @param redactionLevel
     *            the redaction level
     * @return the redaction level
     */
    public static RedactionLevel getRedactionLevel(String redactionLevel) {
        for (RedactionLevel al : RedactionLevel.values()) {
            if (al.redactionLevel.equalsIgnoreCase(redactionLevel)) {
                return al;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return redactionLevel;
    }

}
