package alf.integration.service.all.base.cabinet.legalproceeding;

import alf.integration.service.all.base.CentralBase;

/**
 * The Class MotionBaseTest.
 */
public class MotionBaseTest extends CentralBase{

    /** The Constant VALUE_MOTION_FILE_NAME. */
    public static final String VALUE_MOTION_FILE_NAME = "Motion_One.pdf";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkMotion.pdf";

    /** The Constant VALUE_MOTION_METADATA. */
    public static final String VALUE_MOTION_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"internal\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateMotionIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_MOTION_ATTACHMENT. */
    public static final String CONTENT_MOTION_ATTACHMENT = "src//test//resources//cabinet//legal-proceeding//motion//EXA_1.pdf";

}
