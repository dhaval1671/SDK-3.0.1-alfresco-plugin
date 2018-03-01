package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

/**
 * Created by stank on 11/15/2016.
 */
public class Decision extends LegalProceeding {

    /** The Constant TYPE. */
    public static final String TYPE = "decision";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Decision.TYPE;
    }

}
