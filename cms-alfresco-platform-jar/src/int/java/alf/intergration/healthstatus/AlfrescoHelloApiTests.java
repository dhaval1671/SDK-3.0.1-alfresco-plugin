package alf.intergration.healthstatus;

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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.url.helpers.healthstatus.AlfrescoHealthStatusUrl;

/**
 * Retrieve Summary Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class AlfrescoHelloApiTests extends CentralBase {

    /** The client. */
    public Client client = null;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }

    /**
     * Test retrieve summary content.
     */
    @Test
    public void testAlfrescoHello() {
        String ALFRESCO_HELLO_WEBSCRIPT_URL = AlfrescoHealthStatusUrl.alfrescoHello();
        WebResource webResource = client.resource(ALFRESCO_HELLO_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.TEXT_PLAIN_TYPE);
        b = b.accept(MediaType.TEXT_PLAIN);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
    }

 

}
