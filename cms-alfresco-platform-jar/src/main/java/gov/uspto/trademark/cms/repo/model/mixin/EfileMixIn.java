package gov.uspto.trademark.cms.repo.model.mixin;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by stank on 9/10/2014.
 */
public interface EfileMixIn {

    /**
     * Gets the id.
     *
     * @return the id
     */
    @JsonProperty("trademarkId")
    String getId();

}