package alf.integration.service.all.base;

/**
 * The Class ApplicationBaseTest.
 */
public class UpdatedRegCertBaseTest extends CentralBase {

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/registration-certificate/";

    /** The Constant VALUE_REGISTRATION_CERTIFICATE_FILE_NAME. */
    public static final String VALUE_URC_FILE_NAME = "uRC_CreateTC.docx";

    public static final String UPDATE_URC_FILE_NAME = "UpdateURC.pdf";

    public static final String VALUE_URC_METADATA_ONE = "{ \"accessLevel\" : \"restricted\",   \"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateApplicationIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    public static final String UPDATE_CONTENT_URC_PDF = "src//test//resources//crudfiles//Update.pdf";

    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkApplication.pdf";

    /** The Constant VALUE_REGISTRATION_CERTIFICATE_METADATA. */
    public static final String VALUE_URC_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateApplicationIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    public static final String VALUE_URC_DELETE_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateApplicationIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"internal\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateApplicationIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_REGISTRATION_CERTIFICATE_ATTACHMENT. */
    public static final String CONTENT_URC_ATTACHMENT = "src//test//resources//updatedregistrationcertificate//updatedregistrationcertificate.pdf";

}
