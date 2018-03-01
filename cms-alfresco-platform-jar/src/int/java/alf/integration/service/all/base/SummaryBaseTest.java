package alf.integration.service.all.base;

/**
 * The Class SummaryBaseTest.
 */
public class SummaryBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/summary/";
    
    /** The Constant VALUE_SUMMARY_FILE_NAME. */
    public static final String VALUE_SUMMARY_FILE_NAME = "Summary_1_Version1.0.docx";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkSummary.pdf";

    /** The Constant VALUE_SUMMARY_METADATA. */
    public static final String VALUE_SUMMARY_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSummaryIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateSummaryIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_SUMMARY_ATTACHMENT. */
    public static final String CONTENT_SUMMARY_ATTACHMENT = "src//test//resources//summary//X-Search Summary.docx";

}
