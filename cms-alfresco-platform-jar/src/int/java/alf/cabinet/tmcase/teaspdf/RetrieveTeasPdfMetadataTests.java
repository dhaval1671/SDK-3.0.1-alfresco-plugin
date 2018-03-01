package alf.cabinet.tmcase.teaspdf;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

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

import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TeasPdfBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveTeasPdfMetadataTests extends TeasPdfBaseTest {

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
     * Test retrieve notice metadata.
     */
    @Test
    public void testRetrieveTeaspdfMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_TEAS_PDF.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_TEAS_PDF_FILE_NAME);
        String TEASPDF_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);
        //System.out.println(TEASPDF_RETRIEVE_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(TEASPDF_RETRIEVE_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve notice metadata with valid version.
     */
    @Test
    public void testRetrieveTeaspdfMetadataWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_TEAS_PDF.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_TEAS_PDF_FILE_NAME);
        String TEASPDF_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);          
        WebResource webResource = client.resource(TEASPDF_RETRIEVE_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve notice metadata notice doesnt exists.
     */
    @Test
    public void testRetrieveTeaspdfMetadataTeaspdfDoesntExists() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_TEAS_PDF.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(INVALID_FILE_NAME);
        String TEASPDF_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);        
        WebResource webResource = client.resource(TEASPDF_RETRIEVE_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
