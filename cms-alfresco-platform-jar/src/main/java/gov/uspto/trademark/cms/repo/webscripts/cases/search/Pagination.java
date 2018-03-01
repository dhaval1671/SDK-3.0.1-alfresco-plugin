package gov.uspto.trademark.cms.repo.webscripts.cases.search;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class Pagination.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "from", "size" })
public class Pagination {

    /** The Constant SIZE2. */
    private static final String SIZE2 = "size";

    /** The Constant FROM2. */
    private static final String FROM2 = "from";

    /** The from. */
    @JsonProperty(Pagination.FROM2)
    private Integer from;

    /** The size. */
    @JsonProperty(Pagination.SIZE2)
    private Integer size;

    /**
     * Gets the from.
     *
     * @return The from
     */
    @JsonProperty(Pagination.FROM2)
    public Integer getFrom() {
        return from;
    }

    /**
     * Sets the from.
     *
     * @param from
     *            The from
     */
    @JsonProperty(Pagination.FROM2)
    public void setFrom(Integer from) {
        this.from = from;
    }

    /**
     * Gets the size.
     *
     * @return The size
     */
    @JsonProperty(Pagination.SIZE2)
    public Integer getSize() {
        return size;
    }

    /**
     * Sets the size.
     *
     * @param size
     *            The size
     */
    @JsonProperty(Pagination.SIZE2)
    public void setSize(Integer size) {
        this.size = size;
    }

}