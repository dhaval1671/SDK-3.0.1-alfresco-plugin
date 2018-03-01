package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class Query.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ Query.MATCH2 })
public class Query {

    /** The Constant MATCH2. */
    static final String MATCH2 = "match";

    /** The match. */
    @JsonProperty(MATCH2)
    private String match;

    /**
     * Gets the match.
     *
     * @return The match
     */
    @JsonProperty(MATCH2)
    public String getMatch() {
        return match;
    }

    /**
     * Sets the match.
     *
     * @param match
     *            The match
     */
    @JsonProperty(MATCH2)
    public void setMatch(String match) {
        this.match = match;
    }

}