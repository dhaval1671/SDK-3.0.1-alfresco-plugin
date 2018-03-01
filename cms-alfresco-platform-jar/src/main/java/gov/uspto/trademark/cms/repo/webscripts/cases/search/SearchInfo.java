package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * This class is used to transform the search response into json.
 * 
 * @author vnondapaka
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "searchHitCount", "skipCount", "maxItemsPerPage", "searchText" })
public class SearchInfo {

    /** The search hit count. */
    @JsonProperty("searchHitCount")
    private Long searchHitCount;

    /** The skip count. */
    @JsonProperty("skipCount")
    private Integer skipCount;

    /** The max items per page. */
    @JsonProperty("maxItemsPerPage")
    private Integer maxItemsPerPage;

    /** The search text. */
    @JsonProperty("searchText")
    private String searchText;

    /**
     * Gets the search hit count.
     *
     * @return The searchHitCount
     */
    @JsonProperty("searchHitCount")
    public Long getSearchHitCount() {
        return searchHitCount;
    }

    /**
     * Sets the search hit count.
     *
     * @param searchHitCount
     *            The searchHitCount
     */
    @JsonProperty("searchHitCount")
    public void setSearchHitCount(Long searchHitCount) {
        this.searchHitCount = searchHitCount;
    }

    /**
     * Gets the skip count.
     *
     * @return The skipCount
     */
    @JsonProperty("skipCount")
    public Integer getSkipCount() {
        return skipCount;
    }

    /**
     * Sets the skip count.
     *
     * @param skipCount
     *            The skipCount
     */
    @JsonProperty("skipCount")
    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    /**
     * Gets the max items per page.
     *
     * @return The maxItemsPerPage
     */
    @JsonProperty("maxItemsPerPage")
    public Integer getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    /**
     * Sets the max items per page.
     *
     * @param maxItemsPerPage
     *            The maxItemsPerPage
     */
    @JsonProperty("maxItemsPerPage")
    public void setMaxItemsPerPage(Integer maxItemsPerPage) {
        this.maxItemsPerPage = maxItemsPerPage;
    }

    /**
     * Gets the search text.
     *
     * @return The searchText
     */
    @JsonProperty("searchText")
    public String getSearchText() {
        return searchText;
    }

    /**
     * Sets the search text.
     *
     * @param searchText
     *            The searchText
     */
    @JsonProperty("searchText")
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}