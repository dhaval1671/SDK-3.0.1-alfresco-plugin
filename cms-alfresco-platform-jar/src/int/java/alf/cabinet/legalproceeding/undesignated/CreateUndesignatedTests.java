package alf.cabinet.legalproceeding.undesignated;

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
import alf.integration.service.all.base.cabinet.legalproceeding.UndesignatedBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Undesignated;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateUndesignatedTests extends UndesignatedBaseTest {

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
     * Undesignated create and recreate test.
     */
    @Test
    public void undesignatedCreateAndRecreateTest(){
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_UNDESIGNATED.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_UNDESIGNATED_FILE_NAME);
        String UNDESIGNATED_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(UNDESIGNATED_CREATE_WEBSCRIPT_URL);        
        Map<String, String> undesignatedParam = new HashMap<String, String>();        
        undesignatedParam.put(ParameterKeys.URL.toString(),UNDESIGNATED_CREATE_WEBSCRIPT_URL);
        undesignatedParam.put(ParameterKeys.METADATA.toString(),VALUE_UNDESIGNATED_METADATA);
        undesignatedParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), UndesignatedBaseTest.CONTENT_UNDESIGNATED_ATTACHMENT);        

        testCreateUndesignated(undesignatedParam);
        testDuplicateUndesignatedCreation(undesignatedParam);
    }    

    /**
     * Test create undesignated.
     *
     * @param undesignatedParam the undesignated param
     */
    private void testCreateUndesignated(Map<String, String> undesignatedParam) {

        ClientResponse response = createDocument(undesignatedParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Undesignated.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate undesignated creation.
     *
     * @param undesignatedParam the undesignated param
     */
    private void testDuplicateUndesignatedCreation(Map<String, String> undesignatedParam) {
        ClientResponse response = createDocument(undesignatedParam);         
        assertEquals(409, response.getStatus());

    }  

}
