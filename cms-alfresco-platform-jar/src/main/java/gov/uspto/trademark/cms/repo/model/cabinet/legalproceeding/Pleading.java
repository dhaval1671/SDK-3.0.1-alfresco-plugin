package gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding;

/**
 * Created by stank on Nov/15/2016.
 */
public class Pleading extends LegalProceeding {

    /** The Constant TYPE. */
    public static final String TYPE = "pleading";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return Pleading.TYPE;
    }

}
