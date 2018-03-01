package alf.cabinet.xmlxsldtd.ticrs;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.LegacyTicrsBaseTest;
import alf.integration.service.dtos.LegacyTicrsDto;
import alf.integration.service.url.helpers.tmcase.LegacyTicrsUrl;

/**
 * Company Home > cabinet > xml-xsl-dtd > exportpath > DTD_XSL > A10 > 1.0 > TICRSIMAGERETRIEVAL > 1.0 > a101.0ticrsimageretrieval1.0.xsl
 * Retrieve LegacyTicrs Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveLegacyTicrsContentTests extends LegacyTicrsBaseTest {

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
     * Test retrieve LegacyTicrs content.
     */
    @Test
    public void testRetrieveLegacyTicrsContent_HappyPath01() {
        LegacyTicrsDto ltdInput = new LegacyTicrsDto();
        ltdInput.setDocType("A10");
        ltdInput.setVersionFolder("1.0");
        ltdInput.setFileName("a101.0ticrsimageretrieval1.0.xsl");
        String LEGACY_TICRS_RETRIEVE_CONTENT_WEBSCRIPT_URL = LegacyTicrsUrl.retrieveLegacyTicrsContentUrl(ltdInput);
        //System.out.println(LEGACY_TICRS_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(LEGACY_TICRS_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("10 KB", fileSizeFrResponse);
    }
    
    @Test
    public void testRetrieveLegacyTicrsContent_Negative01() {
        LegacyTicrsDto ltdInput = new LegacyTicrsDto();
        ltdInput.setDocType("A10");
        ltdInput.setVersionFolder("1.095");
        ltdInput.setFileName("a101.0ticrsimageretrieval1.0.xsl");
        String LEGACY_TICRS_RETRIEVE_CONTENT_WEBSCRIPT_URL = LegacyTicrsUrl.retrieveLegacyTicrsContentUrl(ltdInput);
        //System.out.println(LEGACY_TICRS_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(LEGACY_TICRS_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(404, response.getStatus());
    }    

  

}
