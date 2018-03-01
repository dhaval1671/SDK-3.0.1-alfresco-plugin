package alf.cabinet.tmcase.signature;

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
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.SignatureBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * Retrieve Signature Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveSignatureContentTests extends SignatureBaseTest {

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
     * Test retrieve signature content.
     */
    @Test
    public void testRetrieveSignatureContent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_SIGNATURE_FILE_NAME);
        String SIGNATURE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        WebResource webResource = client.resource(SIGNATURE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("30 KB", fileSizeFrResponse);
    }

    /**
     * Test retrieve signature content for invalid serial number.
     */
    @Test
    public void testRetrieveSignatureContentForInvalidSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString());
        urlInput.setFileName(VALUE_SIGNATURE_FILE_NAME);
        String SIGNATURE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);         
        WebResource webResource = client.resource(SIGNATURE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(400, response.getStatus());
    }    

    /**
     * Test retrieve signature content signature dosnt exists.
     */
    @Test
    public void testRetrieveSignatureContentSignatureDosntExists() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(INVALID_FILE_NAME);
        String SIGNATURE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);        
        WebResource webResource = client.resource(SIGNATURE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
