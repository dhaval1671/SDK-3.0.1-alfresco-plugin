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
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * The Class BulkMetadataUpdateEvidenceTest.
 *
 * @author stank
 */

public class BulkMetadataUpdateEvidenceTest extends EvidenceBaseTest{

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
     * Apply bulk metadata to evi happy path.
     */
    @Test
    public void applyBulkMetadataToEviHappyPath(){
        /* Create the needed infrastructure.*/
        testCreateEvidence("proofBulkMetadataUpdateEvidence.pdf", ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        testCreateEvidence("another-proofBulkMetadataUpdateEvidence.pdf", ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        testBulkMetadataUpdateToEvisHappyPath();
        testRetryBulkMetadataUpdateToEvisHappyPath();
        testBMUTEBadMetadata_CommaPresentInJson();
        testBMUTEBadDocumentGunkId();
        testBMUTEBad_VerifyPresenceOfNewMetadataValue();
        testBulkMetadataUpdate_VerifyWithFirstElementOfJsonBeingEmpty();
    }

    /**
     * Test bulk metadata update to evis happy path.
     */
    private void testBulkMetadataUpdateToEvisHappyPath() {
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {         \"accessLevel\": \"public\",     \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);           
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));        
    }

    /**
     * Test retry bulk metadata update to evis happy path.
     */
    private void testRetryBulkMetadataUpdateToEvisHappyPath() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput); 
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));              
    }

    /**
     * Test create evidence.
     *
     * @param VALUE_FILE_NAME the value file name
     * @param VALUE_SERIAL_NUMBER the value serial number
     */
    private void testCreateEvidence(String VALUE_FILE_NAME, String VALUE_SERIAL_NUMBER) {
        Map<String, String> eviParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(VALUE_SERIAL_NUMBER);
        urlInput.setFileName(VALUE_FILE_NAME);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);        
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

    /**
     * Test bmute bad metadata_ comma present in json.
     */
    public void testBMUTEBadMetadata_CommaPresentInJson() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);        
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ],         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(400, response.getStatus());
    }      

    /**
     * Test bmute bad document gunk id.
     */
    public void testBMUTEBadDocumentGunkId() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);        
        String metadata = "[     {         \"documentGunkId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";        
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);    
        assertEquals(400, response.getStatus());
    }    

    /**
     * Test bmuteurl sn folder not present.
     */
    @Test
    public void testBMUTEURLSnFolderNotPresent() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber("00000000");        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);         
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);           

        //404 - 00000000 Folder doesn't exist.
        //400 -  
        if(400 == response.getStatus()){
            assertEquals(400, response.getStatus());
        }else if(404 == response.getStatus()){
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        }        

    }    

    /**
     * Test bmute target folder serial no frmt err.
     */
    @Test
    public void testBMUTETargetFolderSerialNoFrmtErr() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber("9o9");        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);         
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);          
        assertEquals(400, response.getStatus());         
    }    

    /**
     * Test bmute doc id folder not present.
     */
    @Test
    public void testBMUTEDocIdFolderNotPresent() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);         
        String metadata = "[     {         \"documentId\": \"/case/"+ "00000000" + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ "00000000" + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(400, response.getStatus());         
    }     

    /**
     * Test bmute bad_ verify presence of new metadata value.
     */
    private void testBMUTEBad_VerifyPresenceOfNewMetadataValue() {

        //1) "modifiedByUserId\": \"modiByTestCase539\"
        //2) "sourceMedium\": \"usingNewBulk7\"
        //3) Verify update version number
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);         
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {         \"accessLevel\": \"public\",     \"modifiedByUserId\": \"modiByTestCase539\",             \"documentAlias\": \"usingNewBulk\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk7\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";                
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);    
        assertEquals(200, response.getStatus()); 

        /* verify the presence of valid metadata changes
         * ie.,  "modifiedByUserId": "Gunkcopycat" in my-proof.pdf
         * */
        //testBMUTERetrieveEvidenceMetadata();
        testEvidenceMetadataOf_proofBulkMetadataUpdateEvidence_pdf();
        testEvidenceMetadataOf_another_proofBulkMetadataUpdateEvidence_pdf();
    }  

    /**
     * Test evidence metadata of_proof bulk metadata update evidence_pdf.
     */
    private void testEvidenceMetadataOf_proofBulkMetadataUpdateEvidence_pdf() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        urlInput.setFileName("proofBulkMetadataUpdateEvidence.pdf");
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);          

        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;

        //check for creationTime's presence
        String modUsrId = "\"modifiedByUserId\"(.*?)\"modiByTestCase539\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modUsrId));

        //check the version
        String needle = "\"version\":\"1.3\"";
        assertTrue(containsStringLiteral( haystack, needle));           

    }   

    /**
     * Test evidence metadata of_another_proof bulk metadata update evidence_pdf.
     */
    private void testEvidenceMetadataOf_another_proofBulkMetadataUpdateEvidence_pdf() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        urlInput.setFileName("another-proofBulkMetadataUpdateEvidence.pdf");
        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput);         

        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;

        //check for creationTime's presence
        String modUsrId = "\"modifiedByUserId\"(.*?)\"usingNewBulk7\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modUsrId));

        //check the version
        String needle = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral( haystack, needle));        

    }    

    /**
     * Test bulk metadata update_ verify with first element of json being empty.
     */
    public void testBulkMetadataUpdate_VerifyWithFirstElementOfJsonBeingEmpty() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);            
        String metadata = "[     {},     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidence/another-proofBulkMetadataUpdateEvidence.pdf\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(400, response.getStatus());
    }    

    /**
     * Test bulk metadata update_document id_being_empty.
     */
    public void testBulkMetadataUpdate_documentId_being_empty() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());        
        String WEBSCRIPT_URL = CaseOtherUrl.bulkMetadataUpdateToEvidenceType(urlInput);         
        String metadata = "[     {},     {         \"documentId\": \"\",         \"metadata\": {             \"sourceMedium\": \"usingNewBulk\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceGroupNames\": [                 \"working\",                 \"suggested\"             ]         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(400, response.getStatus());
    }    

    /**
     * Test bm u_with document id containing non evi file.
     */
    @Test
    public void testBMU_withDocumentIdContainingNonEviFile() {
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String fileNameWithoutExt = "sampleMarkOneForBulkMetadataUpdateEvidence";
        String ext = ".jpg";
        String fileName = fileNameWithoutExt + ext;
        urlInputOne.setFileName(fileName);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);          

        Map<String, String> mrkParamOne = new HashMap<String, String>();
        mrkParamOne.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParamOne.put(ParameterKeys.METADATA.toString(),MarkBaseTest.VALUE_MRK_METADATA_ONE);
        mrkParamOne.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParamOne);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, responseOne.getStatus());

        String WEBSCRIPT_EXT = "/cms/rest/case/" + ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidences";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;

        String metadata = null;
        if(TmnguiBaseTest.runAgainstCMSLayer){
            metadata = "[     {         \"documentId\": \"/case/77777778/evidence/uspto-image-mark"+ ext +"\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     } ]";
        }
        else{
            metadata = "[     {         \"documentId\": \"/case/77777778/evidence/"+ fileName + "\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceMedia\": \"paper\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceCategory\": \"web\"         }     } ]";    
        }         

        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(403, response.getStatus());
    }     

}
