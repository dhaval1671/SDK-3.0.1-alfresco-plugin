package gov.uspto.trademark.cms.repo.model.mixin;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.aspects.MultimediaRelatedAspect;

public interface MultimediaMixIn {
    
    
    /**
     * Gets the multimedia props.
     *
     * @return the multimedia props
     */
    @JsonUnwrapped
    @JsonProperty("multimediaProps")
    MultimediaRelatedAspect getMultimediaProps();    

}
