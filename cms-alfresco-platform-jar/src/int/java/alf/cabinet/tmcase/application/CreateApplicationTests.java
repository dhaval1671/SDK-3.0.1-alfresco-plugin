package alf.cabinet.tmcase.application;

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

import alf.integration.service.all.base.ApplicationBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Application;


/**
 * The Class CreateApplicationTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateApplicationTests extends ApplicationBaseTest {

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
     * Application create and recreate test.
     */
    @Test
    public void applicationCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_APPLICATION.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_APPLICATION_FILE_NAME);
        String APPLICATION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(APPLICATION_CREATE_WEBSCRIPT_URL);
        Map<String, String> applicationParam = new HashMap<String, String>();
        applicationParam.put(ParameterKeys.URL.toString(),APPLICATION_CREATE_WEBSCRIPT_URL);
        applicationParam.put(ParameterKeys.METADATA.toString(),VALUE_APPLICATION_METADATA);
        applicationParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ApplicationBaseTest.CONTENT_APPLICATION_ATTACHMENT);

        testCreateApplication(applicationParam);
        testDuplicateApplicationCreation(applicationParam);
    }

    /**
     * Test create application.
     *
     * @param applicationParam the application param
     */
    public void testCreateApplication(Map<String, String> applicationParam) {

        ClientResponse response = createDocument(applicationParam);       

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Application.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate application creation.
     *
     * @param applicationParam the application param
     */
    public void testDuplicateApplicationCreation(Map<String, String> applicationParam) {
        ClientResponse response = createDocument(applicationParam);         
        assertEquals(409, response.getStatus());
    }  

}
