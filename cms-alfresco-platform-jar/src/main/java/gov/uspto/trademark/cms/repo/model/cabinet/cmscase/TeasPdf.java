package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on Mar/28/2016.
 */
public class TeasPdf extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "teaspdf";

    /**
     * Returns the type.
     * 
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return TeasPdf.TYPE;
    }

}
