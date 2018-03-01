package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * The Class Signature.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk} 04/24/2015.
 */
public class Signature extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "signature";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Signature.TYPE;
    }

}
