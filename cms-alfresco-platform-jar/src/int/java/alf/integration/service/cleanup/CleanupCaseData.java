package alf.integration.service.cleanup;

import static org.junit.Assert.assertEquals;

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

import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.url.helpers.UrlHelper;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class CleanupCaseData extends TmnguiBaseTest {

    /** The Constant API_KEY. */
    private static final String API_KEY = "P0bl162t10N2";

    /** The client. */
    public Client client = null;

    /** The alf url. */
    public String alfUrl = null;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        //System.out.println(ALF_ADMIN_LOGIN_USER_ID +" <--> "+ ALF_ADMIN_LOGIN_PWD);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));
        alfUrl = UrlHelper.pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }

    /**
     * Cleanup test data.
     */
    @Test
    public void cleanup777TestData() {
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteTestFolder" + "/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/b/c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;
        //System.out.println(alfUrl);
        //System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.header("API-Security-Key", API_KEY);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());        
        //String haystack = str;        
    }
    
    /**
     * Cleanup test data a77.
     */
    @Test
    public void cleanupTestDataA77() {
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteTestFolder" + "/"+ "A77" + "/b/c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;
        //System.out.println(alfUrl);
        //System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.header("API-Security-Key", API_KEY);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());        
        //String haystack = str;        
    }
    
    /**
     * Cleanup test data b77.
     */
    @Test
    public void cleanupTestDataB77() {
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteTestFolder" + "/"+ "B77" + "/b/c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;
        //System.out.println(alfUrl);
        //System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.header("API-Security-Key", API_KEY);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());        
        //String haystack = str;        
    }    


    
}
