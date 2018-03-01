package alf.cabinet.tmcase.withdrawal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.WithdrawalBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Withdrawal;

/**
 * The Class CreateWithdrawalTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateWithdrawalTests extends WithdrawalBaseTest{

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));        
    }    

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }    

    /**
     * Withdrawal create and recreate test.
     */
    @Test
    public void WithdrawalCreateAndRecreateTest(){
        Map<String, String> WithdrawalParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_WITHDRAWAL.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_WITHDRAWAL_FILE_NAME);
        String WITHDRAWAL_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput); 
        //System.out.println(WITHDRAWAL_CREATE_WEBSCRIPT_URL);
        WithdrawalParam.put(ParameterKeys.URL.toString(),WITHDRAWAL_CREATE_WEBSCRIPT_URL);
        WithdrawalParam.put(ParameterKeys.METADATA.toString(),VALUE_WITHDRAWAL_METADATA);
        WithdrawalParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), WithdrawalBaseTest.CONTENT_WITHDRAWAL_ATTACHMENT);        
        testCreateWithdrawal(WithdrawalParam);
        testDuplicateWithdrawalCreation(WithdrawalParam);
    }    

    /**
     * Test create Withdrawal.
     *
     * @param WithdrawalParam the Withdrawal param
     */
    public void testCreateWithdrawal(Map<String, String> WithdrawalParam) {
        ClientResponse response = createDocument(WithdrawalParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Withdrawal.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
    }

    /**
     * Test duplicate Withdrawal creation.
     *
     * @param WithdrawalParam the Withdrawal param
     */
    public void testDuplicateWithdrawalCreation(Map<String, String> WithdrawalParam) {
        ClientResponse response = createDocument(WithdrawalParam);         
        assertEquals(409, response.getStatus());
    }  

}
