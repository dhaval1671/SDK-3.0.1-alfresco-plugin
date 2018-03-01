package alf.drive.efile;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
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
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class DeleteEfileTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class DeleteEfileTests extends EfileBaseTest {

    /** The log. */
    public static Logger LOG = Logger.getLogger(DeleteEfileTests.class);    

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
     * Efile delete test.
     */
    @Test
    public void efileDeleteTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_EFILE_GID_DEF_778_456_TRADEMARKID.toString());
        urlInput.setFileName("my-efile.pdf");
        String EFILE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.efileCreateUrl(urlInput);
        String VALUE_EFILE_METADATA = "{     \"customProperties\": {         \"eFileProperty\": \"Property Value\"     } }";
        Map<String, String> efileParamMap = new HashMap<String, String>();
        efileParamMap.put(ParameterKeys.URL.toString(),EFILE_CREATE_WEBSCRIPT_URL);
        efileParamMap.put(ParameterKeys.METADATA.toString(),VALUE_EFILE_METADATA);
        efileParamMap.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EfileBaseTest.CONTENT_EFILE_ATTACHMENT);         
        testCreateEfile(efileParamMap);
        testDeleteEfile(urlInput);
    }    

    /**
     * Test create efile.
     *
     * @param efileParamMap the efile param map
     */
    private void testCreateEfile(Map<String, String> efileParamMap) {
        ClientResponse response = createDocument(efileParamMap);        
        assertEquals(201, response.getStatus());

    }

    /**
     * Test delete efile.
     *
     * @param urlInput the url input
     */
    private void testDeleteEfile(UrlInputDto urlInput) {
        String EFILE_DELETE_WEBSCRIPT_URL = CaseOtherUrl.efileDeleteUrl(urlInput);
        WebResource webResource = client.resource(EFILE_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.delete(ClientResponse.class);
        assertEquals(500, response.getStatus());

    }

}
