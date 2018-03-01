package alf.integration.service.all.base;

/**
 * The Class NoteBaseTest.
 */
public class NoteBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/note/";

    /** The Constant VALUE_NOTE_FILE_NAME. */
    public static final String VALUE_NOTE_FILE_NAME = "Note_1_Version1.0.pdf";
    
    /** The Constant VALUE_FILE_NAME_DOESNT_EXISTS. */
    protected static final String VALUE_FILE_NAME_DOESNT_EXISTS = "Note_1_Version1.0_Doesnt_exits.pdf";
    
    /** The Constant VALUE_FILE_NAME_DOSNT_EXITS. */
    protected static final String VALUE_FILE_NAME_DOSNT_EXITS = "Note_1_Version1.0_xyz.pdf";

    /** The Constant VALUE_NOTE_METADATA. */
    public static final String VALUE_NOTE_METADATA = "{     \"docSubType\": \"EVI\",     \"docCategory\": \"Migrated\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"CreateNoteIntegraTestV_1_0\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"mailDate\": \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateNoteIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant VALUE_VERSION_VALID. */
    protected static final String VALUE_VERSION_VALID = "1.0";
    
    /** The Constant VALUE_VERSION_INVALID. */
    protected static final String VALUE_VERSION_INVALID = "1.9";

    /** The Constant CONTENT_NOTE_TTACHMENT. */
    public static final String CONTENT_NOTE_TTACHMENT = "src//test//resources//note//1_Note1.0.pdf";

}
