package alf.cabinet.tmcase.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Response;

/**
 * The Class CreateResponseTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateResponseTests extends ResponseBaseTest {

    /** The client. */
    public Client client = null;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
    }    

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
    }    

    /**
     * Response create and recreate test.
     */
    @Test
    public void responseCreateAndRecreateTest(){
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_RESPONSE_FILE_NAME);
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateResponsePut(urlInput);         
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(),VALUE_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ResponseBaseTest.CONTENT_RESPONSE_ATTACHMENT);        

        testCreateResponse(responseParam);
        testDuplicateResponseCreation(responseParam);
    }    

    /**
     * Test create response.
     *
     * @param responseParam the response param
     */
    private void testCreateResponse(Map<String, String> responseParam) {

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
     * Test duplicate response creation.
     *
     * @param responseParam the response param
     */
    private void testDuplicateResponseCreation(Map<String, String> responseParam) {

        ClientResponse response = createDocument(responseParam);         
        assertEquals(409, response.getStatus());

    }  

    /**
     * Test create response with redaction url.
     */
    @Test
    public void testCreateResponseWithRedactionURL() {
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_RESPONSE_FILE_NAME);
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);        
        responseParam.put(ParameterKeys.URL.toString(),RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(),VALUE_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ResponseBaseTest.CONTENT_RESPONSE_ATTACHMENT);  
        ClientResponse response = createDocument(responseParam);            
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    }  

    /**
     * Test create response with extra forward slash in url.
     */
    @Test
    public void testCreateResponseWithExtraForwardSlashInURL() {
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_RESPONSE_FILE_NAME);
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateResponsePut(urlInput) + "/";   
        responseParam.put(ParameterKeys.URL.toString(),RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(),VALUE_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ResponseBaseTest.CONTENT_RESPONSE_ATTACHMENT);  
        ClientResponse response = createDocument(responseParam);            
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    }     

}
