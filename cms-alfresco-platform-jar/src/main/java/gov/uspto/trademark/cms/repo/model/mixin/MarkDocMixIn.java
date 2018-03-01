package gov.uspto.trademark.cms.repo.model.mixin;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.aspects.ImageRelatedAspect;

/**
 * The Interface MultimediaMarkMixIn.
 */
public interface MarkDocMixIn extends MultimediaMixIn{

    /**
     * Gets the image props.
     *
     * @return the image props
     */
    @JsonUnwrapped
    @JsonProperty("imageProps")
    ImageRelatedAspect getImageProps();

}