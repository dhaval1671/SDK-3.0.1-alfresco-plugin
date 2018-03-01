package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class SearchIncomingJson.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "pagination", "query" })

/**
 * The Class SearchIncomingJson.
 */
public class SearchIncomingJson {

    /** The Constant QUERY2. */
    private static final String QUERY2 = "query";

    /** The Constant PAGINATION2. */
    private static final String PAGINATION2 = "pagination";

    /** The pagination. */
    @JsonProperty(SearchIncomingJson.PAGINATION2)
    private Pagination pagination;

    /** The query. */
    @JsonProperty(SearchIncomingJson.QUERY2)
    private Query query;

    /**
     * Gets the pagination.
     *
     * @return The pagination
     */
    @JsonProperty(SearchIncomingJson.PAGINATION2)
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Sets the pagination.
     *
     * @param pagination
     *            The pagination
     */
    @JsonProperty(SearchIncomingJson.PAGINATION2)
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * Gets the query.
     *
     * @return The query
     */
    @JsonProperty(SearchIncomingJson.QUERY2)
    public Query getQuery() {
        return query;
    }

    /**
     * Sets the query.
     *
     * @param query
     *            The query
     */
    @JsonProperty(SearchIncomingJson.QUERY2)
    public void setQuery(Query query) {
        this.query = query;
    }

}