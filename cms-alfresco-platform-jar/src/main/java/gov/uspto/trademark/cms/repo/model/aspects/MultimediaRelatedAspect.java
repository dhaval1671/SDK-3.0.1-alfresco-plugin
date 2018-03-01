package gov.uspto.trademark.cms.repo.model.aspects;

/**
 * Multimedia representation in the repository
 *
 * Created by bgummadi on 6/2/2014.
 */

public class MultimediaRelatedAspect extends AbstractBaseAspect {

    /** The audio codec. */
    public String audioCodec;

    /** The audio compression type. */
    public String audioCompressionType;

    /** The video codec. */
    public String videoCodec;

    /** The video compression type. */
    public String videoCompressionType;

    /** The multimedia duration. */
    public String multimediaDuration;

    /** The multimedia start time. */
    public String multimediaStartTime;

    /** The multimedia comment. */
    public String multimediaComment;

    /** The active. */
    public Boolean active;

    /** The supported. */
    public Boolean supported;

    /** The converted. */
    public Boolean converted;

    /** The cont cd. */
    public String contCD;

    /** The description. */
    public String description;

    /**
     * Gets the audio codec.
     *
     * @return the audio codec
     */
    public String getAudioCodec() {
        return audioCodec;
    }

    /**
     * Sets the audio codec.
     *
     * @param audioCodec
     *            the new audio codec
     */
    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    /**
     * Gets the audio compression type.
     *
     * @return the audio compression type
     */
    public String getAudioCompressionType() {
        return audioCompressionType;
    }

    /**
     * Sets the audio compression type.
     *
     * @param audioCompressionType
     *            the new audio compression type
     */
    public void setAudioCompressionType(String audioCompressionType) {
        this.audioCompressionType = audioCompressionType;
    }

    /**
     * Gets the video codec.
     *
     * @return the video codec
     */
    public String getVideoCodec() {
        return videoCodec;
    }

    /**
     * Sets the video codec.
     *
     * @param videoCodec
     *            the new video codec
     */
    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    /**
     * Gets the video compression type.
     *
     * @return the video compression type
     */
    public String getVideoCompressionType() {
        return videoCompressionType;
    }

    /**
     * Sets the video compression type.
     *
     * @param videoCompressionType
     *            the new video compression type
     */
    public void setVideoCompressionType(String videoCompressionType) {
        this.videoCompressionType = videoCompressionType;
    }

    /**
     * Gets the multimedia duration.
     *
     * @return the multimedia duration
     */
    public String getMultimediaDuration() {
        return multimediaDuration;
    }

    /**
     * Sets the multimedia duration.
     *
     * @param multimediaDuration
     *            the new multimedia duration
     */
    public void setMultimediaDuration(String multimediaDuration) {
        this.multimediaDuration = multimediaDuration;
    }

    /**
     * Gets the multimedia start time.
     *
     * @return the multimedia start time
     */
    public String getMultimediaStartTime() {
        return multimediaStartTime;
    }

    /**
     * Sets the multimedia start time.
     *
     * @param multimediaStartTime
     *            the new multimedia start time
     */
    public void setMultimediaStartTime(String multimediaStartTime) {
        this.multimediaStartTime = multimediaStartTime;
    }

    /**
     * Gets the multimedia comment.
     *
     * @return the multimedia comment
     */
    public String getMultimediaComment() {
        return multimediaComment;
    }

    /**
     * Sets the multimedia comment.
     *
     * @param multimediaComment
     *            the new multimedia comment
     */
    public void setMultimediaComment(String multimediaComment) {
        this.multimediaComment = multimediaComment;
    }

    /**
     * Gets the active.
     *
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active.
     *
     * @param active
     *            the new active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets the supported.
     *
     * @return the supported
     */
    public Boolean getSupported() {
        return supported;
    }

    /**
     * Sets the supported.
     *
     * @param supported
     *            the new supported
     */
    public void setSupported(Boolean supported) {
        this.supported = supported;
    }

    /**
     * Gets the converted.
     *
     * @return the converted
     */
    public Boolean getConverted() {
        return converted;
    }

    /**
     * Sets the converted.
     *
     * @param converted
     *            the new converted
     */
    public void setConverted(Boolean converted) {
        this.converted = converted;
    }

    /**
     * Gets the cont cd.
     *
     * @return the cont cd
     */
    public String getContCD() {
        return contCD;
    }

    /**
     * Sets the cont cd.
     *
     * @param contCD
     *            the new cont cd
     */
    public void setContCD(String contCD) {
        this.contCD = contCD;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
