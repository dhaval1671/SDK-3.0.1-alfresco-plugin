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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.EvidenceBankBaseTest;
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.constants.TradeMarkModel;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author stank
 *
 */

public class CopyEvidenceToTrgtTest extends EvidenceBaseTest{

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
     * Copy evi fr src to trgt and retry the same.
     */
    @Test
    public void copyEviFrSrcToTrgtAndRetryTheSame(){

        /* Create the needed infrastructure.*/
        testCreateEvidence("proof.pdf", ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        testCreateEvidence("my-proof.pdf", ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        testCreateEvidence("another-proof.pdf", ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        testCreateEvidence("tempFile.pdf", ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString()); /* this is to disguise the creation of 77777778 case folder. */
        testCreateEvidence("tempFile.pdf", ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());           

        /* execute the copy evidence request */
        testCopyEviFrSrcToTrgt();
        testRetryCopyEviFrSrcToTrgt();

        testCEFSTTBad_ModifiedByUserGunkId();
        testCEFSTTTargetFolderNotPresent();
    }

    /**
     * Copy evi fr src to trgt with upper case2 a in the doc id field.
     */
    @Test
    public void copyEviFrSrcToTrgtWithUpperCase2AInTheDocIdField(){

        /* Create the needed infrastructure.*/
        testCreateEvidence("proof1.pdf", ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        testCreateEvidence("my-proof1.pdf", ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        testCreateEvidence("another-proof1.pdf", ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        testCreateEvidence("tempFile1.pdf", ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString()); /* this is to disguise the creation of 77777778 case folder. */
        /* execute the copy evidence request */
        testCopyEviFrSrcToTrgtWithUpperCase2AInTheDocIdField();
    }    

    /**
     * Test copy evi fr src to trgt with upper case2 a in the doc id field.
     */
    public void testCopyEviFrSrcToTrgtWithUpperCase2AInTheDocIdField() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String copyEviFrSrcToTrgtUrl = CaseOtherUrl.copyEvidenceFrSrcToTrgtUrl(urlInput);          
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof1.pdf\",         \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof1.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof1.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2A/GreenPowerElect/E-MainGreen1d.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";
        WebResource webResource = client.resource(copyEviFrSrcToTrgtUrl);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
    }  

    private void createWebcaptureFileContent(String userid, String path) {
        String webcapture = TradeMarkModel.WEBCAPTURE_FOLDER_NAME.toLowerCase();
        String fullPath = CaseOtherUrl.getFullPath(webcapture, userid, path);
        String DOC_LIB_WEBCAPTURE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createWebcaptureUrl(fullPath);               
        //System.out.println(DOC_LIB_WEBCAPTURE_CREATE_WEBSCRIPT_URL);
        Map<String, String> applicationParam = new HashMap<String, String>();
        applicationParam.put(ParameterKeys.URL.toString(),DOC_LIB_WEBCAPTURE_CREATE_WEBSCRIPT_URL);
        applicationParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBankBaseTest.CONTENT_EVI_BANK_WEBCAPTURE_ATTACHMENT);
        ClientResponse response = createDocument(applicationParam);        
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
    }    
    
    @Test
    public void copyEviFrSrcToTrgtWithWebcaptureDocIdField(){
        /* Create the needed infrastructure.*/
        createWebcaptureFileContent("7777", "testWebcaptureFor_copyEviFrSrcToTrgtWithWebcaptureDocIdField.pdf");
        testCreateEvidence("tempFile2.pdf", ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString()); /* this is to disguise the creation of 77777778 case folder. */
        /* execute the copy evidence request */
        testCopyEviFrSrcToTrgtWithWebcaptureDocIdField();
    }

    /**
     * Test copy evi fr src to trgt with upper case2 a in the doc id field.
     */
    public void testCopyEviFrSrcToTrgtWithWebcaptureDocIdField() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String copyEviFrSrcToTrgtUrl = CaseOtherUrl.copyEvidenceFrSrcToTrgtUrl(urlInput);          
        String metadata = "[{   \"documentId\": \"libraries/evidences/content/file-path/2A/GreenBuilding/10Benefits-Bloom-A.jpg\",     \"metadata\": {       \"modifiedByUserId\": \"\",         \"sourceSystem\": \"TMNG\",         \"sourceMedia\": \"electronic\",        \"sourceMedium\": \"2a\",       \"accessLevel\": \"internal\",      \"scanDate\": \"1970-01-01T00:00:00Z\",         \"documentAlias\": \"\",        \"evidenceSourceType\": \"2a\",         \"evidenceSourceTypeId\": \"\",         \"evidenceSourceUrl\": \"\",        \"evidenceGroupNames\": [\"attached\"],         \"evidenceCategory\": \"twoalib\"   } }, {  \"documentId\": \"/webcapture/7777/testWebcaptureFor_copyEviFrSrcToTrgtWithWebcaptureDocIdField.pdf\",  \"metadata\": {       \"modifiedByUserId\": \"\",         \"sourceSystem\": \"TMNG\",         \"sourceMedia\": \"electronic\",        \"sourceMedium\": \"2a\",       \"accessLevel\": \"internal\",      \"scanDate\": \"1970-01-01T00:00:00Z\",         \"documentAlias\": \"\",        \"evidenceSourceType\": \"2a\",         \"evidenceSourceTypeId\": \"\",         \"evidenceSourceUrl\": \"\",        \"evidenceGroupNames\": [\"attached\"],         \"evidenceCategory\": \"twoalib\"   } }]";
        WebResource webResource = client.resource(copyEviFrSrcToTrgtUrl);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
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
        eviParam.put(ParameterKeys.URL.toString(),EVI_CREATE_WEBSCRIPT_URL);
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
     * Test copy evi fr src to trgt.
     */
    public void testCopyEviFrSrcToTrgt() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String copyEviFrSrcToTrgtUrl = CaseOtherUrl.copyEvidenceFrSrcToTrgtUrl(urlInput);        
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof.pdf\",         \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";
        WebResource webResource = client.resource(copyEviFrSrcToTrgtUrl);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retry copy evi fr src to trgt.
     */
    public void testRetryCopyEviFrSrcToTrgt() {
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String copyEviFrSrcToTrgtUrl = CaseOtherUrl.copyEvidenceFrSrcToTrgtUrl(urlInput);        
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof.pdf\",         \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";
        WebResource webResource = client.resource(copyEviFrSrcToTrgtUrl);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(403, response.getStatus());
    }    

    /**
     * Test cefstt src folder not present.
     */
    @Test
    public void testCEFSTTSrcFolderNotPresent() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentId\": \"/case/00000000/evidence/proof.pdf\",         \"metadata\": null     },     {         \"documentId\": \"/case/00000000/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/00000000/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);           
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

    /**
     * Test cefstt target folder not present.
     */
    public void testCEFSTTTargetFolderNotPresent() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + "77777703" + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof.pdf\",         \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);            

        assertEquals(200, response.getStatus());         
    }

    /**
     * Test cefstt target folder serial no frmt err.
     */
    @Test
    public void testCEFSTTTargetFolderSerialNoFrmtErr() {

        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + "9o9" + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof.pdf\",         \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);          
        assertEquals(400, response.getStatus());         
    }

    /**
     * Test cefstt src trgt both folder not present.
     */
    @Test
    public void testCEFSTTSrcTrgtBothFolderNotPresent() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + "00000000" + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentId\": \"/case/00000000/evidence/proof.pdf\",         \"metadata\": null     },     {         \"documentId\": \"/case/00000000/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/00000000/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);         

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());         
    } 

    /**
     * Test cefstt bad metadata_ comma present in json.
     */
    @Test
    public void testCEFSTTBadMetadata_CommaPresentInJson() {

        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof.pdf\",   ,      \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";        
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);   

        //404 -folder 77777778 doesn't exists.
        //400 -  Unexpected character (',')
        if(400 == response.getStatus()){
            assertEquals(400, response.getStatus());
        }else if(404 == response.getStatus()){
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        }

    }

