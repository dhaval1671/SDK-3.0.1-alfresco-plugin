package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

/**
 * Created by stank on 11/15/2016.
 */
public class Order extends LegalProceeding {

    /** The Constant TYPE. */
    public static final String TYPE = "order";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Order.TYPE;
    }

}
