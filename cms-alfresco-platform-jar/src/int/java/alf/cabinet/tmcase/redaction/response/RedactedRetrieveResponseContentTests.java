package alf.cabinet.tmcase.redaction.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * Retrieve Response Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RedactedRetrieveResponseContentTests extends RedactionResponseBaseTest {

    /** The client. */
    public static Client client = null;
    
    /** The serial number78. */
    public static String serialNumber78 = ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString();
    
    /** The serial number79. */
    public static String serialNumber79 = ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString();

    /**
     * Sets the up.
     */
    @BeforeClass
    public static void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD)); 
        createResponseDocVersionStackWithOriginalVersionOnTop(serialNumber78);
        createResponseDocVersionStackWithRedactedVersionOnTop(serialNumber79);
    }    

    /**
     * Tear down.
     */
    @AfterClass
    public static void tearDown() {

    }    

    /**
     * Test redacted retrieve response content default flavour original on top.
     */
    @Test
    public void testRedactedRetrieveResponseContentDefaultFlavourOriginalOnTop() {
        //This test case should fetch latest redacted document.
        //Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber78);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String redactedRetrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContent(urlInput);
        //System.out.println(redactedRetrieveResponseContent);
        WebResource webResource = client.resource(redactedRetrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        //String str = response.getEntity(String.class);
        //System.out.println(str);
    }

    /**
     * Test redacted retrieve response content default flavour with redacted on top.
     */
    @Test
    public void testRedactedRetrieveResponseContentDefaultFlavourWithRedactedOnTop() {
        //This test case should fetch latest redacted document.
        //Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber79);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String redactedRetrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContent(urlInput);
        //System.out.println(redactedRetrieveResponseContent);
        WebResource webResource = client.resource(redactedRetrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        assertEquals("33 KB", fileSize);        
    }    

    /**
     * Test redacted retrieve response content flavour original with redacted on top.
     */
    @Test
    public void testRedactedRetrieveResponseContentFlavourOriginalWithRedactedOnTop() {
        //This test case should fetch latest Original document.
        //Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber79);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String redactedRetrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContentFlavourOriginal(urlInput);
        //System.out.println(redactedRetrieveResponseContent);
        WebResource webResource = client.resource(redactedRetrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);    
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        assertEquals("33 KB", fileSize);        
    }    

    /**
     * Test redacted retrieve response content flavour original with original on top.
     */
    @Test
    public void testRedactedRetrieveResponseContentFlavourOriginalWithOriginalOnTop() {
        //This test case should fetch latest Original document.
        //Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber78);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String redactedRetrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContentFlavourOriginal(urlInput);
        //System.out.println(redactedRetrieveResponseContent);
        WebResource webResource = client.resource(redactedRetrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        assertEquals("35 KB", fileSize);        
    }     

}
