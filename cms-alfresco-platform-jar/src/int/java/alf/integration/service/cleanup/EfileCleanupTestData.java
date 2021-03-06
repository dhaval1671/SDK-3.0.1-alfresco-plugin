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

public class EfileCleanupTestData extends TmnguiBaseTest {

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
     * Cleanup efile test7777 data.
     */
    @Test
    public void cleanupEfileTest777Data() {
        //String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_TRADEMARKID.toString() + "/" + "all" + "/" + "c";
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() + "/" + "b" + "/" + "c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;    

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
    
    @Test
    public void cleanupEfileTestGehData() {
        //String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_TRADEMARKID.toString() + "/" + "all" + "/" + "c";
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_GEH_778_001_TRADEMARKID.toString() + "/" + "b" + "/" + "c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;    

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
     * Cleanup efile test7778 data.
     */
    @Test
    public void cleanupEfileTest7778Data() {
        //String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_TRADEMARKID.toString() + "/" + "all" + "/" + "c";
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_MJK_777_008_TRADEMARKID.toString() + "/" + "b" + "/" + "c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;          
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
     * Cleanup efile test7779 data.
     */
    @Test
    public void cleanupEfileTest7779Data() {
        //String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_TRADEMARKID.toString() + "/" + "all" + "/" + "c";
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_UPR_777_009_TRADEMARKID.toString() + "/" + "b" + "/" + "c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;          
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
     * Cleanup efile test7780 data.
     */
    @Test
    public void cleanupEfileTest7780Data() {
        //String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_TRADEMARKID.toString() + "/" + "all" + "/" + "c";
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_GID_DEF_778_456_TRADEMARKID.toString() + "/" + "b" + "/" + "c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;          
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
    
    @Test
    public void cleanupEfileTestAbcData() {
        //String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ ParameterValues.VALUE_EFILE_TRADEMARKID.toString() + "/" + "all" + "/" + "c";
        String WEBSCRIPT_EXT = "/cms/validate/" + "deleteEfileTestFolder" + "/"+ "abc" + "/" + "b" + "/" + "c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;          
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
