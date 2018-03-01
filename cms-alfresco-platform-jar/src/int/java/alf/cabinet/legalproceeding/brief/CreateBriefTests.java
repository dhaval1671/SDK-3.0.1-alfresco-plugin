package alf.cabinet.legalproceeding.brief;

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
import alf.integration.service.all.base.cabinet.legalproceeding.BriefBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Brief;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateBriefTests extends BriefBaseTest {

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
     * Brief create and recreate test.
     */
    @Test
    public void briefCreateAndRecreateTest(){
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_BRIEF.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_BRIEF_FILE_NAME);
        String BRIEF_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(BRIEF_CREATE_WEBSCRIPT_URL);        
        Map<String, String> briefParam = new HashMap<String, String>();        
        briefParam.put(ParameterKeys.URL.toString(),BRIEF_CREATE_WEBSCRIPT_URL);
        briefParam.put(ParameterKeys.METADATA.toString(),VALUE_BRIEF_METADATA);
        briefParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), BriefBaseTest.CONTENT_BRIEF_ATTACHMENT);        

        testCreateBrief(briefParam);
        testDuplicateBriefCreation(briefParam);
    }    

    /**
     * Test create brief.
     *
     * @param briefParam the brief param
     */
    private void testCreateBrief(Map<String, String> briefParam) {

        ClientResponse response = createDocument(briefParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Brief.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate brief creation.
     *
     * @param briefParam the brief param
     */
    private void testDuplicateBriefCreation(Map<String, String> briefParam) {
        ClientResponse response = createDocument(briefParam);         
        assertEquals(409, response.getStatus());

    }  

}
