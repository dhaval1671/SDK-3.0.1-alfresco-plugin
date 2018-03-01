package alf.cabinet.madrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.MadridBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.MadridUrlInputDto;
import alf.integration.service.url.helpers.madrid.MadridCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkMadridTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.madridib.Madrid;

/**
 * The Class CreateMadridTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateMadridTests extends MadridBaseTest {

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
     * madrid create and recreate test.
     */
    @Test
    public void madridCreateAndRecreateTest(){
        MadridUrlInputDto urlInput = new MadridUrlInputDto(TradeMarkMadridTypes.TYPE_MADRID.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MADRID_FILE_NAME);
        String MADRID_CREATE_WEBSCRIPT_URL = MadridCreateUrl.createMadridUrl(urlInput);
        //System.out.println(MADRID_CREATE_WEBSCRIPT_URL);
        Map<String, String> madridParam = new HashMap<String, String>();
        madridParam.put(ParameterKeys.URL.toString(),MADRID_CREATE_WEBSCRIPT_URL);
        //madridParam.put(ParameterKeys.METADATA.toString(),VALUE_MADRID_METADATA);
        madridParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MadridBaseTest.CONTENT_MADRID_ATTACHMENT);

        testCreateMadrid(madridParam);
        testDuplicateMadridCreation(madridParam);
    }

    /**
     * Test create madrid.
     *
     * @param madridParam the madrid param
     */
    private void testCreateMadrid(Map<String, String> madridParam) {

        ClientResponse response = createDocument(madridParam);       

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Madrid.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate madrid creation.
     *
     * @param madridParam the madrid param
     */
    private void testDuplicateMadridCreation(Map<String, String> madridParam) {
        ClientResponse response = createDocument(madridParam);         
        assertEquals(409, response.getStatus());
    }  

}
