package alf.cabinet.publication.eog;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

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

import alf.integration.service.all.base.EogBaseTest;
import alf.integration.service.all.base.EogParameterValues;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.EogUrlInputDto;
import alf.integration.service.url.helpers.publication.PublicationRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveEogContentTests extends EogBaseTest{

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
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testRetrieveEogContent() throws IOException {
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName(VALUE_EOG_FILE_NAME);
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);
        //System.out.println(PUBLICATION_CONTENT_URL);        
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(200, response.getStatus());        
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals("78 KB", fileSizeFrResponse);        
    }

    /**
     * Test retrieve note content for invalid serial number.
     */
    @Test
    public void testRetrieveEogContentForInvalidDateFolder() {
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_FOLDER_DOES_NOT_EXIST_20150632.toString());
        urlInput.setFileName(VALUE_EOG_FILE_NAME);
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);         
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(400, response.getStatus());
    }    

    /**
     * Test retrieve note content with valid version.
     */
    @Test
    public void testRetrieveEogContentWithValidVersion() {
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName(VALUE_EOG_FILE_NAME);
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);        
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
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
    public void testRetrieveEogContentWithInValidVersion() {
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName(VALUE_EOG_FILE_NAME);
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);          
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
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
    public void testRetrieveEogContentDosntExists() {
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName(VALUE_FILE_NAME_DOESNT_EXISTS);
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);        
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
