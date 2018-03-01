package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class SearchOutGoingJson.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "searchInfo", "results" })
public class SearchOutGoingJson {

    /** The search info. */
    @JsonProperty("searchInfo")
    private SearchInfo searchInfo;

    /** The results. */
    @JsonProperty("results")
    private List<Result> results = new ArrayList<Result>();

    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the search info.
     *
     * @return The searchInfo
     */
    @JsonProperty("searchInfo")
    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    /**
     * Sets the search info.
     *
     * @param searchInfo
     *            The searchInfo
     */
    @JsonProperty("searchInfo")
    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    /**
     * Gets the results.
     *
     * @return The results
     */
    @JsonProperty("results")
    public List<Result> getResults() {
        return results;
    }

    /**
     * Sets the results.
     *
     * @param results
     *            The results
     */
    @JsonProperty("results")
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * Gets the additional properties.
     *
     * @return the additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Sets the additional property.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}