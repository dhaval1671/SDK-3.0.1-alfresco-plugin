package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on oct/14/2015.
 */
public class Withdrawal extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "withdrawal";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Withdrawal.TYPE;
    }

}
