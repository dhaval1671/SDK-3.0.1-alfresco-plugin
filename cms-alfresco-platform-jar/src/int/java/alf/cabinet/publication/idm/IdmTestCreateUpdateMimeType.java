package alf.cabinet.publication.idm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.FileUtils;
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
import alf.integration.service.url.helpers.publication.PublicationRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual.Idm;
import junitx.framework.ListAssert;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class IdmTestCreateUpdateMimeType extends IdmBaseTest{

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
     * Evidence create and recreate test.
     */
    @Test
    public void idmCreatePdfFileDisguisedAsDocFile(){
        
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("IdmPdfFileDisguisedAsDocFile.doc");
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);        
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//PdfFileDisguisedAsDocFile.doc");
        testCreateEvidence(eviParam);
        checkIfFileGottenStoredCorrectlyInAlfresco(urlInput);
    }   
    
    /**
     * Test create idm.
     *
     * @param eviParam the evi param
     */
    private void testCreateEvidence(Map<String, String> eviParam) {

        ClientResponse response = createDocument(eviParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());
        String haystack = str;
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Idm.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
    }
    
    /**
     *   
     *
     * @param urlInput the url input
     * @return void
     * @Title: checkIfCorrectFileGottenDetected
     * @Description: 
     */ 
    private void checkIfFileGottenStoredCorrectlyInAlfresco(IdmUrlInputDto urlInput) {
        String IDM_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);          
        WebResource webResource = client.resource(IDM_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),urlInput.getSerialNumber());
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), urlInput.getFileName());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        //This is PdfFileDisguisedAsDocFile.doc file being retrieved ie why 14 KB.
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        assertEquals("80 KB", fileSizeFrResponse); 
        
        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> first = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        second.add("application/pdf;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);          
    }

    /**
     * Idm create doc file disguised as pdf file.
     */
    @Test
    public void idmCreateDocFileDisguisedAsPdfFile(){
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("IdmDocFileDisguisedAsPdfFile.pdf");
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);     
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//DocFileDisguisedAsPdfFile.pdf");
        
        ClientResponse response = createDocument(eviParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());   
        
        String IDM_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);          
        WebResource webResource = client.resource(IDM_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),urlInput.getSerialNumber());
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), urlInput.getFileName());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse responseTwo = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(responseTwo.getLength());
        //This is DocFileDisguisedAsPdfFile.pdf file being retrieved ie why 14 KB.
        //System.out.println(fileSize);        
        assertEquals(200, responseTwo.getStatus());
        assertEquals("21 KB", fileSizeFrResponse); 
        
        MultivaluedMap<String,String> map = responseTwo.getHeaders();
        List<String> first = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        second.add("application/msword;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);           
    }  
    
    
    /**
     * Idm create pdf file without extension.
     */
    @Test
    public void idmCreatePdfFileWithoutExtension(){
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("PdfFileWithoutExtension");
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);     
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//PdfFileWithoutExtension");
        
        ClientResponse response = createDocument(eviParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());   
        
        String IDM_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);          
        WebResource webResource = client.resource(IDM_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),urlInput.getSerialNumber());
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), urlInput.getFileName());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse responseTwo = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(responseTwo.getLength());
        //This is DocFileDisguisedAsPdfFile.pdf file being retrieved ie why 14 KB.
        //System.out.println(fileSize);        
        assertEquals(200, responseTwo.getStatus());
        assertEquals("80 KB", fileSizeFrResponse); 
        
        MultivaluedMap<String,String> map = responseTwo.getHeaders();
        List<String> first = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        second.add("application/pdf;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);           
    }      

    /**
     * Idm create txt file without extension.
     */
    @Test
    public void idmCreateTxtFileWithoutExtension(){
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("TxtFileWithoutExtension");
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);    
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//TxtFileWithoutExtension");
        
        ClientResponse response = createDocument(eviParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());   
        
        String IDM_CONTENT_WEBSCRIPT_URL = PublicationRetrieveContentUrl.retrieveIdmContentUrl(urlInput);          
        WebResource webResource = client.resource(IDM_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),urlInput.getSerialNumber());
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), urlInput.getFileName());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse responseTwo = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(responseTwo.getLength());
        //This is DocFileDisguisedAsPdfFile.pdf file being retrieved ie why 14 KB.
        //System.out.println(fileSize);        
        assertEquals(200, responseTwo.getStatus());
        assertEquals("48 bytes", fileSizeFrResponse); 
        
        MultivaluedMap<String,String> map = responseTwo.getHeaders();
        List<String> first = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        second.add("application/octet-stream;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);           
    } 

   

}
