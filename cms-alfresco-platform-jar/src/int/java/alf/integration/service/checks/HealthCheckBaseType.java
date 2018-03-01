package alf.integration.service.checks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.junit.Before;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.url.helpers.UrlHelper;

/**
 * The Class HealthCheckBaseType.
 */
public class HealthCheckBaseType extends TmnguiBaseTest{

    /** The Constant API_SECURITY_KEY. */
    static final String API_SECURITY_KEY = "API-Security-Key";
    
    /** The Constant VALUE. */
    static final String VALUE = "P0bl162t10N2";

    /** The client. */
    public Client client = null;
    
    /** The alf url. */
    String alfUrl = null;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));

        alfUrl = UrlHelper.pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
    }    

    /**
     * Check folder presence and its type.
     *
     * @param path the path
     * @param qname the qname
     */
    protected void checkFolderPresenceAndItsType(String path, String qname) {
        String WEBSCRIPT_EXT = "/cms/validate/" + "validateTopLevelFolders" + "/"+ path + "/" + qname + "/" + "c";
        String WEBSCRIPT_URL = alfUrl + WEBSCRIPT_EXT;
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.header(API_SECURITY_KEY, VALUE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());
        String haystack = str;
        //check for presence of NodeRef
        String nodeRefUuid = "workspace://SpacesStore/[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, nodeRefUuid));
    }

}
