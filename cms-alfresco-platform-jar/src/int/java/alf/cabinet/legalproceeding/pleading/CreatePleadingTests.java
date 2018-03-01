package alf.cabinet.legalproceeding.pleading;

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
import alf.integration.service.all.base.cabinet.legalproceeding.PleadingsBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Pleading;

/**

 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreatePleadingTests extends PleadingsBaseTest {

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
     * LegalProceeding create and recreate test.
     */
    @Test
    public void leaglProceedingCreateAndRecreateTest(){
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_PLEADING.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_PLEADING_LEGALPROCEEDING_FILE_NAME);
        String LEGALPROCEEDING_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(LEGALPROCEEDING_CREATE_WEBSCRIPT_URL);        
        Map<String, String> leaglProceedingParam = new HashMap<String, String>();        
        leaglProceedingParam.put(ParameterKeys.URL.toString(),LEGALPROCEEDING_CREATE_WEBSCRIPT_URL);
        leaglProceedingParam.put(ParameterKeys.METADATA.toString(),VALUE_LEGALPROCEEDING_METADATA);
        leaglProceedingParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), PleadingsBaseTest.CONTENT_LEGALPROCEEDING_ATTACHMENT);        

        testCreateLegalProceeding(leaglProceedingParam);
        testDuplicateLegalProceedingCreation(leaglProceedingParam);
    }    

    /**
     * Test create LegalProceeding.
     *
     * @param leaglProceedingParam the LegalProceeding param
     */
    private void testCreateLegalProceeding(Map<String, String> leaglProceedingParam) {

        ClientResponse response = createDocument(leaglProceedingParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Pleading.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate LegalProceeding creation.
     *
     * @param leaglProceedingParam the LegalProceeding param
     */
    private void testDuplicateLegalProceedingCreation(Map<String, String> leaglProceedingParam) {

        ClientResponse response = createDocument(leaglProceedingParam);         
        assertEquals(409, response.getStatus());

    }  

}
