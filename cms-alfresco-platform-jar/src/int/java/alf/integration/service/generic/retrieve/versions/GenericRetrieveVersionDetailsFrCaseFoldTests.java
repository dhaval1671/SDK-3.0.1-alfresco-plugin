package alf.integration.service.generic.retrieve.versions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionEvidenceBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class GenericRetrieveVersionDetailsFrCaseFoldTests extends EvidenceBaseTest{

    private static Logger LOG = Logger.getLogger(GenericRetrieveVersionDetailsFrCaseFoldTests.class);
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
     * Test retrieve version details of file fr case folder tests.
     */
    @Test
    public void testRetrieveVersionDetailsOfDocFrCaseFolderTests_ACL_Restricted() {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("Retrieve_Mark_Versions.jpeg");
        urlInput.setAccessLevel("restricted");
        
        createSampleMark(urlInput);
        updateSampleMark(urlInput);
        redactSampleMark(urlInput);
        

        String VERSIONS_WEBSCRIPT_URL = CaseOtherUrl.genericRetrieveVersionsUrl(urlInput);
        //System.out.println(VERSIONS_WEBSCRIPT_URL);
        WebResource webResource = client.resource(VERSIONS_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needleOne = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral(haystack, needleOne));        
        String needleTwo = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral(haystack, needleTwo)); 
        String needleThree = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needleThree)); 
        
        
        testRetrieveVersionDetailsOfDocFrCaseFolderTests_ACL_Public(urlInput);
    }

    /**  
     * @Title: testRetrieveVersionDetailsOfDocFrCaseFolderTests_ACL_Public  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void testRetrieveVersionDetailsOfDocFrCaseFolderTests_ACL_Public(UrlInputDto urlInput) {
        urlInput.setAccessLevel("public");
        String VERSIONS_WEBSCRIPT_URL = CaseOtherUrl.genericRetrieveVersionsUrl(urlInput);
        //System.out.println(VERSIONS_WEBSCRIPT_URL);
        WebResource webResource = client.resource(VERSIONS_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needleOne = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral(haystack, needleOne));        
        String needleTwo = "\"version\":\"1.1\"";
        assertFalse(containsStringLiteral(haystack, needleTwo)); 
        String needleThree = "\"version\":\"1.0\"";
        assertFalse(containsStringLiteral(haystack, needleThree)); 
        
    }

    /**  
     * @Title: createSampleMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void createSampleMark(UrlInputDto urlInput) {
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"restricted\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);        
        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, response.getStatus());
        String haystack = str;
        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
        
    }

    /**  
     * @Title: updateSampleMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void updateSampleMark(UrlInputDto urlInputOne) {
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";        
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);         
        
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(MarkBaseTest.CONTENT_MRK_TIF);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE));
        WebResource webResource = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMarkContentAndMetadata:: \n" + haystack);        
        assertEquals(200, response.getStatus());
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
    }

    /**  
     * @Title: redactSampleMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void redactSampleMark(UrlInputDto urlInput) {
        String VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL = "{  \"accessLevel\": \"public\",  \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";
        Map<String, String> redactedEvidenceParam = new HashMap<String, String>();
        String REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        // System.out.println(REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.URL.toString(), REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        redactedEvidenceParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
        RedactionEvidenceBaseTest.CONTENT_CREATE_REDACTED_EVIDENCE);
        ClientResponse redactedEvidence = CentralBase.updateDocument(redactedEvidenceParam);
        String str = redactedEvidence.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, redactedEvidence.getStatus());
        String haystack = str;
        String validDocumentID = "/" + MarkDoc.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    @Test
    public void testRetrieveVersionWhenNoPublicDocPresent() {
        //  DE7364: System does not return the expected results for retrieving the list of versions by docId
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputTwo.setFileName("Retrieve_Mark_Versions_Two.jpeg");
        urlInputTwo.setAccessLevel("public");
        
        createSampleMarkTwo(urlInputTwo);
        updateSampleMarkTwo(urlInputTwo);

        String VERSIONS_WEBSCRIPT_URL = CaseOtherUrl.genericRetrieveVersionsUrl(urlInputTwo);
        //System.out.println(VERSIONS_WEBSCRIPT_URL);
        WebResource webResource = client.resource(VERSIONS_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());

    }    
    
    private void createSampleMarkTwo(UrlInputDto urlInput) {
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);        
        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, response.getStatus());
        String haystack = str;
        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
        
    }

    /**  
     * @Title: updateSampleMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void updateSampleMarkTwo(UrlInputDto urlInputOne) {
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";        
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);         
        
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(MarkBaseTest.CONTENT_MRK_TIF);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE));
        WebResource webResource = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMarkContentAndMetadata:: \n" + haystack);        
        assertEquals(200, response.getStatus());
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
    }    
    
    @Test
    public void testRetrieveVersionNoDocPresent() {
        // DE7364: System does not return the expected results for retrieving the list of versions by docId
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputTwo.setFileName("Retrieve_Mark_Versions_Thee.jpeg");
        urlInputTwo.setAccessLevel("public");
        String VERSIONS_WEBSCRIPT_URL = CaseOtherUrl.genericRetrieveVersionsUrl(urlInputTwo);
        //System.out.println(VERSIONS_WEBSCRIPT_URL);
        WebResource webResource = client.resource(VERSIONS_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(404, response.getStatus());
    }     
    
}
