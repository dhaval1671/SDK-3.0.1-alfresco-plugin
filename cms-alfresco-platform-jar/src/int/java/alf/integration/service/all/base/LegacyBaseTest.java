package alf.integration.service.all.base;

import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class LegacyBaseTest.
 */
public class LegacyBaseTest extends CentralBase {

    /** The Constant URL_MIDFIX_CREATE. */
    public static final String URL_MIDFIX_CREATE = "/" + TradeMarkDocumentTypes.TYPE_LEGACY.getAlfrescoTypeName() + "/";

    /** The Constant URL_MIDFIX_RETRIEVE. */
    public static final String URL_MIDFIX_RETRIEVE = "/" + TradeMarkDocumentTypes.TYPE_LEGACY.getAlfrescoTypeName() + "/";

    /** The Constant VALUE_LGCY_FILE_NAME. */
    public static final String VALUE_LGCY_FILE_NAME = "Legacy_1_Version1.0.pdf";

    /** The Constant VALUE_FILE_NAME_DOESNT_EXISTS. */
    protected static final String VALUE_FILE_NAME_DOESNT_EXISTS = "Legacy_1_Version1.0_Doesnt_exits.pdf";

    /** The Constant VALUE_FILE_NAME_DOSNT_EXITS. */
    protected static final String VALUE_FILE_NAME_DOSNT_EXITS = "Legacy_1_Version1.0_xyz.pdf";

    /** The Constant VALUE_LGCY_METADATA. */
    public static final String VALUE_LGCY_METADATA = "{     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"documentName\": \"FirstLegacy.pdf\",  \"accessLevel\": \"public\",     \"modifiedByUserId\": \"CreateLegacyIntegraTestV_1_0\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\", \"legacyCategory\": \"Migrated\", \"docCode\": \"LGCY\", \"migrationMethod\":\"LZL\", \"migrationSource\":\"TICRS\"  }";

    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateLegacyIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant VALUE_VERSION_VALID. */
    protected static final String VALUE_VERSION_VALID = "1.0";

    /** The Constant VALUE_VERSION_INVALID. */
    protected static final String VALUE_VERSION_INVALID = "1.9";

    /** The Constant CONTENT_LGCY_ATTACHMENT. */
    public static final String CONTENT_LGCY_ATTACHMENT = "src//test//resources//legacy//1_Legacy1.0.pdf";

}
