package alf.integration.service.all.base;

/**
 * The Class ReceiptBaseTest.
 */
public class ReceiptBaseTest extends CentralBase{

    /** The Constant URL_MIDFIX. */
    public static final String URL_MIDFIX = "/receipt/";
    
    /** The Constant VALUE_RECEIPT_FILE_NAME. */
    public static final String VALUE_RECEIPT_FILE_NAME = "Receipt_1_Version1.0.pdf";
    
    /** The Constant INVALID_FILE_NAME. */
    protected static final String INVALID_FILE_NAME = "jnkReceipt.pdf";

    /** The Constant VALUE_RECEIPT_METADATA. */
    public static final String VALUE_RECEIPT_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateReceiptIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    /** The Constant VALUE_METADATA_JUNK_PROPS. */
    protected static final String VALUE_METADATA_JUNK_PROPS = "{\"modifiedByUserId123\" : \"CreateSummaryIntegraTestV_1_0\",   \"sourceSystem34w\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    /** The Constant CONTENT_RECEIPT_ATTACHMENT. */
    public static final String CONTENT_RECEIPT_ATTACHMENT = "src//test//resources//receipt//1_Receipt1.0.pdf";

}
