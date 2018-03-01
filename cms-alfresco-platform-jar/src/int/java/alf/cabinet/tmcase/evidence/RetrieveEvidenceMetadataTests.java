package alf.cabinet.tmcase.evidence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * The Class RetrieveEvidenceMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveEvidenceMetadataTests extends EvidenceBaseTest {

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
     * Test retrieve evidence metadata.
     */
    @Test
    public void testRetrieveEvidenceMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);          
        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;
        //check for creationTime's presence
        String creationTime = "\"creationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, creationTime));
        //check for modificationTime's presence
        String modificationTime = "\"modificationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modificationTime));
        //check for evidence document size's presence
        String size = "\"documentSize\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));
        //check for mimetype's presence
        String mimeType = "\"mimeType\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, mimeType));
        String needle = "\"documentAlias\"";
        assertTrue(containsStringLiteral( haystack, needle));  
        String docType = "\"documentType\"(.*?)\""+ Evidence.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));          
    }

    /**
     * Test retrieve evidence metadata with valid version.
     */
    @Test
    public void testRetrieveEvidenceMetadataWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);          
        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_VALID);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve evidence metadata with in valid version.
     */
    @Test
    public void testRetrieveEvidenceMetadataWithInValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);         
        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_INVALID);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test load date correct fetching when provided with in metadata.
     */
    @Test
    public void testLoadDateCorrectFetchingWhenProvidedWithInMetadata(){
        String loadDate = "2013-07-16T07:41:19.000-0400";
        createEvidenceWithLoadDatePresent(loadDate);
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("loadDateSuppliedInMetadataEvi.pdf");
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);           
        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;
        //check for loadDate's presence
        String creationTime = "\"loadDate\"(.*?)\""+ loadDate +"\"";
        //System.out.println(creationTime);
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, creationTime));            
    }

    /**
     * Test load date correct fetching as create date when not provided with in metadata.
     */
    @Test
    public void testLoadDateCorrectFetchingAsCreateDateWhenNotProvidedWithInMetadata(){
        createEvidenceLoadDateAbsent();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("loadDateNOTSuppliedInMetadataEvi.pdf");
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);            
        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;
        //mine for creation Date.
        String needle = "\"creationTime\"(.*?)\"(.*?)\"";
        String creationTime = extractStringWithRegEx( haystack, needle);
        String isoDateFrmtRegex = "\\d{4}(.*?)\\d{4}";
        String creationTimeStamp = extractStringWithRegEx( creationTime, isoDateFrmtRegex);
        //System.out.println(creationTimeStamp);
        //check for loadDate's presence
        String loadDate = "\"loadDate\"(.*?)\""+ creationTimeStamp +"\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, loadDate));        
    }
    
    @Test
    public void verifyIfDocSizeIsComingCorrectEvenWhenWrongWasPassWhileCreating(){
        
        // create evidence
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("eviToVerifyDocSize.pdf");
        
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_WITH_DOC_SIZE_Hello6789000);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());        
        
        // retrieve metadata
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);          
        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse responseTwo = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, responseTwo.getStatus());
        String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(strTwo);        
        String haystack = strTwo;
        //check for evidence document size's presence
        String size = "\"documentSize\":\"80280\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));        
        
    }

    /**
     * Creates the evidence load date absent.
     */
    private void createEvidenceLoadDateAbsent(){
        String metadataWithoutLoadDate = "{   \"documentAlias\": \"DocNameForTmngUiDisplay\",   \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
        testCreateEvidence("loadDateNOTSuppliedInMetadataEvi.pdf", metadataWithoutLoadDate, null);

    }    

    /**
     * Creates the evidence with load date present.
     *
     * @param loadDate the load date
     */
    private void createEvidenceWithLoadDatePresent(String loadDate){
        String metadataWithLoadDate = "{   \"loadDate\" : \"" + loadDate + "\", \"documentAlias\": \"DocNameForTmngUiDisplay\",   \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"public\",     \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
        testCreateEvidence("loadDateSuppliedInMetadataEvi.pdf", metadataWithLoadDate, loadDate);
    }

    /**
     * Test create evidence.
     *
     * @param fileName the file name
     * @param metadata the metadata
     * @param loadDate the load date
     */
    private void testCreateEvidence(String fileName, String metadata, String loadDate) {
        Map<String, String> eviParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(fileName);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), metadata);        
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Evidence.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));    

    }

}
