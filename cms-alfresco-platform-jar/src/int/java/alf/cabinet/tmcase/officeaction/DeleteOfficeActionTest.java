package alf.cabinet.tmcase.officeaction;

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
import alf.integration.service.url.helpers.tmcase.CaseDeleteUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;

/**
 * The Class DeleteAttachmentTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class DeleteOfficeActionTest extends OfficeActionBaseTest {

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
     * Attachment delete test.
     */
    @Test
    public void deleteInternalOfficeAction() {

        //create office Action
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        officeActionCreateAndRecreateTestFor77777777(urlInput);
        
        //delete office action.
        String ATTCH_DELETE_WEBSCRIPT_URL = CaseDeleteUrl.genericDeleteUrl(urlInput);
        //System.out.println(ATTCH_DELETE_WEBSCRIPT_URL);
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);
        WebResource webResource = client.resource(ATTCH_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseDelete = b.delete(ClientResponse.class);
        //String str = responseDelete.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, responseDelete.getStatus());        
    }
    
    /**
     * Test create sample evi one for off actn.
     * @param urlInput2 
     */
    private void testCreateSampleEviOneForOffActn(UrlInputDto urlInput2) {
        String VALUE_EVIDENCE_METADATA = "{    \"accessLevel\": \"internal\", \"documentAlias\": \"DocNameForTmngUiDisplay\",  \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\", \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
        urlInput2.setDocType(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput2.setFileName("x-search-found-for-delete-oa.pdf");
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput2);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Test create sample evi two for off actn.
     * @param urlInput2 
     */
    private void testCreateSampleEviTwoForOffActn(UrlInputDto urlInput2) {
        String VALUE_EVIDENCE_METADATA = "{    \"accessLevel\": \"internal\", \"documentAlias\": \"DocNameForTmngUiDisplay\",  \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",  \"documentCode\": \"EVI\",     \"version\": \"1.0\",     \"documentStartDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\" }";
        urlInput2.setDocType(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput2.setFileName("my-findings-for-delete-oa.pdf");
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput2);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Office action create and recreate test for77777777.
     * @param urlInput 
     */
    private void officeActionCreateAndRecreateTestFor77777777(UrlInputDto urlInput) {
        String VALUE_OFFICEACTION_METADATA = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"evidenceList\": [         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()+"/evidence/x-search-found-for-delete-oa.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }         },         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()+"/evidence/my-findings-for-delete-oa.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"working\",                     \"sent\"                 ]             }         }     ] }";
        testCreateSampleEviOneForOffActn(urlInput);
        testCreateSampleEviTwoForOffActn(urlInput);
        urlInput.setDocType(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInput.setFileName("officeAcctionToBeDeletedTwo.pdf");
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), VALUE_OFFICEACTION_METADATA);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        testCreateOfficeAction(offActnParam);
    }

    private void testCreateOfficeAction(Map<String, String> offActnParam) {
        ClientResponse response = createDocument(offActnParam);

        String str = response.getEntity(String.class);
        //System.out.println(str);

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));

        // check for valid docuemnt ID
        String validDocumentID = "/" + OfficeAction.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }


}
