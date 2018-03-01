package alf.integration.service.all.base.cabinet.legalproceeding;

import alf.integration.service.all.base.CentralBase;

/**
 * The Class BriefBaseTest.
 */
public class BriefBaseTest extends CentralBase{

    /** The Constant VALUE_BRIEF_FILE_NAME. */
    public static final String VALUE_BRIEF_FILE_NAME = "Brief_One.pdf";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkBrief.pdf";

    /** The Constant VALUE_BRIEF_METADATA. */
    public static final String VALUE_BRIEF_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"internal\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateBriefIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_BRIEF_ATTACHMENT. */
    public static final String CONTENT_BRIEF_ATTACHMENT = "src//test//resources//cabinet//legal-proceeding//brief//EXA_1.pdf";

}
