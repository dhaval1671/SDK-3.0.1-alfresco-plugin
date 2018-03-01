package gov.uspto.trademark.cms.repo.model.aspects;

/**
 * Evidence Bank aspect representation in the repository
 *
 * Created by stank on 6/2/2014.
 */
public class EvidenceBankAspect extends AbstractBaseAspect {

    /** The evidence source url. */
    private String source;

    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source.
     *
     * @param source
     *            the new source
     */
    public void setSource(String source) {
        this.source = source;
    }

}
