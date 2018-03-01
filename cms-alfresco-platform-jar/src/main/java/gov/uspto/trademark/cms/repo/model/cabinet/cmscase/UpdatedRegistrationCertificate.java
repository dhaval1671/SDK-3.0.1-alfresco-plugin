package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on Mar/03/2016.
 */
public class UpdatedRegistrationCertificate extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "updated-registration-certificate";

    /**
     * Returns the type.
     * 
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return UpdatedRegistrationCertificate.TYPE;
    }

}
