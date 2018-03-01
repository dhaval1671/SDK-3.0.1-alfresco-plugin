package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on Sep/25/2014.
 */
public class Summary extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "summary";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Summary.TYPE;
    }

}
