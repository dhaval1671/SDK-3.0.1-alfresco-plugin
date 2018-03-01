package alf.tmcontent.ais.ticrsdocument;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TicrsDocumentBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.model.TicrsDocument;

/**
 * The Class CreateTicrsDocumentTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateTicrsDocumentTests extends TicrsDocumentBaseTest {

    /** The client. */
    protected Client client = null;

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
     * TicrsDocument create and recreate test.
     */
    @Test
    public void ticrsDocumentCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setDocType(TicrsDocument.TYPE);
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_TICRS_DOCUMENT_FILE_NAME);
        String SPECIMEN_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(SPECIMEN_CREATE_WEBSCRIPT_URL);
        Map<String, String> ticrsDocumentParam = new HashMap<String, String>();
        ticrsDocumentParam.put(ParameterKeys.URL.toString(),SPECIMEN_CREATE_WEBSCRIPT_URL);
        ticrsDocumentParam.put(ParameterKeys.METADATA.toString(), VALUE_TICRS_DOCUMENT_METADATA);
        ticrsDocumentParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TicrsDocumentBaseTest.CONTENT_TICRS_DOCUMENT_ATTACHMENT);

        testCreateTicrsDocument(ticrsDocumentParam);
        testDuplicateTicrsDocumentCreation(ticrsDocumentParam);
    }

    /**
     * Test create ticrsDocument.
     *
     * @param ticrsDocumentParam the ticrsDocument param
     */
    private void testCreateTicrsDocument(Map<String, String> ticrsDocumentParam) {

        ClientResponse response = createDocument(ticrsDocumentParam);       

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ TicrsDocument.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate ticrsDocument creation.
     *
     * @param ticrsDocumentParam the ticrsDocument param
     */
    private void testDuplicateTicrsDocumentCreation(Map<String, String> ticrsDocumentParam) {
        ClientResponse response = createDocument(ticrsDocumentParam);         
        assertEquals(409, response.getStatus());
    }  

}
