package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on 7/23/2014.
 */
public class Notice extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "notice";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Notice.TYPE;
    }

}
