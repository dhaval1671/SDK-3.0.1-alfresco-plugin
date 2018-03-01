package alf.drive.efile;

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

import alf.integration.service.all.base.EfileBaseTest;
import alf.integration.service.all.base.ParameterValues;

/**
 * Retrieve Efile Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveEfileContentTests extends EfileBaseTest {

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
     * Test retrieve efile content with version.
     */
    @Test
    public void testRetrieveEfileContentWithVersion() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "my-efile.pdf" + "?version=" + "1.0";
        String EFILE_RETRIEVE_CONTENT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveContent(EFILE_RETRIEVE_CONTENT_URL);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
    } 

    /**
     * Test retrieve efile content without version.
     */
    @Test
    public void testRetrieveEfileContentWithoutVersion() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "my-efile.pdf";
        String EFILE_RETRIEVE_CONTENT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveContent(EFILE_RETRIEVE_CONTENT_URL);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
    }  

    /**
     * Test retrieve efile content with invalid trademark id.
     */
    @Test
    public void testRetrieveEfileContentWithInvalidTrademarkId() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + "0000" + "/" + "my-efile.pdf";
        String EFILE_RETRIEVE_CONTENT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveContent(EFILE_RETRIEVE_CONTENT_URL);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(org.springframework.http.HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }  

    /**
     * Test retrieve efile content with file that does not exist.
     */
    @Test
    public void testRetrieveEfileContentWithFileThatDoesNotExist() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/e-file/" + ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "ThisEfileDoesNotExist.pdf";
        String EFILE_RETRIEVE_CONTENT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;             
        ClientResponse response = retrieveContent(EFILE_RETRIEVE_CONTENT_URL);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

    /**
     * Retrieve content.
     *
     * @param EFILE_RETRIEVE_CONTENT_URL the efile retrieve content url
     * @return the client response
     */
    private ClientResponse retrieveContent(String EFILE_RETRIEVE_CONTENT_URL) {
        WebResource webResource = client.resource(EFILE_RETRIEVE_CONTENT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        return response;
    }    

}
