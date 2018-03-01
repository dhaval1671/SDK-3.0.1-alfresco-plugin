package alf.integration.service.retrieveContentAsAttachment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TicrsDocumentBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.model.TicrsDocument;

/**
 * Retrieve TicrsDocument Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveTicrsDocumentContentAsAttachmentTests extends TicrsDocumentBaseTest {

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
    
    @Test
    public void testTicrsDocumentContentRetrievalAsAtttachement(){
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setDocType(TicrsDocument.TYPE);
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("ticrsDocumentContentRetrievalAsAttachment.docx");

        createTicrsDocument(urlInput);
        testRetrieveTicrsDocumentContent(urlInput);
    }

    /**  
     * @Title: createTicrsDocument  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void createTicrsDocument(UrlInputDto urlInput) {
        String SPECIMEN_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(SPECIMEN_CREATE_WEBSCRIPT_URL);
        Map<String, String> ticrsDocumentParam = new HashMap<String, String>();
        ticrsDocumentParam.put(ParameterKeys.URL.toString(),SPECIMEN_CREATE_WEBSCRIPT_URL);
        ticrsDocumentParam.put(ParameterKeys.METADATA.toString(), VALUE_TICRS_DOCUMENT_METADATA);
        ticrsDocumentParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TicrsDocumentBaseTest.CONTENT_TICRS_DOCUMENT_TIF);
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
     * Test retrieve specimen content.
     * @param urlInput 
     */

    private void testRetrieveTicrsDocumentContent(UrlInputDto urlInput) {
        String TICRS_DOCUMENT_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentAsAttachmentUrl(urlInput);
        //System.out.println(TICRS_DOCUMENT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(TICRS_DOCUMENT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("31 KB", fileSizeFrResponse);
    }

  

}
