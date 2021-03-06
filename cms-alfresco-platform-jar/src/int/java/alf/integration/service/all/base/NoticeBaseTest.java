package alf.integration.service.all.base;

/**
 * The Class NoticeBaseTest.
 */
public class NoticeBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/notice/";
    
    /** The Constant URL_POSTFIX_CONTENT. */
    protected static final String URL_POSTFIX_CONTENT = "";

    /** The Constant VALUE_NOTICE_FILE_NAME. */
    public static final String VALUE_NOTICE_FILE_NAME = "Notice_1_Version1.0.pdf";
    
    /** The Constant VALUE_NOTICE_FILE_NAME_TWO. */
    public static final String VALUE_NOTICE_FILE_NAME_TWO = "Notice_2_Version1.0.pdf";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkNotice.pdf";

    /** The Constant VALUE_NOTICE_METADATA. */
    public static final String VALUE_NOTICE_METADATA = "{     \"docSubType\": \"EVI\",     \"docCategory\": \"Migrated\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"CreateNoticeIntegraTestV_1_0\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"mailDate\": \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateNoticeIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_NOTICE_METADATA_WITHOUT_SOURCE_MEDIA. */
    public static final String VALUE_NOTICE_METADATA_WITHOUT_SOURCE_MEDIA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateNoticeIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\", \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_NOTICE_METADATA_WITHOUT_SOURCE_MEDIUM. */
    public static final String VALUE_NOTICE_METADATA_WITHOUT_SOURCE_MEDIUM = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateNoticeIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\", \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_NOTICE_METADATA_WITHOUT_SOURCE_SYSTEM. */
    public static final String VALUE_NOTICE_METADATA_WITHOUT_SOURCE_SYSTEM = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateNoticeIntegraTestV_1_0\", \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_NOTICE_ATTACHMENT. */
    public static final String CONTENT_NOTICE_ATTACHMENT = "src//test//resources//notice//1_Notice1.0.pdf";

}
