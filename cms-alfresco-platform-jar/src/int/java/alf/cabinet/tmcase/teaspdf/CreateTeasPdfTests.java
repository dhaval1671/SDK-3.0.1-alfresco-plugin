package alf.cabinet.tmcase.teaspdf;

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
import alf.integration.service.all.base.TeasPdfBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.TeasPdf;

/**
 * The Class CreateTeaspdfTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateTeasPdfTests extends TeasPdfBaseTest{

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
     * Teaspdf create and recreate test.
     */
    @Test
    public void TeaspdfCreateAndRecreateTest(){
        Map<String, String> TeaspdfParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_TEAS_PDF.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_TEAS_PDF_FILE_NAME);
        String TEASPDF_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput); 
        //System.out.println(TEASPDF_CREATE_WEBSCRIPT_URL);
        TeaspdfParam.put(ParameterKeys.URL.toString(),TEASPDF_CREATE_WEBSCRIPT_URL);
        TeaspdfParam.put(ParameterKeys.METADATA.toString(),VALUE_TEAS_PDF_METADATA);
        TeaspdfParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TeasPdfBaseTest.CONTENT_TEAS_PDF_ATTACHMENT);        
        testCreateTeaspdf(TeaspdfParam);
        testDuplicateTeaspdfCreation(TeaspdfParam);
    }    

    /**
     * Test create Teaspdf.
     *
     * @param TeaspdfParam the Teaspdf param
     */
    public void testCreateTeaspdf(Map<String, String> TeaspdfParam) {
        ClientResponse response = createDocument(TeaspdfParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ TeasPdf.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
    }

    /**
     * Test duplicate Teaspdf creation.
     *
     * @param TeaspdfParam the Teaspdf param
     */
    public void testDuplicateTeaspdfCreation(Map<String, String> TeaspdfParam) {
        ClientResponse response = createDocument(TeaspdfParam);         
        assertEquals(409, response.getStatus());
    }  

}
