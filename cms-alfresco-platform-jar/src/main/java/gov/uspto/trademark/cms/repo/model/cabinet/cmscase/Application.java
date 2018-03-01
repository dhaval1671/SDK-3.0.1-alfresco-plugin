package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on 7/23/2014.
 */
public class Application extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "application";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Application.TYPE;
    }

}
