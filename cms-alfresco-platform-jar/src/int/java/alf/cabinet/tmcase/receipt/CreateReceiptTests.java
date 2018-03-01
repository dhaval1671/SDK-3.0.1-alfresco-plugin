package alf.cabinet.tmcase.receipt;

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
import alf.integration.service.all.base.ReceiptBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Receipt;

/**
 * The Class CreateReceiptTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateReceiptTests extends ReceiptBaseTest {

    /** The client. */
    public Client client = null;

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
     * Receipt create and recreate test.
     */
    @Test
    public void receiptCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RECEIPT.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_RECEIPT_FILE_NAME);
        String RECEIPT_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(RECEIPT_CREATE_WEBSCRIPT_URL);
        Map<String, String> receiptParam = new HashMap<String, String>();
        receiptParam.put(ParameterKeys.URL.toString(),RECEIPT_CREATE_WEBSCRIPT_URL);
        receiptParam.put(ParameterKeys.METADATA.toString(),VALUE_RECEIPT_METADATA);
        receiptParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ReceiptBaseTest.CONTENT_RECEIPT_ATTACHMENT);        
        testCreateReceipt(receiptParam);
        testDuplicateReceiptCreation(receiptParam);
    }    

    /**
     * Test create receipt.
     *
     * @param receiptParam the receipt param
     */
    public void testCreateReceipt(Map<String, String> receiptParam) {
        ClientResponse response = createDocument(receiptParam);            
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Receipt.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
    }

    /**
     * Test duplicate receipt creation.
     *
     * @param receiptParam the receipt param
     */
    public void testDuplicateReceiptCreation(Map<String, String> receiptParam) {
        ClientResponse response = createDocument(receiptParam);         
        assertEquals(409, response.getStatus());
    }  

}
