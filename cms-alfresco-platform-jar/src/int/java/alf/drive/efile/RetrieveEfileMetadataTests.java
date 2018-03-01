package alf.drive.efile;

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

import alf.integration.service.all.base.EfileBaseTest;
import alf.integration.service.all.base.ParameterValues;
import gov.uspto.trademark.cms.repo.model.drive.efile.Efile;

/**
 * The Class RetrieveEfileMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveEfileMetadataTests extends EfileBaseTest {

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
     * Test retrieve efile metadata with valid version.
     */
    @Test
    public void testRetrieveEfileMetadataWithValidVersion() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "my-efile.pdf" + "/metadata?version=" + "1.0";
        String EFILE_RETRIEVE_METADATA_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveMetadata(EFILE_RETRIEVE_METADATA_URL);
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

        //check for Efile document's size presence
        String size = "\"documentSize\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));

        //check for mimetype's presence
        String mimetype = "\"mimeType\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, mimetype));

        String docType = "\"documentType\"(.*?)\""+ Efile.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));          

    }

    /**
     * Test retrieve efile metadata without version.
     */
    @Test
    public void testRetrieveEfileMetadataWithoutVersion() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "my-efile.pdf" + "/metadata";
        String EFILE_RETRIEVE_METADATA_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveMetadata(EFILE_RETRIEVE_METADATA_URL);
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

        //check for Efile document's size presence
        String size = "\"documentSize\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));

        //check for mimetype's presence
        String mimetype = "\"mimeType\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, mimetype));

        //check for id's presence
        String id = "\"id\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, id));         

    }    

    /**
     * Test retrieve efile metadata with invalid version.
     */
    @Test
    public void testRetrieveEfileMetadataWithInvalidVersion() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "my-efile.pdf" + "/metadata?version=" + "3.9";
        String EFILE_RETRIEVE_METADATA_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveMetadata(EFILE_RETRIEVE_METADATA_URL);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }   

    /**
     * Test retrieve efile metadata with file that does not exist.
     */
    @Test
    public void testRetrieveEfileMetadataWithFileThatDoesNotExist() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "thisEfileDoesNotExist.pdf" + "/metadata";
        String EFILE_RETRIEVE_METADATA_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveMetadata(EFILE_RETRIEVE_METADATA_URL);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }      

    /**
     * Retrieve metadata.
     *
     * @param EFILE_RETRIEVE_METADATA_URL the efile retrieve metadata url
     * @return the client response
     */
    private ClientResponse retrieveMetadata(String EFILE_RETRIEVE_METADATA_URL) {
        WebResource webResource = client.resource(EFILE_RETRIEVE_METADATA_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        return response;
    }

}
