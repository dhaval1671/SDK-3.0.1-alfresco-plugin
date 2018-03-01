package alf.cabinet.publication.eog;

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

import alf.integration.service.all.base.EogBaseTest;
import alf.integration.service.all.base.EogParameterValues;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.EogUrlInputDto;
import alf.integration.service.url.helpers.publication.PublicationCreateUrl;
import alf.integration.service.url.helpers.publication.PublicationRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog;
import junitx.framework.ListAssert;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class EogTestCreateUpdateMimeType extends EogBaseTest{

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
    public void eogCreatePdfFileDisguisedAsDocFile(){
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName("EogPdfFileDisguisedAsDocFile.doc");
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);
        Map<String, String> eogParam = new HashMap<String, String>();
        //System.out.println(EOG_CREATE_WEBSCRIPT_URL);
        eogParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        //eogParam.put(ParameterKeys.METADATA.toString(), VALUE_NOTE_METADATA);
        eogParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//PdfFileDisguisedAsDocFile.doc");        
        testCreateEog(eogParam);
        checkIfFileGottenStoredCorrectlyInAlfresco(urlInput);
    }   
    
    /**
     * Test create evidence.
     *
     * @param eogParam the evi param
     */
    private void testCreateEog(Map<String, String> eogParam) {

        ClientResponse response = createDocument(eogParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());
        String haystack = str;
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Eog.TYPE +"/";//;
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
    private void checkIfFileGottenStoredCorrectlyInAlfresco(EogUrlInputDto urlInput) {
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);          
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
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
     * Eog create doc file disguised as pdf file.
     */
    @Test
    public void eogCreateDocFileDisguisedAsPdfFile(){
        
        
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName("EogDocFileDisguisedAsPdfFile.pdf");
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);        
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        //eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//DocFileDisguisedAsPdfFile.pdf");
        ClientResponse response = createDocument(eviParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());   
        
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);          
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
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
     * Eog create pdf file without extension.
     */
    @Test
    public void eogCreatePdfFileWithoutExtension(){
        
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName("PdfFileWithoutExtension");
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);          
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//PdfFileWithoutExtension");
        
        ClientResponse response = createDocument(eviParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());   
        
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);            
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
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
     * Eog create txt file without extension.
     */
    @Test
    public void eogCreateTxtFileWithoutExtension(){
        
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName("TxtFileWithoutExtension");
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);        
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), "src//test//resources//mimeType//TxtFileWithoutExtension");
        
        ClientResponse response = createDocument(eviParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());   
        
        String PUBLICATION_CONTENT_URL = PublicationRetrieveContentUrl.retrieveEogContentUrl(urlInput);          
        WebResource webResource = client.resource(PUBLICATION_CONTENT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),urlInput.getSerialNumber());
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(),  urlInput.getFileName());
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
