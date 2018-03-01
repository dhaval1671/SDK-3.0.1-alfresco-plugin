package alf.integration.service.all.base.cabinet.legalproceeding;

import alf.integration.service.all.base.CentralBase;

/**
 * The Class PleadingBaseTest.
 */
public class PleadingsBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/summary/";
    
    /** The Constant VALUE_PLEADING_FILE_NAME. */
    public static final String VALUE_PLEADING_LEGALPROCEEDING_FILE_NAME = "Pleading_Legal_Proceeding.pdf";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkPleading.pdf";

    /** The Constant VALUE_PLEADING_METADATA. */
    public static final String VALUE_LEGALPROCEEDING_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"internal\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreatePleadingIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_PLEADING_ATTACHMENT. */
    public static final String CONTENT_LEGALPROCEEDING_ATTACHMENT = "src//test//resources//cabinet//legal-proceeding//pleading//EXA_1.pdf";

}
