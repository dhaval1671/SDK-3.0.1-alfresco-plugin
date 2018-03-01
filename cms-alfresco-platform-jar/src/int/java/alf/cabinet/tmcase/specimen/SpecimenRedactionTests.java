package alf.cabinet.tmcase.specimen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.SpecimenBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Specimen;

/**
 * The Class CreateSpecimenTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class SpecimenRedactionTests extends SpecimenBaseTest {

    private static final String ORIGINAL_SPECIMEN_FILE_SIZE_ONE = "79 KB";
    private static final String CREATE_SPECIMEN_ONE = "\"createSpecimen_One\"";    
    private static final String REDACT_SPECIMEN_CONTENT_AND_METADATA_TWO = "\"redactSpecimenContentAndMetadata_Two\"";

    public static final String CREATE_SPECIMEN_CONTENT = "src//test//resources//crudfiles//Create.pdf"; 
    private static final String CREATE_SPECIMEN_METADATA = "{\"documentAlias\": "+CREATE_SPECIMEN_ONE+", \"modifiedByUserId\" : \"CreateSpecimenIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";

    public static final String REDACT_SPECIMEN_CONTENT_ONE = "src//test//resources//specimen//specimen.pdf"; 
    private static final String REDACT_SPECIMEN_METADATA_ONE = "{\"redactionLevel\":\"Full\", \"documentAlias\": "+REDACT_SPECIMEN_CONTENT_AND_METADATA_TWO+", \"modifiedByUserId\" : \"CreateSpecimenIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";    
    
    public static final String UPDATE_SPECIMEN_CONTENT = "src//test//resources//crudfiles//Update.pdf"; 
    private static final String UPDATE_SPECIMEN_METADATA = "{\"documentAlias\": \"redactedSpecimenUpdated\", \"modifiedByUserId\" : \"CreateSpecimenIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
    
    public static final String REDACT_SPECIMEN_CONTENT_TWO = "src//test//resources//mark//image//1_Mark1.0.bmp"; 
    
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

    @Test
    public void specimenRedactionTests() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("SpecimenFileToBeRedacted.docx");        
        createSpecimen(urlInput);
        testRedactedSpecimenContentAndMetadata(urlInput);
        testUpdateRedactedSpecimen(urlInput);
        redactOnlyContent(urlInput);
        retrieveOriginalContent(urlInput);
        retrieveOriginalMetadata(urlInput);    
        restoreToOriginal(urlInput);        
        redactOnlyMetadata(urlInput);
        retrieveRedactedContent(urlInput);
        retrieveRedacteMetadata(urlInput);
    } 

    /**  
     * @Title: retrieveRedacteMetadata  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void retrieveRedacteMetadata(UrlInputDto urlInput) {
        String redactedRetrieveResponseMetadata = CaseRetrieveMetadataUrl.getRedactedRetrieveMetadata(urlInput);
        // System.out.println(redactedRetrieveResponseMetadata);
        WebResource webResource = client.resource(redactedRetrieveResponseMetadata);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);        
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());        
    }

    /**  
     * @Title: retrieveRedactedContent  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void retrieveRedactedContent(UrlInputDto urlInput) {
        String redactedRetrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContent(urlInput);
        //System.out.println(redactedRetrieveResponseContent);
        WebResource webResource = client.resource(redactedRetrieveResponseContent);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);          
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(404, response.getStatus());
        String haystack = str;
        String needle = "Filename Not found: SpecimenFileToBeRedacted.docx";
        assertTrue(containsStringLiteral( haystack, needle));        
    }

    /**  
     * @Title: redactOnlyMetadata  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void redactOnlyMetadata(UrlInputDto urlInput) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);         
        //System.out.println(REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SpecimenRedactionTests.REDACT_SPECIMEN_CONTENT_TWO); 
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);            
        String str = redactedResponse.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, redactedResponse.getStatus());
        String haystack = str;
        String needle = "Incoming metadata should NOT be blank";
        assertTrue(containsStringLiteral( haystack, needle));
    }

    /**  
     * @Title: restoreToOriginal  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void restoreToOriginal(UrlInputDto urlInput) {
        String RedactedRetrieveResponseContent = CaseOtherUrl.deleteRestoreRedactedToOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveResponseContent);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseMetadata = b.delete(ClientResponse.class);        
        String str = responseMetadata.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, responseMetadata.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral( haystack, needle));        
        verifyRestoreOperation(urlInput);
    }

    /**  
     * @Title: verifyRestoreOperation  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void verifyRestoreOperation(UrlInputDto urlInput) {
        String metadataPieceForVerification = SpecimenRedactionTests.CREATE_SPECIMEN_ONE;
        String createFileSize = SpecimenRedactionTests.ORIGINAL_SPECIMEN_FILE_SIZE_ONE;
        verifyContentAndMetadata(urlInput, metadataPieceForVerification, createFileSize); 
    }

    /**  
     * @Title: retrieveOriginalMetadata  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void retrieveOriginalMetadata(UrlInputDto urlInput) {
        String RedactedRetrieveResponseContent = CaseRetrieveMetadataUrl.getRedactedRetrieveMetadataFlavourOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveResponseContent);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseMetadata = b.get(ClientResponse.class);
        assertEquals(200, responseMetadata.getStatus());
        String str = responseMetadata.getEntity(String.class);
        //System.out.println(str);
        String haystack = str;
        //check for creationTime's presence
        String pieceToVerify = SpecimenRedactionTests.CREATE_SPECIMEN_ONE;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, pieceToVerify));         
    }

    /**  
     * @Title: retrieveOriginalContent  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void retrieveOriginalContent(UrlInputDto urlInput) {
        String createFileSize = SpecimenRedactionTests.ORIGINAL_SPECIMEN_FILE_SIZE_ONE;
        String SPECIMEN_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.getRedactedRetrieveContentFlavourOriginal(urlInput);
        WebResource webResourceContent = client.resource(SPECIMEN_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse responseContent = webResourceContent.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(responseContent.getLength());
        // System.out.println(fileSize);
        assertEquals(200, responseContent.getStatus());
        assertEquals(createFileSize, fileSizeFrResponse);   
    }

    /**  
     * @Title: redactOnlyContent  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void redactOnlyContent(UrlInputDto urlInput) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);         
        //System.out.println(REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SpecimenRedactionTests.REDACT_SPECIMEN_CONTENT_TWO); 
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);            
        String str = redactedResponse.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, redactedResponse.getStatus());
        String haystack = str;
        String needle = "Incoming metadata should NOT be blank";
        assertTrue(containsStringLiteral( haystack, needle));
    }

    /**
     * Specimen create and recreate test.
     */
    private void createSpecimen(UrlInputDto urlInput){
        String SPECIMEN_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(SPECIMEN_CREATE_WEBSCRIPT_URL);
        Map<String, String> specimenParam = new HashMap<String, String>();
        specimenParam.put(ParameterKeys.URL.toString(),SPECIMEN_CREATE_WEBSCRIPT_URL);
        specimenParam.put(ParameterKeys.METADATA.toString(),SpecimenRedactionTests.CREATE_SPECIMEN_METADATA);
        specimenParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SpecimenRedactionTests.CREATE_SPECIMEN_CONTENT);
        testCreateSpecimen(specimenParam);
        verifyCreation(urlInput);        
    }

    /**  
     * @Title: verifyCreation  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void verifyCreation(UrlInputDto urlInput) {
        String metadataPieceForVerification = SpecimenRedactionTests.CREATE_SPECIMEN_ONE;
        String createFileSize = SpecimenRedactionTests.ORIGINAL_SPECIMEN_FILE_SIZE_ONE;
        verifyContentAndMetadata(urlInput, metadataPieceForVerification, createFileSize);        
    }

    private void verifyContentAndMetadata(UrlInputDto urlInput, String metadataPieceForVerification, String createFileSize) {
        
        if(null != metadataPieceForVerification){
        String SPECIMEN_RETRIEVE_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);
        WebResource webResourceMetadata = client.resource(SPECIMEN_RETRIEVE_METADATA_WEBSCRIPT_URL);
        ClientResponse responseMetadata = webResourceMetadata.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, responseMetadata.getStatus());
        String str = responseMetadata.getEntity(String.class);
        //System.out.println(str);
        String haystack = str;
        //check for creationTime's presence
        String creationTime = metadataPieceForVerification;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, creationTime));        
        }
        
        if(null != createFileSize){
        String SPECIMEN_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        WebResource webResourceContent = client.resource(SPECIMEN_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse responseContent = webResourceContent.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(responseContent.getLength());
        // System.out.println(fileSize);
        assertEquals(200, responseContent.getStatus());
        assertEquals(createFileSize, fileSizeFrResponse);      
        }
    }

    /**
     * Test create specimen.
     *
     * @param specimenParam the specimen param
     */
    private void testCreateSpecimen(Map<String, String> specimenParam) {
        ClientResponse response = createDocument(specimenParam);       
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Specimen.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));  
    }    
    
    private void testRedactedSpecimenContentAndMetadata(UrlInputDto urlInput) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);         
        //System.out.println(REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), SpecimenRedactionTests.REDACT_SPECIMEN_METADATA_ONE);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SpecimenRedactionTests.REDACT_SPECIMEN_CONTENT_ONE); 
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);            
        String str = redactedResponse.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, redactedResponse.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Specimen.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));  
        verifyRedaction(urlInput);
    }
    
    private void verifyRedaction(UrlInputDto urlInput) {
        String metadataPieceForVerification = SpecimenRedactionTests.REDACT_SPECIMEN_CONTENT_AND_METADATA_TWO;
        String createFileSize = "30 KB";
        verifyContentAndMetadata(urlInput, metadataPieceForVerification, createFileSize);        
    }
    
    private void testUpdateRedactedSpecimen(UrlInputDto urlInput) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseUpdateUrl.updateSpecimenUrl(urlInput);         
        redactedResponseParam.put(ParameterKeys.URL.toString(),REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), SpecimenRedactionTests.UPDATE_SPECIMEN_METADATA);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SpecimenRedactionTests.UPDATE_SPECIMEN_CONTENT);         
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);   
        String str = redactedResponse.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(400, redactedResponse.getStatus());
        String haystack = str;
        String needle = "Update is not allowed on Redacted Document";
        assertTrue(containsStringLiteral( haystack, needle));
    }    

}
