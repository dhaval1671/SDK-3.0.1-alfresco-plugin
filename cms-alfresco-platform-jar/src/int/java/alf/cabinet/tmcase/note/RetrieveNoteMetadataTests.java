package alf.cabinet.tmcase.note;

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

import alf.integration.service.all.base.NoteBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Note;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveNoteMetadataTests extends NoteBaseTest {

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
     * Test retrieve note metadata.
     */
    @Test
    public void testRetrieveNoteMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_NOTE_FILE_NAME);
        String NOTE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);         
        WebResource webResource = client.resource(NOTE_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;

        //check for creationTime's presence
        String creationTime = "\"creationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, creationTime));

        //check for modificationTime's presence
        String modificationTime = "\"modificationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modificationTime));

        //check for Note document's size presence
        String size = "\"documentSize\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));

        //check for mimetype's presence
        String mimetype = "\"mimeType\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, mimetype));

        String needle = "\"documentAlias\"";
        assertTrue(containsStringLiteral( haystack, needle));      

        String docType = "\"documentType\"(.*?)\""+ Note.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));          
    }

    /**
     * Test retrieve note metadata with valid version.
     */
    @Test
    public void testRetrieveNoteMetadataWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_NOTE_FILE_NAME);
        String NOTE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);           
        WebResource webResource = client.resource(NOTE_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_VALID);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve note metadata with in valid version.
     */
    @Test
    public void testRetrieveNoteMetadataWithInValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_NOTE_FILE_NAME);
        String NOTE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);           
        WebResource webResource = client.resource(NOTE_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_INVALID);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test retrieve note metadata note doesnt exists.
     */
    @Test
    public void testRetrieveNoteMetadataNoteDoesntExists() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_FILE_NAME_DOSNT_EXITS);
        String NOTE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);          
        WebResource webResource = client.resource(NOTE_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
