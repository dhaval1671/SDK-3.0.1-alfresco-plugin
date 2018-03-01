package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on Sep/25/2014.
 */
public class Response extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "response";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Response.TYPE;
    }

}
