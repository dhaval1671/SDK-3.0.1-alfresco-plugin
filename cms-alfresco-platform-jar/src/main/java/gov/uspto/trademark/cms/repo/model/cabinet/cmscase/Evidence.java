package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

import org.codehaus.jackson.annotate.JsonUnwrapped;

import gov.uspto.trademark.cms.repo.model.aspects.EvidenceAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MultimediaRelatedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.PcmRelatedAspect;

/**
 * Evidence Type representation in the repository
 * <p/>
 * Created by bgummadi on 6/2/2014.
 */
public class Evidence extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "evidence";

    /** The evidence aspect. */
    @JsonUnwrapped
    private EvidenceAspect evidenceAspect = new EvidenceAspect();

    /** The multimedia props. */
    public MultimediaRelatedAspect multimediaProps = new MultimediaRelatedAspect();

    /** The pcm related aspect. */
    @JsonUnwrapped
    private PcmRelatedAspect pcmRelatedAspect = new PcmRelatedAspect();

    /**
     * Gets the evidence aspect.
     *
     * @return the evidence aspect
     */
    public EvidenceAspect getEvidenceAspect() {
        return evidenceAspect;
    }

    /**
     * Sets the evidence aspect.
     *
     * @param evidenceAspect
     *            the new evidence aspect
     */
    public void setEvidenceAspect(EvidenceAspect evidenceAspect) {
        this.evidenceAspect = evidenceAspect;
    }

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Evidence.TYPE;
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