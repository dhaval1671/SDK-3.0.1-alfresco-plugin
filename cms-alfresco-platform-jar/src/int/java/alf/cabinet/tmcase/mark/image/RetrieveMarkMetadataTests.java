package alf.cabinet.tmcase.mark.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveMarkMetadataTests extends MarkBaseTest {

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
     * Test retrieve mark metadata.
     */
    @Test
    public void testRetrieveMarkMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needle = "\"documentAlias\":";
        assertTrue(containsStringLiteral(haystack, needle));    

        //verify the attribute value documentType for image mark
        String docTypeOfImgMrk = "\"documentType\"(.*?)\""+ MarkDoc.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docTypeOfImgMrk));   

        String imageMarkDocumentName = "\"documentName\"(.*?)\""+ VALUE_MRK_FILE_NAME + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, imageMarkDocumentName));         
    }

    /**
     * Test retrieve mark metadata with valid version.
     */
    @Test
    public void testRetrieveMarkMetadataWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setVersion(VALUE_VERSION_VALID);
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);        
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve mark metadata with in valid version.
     */
    @Test
    public void testRetrieveMarkMetadataWithInValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setVersion(VALUE_VERSION_INVALID);
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);            
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        // System.out.println(str);        
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test retrieve mark metadata from multiple cases where no mark present.
     */
    @Test
    public void testRetrieveMarkMetadataFromMultipleCasesWhereNoMarkPresent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);         

        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(PARAM_TYPE, VALUE_TYPE);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
