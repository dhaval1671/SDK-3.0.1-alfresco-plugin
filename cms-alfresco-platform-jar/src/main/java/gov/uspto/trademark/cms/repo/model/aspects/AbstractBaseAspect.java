package gov.uspto.trademark.cms.repo.model.aspects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Super Type for all repository types
 *
 * Created by bgummadi on 6/2/2014.
 */
public abstract class AbstractBaseAspect {

    /** The properties. */
    @JsonIgnore
    protected List<String> properties = new ArrayList<String>();

    /**
     * Instantiates a new base aspect.
     *
     * @param properties
     *            the properties
     */
    public AbstractBaseAspect(String... properties) {
        this.properties.addAll(Arrays.asList(properties));
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public List<String> getProperties() {
        return this.properties;
    }

}
