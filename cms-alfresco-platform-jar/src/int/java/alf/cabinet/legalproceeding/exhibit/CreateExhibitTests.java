package alf.cabinet.legalproceeding.exhibit;

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
import alf.integration.service.all.base.cabinet.legalproceeding.ExhibitBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Exhibit;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateExhibitTests extends ExhibitBaseTest {

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
     * Exhibit create and recreate test.
     */
    @Test
    public void exhibitCreateAndRecreateTest(){
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_EXHIBIT.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EXHIBIT_FILE_NAME);
        String EXHIBIT_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(EXHIBIT_CREATE_WEBSCRIPT_URL);        
        Map<String, String> exhibitParam = new HashMap<String, String>();        
        exhibitParam.put(ParameterKeys.URL.toString(),EXHIBIT_CREATE_WEBSCRIPT_URL);
        exhibitParam.put(ParameterKeys.METADATA.toString(),VALUE_EXHIBIT_METADATA);
        exhibitParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ExhibitBaseTest.CONTENT_EXHIBIT_ATTACHMENT);        

        testCreateExhibit(exhibitParam);
        testDuplicateExhibitCreation(exhibitParam);
    }    

    /**
     * Test create exhibit.
     *
     * @param exhibitParam the exhibit param
     */
    private void testCreateExhibit(Map<String, String> exhibitParam) {

        ClientResponse response = createDocument(exhibitParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Exhibit.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate exhibit creation.
     *
     * @param exhibitParam the exhibit param
     */
    private void testDuplicateExhibitCreation(Map<String, String> exhibitParam) {
        ClientResponse response = createDocument(exhibitParam);         
        assertEquals(409, response.getStatus());

    }  

}
