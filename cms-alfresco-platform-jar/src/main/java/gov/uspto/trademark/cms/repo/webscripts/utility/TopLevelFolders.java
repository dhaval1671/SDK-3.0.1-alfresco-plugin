package gov.uspto.trademark.cms.repo.webscripts.utility;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class TopLevelFolders.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "topLevelFolders" })
public class TopLevelFolders {

    /** The Top level folders. */
    @JsonProperty("TopLevelFolders")
    private List<TopLevelFolder> topLevelFolders = new ArrayList<TopLevelFolder>();

    /**
     * Gets the top level folders.
     *
     * @return The TopLevelFolders
     */
    @JsonProperty("topLevelFolders")
    public List<TopLevelFolder> getTopLevelFolders() {
        return topLevelFolders;
    }

    /**
     * Sets the top level folders.
     *
     * @param topLevelFolders
     *            The TopLevelFolders
     */
    @JsonProperty("topLevelFolders")
    public void setTopLevelFolders(List<TopLevelFolder> topLevelFolders) {
        this.topLevelFolders = topLevelFolders;
    }

}