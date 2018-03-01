package alf.integration.service.all.base;

/**
 * The Class AttachmentBaseTest.
 */
public class AttachmentBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/attachment/";

    /** The Constant VALUE_ATTACHMENT_FILE_NAME. */
    public static final String VALUE_ATTACHMENT_FILE_NAME = "Attachment_1_Version1.0.docx";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkAttachment.pdf";

    /** The Constant VALUE_ATTACHMENT_METADATA. */
    public static final String VALUE_ATTACHMENT_METADATA_ACCESS_PUBLIC = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateAttachmentIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_ATTACHMENT_METADATA. */
    public static final String VALUE_ATTACHMENT_METADATA_ACCESS_INTERNAL = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateAttachmentIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"internal\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateAttachmentIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_ATTACHMENT_ATTACHMENT. */
    public static final String CONTENT_ATTACHMENT = "src//test//resources//attachment//attachment.pdf";

}
