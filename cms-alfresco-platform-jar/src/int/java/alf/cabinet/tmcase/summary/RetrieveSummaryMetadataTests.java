package alf.cabinet.tmcase.summary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

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
import alf.integration.service.all.base.SummaryBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Summary;
import junitx.framework.ListAssert;

/**

 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveSummaryMetadataTests extends SummaryBaseTest {

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
     * Test retrieve summary metadata.
     */
    @Test
    public void testRetrieveSummaryMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SUMMARY.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_SUMMARY_FILE_NAME);
        String SUMMARY_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);         
        WebResource webResource = client.resource(SUMMARY_RETRIEVE_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());

        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> first = map.get("Cache-Control");
        List<String> second = new ArrayList<String>();
        second.add("no-cache, no-store, must-revalidate");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);        

        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;

        //check for creationTime's presence
        String creationTime = "\"creationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, creationTime));

        //check for modificationTime's presence
        String modificationTime = "\"modificationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modificationTime));

        //check for Summary document's size presence
        String size = "\"documentSize\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));

        //check for mimetype's presence
        String mimetype = "\"mimeType\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, mimetype));

        String needle = "\"documentAlias\"";
        assertTrue(containsStringLiteral( haystack, needle)); 

        String docType = "\"documentType\"(.*?)\""+ Summary.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));          
    }

    /**
     * Test retrieve summary metadata with valid version.
     */
    @Test
    public void testRetrieveSummaryMetadataWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SUMMARY.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_SUMMARY_FILE_NAME);
        String SUMMARY_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);        
        WebResource webResource = client.resource(SUMMARY_RETRIEVE_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve summary metadata summary doesnt exists.
     */
    @Test
    public void testRetrieveSummaryMetadataSummaryDoesntExists() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SUMMARY.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(INVALID_FILE_NAME);
        String SUMMARY_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);         
        WebResource webResource = client.resource(SUMMARY_RETRIEVE_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
