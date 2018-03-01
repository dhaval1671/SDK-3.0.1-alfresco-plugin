package alf.documentlibrary.webcapture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

import alf.integration.service.all.base.EvidenceBankBaseTest;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;

/**
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveFolderContentOfWebcaptureThruEvidenceLibAPIsTests extends EvidenceBankBaseTest{

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));        
    }    

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {

    }    

    /**
     * Test retrieve WebCapture's folder content fr2 a evidence lib.
     * US45844: Security Issues were happening when 'WebCapture' folder was residing inside "EvidenceBank" folder, 
     * as it was retrievable using EviLib folder content retrievable api.
     * 
     * Using url: GET http://localhost:8080/alfresco/s/cms/rest/libraries/evidences/content/folder-path/
     *                http://localhost:8080/alfresco/s/cms/rest/libraries/evidences/content/folder-path/webcapture/7777
     * 
     */
    @Test
    public void testTryRetrievalOfWebcaptureUsingEvidenceLibUrl() {

        String WEBSCRIPT_EXT = EVI_BANK_CMS_REST_PREFIX + "/" + PathResolver.EVIDENCE_LIBRARY_FOLDER_PATH_PREFIX + "webcapture/7777";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;          
        //System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        webResource = webResource.queryParam(PARAM_PATH, VALUE_PATH_FROM_FOLDER);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        
        /* DE26327 - this defect was create so that all mime/type are correctly stored */
        String haystack = str;
        String needle = "\"mimetype\":\"application/octet-stream";
        assertFalse(containsStringLiteral( haystack, needle));
        
    }
    
    @Test
    public void testTopLevelFoldersOfEvidenceBankDONOTExpectWebcaptureAsItIsNowMovedOutside() {

        String WEBSCRIPT_EXT = EVI_BANK_CMS_REST_PREFIX + "/" + PathResolver.EVIDENCE_LIBRARY_FOLDER_PATH_PREFIX;
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;          
        //System.out.println(WEBSCRIPT_URL);
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        webResource = webResource.queryParam(PARAM_PATH, VALUE_PATH_FROM_FOLDER);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        
        String haystack = str;
        String needle = "\"name\":\"2A";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String webcapture = "\"name\":\"WebCapture\"";
        //System.out.println(validDocumentID);
        assertFalse(containsStringLiteral( haystack, webcapture));        
        
    }    

   

}
