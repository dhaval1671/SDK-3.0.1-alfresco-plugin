package alf.integration.service.all.base;

/**
 * The Class OfficeActionBaseTest.
 */
public class OfficeActionBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/office-action/";
    
    /** The Constant URL_POSTFIX. */
    public static final String URL_POSTFIX = "/metadata";    

    /** The Constant VALUE_OFFACTN_FILE_NAME. */
    public static final String VALUE_OFFACTN_FILE_NAME = "OfficeAction_1_Version1.0.pdf";
    
    /** The Constant VALUE_OFFACTN_FILE_NAME_TWO. */
    public static final String VALUE_OFFACTN_FILE_NAME_TWO = "TwoOfficeAction_1_Version1.0.pdf";
    
    /** The Constant VALUE_OFFACTN_FILE_NAME_THREE. */
    public static final String VALUE_OFFACTN_FILE_NAME_THREE = "ThreeOfficeAction_1_Version1.0.pdf";

    /** The Constant VALUE_EVIDENCE_METADATA. */
    public static final String VALUE_EVIDENCE_METADATA = "{    \"documentAlias\": \"DocNameForTmngUiDisplay\",  \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"internal\",     \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
    
    /** The Constant VALUE_OFFICEACTION_METADATA. */
    public static final String VALUE_OFFICEACTION_METADATA = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"evidenceList\": [         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()+"/evidence/x-search-found.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }         },         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()+"/evidence/my-findings.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"working\",                     \"sent\"                 ]             }         }     ] }";
    
    /** The Constant VALUE_OFFICEACTION_METADATA_TWO. */
    protected static final String VALUE_OFFICEACTION_METADATA_TWO = "{     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"evidenceList\": [         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString()+"/evidence/x-search-found.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }         },         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()+"/evidence/my-findings.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"working\",                     \"sent\"                 ]             }         }     ] }";
    
    /** The Constant VALUE_OFFICEACTION_METADATA_THREE. */
    public static final String VALUE_OFFICEACTION_METADATA_THREE = "{     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"UserXYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"scanDate\": \"2014-04-23T13: 42: 24.962-04: 00\",     \"evidenceList\": [         {             \"documentId\": \"/case/77777777/evidence/NoteForOffActnAssoc.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }         }     ] }";    
    
    /** The Constant VALUE_OFFICEACTION_METADATA_WITHOUT_EVI_DOC_LIST. */
    public static final String VALUE_OFFICEACTION_METADATA_WITHOUT_EVI_DOC_LIST = "{     \"docSubType\": \"EVI\",     \"docCategory\": \"Migrated\",     \"accessLevel\": \"internal\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"mailDate\": \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_EVIDENCE_ATTACHMENT. */
    protected static final String CONTENT_EVIDENCE_ATTACHMENT = "src//test//resources//evidence//1_Evidence1.0.pdf";
    
    /** The Constant CONTENT_OFFICEACTION_ATTACHMENT. */
    public static final String CONTENT_OFFICEACTION_ATTACHMENT = "src//test//resources//officeaction//1_OfficeAction1.0.pdf";

    /** The Constant VALUE_VERSION_VALID. */
    protected static final String VALUE_VERSION_VALID = "1.0";
    
    /** The Constant VALUE_VERSION_INVALID. */
    protected static final String VALUE_VERSION_INVALID = "1.9";

}
