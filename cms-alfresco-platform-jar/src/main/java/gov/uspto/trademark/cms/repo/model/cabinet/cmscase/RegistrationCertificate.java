package gov.uspto.trademark.cms.repo.model.cabinet.cmscase;

/**
 * Created by stank on Feb/11/2016.
 */
public class RegistrationCertificate extends Document {

    /** The Constant TYPE. */
    public static final String TYPE = "registration-certificate";

    /**
     * Returns the type.
     * 
     * @return the document type
     */
    @Override
    public String getDocumentType() {
        return RegistrationCertificate.TYPE;
    }

}
