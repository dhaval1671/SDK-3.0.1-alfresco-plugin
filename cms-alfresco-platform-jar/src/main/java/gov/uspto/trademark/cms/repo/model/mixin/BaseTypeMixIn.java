package gov.uspto.trademark.cms.repo.model.mixin;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bgummadi on 9/10/2014.
 */
public interface BaseTypeMixIn {

    /**
     * Gets the version.
     *
     * @return the version
     */
    @JsonProperty("versionLabel")
    String getVersion();

    /**
     * Gets the creation time.
     *
     * @return the creation time
     */
    @JsonProperty("created")
    Date getCreationTime();

    /**
     * Gets the modification time.
     *
     * @return the modification time
     */
    @JsonProperty("modified")
    Date getModificationTime();

    /**
     * Gets the document type.
     *
     * @return the document type
     */
    @JsonIgnore
    String getDocumentType();

    /**
     * The Interface ClientBaseTypeMixIn.
     */
    interface ClientBaseTypeMixIn {

        /**
         * Gets the content.
         *
         * @return the content
         */
        @JsonIgnore
        String getContent();

    }

}