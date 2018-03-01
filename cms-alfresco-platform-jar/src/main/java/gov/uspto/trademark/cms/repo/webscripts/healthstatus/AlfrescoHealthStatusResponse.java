package gov.uspto.trademark.cms.repo.webscripts.healthstatus;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class AlfrescoHealthStatusResponse is used to determine the health status
 * of Alfresco and its dependencies.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "name", "status", "details" })
public class AlfrescoHealthStatusResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("details")
    private List<Detail> details = new ArrayList<Detail>();

    /**
     *
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *            The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *            The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return The details
     */
    @JsonProperty("details")
    public List<Detail> getDetails() {
        return details;
    }

    /**
     *
     * @param details
     *            The details
     */
    @JsonProperty("details")
    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}