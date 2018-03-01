package alf.documentlibrary.webcapture;

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

import alf.integration.service.all.base.EvidenceBankBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.WebcaptureBaseTest;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class CreateWebcaptureTests extends WebcaptureBaseTest{

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
    public void testCreateInsideEviBankWabcaptureOne_happyPath() {
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "testEvidence.pdf"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
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
    
    @Test
    public void testCreateInsideEviBankWabcapture_negativeCase_Wrong_fileNameWithSlash() {
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "one/testEvidence.pdf"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        ClientResponse response = coreCreation(fullPath);
        
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void negativeTestCreateInsideEviBankWabcaptureOneA() {
        // 'path' parameter cannot start with forward slash.
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "helloWorldEvidence.pdf"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        ClientResponse response = coreCreation(fullPath);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
    }    
    
    @Test
    public void negativeTestCreateInsideEviBankWabcaptureTwo() {
        // 'path' parameter cannot start with forward slash.
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "/one/testOneEvidence.pdf"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        ClientResponse response = coreCreation(fullPath);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void negativeTestCreateInsideEviBankWabcaptureThree() {
        //I need to fix or handle this situation, probable flag for bug.
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "a///"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        ClientResponse response = coreCreation(fullPath);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void testCreateInsideEviBankWabcaptureSeven() {
        //ideally should be throwing integrity violation from alfresco, but looks like java jersey rest client is encoding the stuff and messing up.
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "..pdf"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        ClientResponse response = coreCreation(fullPath);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
    } 
    
    @Test
    public void negativeTestCreateInsideEviBankWabcaptureEight() {
        //ideally should be throwing integrity violation from alfresco, but looks like java jersey rest client is encoding the stuff and messing up.
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "1/2/3/4/5/6/7/8/9/10/11/12/13/14/15/16/17/18/19/20/21/22/23/23/24/25/test"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        ClientResponse response = coreCreation(fullPath);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void negativeTestCreateInsideEviBankWabcaptureNine() {
        //ideally should be throwing integrity violation from alfresco, but looks like java jersey rest client is encoding the stuff and messing up.
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String userid = "7777";
        String path = "BambooConstructMaterials/2Def-Bamboo.jpg"; 
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        ClientResponse response = coreCreation(fullPath);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(400, response.getStatus());
    }    

    private ClientResponse coreCreation(String fullPath) {
        String DOC_LIB_WEBCAPTURE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createWebcaptureUrl(fullPath);               
        
        //System.out.println(EVI_BANK_WEBCAPTURE_CREATE_WEBSCRIPT_URL);
        Map<String, String> applicationParam = new HashMap<String, String>();
        applicationParam.put(ParameterKeys.URL.toString(),DOC_LIB_WEBCAPTURE_CREATE_WEBSCRIPT_URL);
        applicationParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBankBaseTest.CONTENT_EVI_BANK_WEBCAPTURE_ATTACHMENT);
        
        ClientResponse response = createDocument(applicationParam);
        return response;
    }



}
