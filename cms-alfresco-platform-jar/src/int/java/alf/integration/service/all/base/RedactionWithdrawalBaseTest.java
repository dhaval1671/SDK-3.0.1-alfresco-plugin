package alf.integration.service.all.base;

import org.apache.log4j.Logger;

/**
 * The Class RedactionResponseBaseTest.
 */
public class RedactionWithdrawalBaseTest extends CentralBase {

    /** The log. */
    public static Logger LOG = Logger.getLogger(RedactionWithdrawalBaseTest.class);

    public static final String VALUE_WITHDRAWAL_FILE_NAME_TWO = "Withdrawal_2_Version1.0.pdf";
    
    public static final String CONTENT_WITHDRAWAL_ATTACHMENT = "src//test//resources//withdrawal//1_Withdrawal1.0.pdf";
    
    public static final String CONTENT_CREATE_REDACTED_RESPONSE = "src//test//resources//redaction//response//2b_Create_RedactedResponse.pdf";
    


    /**
     * Instantiates a new redaction response base test.
     */
    public RedactionWithdrawalBaseTest() {
        super();
    }

}