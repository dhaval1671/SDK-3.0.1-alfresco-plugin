package gov.uspto.trademark.cms.repo.webscripts.utility;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class TopLevelFolder.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "name", "type", "children" })
public class TopLevelFolder {

    /** The name. */
    @JsonProperty("name")
    private String name;

    /** The type. */
    @JsonProperty("type")
    private String type;

    /** The children. */
    @JsonProperty("children")
    private List<Child> children = new ArrayList<Child>();

    /**
     * Gets the name.
     *
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type.
     *
     * @return The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the children.
     *
     * @return The children
     */
    @JsonProperty("children")
    public List<Child> getChildren() {
        return children;
    }

    /**
     * Sets the children.
     *
     * @param children
     *            The children
     */
    @JsonProperty("children")
    public void setChildren(List<Child> children) {
        this.children = children;
    }

}