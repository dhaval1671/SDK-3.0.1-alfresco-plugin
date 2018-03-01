package alf.integration.service.all.base;

/**
 * The Class ApplicationBaseTest.
 */
public class ApplicationBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/application/";
    
    /** The Constant VALUE_APPLICATION_FILE_NAME. */
    public static final String VALUE_APPLICATION_FILE_NAME = "Application_1_Version1.0.docx";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkApplication.pdf";

    /** The Constant VALUE_APPLICATION_METADATA. */
    public static final String VALUE_APPLICATION_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateApplicationIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateApplicationIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_APPLICATION_ATTACHMENT. */
    public static final String CONTENT_APPLICATION_ATTACHMENT = "src//test//resources//application//application.pdf";

}
