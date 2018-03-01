package alf.integration.service.all.base;

import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class MarkBaseTest.
 */
public class GenericMarkBaseTest extends CentralBase {

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/" + TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName() + "/image";

    /** The Constant URL_MIDFIX_RENDITION. */
    public static final String URL_MIDFIX_RENDITION = "/" + TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName()
            + "/image/rendition";

    /** The Constant URL_POSTFIX_VERSIONS. */
    public static final String URL_POSTFIX_VERSIONS = "/versions";

    /** The Constant URL_VERSION_PARAM. */
    public static final String URL_VERSION_PARAM = "?version=";

    /** The Constant VALUE_MULTIPLE_SERIAL_NUMBER_TWO. */
    protected static final String VALUE_MULTIPLE_SERIAL_NUMBER_TWO = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()
            + "," + ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + ","
            + ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString();

    /** The Constant VALUE_MULTIIPLE_SERIAL_NUMBER. */
    protected static final String VALUE_MULTIIPLE_SERIAL_NUMBER = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + ","
            + ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString();

    /** The Constant VALUE_MRK_METADATA_ONE. */
    public static final String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";

    /** The Constant VALUE_MRK_PCM_METADATA. */
    public static final String VALUE_MRK_PCM_METADATA = "{     \"modifiedByUserId\": \"User XYZ\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"mailDate\": \"2014-04-23T13:42:24.962-04:00\",     \"effectiveMailDate\": \"2014-04-23T13:42:24.962-04:00\",     \"effectiveMailDateReason\": \"mail-date\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"isColorMark\": true,     \"isThreeDimensional\": false,     \"isMarkCropped\": true,     \"isMarkAccepted\": true,     \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\",     \"createdByUserId\": \"CoderX\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"docSubType\": \"MRK\",     \"migrationMethod\": \"LZL\",     \"migrationSource\": \"TICRS\",     \"documentName\": \"myTM\",     \"docCategory\": \"Migrated\",     \"pcmABSN\": 123,     \"pcmAFN\": 123,     \"pcmNoteNum\": 123,     \"pcmSeqNum\": 123,     \"pcmReplaceBySeqNum\": 123,     \"pcmFileSuffix\": \"pcmFileSuffix\",     \"pcmMediaType\": \"pcm-mediaType\",     \"pcmOriginalFileName\": \"pcm-originalFileName\",     \"pcmRsn\": 33333,     \"pcmFileSize\": 3333,     \"pcmCreateDateTime\": \"2015-07-16T07:41:19.000-04:00\",     \"pcmUpdateDateTime\": \"2015-07-16T07:41:19.000-04:00\",     \"pcmLastModifiedDate\": \"2015-07-16T07:41:19.000-04:00\",     \"pcmOracleApplyTime\": \"2015-07-16T07:41:19.000-04:00\" }";

    /** The Constant VALUE_MRK_METADATA_TWO. */
    public static final String VALUE_MRK_METADATA_TWO = "{ \"accessLevel\": \"internal\", \"documentAlias\": \"nick123\", \"createdByUserId\" : \"CoderX\", \"modifiedByUserId\" : \"metFor1.2\", \"mailDate\" : \"2013-06-25T07:41:19.000-04:00\", \"loadDate\" : \"2013-07-16T07:41:19.000-04:00\", \"drawingAcceptanceIndicator\" : true, \"scanDate\" : \"2013-06-16T05:33:22.000-04:00\", \"effectiveStartDate\" : \"2013-07-06T00:00:00.000-00:00\", \"sourceSystem\" : \"TICRS\", \"sourceMedia\" : \"E\", \"sourceMedium\" : \"Email\", \"documentName\" : \"myTM\" }";

    /** The Constant METADATA_SIMULATE_IMAGE_MARK_MIGRATION. */
    public static final String METADATA_SIMULATE_IMAGE_MARK_MIGRATION = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\", \"legacyCategory\": \"Migrated\", \"docCode\": \"MRK\", \"migrationMethod\":\"LZL\", \"migrationSource\":\"TICRS\" }";

    /** The Constant GENERIC_SIM_MRK_FILE_NAME. */
    public static final String GENERIC_SIM_MRK_FILE_NAME = "GenericSimulatedLegacyMarkMigration.jpg";

    /** The Constant VALUE_MRK_FILE_NAME. */
    public static final String GENERIC_MRK_FILE_NAME = "Generic_1_Mark.tif";

    /** The Constant PCM_MRK_FILE_NAME. */
    public static final String PCM_MRK_FILE_NAME = "Pcm_1_Mark.bmp";

    /** The Constant VALUE_77777778_FILE_NAME. */
    protected static final String VALUE_77777778_FILE_NAME = "Mark_2_Version1.0.jpg";

    /** The Constant VALUE_VERSION_VALID. */
    protected static final String VALUE_VERSION_VALID = "1.0";

    /** The Constant VALUE_VERSION_INVALID. */
    protected static final String VALUE_VERSION_INVALID = "1.9";

    /** The Constant PARAM_TYPE. */
    protected static final String PARAM_TYPE = "type";

    /** The Constant VALUE_TYPE. */
    protected static final String VALUE_TYPE = "all";

    /** The Constant PARAM_RENDITION_NAME. */
    protected static final String PARAM_RENDITION_NAME = "name";

    /** The Constant CONTENT_MRK_BMP. */
    public static final String CONTENT_MRK_BMP = "src//test//resources//mark//image//1_Mark1.0.bmp";

    /** The Constant CONTENT_MRK_JPG. */
    public static final String CONTENT_MRK_JPG = "src//test//resources//mark//image//2_Mark1.0.jpg";

    /** The Constant CONTENT_MRK_GIF. */
    public static final String CONTENT_MRK_GIF = "src//test//resources//mark//image//3_Mark1.0.gif";

    /** The Constant CONTENT_MRK_TIF. */
    public static final String CONTENT_MRK_TIF = "src//test//resources//mark//image//4_Mark1.0.TIF";

    /** The Constant CONTENT_MRK_PNG. */
    public static final String CONTENT_MRK_PNG = "src//test//resources//mark//image//5_Mark1.0.png";

    /** The Constant DOCID_IMAGE_POST_FIX. */
    protected static final String DOCID_IMAGE_POST_FIX = "image";

}
