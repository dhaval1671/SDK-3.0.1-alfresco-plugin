package alf.cabinet.legalproceeding.decision;

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
import alf.integration.service.all.base.cabinet.legalproceeding.DecisionBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Decision;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateDecisionTests extends DecisionBaseTest {

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
     * Decision create and recreate test.
     */
    @Test
    public void decisionCreateAndRecreateTest(){
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_DECISION.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_DECISION_FILE_NAME);
        String DECISION_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(DECISION_CREATE_WEBSCRIPT_URL);        
        Map<String, String> decisionParam = new HashMap<String, String>();        
        decisionParam.put(ParameterKeys.URL.toString(),DECISION_CREATE_WEBSCRIPT_URL);
        decisionParam.put(ParameterKeys.METADATA.toString(),VALUE_DECISION_METADATA);
        decisionParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), DecisionBaseTest.CONTENT_DECISION_ATTACHMENT);        

        testCreateDecision(decisionParam);
        testDuplicateDecisionCreation(decisionParam);
    }    

    /**
     * Test create decision.
     *
     * @param decisionParam the decision param
     */
    private void testCreateDecision(Map<String, String> decisionParam) {

        ClientResponse response = createDocument(decisionParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Decision.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate decision creation.
     *
     * @param decisionParam the decision param
     */
    private void testDuplicateDecisionCreation(Map<String, String> decisionParam) {
        ClientResponse response = createDocument(decisionParam);         
        assertEquals(409, response.getStatus());

    }  

}
