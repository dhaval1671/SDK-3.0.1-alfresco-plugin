package alf.cabinet.legalproceeding.exhibit;

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
import alf.integration.service.all.base.cabinet.legalproceeding.ExhibitBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;

/**
 * Retrieve Exhibit Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveExhibitContentTests extends ExhibitBaseTest {

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
     * Test retrieve exhibit content.
     */
    @Test
    public void testRetrieveExhibitContent() {
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_EXHIBIT.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EXHIBIT_FILE_NAME);
        urlInput.setAccessLevel("internal");
        String EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL = LegalProceedingUrl.retrieveContentGenericUrl(urlInput);
        WebResource webResource = client.resource(EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("4 KB", fileSizeFrResponse);
    }

    /**
     * Test retrieve exhibit content for invalid serial number.
     */
    @Test
    public void testRetrieveExhibitContentForInvalidSerialNumber() {
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_EXHIBIT.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString());
        urlInput.setFileName(VALUE_EXHIBIT_FILE_NAME);
        urlInput.setAccessLevel("internal");
        String EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL = LegalProceedingUrl.retrieveContentGenericUrl(urlInput);
        WebResource webResource = client.resource(EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve exhibit content exhibit dosnt exists.
     */
    @Test
    public void testRetrieveExhibitContentExhibitDosntExists() {
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_EXHIBIT.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(INVALID_FILE_NAME);
        urlInput.setAccessLevel("internal");
        String EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL = LegalProceedingUrl.retrieveContentGenericUrl(urlInput);
        WebResource webResource = client.resource(EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test retrieve exhibit content where file name supplied is blank.
     */
    @Test
    public void testRetrieveExhibitContentWhereFileNameSuppliedIsBlank() {
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_EXHIBIT.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("");
        urlInput.setAccessLevel("internal");
        String EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL = LegalProceedingUrl.retrieveContentGenericUrl(urlInput);
        WebResource webResource = client.resource(EXHIBIT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(404, response.getStatus());
    }

}
