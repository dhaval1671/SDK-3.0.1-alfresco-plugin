package gov.uspto.trademark.cms.repo.model.aspects;

/**
 * Source aspect representation in the repository
 *
 * Created by bgummadi on 6/3/2014.
 */
public class ApplicationSource extends AbstractBaseAspect {

    /** The source media. */
    private String sourceMedia;

    /** The source medium. */
    private String sourceMedium;

    /**
     * Gets the source media.
     *
     * @return the source media
     */
    public String getSourceMedia() {
        return sourceMedia;
    }

    /**
     * Sets the source media.
     *
     * @param sourceMedia
     *            the new source media
     */
    public void setSourceMedia(String sourceMedia) {
        this.sourceMedia = sourceMedia;
    }

    /**
     * Gets the source medium.
     *
     * @return the source medium
     */
    public String getSourceMedium() {
        return sourceMedium;
    }

    /**
     * Sets the source medium.
     *
     * @param sourceMedium
     *            the new source medium
     */
    public void setSourceMedium(String sourceMedium) {
        this.sourceMedium = sourceMedium;
    }
}
