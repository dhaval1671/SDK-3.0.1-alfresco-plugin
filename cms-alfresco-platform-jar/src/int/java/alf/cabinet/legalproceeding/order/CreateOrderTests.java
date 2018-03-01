package alf.cabinet.legalproceeding.order;

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
import alf.integration.service.all.base.cabinet.legalproceeding.OrderBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Order;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateOrderTests extends OrderBaseTest {

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
     * Order create and recreate test.
     */
    @Test
    public void orderCreateAndRecreateTest(){
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_ORDER.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_ORDER_FILE_NAME);
        String ORDER_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(ORDER_CREATE_WEBSCRIPT_URL);        
        Map<String, String> orderParam = new HashMap<String, String>();        
        orderParam.put(ParameterKeys.URL.toString(),ORDER_CREATE_WEBSCRIPT_URL);
        orderParam.put(ParameterKeys.METADATA.toString(),VALUE_ORDER_METADATA);
        orderParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OrderBaseTest.CONTENT_ORDER_ATTACHMENT);        

        testCreateOrder(orderParam);
        testDuplicateOrderCreation(orderParam);
    }    

    /**
     * Test create order.
     *
     * @param orderParam the order param
     */
    private void testCreateOrder(Map<String, String> orderParam) {

        ClientResponse response = createDocument(orderParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Order.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate order creation.
     *
     * @param orderParam the order param
     */
    private void testDuplicateOrderCreation(Map<String, String> orderParam) {
        ClientResponse response = createDocument(orderParam);         
        assertEquals(409, response.getStatus());

    }  

}
