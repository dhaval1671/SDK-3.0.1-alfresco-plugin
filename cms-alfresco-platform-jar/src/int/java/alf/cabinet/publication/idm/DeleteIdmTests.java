package alf.cabinet.publication.idm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import alf.integration.service.all.base.IdmBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.IdmUrlInputDto;
import alf.integration.service.url.helpers.publication.PublicationCreateUrl;
import alf.integration.service.url.helpers.publication.PublicationDeleteUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual.Idm;

/**
 * Test class for the DeleteIdManual service.
 * 
 * @author Zorina Simeonova
 *
 */

public class DeleteIdmTests extends IdmBaseTest {

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
     * Test delete id-manual content.
     */
    @Test
    public void testDeleteIdmContent() {
        Map<String, String> idmParam = new HashMap<String, String>();
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777781_NUMBER.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        //Create Idm
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);
        idmParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        idmParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), IdmBaseTest.CONTENT_IDM_ATTACHMENT);
        testCreateIdm(idmParam);
        //Delete Idm
        String DELETE_CONTENT_WEBSCRIPT_URL = PublicationDeleteUrl.deleteIdmanualUrl(urlInput);
        WebResource webResource = client.resource(DELETE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseOne = b.delete(ClientResponse.class);
        assertEquals(200, responseOne.getStatus());
        //try to delete again the same file 
        ClientResponse responseTwo = b.delete(ClientResponse.class);
        String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(strTwo);        
        String haystackTwo = strTwo;
        assertEquals(404, responseTwo.getStatus());
        //String docTypeOfImgMrk = "Requested File Not Found";
        String docTypeOfImgMrk = urlInput.getSerialNumber() + " File or Folder doesn't exist";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, docTypeOfImgMrk));        
    }
    
    /**
     * Delete idm content with serial number less than eight characters.
     * DE5794: Not able to delete a folder having serial no /ID  less than 8 digits/characters
     */
    @Test
    public void deleteIdmContentWithSerialNumberLessThanEightCharacters() {
        Map<String, String> idmParam = new HashMap<String, String>();
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(IdmBaseTest._777AJ);
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        //Create Idm
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);
        idmParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        idmParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), IdmBaseTest.CONTENT_IDM_ATTACHMENT);
        testCreateIdm(idmParam);
        //Delete Idm
        String DELETE_CONTENT_WEBSCRIPT_URL = PublicationDeleteUrl.deleteIdmanualUrl(urlInput);
        WebResource webResource = client.resource(DELETE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseOne = b.delete(ClientResponse.class);
        //String strTwo = responseOne.getEntity(String.class);
        //System.out.println(strTwo);  
        assertEquals(200, responseOne.getStatus());
    }    
    
    
    @Test
    public void tryDeletingIdmFileWhenMultipleFilesArePresentInTheFolder() {
        //Create Idm file one
        IdmUrlInputDto urlInputOne = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777782_NUMBER.toString());
        urlInputOne.setFileName("idm-file-to-be-deleted-one.pdf");        
        String IDM_CREATE_WEBSCRIPT_URL_ONE = PublicationCreateUrl.createIdmanualUrl(urlInputOne);
        Map<String, String> idmParamOne = new HashMap<String, String>();
        idmParamOne.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL_ONE);
        idmParamOne.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), IdmBaseTest.CONTENT_IDM_ATTACHMENT);
        testCreateIdm(idmParamOne);
        
        //Create Idm file two
        IdmUrlInputDto urlInputTwo = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777782_NUMBER.toString());
        urlInputTwo.setFileName("idm-file-to-be-deleted-two.pdf");        
        String IDM_CREATE_WEBSCRIPT_URL_TWO = PublicationCreateUrl.createIdmanualUrl(urlInputTwo);
        Map<String, String> idmParamTwo = new HashMap<String, String>();
        idmParamTwo.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL_TWO);
        idmParamTwo.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), IdmBaseTest.CONTENT_IDM_ATTACHMENT);
        testCreateIdm(idmParamTwo);
        
        //Delete Idm
        String DELETE_CONTENT_WEBSCRIPT_URL = PublicationDeleteUrl.deleteIdmanualUrl(urlInputOne);
        WebResource webResource = client.resource(DELETE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseOne = b.delete(ClientResponse.class);
        assertEquals(200, responseOne.getStatus());
        //try to delete again the same file 
        ClientResponse responseTwo = b.delete(ClientResponse.class);
        String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(strTwo);        
        String haystackTwo = strTwo;
        assertEquals(404, responseTwo.getStatus());
        String docTypeOfImgMrk = "Requested File";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, docTypeOfImgMrk));        
    }    

    /**
     * Test delete id-manual content.
     */
    @Test
    public void testDeleteNotExistingFile() {
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777781_NUMBER.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        String DELETE_CONTENT_WEBSCRIPT_URL = PublicationDeleteUrl.deleteIdmanualUrl(urlInput);
        WebResource webResource = client.resource(DELETE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.delete(ClientResponse.class);
        assertEquals(404, response.getStatus());
    }

    /**
     * Test create id-manual.
     *
     * @param noteParam
     *            the note param
     */
    private void testCreateIdm(Map<String, String> noteParam) {
        ClientResponse response = createDocument(noteParam);
        String str = response.getEntity(String.class);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\"";
        assertFalse(containsStringLiteral(haystack, needle));
        String validDocumentID = "/" + Idm.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }
    
 

}
