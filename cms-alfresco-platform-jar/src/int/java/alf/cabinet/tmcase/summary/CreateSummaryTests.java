package alf.cabinet.tmcase.summary;

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
import alf.integration.service.all.base.SummaryBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Summary;

/**

 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateSummaryTests extends SummaryBaseTest {

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
     * Summary create and recreate test.
     */
    @Test
    public void summaryCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SUMMARY.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_SUMMARY_FILE_NAME);
        String SUMMARY_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(SUMMARY_CREATE_WEBSCRIPT_URL);        
        Map<String, String> summaryParam = new HashMap<String, String>();        
        summaryParam.put(ParameterKeys.URL.toString(),SUMMARY_CREATE_WEBSCRIPT_URL);
        summaryParam.put(ParameterKeys.METADATA.toString(),VALUE_SUMMARY_METADATA);
        summaryParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SummaryBaseTest.CONTENT_SUMMARY_ATTACHMENT);        

        testCreateSummary(summaryParam);
        testDuplicateSummaryCreation(summaryParam);
    }    

    /**
     * Test create summary.
     *
     * @param summaryParam the summary param
     */
    private void testCreateSummary(Map<String, String> summaryParam) {

        ClientResponse response = createDocument(summaryParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Summary.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate summary creation.
     *
     * @param summaryParam the summary param
     */
    private void testDuplicateSummaryCreation(Map<String, String> summaryParam) {

        ClientResponse response = createDocument(summaryParam);         
        assertEquals(409, response.getStatus());

    }  

}
