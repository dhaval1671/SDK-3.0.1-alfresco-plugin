package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.aspects.BusinessVersionAspect;
import gov.uspto.trademark.cms.repo.model.aspects.ImageRelatedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MultimediaRelatedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.PcmRelatedAspect;

/**
 * Mark Type representation in the repository
 * <p/>
 * Created by bgummadi on 6/3/2014.
 */
public class MarkDoc extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "mark";

    /** The business version aspect. */
    @JsonUnwrapped
    private BusinessVersionAspect businessVersionAspect = new BusinessVersionAspect();

    /** The pcm related aspect. */
    @JsonUnwrapped
    private PcmRelatedAspect pcmRelatedAspect = new PcmRelatedAspect();

    /** The multimedia props. */
    public MultimediaRelatedAspect multimediaProps = new MultimediaRelatedAspect();

    /** The image props. */
    public ImageRelatedAspect imageProps = new ImageRelatedAspect();

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

    /**
     * Gets the image props.
     *
     * @return the image props
     */
    public ImageRelatedAspect getImageProps() {
        return imageProps;
    }

    /**
     * Sets the image props.
     *
     * @param imageProps
     *            the new image props
     */
    public void setImageProps(ImageRelatedAspect imageProps) {
        this.imageProps = imageProps;
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
     * Gets the business version aspect.
     *
     * @return the business version aspect
     */
    public BusinessVersionAspect getBusinessVersionAspect() {
        return businessVersionAspect;
    }

    /**
     * Sets the business version aspect.
     *
     * @param businessVersionAspect
     *            the new business version aspect
     */
    public void setBusinessVersionAspect(BusinessVersionAspect businessVersionAspect) {
        this.businessVersionAspect = businessVersionAspect;
    }

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return MarkDoc.TYPE;
    }

}
