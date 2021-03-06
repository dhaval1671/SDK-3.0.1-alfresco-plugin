package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by bgummadi on 2/3/2015.
 */
public class Receipt extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "receipt";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Receipt.TYPE;
    }

}
