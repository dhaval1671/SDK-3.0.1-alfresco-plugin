package alf.integration.service.all.base;

/**
 * The Class SignatureBaseTest.
 */
public class SignatureBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/signature/";
    
    /** The Constant VALUE_SIGNATURE_FILE_NAME. */
    public static final String VALUE_SIGNATURE_FILE_NAME = "Signature_1_Version1.0.docx";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkSignature.pdf";

    /** The Constant VALUE_SIGNATURE_METADATA. */
    public static final String VALUE_SIGNATURE_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSignatureIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_SIGNATURE_METADATA. */
    public static final String VALUE_SIGNATURE_METADATA_INTERNAL = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSignatureIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"internal\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_SIGNATURE_METADATA. */
    public static final String VALUE_SIGNATURE_METADATA_RESTRICTED = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSignatureIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"restricted\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant VALUE_SIGNATURE_METADATA. */
    public static final String VALUE_SIGNATURE_METADATA_NO_AL = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSignatureIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",  \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant VALUE_SIGNATURE_METADATA. */
    public static final String VALUE_SIGNATURE_METADATA_WRONG_AL = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSignatureIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"hello\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateSignatureIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_SIGNATURE_ATTACHMENT. */
    public static final String CONTENT_SIGNATURE_ATTACHMENT = "src//test//resources//signature//signature.pdf";

}
