package alf.integration.service.all.base;

import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class MultimediaMarkBaseTest.
 */
public class TransformVideoMarkBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/"+ TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName() + "/multimedia";
    
    /** The Constant MM_UPDATE. */
    private static final String MM_UPDATE = "/cms/multimediamark/update";
    
    /** The Constant MM_UPDATE_WEBSCRIPT_URL. */
    protected static final String MM_UPDATE_WEBSCRIPT_URL = ALFRESCO_URL + MM_UPDATE;    

    /** The Constant VALUE_77777778_FILE_NAME. */
    protected static final String VALUE_77777778_FILE_NAME = "SecondMultimediaMark.avi"; 
    
    /** The Constant VALUE_MMRK_FILE_NAME. */
    public static final String VALUE_MMRK_FILE_NAME = "FirstMultimediaMark.avi";

    /** The Constant VALUE_MMRK_METADATA. */
    public static final String VALUE_MMRK_METADATA = "{  \"accessLevel\": \"restricted\", \"loadDate\": \"2000-01-01T17:42:24.962Z\",     \"modifiedByUserId\": \"User XYZ\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"documentName\": \"image\",     \"documentCode\": \"MRK\",     \"mailDate\": \"2014-04-23T17:42:24.962Z\", \"scanDate\": \"2014-04-23T17:42:24.962Z\",     \"serialNumber\": \"87654321\",     \"direction\": \"Incoming\",     \"category\": \"Drawing\",     \"migrationProps\": {         \"migrationMethod\": \"lazy-loader\",         \"migrationSource\": \"tcim\"     },     \"isColorMark\": true,     \"isThreeDimensional\": false,     \"isMarkCropped\": true,     \"isMarkAccepted\": true,     \"effectiveStartDate\": \"2014-04-23T17:42:24.962Z\",     \"effectiveEndDate\": null,     \"imageProps\": null,     \"multimediaProps\": {         \"multimediaStartTime\": \"00:04:05.678\",         \"multimediaComment\": \"No comments\",         \"videoCodec\": \"DivX\",         \"videoCompressionType\": \"H.264\",         \"audioCodec\": \"FFmpeg\",         \"audioCompressionType\": \"MPEG-1\",         \"multimediaDuration\": \"01:02:03.123\"     } }";

    /** The Constant VALUE_VERSION_VALID. */
    protected static final String VALUE_VERSION_VALID = "1.0";
    
    /** The Constant VALUE_VERSION_INVALID. */
    protected static final String VALUE_VERSION_INVALID = "1.9";

    /** The Constant PARAM_TYPE. */
    protected static final String PARAM_TYPE = "type";
    
    /** The Constant VALUE_TYPE. */
    protected static final String VALUE_TYPE = "all";

    /** The Constant DOCID_MULTIMEDIA_POST_FIX. */
    protected static final String DOCID_MULTIMEDIA_POST_FIX = "multimedia";
    
    public static final String CONTENT_MM_ASF = "src//test//resources//mark//multimedia//video//01_ASF_PlayingSkateHockey.asf";
    public static final String CONTENT_MM_MPG = "src//test//resources//mark//multimedia//video//02_MPG_BicycleRace.mpg";
    public static final String CONTENT_MM_WMV = "src//test//resources//mark//multimedia//video//03_WMV_SportsMainToScenesBackground.wmv";
    public static final String CONTENT_MM_MKV = "src//test//resources//mark//multimedia//video//04_MKV_Matroska_WikipediaAnnouncement.mkv";
    public static final String CONTENT_MM_AVI = "src//test//resources//mark//multimedia//video//05_AVI_Drop.avi";
    public static final String CONTENT_MM_3GP = "src//test//resources//mark//multimedia//video//06_3GP_LegoToy.3gp";
    public static final String CONTENT_MM_MOV = "src//test//resources//mark//multimedia//video//07_MOV_AppleQuickTime.mov";
    public static final String CONTENT_MM_OGV = "src//test//resources//mark//multimedia//video//08_OGV_Wildlife_plays.ogv";
    public static final String CONTENT_MM_TEETAR_VIDEO_SONG = "src//test//resources//mark//multimedia//video//10_5MB_TeetarKeAageTeetarVideoSong.avi";
    public static final String CONTENT_MM_TXT_DISGUISED_AS_AVI = "src//test//resources//mark//multimedia//video//11_440Bytes_TxtFileDisguisedAsAVI.avi";
    public static final String TRANSFORM_FAILING_CONTENT_MM_3GP = "src//test//resources//mark//multimedia//video//36_Mp4TransformFailing.3gp";
    
}
