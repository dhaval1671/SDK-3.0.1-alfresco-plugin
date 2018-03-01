package alf.cabinet.tmcase.redaction.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionResponseBaseTest;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Response;

/**
 * The Class RedactUpdateToResponseTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RedactUpdateToResponseTests extends RedactionResponseBaseTest {

    /** The log. */
    public static Logger LOG = Logger.getLogger(RedactUpdateToResponseTests.class);
    
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
     * Creates the regular response.
     *
     * @param urlInput the url input
     */
    private void createRegularResponse(UrlInputDto urlInput) {
        Map<String, String> responseParam = new HashMap<String, String>();
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateResponsePut(urlInput);         
        responseParam.put(ParameterKeys.URL.toString(),RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RedactionResponseBaseTest.CONTENT_REGULAR_RESPONSE_TO_BE_REDACTED);        
        ClientResponse response = createDocument(responseParam);            
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Response.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID)); 
    }       

    /**
     * Redacted response create and recreate test.
     */
    @Test
    public void redactedResponseCreateAndRecreateTest() {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(RESPONSE_TO_BE_REDACTED_TWO);        

        createRegularResponse(urlInput);
        testCreateRedactedResponse(urlInput);
        testUpdateRedactedResponse(urlInput);
    }    

    /**
     * Test create redacted response.
     *
     * @param urlInput the url input
     */
    private void testCreateRedactedResponse(UrlInputDto urlInput) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);         
        //System.out.println(REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RedactionResponseBaseTest.CONTENT_CREATE_REDACTED_RESPONSE); 
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);            
        String str = redactedResponse.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, redactedResponse.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Response.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
    }

    /**
     * Test update redacted response.
     *
     * @param urlInput the url input
     */
    private void testUpdateRedactedResponse(UrlInputDto urlInput) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);         
        redactedResponseParam.put(ParameterKeys.URL.toString(),REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RedactionResponseBaseTest.CONTENT_UPDATE_REDACTED_RESPONSE);         
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);   
        String str = redactedResponse.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, redactedResponse.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Response.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));           
    }

    /**
     * Test create redacted response where original response does not exits.
     */
    @Test
    public void testCreateRedactedResponseWhereOriginalResponseDoesNOTExits() {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(RESPONSE_TO_BE_REDACTED_DOES_NOT_EXITS);
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);         
        //System.out.println(REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RedactionResponseBaseTest.CONTENT_CREATE_REDACTED_RESPONSE); 
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);            
        //String str = redactedResponse.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(HttpStatus.NOT_FOUND.value(), redactedResponse.getStatus());
    }    

}
