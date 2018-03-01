package alf.integration.service.all.base;

/**
 * The Class SpecimenBaseTest.
 */
public class TicrsDocumentBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/specimen/";
    
    /** The Constant VALUE_TICRS_DOCUMENT_FILE_NAME. */
    public static final String VALUE_TICRS_DOCUMENT_FILE_NAME = "Ticrs_Document_1_Version1.0.docx";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkSpecimen.pdf";

    /** The Constant VALUE_TICRS_DOCUMENT_METADATA. */
    public static final String VALUE_TICRS_DOCUMENT_METADATA = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\", \"legacyCategory\": \"Migrated\", \"docCode\": \"MRK\", \"migrationMethod\":\"LZL\", \"migrationSource\":\"TICRS\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateTicrsDocumentIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_TICRS_DOCUMENT_ATTACHMENT. */
    public static final String CONTENT_TICRS_DOCUMENT_ATTACHMENT = "src//test//resources//ticrsdocument//ticrsdocument.pdf";
    
    public static final String CONTENT_TICRS_DOCUMENT_TIF = "src//test//resources//ticrsdocument//ticrsDocumentOne.tif";
    
    public static final String CONTENT_TICRS_DOCUMENT_JPG = "src//test//resources//ticrsdocument//ticrssDocumentTwo.jpeg";



}
