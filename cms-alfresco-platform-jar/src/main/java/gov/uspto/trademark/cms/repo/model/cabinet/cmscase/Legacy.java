package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.aspects.MultimediaRelatedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.PcmRelatedAspect;

/**
 * Created by stank on 7/23/2014.
 */
public class Legacy extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "legacy";

    /** The multimedia props. */
    public MultimediaRelatedAspect multimediaProps = new MultimediaRelatedAspect();

    /** The pcm related aspect. */
    @JsonUnwrapped
    private PcmRelatedAspect pcmRelatedAspect = new PcmRelatedAspect();

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Legacy.TYPE;
    }

    /**
     * Gets the pcm related aspect.
     *
     * @return the pcm related aspect
     */
    public PcmRelatedAspect getPcmRelatedAspect() {
        return pcmRelatedAspect;
    }

    /**
     * Sets the pcm related aspect.
     *
     * @param pcmRelatedAspect
     *            the new pcm related aspect
     */
    public void setPcmRelatedAspect(PcmRelatedAspect pcmRelatedAspect) {
        this.pcmRelatedAspect = pcmRelatedAspect;
    }

    /**
     * Gets the multimedia props.
     *
     * @return the multimedia props
     */
    public MultimediaRelatedAspect getMultimediaProps() {
        return multimediaProps;
    }

    /**
     * Sets the multimedia props.
     *
     * @param multimediaProps
     *            the new multimedia props
     */
    public void setMultimediaProps(MultimediaRelatedAspect multimediaProps) {
        this.multimediaProps = multimediaProps;
    }

}
