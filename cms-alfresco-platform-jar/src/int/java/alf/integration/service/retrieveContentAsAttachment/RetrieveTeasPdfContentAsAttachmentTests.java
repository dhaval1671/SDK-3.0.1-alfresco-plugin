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
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TeasPdfBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.TeasPdf;

/**
 * Retrieve Teaspdf Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveTeasPdfContentAsAttachmentTests extends TeasPdfBaseTest{

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
    public void testTeasPdfContentRetrievalAsAtttachement(){
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setDocType(TeasPdf.TYPE);
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("TeasPdfDocToBeRetrieveAsAttachment");

        createTicrsDocument(urlInput);
        testRetrieveTeaspdfContent(urlInput);
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
        ticrsDocumentParam.put(ParameterKeys.METADATA.toString(), VALUE_TEAS_PDF_METADATA);
        ticrsDocumentParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TeasPdfBaseTest.CONTENT_TEAS_PDF_ATTACHMENT);
        ClientResponse response = createDocument(ticrsDocumentParam);       
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        //check for valid docuemnt ID
        String validDocumentID = "/"+ TeasPdf.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
    }
    
    private void testRetrieveTeaspdfContent(UrlInputDto urlInput) {
        String TEASPDF_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentAsAttachmentUrl(urlInput);
        //System.out.println(TEASPDF_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(TEASPDF_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("78 KB", fileSizeFrResponse);
    }

  

}
