package alf.documentlibrary.webcapture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
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
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.EvidenceBankBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.WebcaptureBaseTest;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;

/**
 * 
 * @author stank
 *
 */

public class RenameWebcaptureTest extends WebcaptureBaseTest {

    /** The log. */
    private static final Logger log =  Logger.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        log.info("### Executing " + Thread.currentThread().getStackTrace()[TMConstants.ONE].getMethodName() + " ####");
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
     * Test update evidence metadata first.
     */
    @Test
    public void testRenameWebcapture_happyPath() {
        String useridOne = "7777";
        String fileNameOne= "testRenameWebcapture347.pdf";         
        //create sample webcapture content
        testCreateWabcaptureOne_happyPath(useridOne, fileNameOne);
        testRenameWebcapture(useridOne, fileNameOne);
    }
    
    @Test
    public void neg_tryRenamingFileThatDoesNOTExists() {
        String useridOne = "7777";
        String fileNameOne= "testRenameWebcapture348.pdf";         
        //create sample webcapture content
        testCreateWabcaptureOne_happyPath(useridOne, fileNameOne);
        //testRenameWebcapture(useridOne, "testRenameWebcapture348.pdf");
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String fullPath = CaseOtherUrl.getFullPath(webcapture, useridOne, "fileThatDoesNOTExists.pdf");
        String WEBCAPTURE_RENAME_WEBSCRIPT_URL = CaseOtherUrl.renameWebcaptureUrl(fullPath);  
        String metadata = "{ \"newFilename\": \"renamedByWebcapture-743.pdf\" }";
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadata));
        WebResource webResource = client.resource(WEBCAPTURE_RENAME_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        assertEquals(404, response.getStatus());        
    }  
    
    @Test
    public void neg_tryRenamingFileWithSameNewName() {
        String useridOne = "7777";
        String fileNameOne= "testRenameWebcapture349.pdf";         
        //create sample webcapture content
        testCreateWabcaptureOne_happyPath(useridOne, fileNameOne);
        //testRenameWebcapture(useridOne, "testRenameWebcapture348.pdf");
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String fullPath = CaseOtherUrl.getFullPath(webcapture, useridOne, "testRenameWebcapture349.pdf");
        String WEBCAPTURE_RENAME_WEBSCRIPT_URL = CaseOtherUrl.renameWebcaptureUrl(fullPath);  
        String metadata = "{ \"newFilename\": \"testRenameWebcapture349.pdf\" }";
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadata));
        WebResource webResource = client.resource(WEBCAPTURE_RENAME_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        assertEquals(409, response.getStatus());        
    }     
    
    private void testRenameWebcapture(String userid, String fileName) {
        
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, fileName);
        String WEBCAPTURE_RENAME_WEBSCRIPT_URL = CaseOtherUrl.renameWebcaptureUrl(fullPath);  
        String metadata = "{ \"newFilename\": \"renamedByWebcapture-743.pdf\" }";
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadata));
        WebResource webResource = client.resource(WEBCAPTURE_RENAME_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
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
