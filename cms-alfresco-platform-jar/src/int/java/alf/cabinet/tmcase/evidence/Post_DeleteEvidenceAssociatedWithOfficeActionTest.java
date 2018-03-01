package alf.cabinet.tmcase.evidence;

import static org.junit.Assert.assertEquals;
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
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.OfficeActionBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;

/**
 * 
 * @author stank
 *
 */
public class Post_DeleteEvidenceAssociatedWithOfficeActionTest extends EvidenceBaseTest{

    private static final String X_SEARCH_FOUND_PDF = "x-search-found_247.pdf";
    private static final String MY_FINDINGS_PDF = "my-findings_247.pdf";

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
     * Test create sample evi one for off actn.
     */
    private void testCreateSampleEviOneForOffActn() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(X_SEARCH_FOUND_PDF);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        String VALUE_EVIDENCE_METADATA = "{       \"accessLevel\": \"internal\",  \"documentAlias\": \"DocNameForTmngUiDisplay\",  \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Test create sample evi two for off actn.
     */
    private void testCreateSampleEviTwoForOffActn() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(MY_FINDINGS_PDF);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        String VALUE_EVIDENCE_METADATA = "{        \"accessLevel\": \"internal\",    \"documentAlias\": \"DocNameForTmngUiDisplay\",  \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",  \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Office action create and recreate test for77777777.
     */
    //@Test
    public void officeActionCreateAndRecreateTestFor77777777() {
        testCreateSampleEviOneForOffActn();
        testCreateSampleEviTwoForOffActn();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String VALUE_OFFACTN_FILE_NAME = "OfficeAction_1_Version1.0_247.pdf";
        urlInput.setFileName(VALUE_OFFACTN_FILE_NAME);
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        String VALUE_OFFICEACTION_METADATA = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"evidenceList\": [         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()+"/evidence/"+X_SEARCH_FOUND_PDF+"\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }         },         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()+"/evidence/"+MY_FINDINGS_PDF+"\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"working\",                     \"sent\"                 ]             }         }     ] }";
        offActnParam.put(ParameterKeys.METADATA.toString(), VALUE_OFFICEACTION_METADATA);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);

        ClientResponse response = createDocument(offActnParam);

        String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));

        // check for valid docuemnt ID
        String validDocumentID = "/" + OfficeAction.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));

    }
   
    /**
     * Test copy evi fr src to trgt.
     */
    @Test
    public void testCopyEviFrSrcToTrgtThenTryToDeleteEviWithAssoc() {
        
        officeActionCreateAndRecreateTestFor77777777();
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String copyEviFrSrcToTrgtUrl = CaseOtherUrl.copyEvidenceFrSrcToTrgtUrl(urlInput);        
        String metadata = "[{ \"documentId\": \"/case/77777777/evidence/"+ MY_FINDINGS_PDF +"\", \"metadata\": { \"accessLevel\": \"internal\", \"modifiedByUserId\": \"copycat\",  \"evidenceGroupNames\": [] } }, { \"documentId\": \"/case/77777777/evidence/"+ X_SEARCH_FOUND_PDF +"\", \"metadata\": { \"accessLevel\": \"internal\", \"evidenceGroupNames\": [\"working\"], \"evidenceCategory\": \"x-search\" } }] ";
        WebResource webResource = client.resource(copyEviFrSrcToTrgtUrl);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
        evidenceDeleteTest();
        
    }


    //@Test
    public void evidenceDeleteTest() {
        String caseId = ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString();
        String firstFile = MY_FINDINGS_PDF;
        String secondFile = X_SEARCH_FOUND_PDF;        
        testDeleteEvidence(caseId, firstFile, secondFile);
    }
    
    private void testDeleteEvidence(String caseId, String firstFile, String secondFile) {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);          
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId
                + "/evidence/"+firstFile+"\"     },     {         \"documentId\": \"/case/"
                + caseId + "/evidence/"+secondFile+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.post(ClientResponse.class, metadata);
         //String str = response.getEntity(String.class);
         //System.out.println(response.getStatus() + ": " + str);
        assertEquals(200, response.getStatus());

        // String haystack = response.getEntity(String.class);
        // String needle = "\"version\":\"1.0\"";
        // assertTrue(contains( haystack, needle));
    }    

}
