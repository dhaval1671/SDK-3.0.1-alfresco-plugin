package gov.uspto.trademark.cms.repo.model.aspects;

import java.util.Map;

/**
 * The Class EfileAspect.
 */
public class EfileAspect {

    /** The custom properties. */
    private Map<String, String> customProperties;

    /**
     * Gets the custom properties.
     *
     * @return the custom properties
     */
    public Map<String, String> getCustomProperties() {
        return customProperties;
    }

    /**
     * Sets the custom properties.
     *
     * @param customProperties
     *            the custom properties
     */
    public void setCustomProperties(Map<String, String> customProperties) {
        this.customProperties = customProperties;
    }
}
