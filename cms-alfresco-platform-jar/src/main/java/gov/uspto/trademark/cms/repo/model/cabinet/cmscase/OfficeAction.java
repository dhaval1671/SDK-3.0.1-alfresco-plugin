package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by bgummadi on 6/13/2014.
 */
public class OfficeAction extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "office-action";

    /**
     * Returns the type.
     *
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return OfficeAction.TYPE;
    }

}