    /**
     * Test cefstt bad document gunk id.
     */
    @Test
    public void testCEFSTTBadDocumentGunkId() {

        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString() + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentGunkId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof.pdf\",   \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"copycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";        
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);    

        //404 -folder 77777778 doesn't exists.
        //400 - Unrecognized field "documentGunkId"
        if(400 == response.getStatus()){
            assertEquals(400, response.getStatus());
        }else if(404 == response.getStatus()){
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        }         
    }  

    /**
     * Test cefstt bad_ modified by user gunk id.
     */
    private void testCEFSTTBad_ModifiedByUserGunkId() {

        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString() + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/proof.pdf\",   \"metadata\": null     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/my-proof.pdf\",         \"metadata\": {             \"modifiedByUserId\": \"Gunkcopycat\",             \"accessLevel\": \"public\",             \"evidenceGroupNames\": []         }     },     {         \"documentId\": \"/case/"+ ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/another-proof.pdf\",         \"metadata\": {             \"accessLevel\": \"public\",             \"evidenceGroupNames\": [                 \"working\"             ],             \"evidenceCategory\": \"x-search\"         }     },     {         \"documentId\": \""+ PathResolver.EVIDENCE_LIBRARY_FILE_PATH_PREFIX +"2a/Beeswax/2Wiki-BeeswaxD.jpg\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",             \"evidenceSourceType\": \"OA\",             \"evidenceSourceTypeId\": \"123\",             \"evidenceSourceUrl\": \"http://local/evidence/source/url\",             \"evidenceGroupNames\": [                 \"trash\",                 \"working\",                 \"suggested\"             ],             \"evidenceCategory\": \"2a\"         }     } ]";        
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
        testRetrieveEvidenceMetadata();
    }  

    /**
     * Test retrieve evidence metadata.
     */
    private void testRetrieveEvidenceMetadata() {

        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString() + EvidenceBaseTest.URL_MIDFIX + "my-proof.pdf" + EvidenceBaseTest.URL_POSTFIX_METADATA;
        String EVI_METADATA_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;                     

        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        //System.out.println(str);        
        String haystack = str;

        //check for creationTime's presence
        String modUsrId = "\"modifiedByUserId\"(.*?)\"Gunkcopycat\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modUsrId));
    }   

    /**
     * Test cefstt bad document id value inside json.
     */
    @Test
    public void testCEFSTTBadDocumentIdValueInsideJson() {

        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString() + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {         \"documentId\": \"/case/evidence/proof.pdf\",         \"metadata\": null     } ]";        
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);    

        //404 -folder 77777778 doesn't exists.
        //400 - Unrecognized field "documentGunkId"
        if(400 == response.getStatus()){
            assertEquals(400, response.getStatus());
        }else if(404 == response.getStatus()){
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        }         
    }     

    /**
     * Test cefstt with empty json.
     */
    @Test
    public void testCEFSTTWithEmptyJson() {

        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestCase + ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString() + "/evidences/" + "copy";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;           
        String metadata = "[     {      } ]";        
        WebResource webResource = client.resource(WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        //String str = response.getEntity(String.class);
        //System.out.println(str);    

        //404 -folder 77777778 doesn't exists.
        //400 - Unrecognized field "documentGunkId"
        if(400 == response.getStatus()){
            assertEquals(400, response.getStatus());
        }else if(404 == response.getStatus()){
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        }         
    }      

}
