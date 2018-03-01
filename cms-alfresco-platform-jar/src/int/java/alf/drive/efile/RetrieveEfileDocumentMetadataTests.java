package alf.drive.efile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EfileBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;

/**
 * The Class RetrieveEfileDocumentMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveEfileDocumentMetadataTests extends EfileBaseTest {

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
     * Creates the efile document for retrieval.
     */
    private void createEfileDocumentForRetrieval(){
        createEfile("my-efile-One.pdf");
        createEfile("my-efile-Two.pdf");
    }

    /**
     * Creates the efile.
     *
     * @param fileName the file name
     */
    private void createEfile(String fileName) {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + fileName;
        String EFILE_CREATE_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;              
        String VALUE_EFILE_METADATA = "{     \"customProperties\": {         \"eFileProperty\": \"Property Value\"     } }";

        Map<String, String> efileParamMap = new HashMap<String, String>();
        efileParamMap.put(ParameterKeys.URL.toString(),EFILE_CREATE_WEBSCRIPT_URL);
        efileParamMap.put(ParameterKeys.METADATA.toString(),VALUE_EFILE_METADATA);
        efileParamMap.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EfileBaseTest.CONTENT_EFILE_ATTACHMENT);         

        ClientResponse response = createDocument(efileParamMap);        
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
    }

    /**
     * Test retrieve efile document metadata.
     */
    @Test
    public void testRetrieveEfileDocumentMetadata() {

        //Create the efile documents
        createEfileDocumentForRetrieval();

        //Retrieve the efile documents.
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/documents/metadata";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;
        //System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());

        String haystack = str;

        //check for presence of image mark
        String efileOne = "/drive/e-file/"+ ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/my-efile-One.pdf";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, efileOne)); 

        //check for presence of multimedia mark
        String efileTwo = "/drive/e-file/"+ ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/my-efile-Two.pdf";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, efileTwo));

    }    

}
