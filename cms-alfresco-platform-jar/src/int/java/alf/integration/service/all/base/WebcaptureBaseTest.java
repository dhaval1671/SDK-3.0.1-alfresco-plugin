package alf.integration.service.all.base;

/**
 * The Class EvidenceBaseTest.
 */
public class WebcaptureBaseTest extends CentralBase{

    /** The Constant URL_PREFIX_RETRIEVE_FILE_NAMES. */
    public static final String URL_PREFIX_RETRIEVE_FILE_NAMES = "/cms/rest/cases/";
    
    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/evidence/";
    
    /** The Constant URL_MIDFIX_RETRIEVE_FILE_NAMES. */
    public static final String URL_MIDFIX_RETRIEVE_FILE_NAMES = "/evidences";  
    
    /** The Constant URL_POSTFIX_METADATA. */
    public static final String URL_POSTFIX_METADATA = "/metadata";
    
    /** The Constant URL_POSTFIX_VERSIONS. */
    public static final String URL_POSTFIX_VERSIONS = "/versions";
    
    /** The Constant URL_VERSION_PARAM. */
    public static final String URL_VERSION_PARAM ="?version=";    

    /** The Constant EVI_TWO_A_LIB_URL_PREFIX. */
    public static final String EVI_BANK_CMS_REST_PREFIX = "/cms/rest";

    /** The Constant EVI_DELETE. */
    private static final String EVI_DELETE = "/cms/evidence/deleteevidences";
    
    /** The Constant EVI_DELETE_WEBSCRIPT_URL. */
    protected static final String EVI_DELETE_WEBSCRIPT_URL = ALFRESCO_URL + EVI_DELETE;    

    /** The Constant VALUE_MULTIPLE_SERIAL_NUMBER. */
    protected static final String VALUE_MULTIPLE_SERIAL_NUMBER = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "," + ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString();    

    /** The Constant VALUE_EVIDENCE_FILE_NAME. */
    public static final String VALUE_EVIDENCE_FILE_NAME = "Evidence_1_Version1.0.pdf";
    
    /** The Constant VALUE_EVIDENCE_TWO_FILE_NAME. */
    public static final String VALUE_EVIDENCE_TWO_FILE_NAME = "Evidence_2_Version1.0.pdf";
    
    /** The Constant EVIDENCE_FILE_NAME_THREE. */
    public static final String EVIDENCE_FILE_NAME_THREE = "Evidence_3_Version1.0.pdf";
    
    /** The Constant EVIDENCE_FILE_NAME_THREE. */
    public static final String EVIDENCE_FILE_NAME_FOUR = "Evidence_4_Version1.0.pdf";    

    /** The Constant VALUE_EVIDENCE_METADATA. */
    public static final String VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL = "{  \"accessLevel\": \"internal\",  \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";
    
    public static final String VALUE_EVIDENCE_METADATA_WITH_DOC_SIZE_Hello6789000 = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"documentSize\": \"Hello6789000\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\",     \"redactionLevel\": \"Partial\" }";    
    
    /** The Constant VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC. */
    public static final String VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC = "{  \"accessLevel\": \"public\",  \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";    
    
    /** The Constant VALUE_EVIDENCE_METADATA_WITH_REDACTIONFLAG_NONE. */
    public static final String VALUE_EVIDENCE_METADATA_WITH_REDACTIONFLAG_NONE = "{  \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"None\" }";    
    
    /** The Constant VALUE_EVIDENCE_METADATA_WITHOUT_MANDATORY_ATTRIB. */
    public static final String VALUE_EVIDENCE_METADATA_WITHOUT_MANDATORY_ATTRIB = "{ \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\", \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ] }";
    
    /** The Constant VALUE_EVI_UPDATED_METADATA_ONE. */
    public static final String VALUE_EVI_UPDATED_METADATA_ONE = "{ \"modifiedByUserId\": \"UpdateByIntegrationTestID4965\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\", \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";    
    
    /** The Constant VALUE_EVI_UPDATED_METADATA_WITH_LOWER_VERSION_ATTRIB. */
    protected static final String VALUE_EVI_UPDATED_METADATA_WITH_LOWER_VERSION_ATTRIB = "{     \"version\": \"1.0\", \"modifiedByUserId\": \"ID4965\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\", \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
    
    /** The Constant VALUE_EVI_UPDATED_METADATA_WITH_HIGHER_VERSION_ATTRIB. */
    protected static final String VALUE_EVI_UPDATED_METADATA_WITH_HIGHER_VERSION_ATTRIB = "{     \"version\": \"1.9\", \"modifiedByUserId\": \"ID4965\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\", \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
    
    /** The Constant VALUE_EVI_METADATA. */
    protected static final String VALUE_EVI_METADATA = "{ \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\", \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";

    /** The Constant VALUE_VERSION_VALID. */
    protected static final String VALUE_VERSION_VALID = "1.0";
    
    /** The Constant VALUE_VERSION_INVALID. */
    protected static final String VALUE_VERSION_INVALID = "1.9";

    /** The Constant PARAM_PATH. */
    protected static final String PARAM_PATH = "path";
    
    /** The Constant VALUE_PATH. */
    protected static final String VALUE_PATH = "/BambooConstructMaterials/2Def-Bamboo.jpg";
    
    /** The Constant VALUE_PATH_FROM_FOLDER. */
    protected static final String VALUE_PATH_FROM_FOLDER = "/BambooConstructMaterials";

    /** The Constant CONTENT_EVI_ATTACHMENT. */
    public static final String CONTENT_EVI_BANK_WEBCAPTURE_ATTACHMENT = "src//test//resources//evidenceBank//webcapture//WebCapture.pdf";

}
