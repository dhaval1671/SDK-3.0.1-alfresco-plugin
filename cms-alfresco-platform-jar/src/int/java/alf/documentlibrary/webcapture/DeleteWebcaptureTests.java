package alf.documentlibrary.webcapture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

import alf.integration.service.all.base.EvidenceBankBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.WebcaptureBaseTest;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class DeleteWebcaptureTests extends WebcaptureBaseTest{

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
    public void deleteWebcaptureFiles_happyPath(){
        String useridOne = "7777";
        String fileNameOne= "testEvidenceOne.pdf";         
        String useridTwo = "7778";
        String fileNameTwo = "testEvidenceTwo.pdf"; 
        //create sample webcapture content
        testCreateWabcaptureOne_happyPath(useridOne, fileNameOne);
        testCreateWabcaptureOne_happyPath(useridTwo, fileNameTwo);
        deleteWebcaptureContent(useridOne, fileNameOne);
        deleteWebcaptureContent(useridTwo, fileNameTwo);
    }
    
    @Test
    public void neg_tryToDeleteFileWhichIsNOTThere(){
        String useridOne = "7777";
        String fileNameOne= "testEvidenceThree.pdf";
        String fileNameFour= "testEvidenceFour.pdf";
        //create sample webcapture content
        testCreateWabcaptureOne_happyPath(useridOne, fileNameOne);
        
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String fullPath = CaseOtherUrl.getFullPath(webcapture, useridOne, fileNameFour);
        String WEBCAPTURE_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteWebcaptureUrl(fullPath);          
        //System.out.println(WEBCAPTURE_DELETE_WEBSCRIPT_URL);        
        Map<String, String> applicationParam = new HashMap<String, String>();
        applicationParam.put(ParameterKeys.URL.toString(),WEBCAPTURE_DELETE_WEBSCRIPT_URL);
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);
        WebResource webResource = client.resource(WEBCAPTURE_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.delete(ClientResponse.class);        
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(404, response.getStatus());
    }    

    private void deleteWebcaptureContent(String userid, String fileName) {
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, fileName);
        String WEBCAPTURE_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteWebcaptureUrl(fullPath);          
        //System.out.println(WEBCAPTURE_DELETE_WEBSCRIPT_URL);        
        Map<String, String> applicationParam = new HashMap<String, String>();
        applicationParam.put(ParameterKeys.URL.toString(),WEBCAPTURE_DELETE_WEBSCRIPT_URL);
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);
        WebResource webResource = client.resource(WEBCAPTURE_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.delete(ClientResponse.class);        
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
    }
    
    private void testCreateWabcaptureOne_happyPath(String userid, String fileName) {
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, fileName);
        ClientResponse response = coreCreation(fullPath);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"size\":\"82749";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "\"documentId\":\""+fullPath+"\"";
        //System.out.println(validDocumentID);
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
    }    
    
    private ClientResponse coreCreation(String fullPath) {
        String EVI_BANK_WEBCAPTURE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createWebcaptureUrl(fullPath);          
        //System.out.println(EVI_BANK_WEBCAPTURE_CREATE_WEBSCRIPT_URL);
        Map<String, String> applicationParam = new HashMap<String, String>();
        applicationParam.put(ParameterKeys.URL.toString(),EVI_BANK_WEBCAPTURE_CREATE_WEBSCRIPT_URL);
        applicationParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBankBaseTest.CONTENT_EVI_BANK_WEBCAPTURE_ATTACHMENT);
        ClientResponse response = createDocument(applicationParam);
        return response;
    }

}
