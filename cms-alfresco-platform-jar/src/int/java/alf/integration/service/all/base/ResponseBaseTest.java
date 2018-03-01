package alf.integration.service.all.base;

import org.apache.log4j.Logger;

/**
 * The Class ResponseBaseTest.
 */
public class ResponseBaseTest extends CentralBase {

    /** The log. */
    public static Logger LOG = Logger.getLogger(ResponseBaseTest.class);

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/response/";

    /** The Constant URL_POSTFIX. */
    public static final String URL_POSTFIX = "/metadata";

    /** The Constant VALUE_RESPONSE_FILE_NAME. */
    public static final String VALUE_RESPONSE_FILE_NAME = "Response_1_Version1.0.pdf";

    /** The Constant VALUE_RESPONSE_FILE_NAME_TWO. */
    public static final String VALUE_RESPONSE_FILE_NAME_TWO = "Response_2_Version1.0.pdf";

    /** The Constant URL_POSTFIX_REDACTION. */
    public static final String URL_POSTFIX_REDACTION = "/redaction";

    /** The Constant URL_POSTFIX_ORIGINAL. */
    public static final String URL_POSTFIX_ORIGINAL = "/original";

    /** The Constant URL_VERSION_PARAMETER. */
    public static final String URL_VERSION_PARAMETER = "?version=";

    /** The Constant INVALID_FILE_NAME. */
    public static final String INVALID_FILE_NAME = "jnkResponse.pdf";

    /** The Constant VALUE_ORIGINAL_RESPONSE_METADATA. */
    public static final String VALUE_ORIGINAL_RESPONSE_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateResponseIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\"}";
    /** The Constant VALUE_RESPONSE_METADATA. */
    public static final String VALUE_RESPONSE_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateResponseIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\", \"redactionLevel\": \"PARTIAL\" }";

    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateResponseIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\", \"redactionLevel\": \"PARTIAL\" }";

    /** The Constant CONTENT_RESPONSE_ATTACHMENT. */
    public static final String CONTENT_RESPONSE_ATTACHMENT = "src//test//resources//response//1_Response1.0.pdf";

}
