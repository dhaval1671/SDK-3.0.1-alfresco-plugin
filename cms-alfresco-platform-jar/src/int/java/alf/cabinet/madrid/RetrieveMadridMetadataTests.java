package alf.cabinet.madrid;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.MadridBaseTest;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.MadridUrlInputDto;
import alf.integration.service.url.helpers.madrid.MadridRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkMadridTypes;

/**
 * The Class RetrieveMadridMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveMadridMetadataTests extends MadridBaseTest {

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
     * Test retrieve madrid metadata.
     */
    @Test
    public void testRetrieveMadridMetadata() {
        MadridUrlInputDto urlInput = new MadridUrlInputDto(TradeMarkMadridTypes.TYPE_MADRID.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MADRID_FILE_NAME);
        String MADRID_RETRIEVE_METADATA_WEBSCRIPT_URL = MadridRetrieveMetadataUrl.retrieveMadridMetadata(urlInput);
        //System.out.println(MADRID_RETRIEVE_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MADRID_RETRIEVE_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);        
        assertEquals(404, response.getStatus());
    }

}
