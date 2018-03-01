package gov.uspto.trademark.cms.repo.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum AccessLevel.
 */
public enum AccessLevel {

    /** The internal. */
    INTERNAL("internal"),

    /** The public. */
    PUBLIC("public"),

    /** Restricted */
    RESTRICTED("restricted");

    /** The Constant ACCESS_LEVEL_KEY. */
    public static final String ACCESS_LEVEL_KEY = "accessLevel";

    /** The access level. */
    private final String fieldAccessLevel;

    /**
     * Instantiates a new access level.
     *
     * @param accessLevel
     *            the access level
     */
    AccessLevel(String accessLevel) {
        this.fieldAccessLevel = accessLevel;
    }

    public String getFieldAccessLevel() {
        return fieldAccessLevel;
    }

    /**
     * Gets the access level.
     *
     * @param accessLevel
     *            the access level
     * @return the access level
     */
    public static AccessLevel getAccessLevel(String accessLevel) {
        for (AccessLevel al : AccessLevel.values()) {
            if (al.getFieldAccessLevel().equalsIgnoreCase(accessLevel)) {
                return al;
            }
        }
        return null;
    }

    /**
     * Gets the access level enumeration for a given String. Returns default
     * value
     *
     * @param accessLevel
     *            the access level
     * @return the access level
     */
    public static AccessLevel getAccessLevelOrDefault(String accessLevel) {
        AccessLevel al = AccessLevel.getAccessLevel(accessLevel);
        if (al == null) {
            al = PUBLIC;
        }
        return al;
    }

    /**
     * Returns the list of values based on the accessLevel hierarchy
     *
     * @param accessLevel
     * @return
     */
    public static List<String> getAccessLevelHierarchy(String accessLevel) {
        List<String> accessLevels = new ArrayList<String>();
        AccessLevel al = AccessLevel.getAccessLevelOrDefault(accessLevel);
        switch (al) {
        case PUBLIC:
            accessLevels.add(al.getFieldAccessLevel());
            break;
        case INTERNAL:
            accessLevels.add(PUBLIC.getFieldAccessLevel());
            accessLevels.add(al.getFieldAccessLevel());
            break;
        case RESTRICTED:
            accessLevels.add(PUBLIC.getFieldAccessLevel());
            accessLevels.add(INTERNAL.getFieldAccessLevel());
            accessLevels.add(al.getFieldAccessLevel());
            break;
        default:
            accessLevels.add(PUBLIC.getFieldAccessLevel());
        }
        return accessLevels;
    }
}
