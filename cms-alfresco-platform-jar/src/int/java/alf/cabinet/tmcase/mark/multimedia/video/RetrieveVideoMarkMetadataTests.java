package alf.cabinet.tmcase.mark.multimedia.video;

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

import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
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

public class RetrieveVideoMarkMetadataTests extends VideoMarkBaseTest {

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
     * Test retrieve mm mark metadata.
     */
    @Test
    public void testRetrieveMmMarkMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);        

        //System.out.println(MM_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MM_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;

        //check for creationTime's presence
        String creationTime = "\"creationTime\"(.*?)\"(.+?)\"";
        boolean status = containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, creationTime);
        assertTrue(status);

        //check for modificationTime's presence
        String modificationTime = "\"modificationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modificationTime));

        //check for Multimedia-Mark's document size presence
        String size = "\"documentSize\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));

        //check for mimetype's presence
        String mimetype = "\"mimeType\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, mimetype));

        String needle = "\"documentAlias\"";
        assertTrue(containsStringLiteral( haystack, needle));        

        //verify the attribute value documentType for multimedia mark
        String docTypeOfMulMrk = "\"documentType\"(.*?)\""+ MarkDoc.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docTypeOfMulMrk));    

        String multimediaMarkDocumentName = "\"documentName\"(.*?)\""+ VideoMarkBaseTest.VALUE_MMRK_FILE_NAME + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, multimediaMarkDocumentName));          
    }

    /**
     * Test retrieve mm mark metadata with valid version.
     */
    @Test
    public void testRetrieveMmMarkMetadataWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);
        //System.out.println(MM_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MM_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_VALID);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve mm mark metadata with in valid version.
     */
    @Test
    public void testRetrieveMmMarkMetadataWithInValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);         
        WebResource webResource = client.resource(MM_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_INVALID);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test retrieve mm mark metadata from bad serial number.
     */
    @Test
    public void testRetrieveMmMarkMetadataFromBadSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT3.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);             
        WebResource webResource = client.resource(MM_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(PARAM_TYPE, VALUE_TYPE);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(400, response.getStatus());
    }      

    /**
     * Test retrieve mm mark metadata from multiple cases where no mark present.
     */
    @Test
    public void testRetrieveMmMarkMetadataFromMultipleCasesWhereNoMarkPresent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);         
        WebResource webResource = client.resource(MM_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(PARAM_TYPE, VALUE_TYPE);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
