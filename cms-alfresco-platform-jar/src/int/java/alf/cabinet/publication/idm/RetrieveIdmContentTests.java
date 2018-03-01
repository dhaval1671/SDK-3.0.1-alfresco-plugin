package alf.cabinet.publication.idm;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.IdmBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.IdmUrlInputDto;
import alf.integration.service.url.helpers.publication.PublicationRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveIdmContentTests extends IdmBaseTest{

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
     * Test retrieve note content.
     */
    @Test
    public void testRetrieveIdmContent() {
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        String IDM_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);        
        WebResource webResource = client.resource(IDM_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        assertEquals("78 KB", fileSizeFrResponse);        
    }

    /**
     * Test retrieve note content for invalid serial number.
     */
    @Test
    public void testRetrieveIdmContentForInvalidSerialNumber() {
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        String NOTE_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);         
        WebResource webResource = client.resource(NOTE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(404, response.getStatus());
    }    

    /**
     * Test retrieve note content with valid version.
     */
    @Test
    public void testRetrieveIdmContentWithValidVersion() {
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        String NOTE_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);        
        WebResource webResource = client.resource(NOTE_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_VALID);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(404, response.getStatus());
    }    

    /**
     * Test retrieve note content with in valid version.
     */
    @Test
    public void testRetrieveIdmContentWithInValidVersion() {
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        String NOTE_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);          
        WebResource webResource = client.resource(NOTE_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_INVALID);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test retrieve note content note dosnt exists.
     */
    @Test
    public void testRetrieveIdmContentNoteDosntExists() {
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_FILE_NAME_DOESNT_EXISTS);
        String NOTE_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);        
        WebResource webResource = client.resource(NOTE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
