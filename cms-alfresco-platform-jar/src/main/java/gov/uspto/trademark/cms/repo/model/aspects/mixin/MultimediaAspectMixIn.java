package gov.uspto.trademark.cms.repo.model.aspects.mixin;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The Interface MultimediaAspectMixIn.
 */
public interface MultimediaAspectMixIn {

    /**
     * Gets the multimedia start time.
     *
     * @return the multimedia start time
     */
    @JsonProperty("mm-StartTime")
    String getMultimediaStartTime();

    /**
     * Gets the multimedia comment.
     *
     * @return the multimedia comment
     */
    @JsonProperty("mm-Comment")
    String getMultimediaComment();

    /**
     * Gets the multimedia duration.
     *
     * @return the multimedia duration
     */
    @JsonProperty("mm-Duration")
    String getMultimediaDuration();

    /**
     * Gets the audio codec.
     *
     * @return the audio codec
     */
    @JsonProperty("codecAudio")
    String getAudioCodec();

    /**
     * Gets the video codec.
     *
     * @return the video codec
     */
    @JsonProperty("codecVideo")
    String getVideoCodec();

    /**
     * Gets the cont cd.
     *
     * @return the cont cd
     */
    @JsonProperty("cont-cd")
    String getContCD();

}