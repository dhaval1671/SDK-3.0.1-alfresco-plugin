package alf.drive.eogtemplate;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EogTemplateBaseTest;
import alf.integration.service.all.base.ParameterValues;

public class RetrieveEogTemplateContentTests extends EogTemplateBaseTest {

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
     * Test retrieve efile content without version.
     */
    @Test
    public void testRetrieveEogTemplateContent() {
        String WEBSCRIPT_EXT = "/cms/rest/drive/eogtemplate/" + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/"
                + EOG_TEMPLATE_FILE_NAME;
        String EOG_RETRIEVE_CONTENT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;
        ClientResponse response = retrieveContent(EOG_RETRIEVE_CONTENT_URL);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, response.getStatus());
    }

    /**
     * Retrieve content.
     *
     * @param EFILE_RETRIEVE_CONTENT_URL
     *            the efile retrieve content url
     * @return the client response
     */
    private ClientResponse retrieveContent(String EOG_RETRIEVE_CONTENT_URL) {
        WebResource webResource = client.resource(EOG_RETRIEVE_CONTENT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        return response;
    }

}
