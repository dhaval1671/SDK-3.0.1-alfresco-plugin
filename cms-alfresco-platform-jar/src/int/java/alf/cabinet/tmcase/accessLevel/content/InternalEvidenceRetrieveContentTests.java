package alf.cabinet.tmcase.accessLevel.content;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * Retrieve Evidence Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class InternalEvidenceRetrieveContentTests extends CentralBase {

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
     * Test retrieve evidence content.
     */
    @Test
    public void createAndRetrveRestrictedEvidenceConWithClientReqAccessLevelPublic() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777784_NUMBER.toString());
        urlInput.setFileName("AccessLevelInternalEvidence_RteveCont.pdf");
        
        //create evidence
        evidenceCreateAndRecreateTest(urlInput);
        
        retrieveInternalEvidenceWithClientRequestPublicAccessLevel(urlInput); 
        retrieveInternalEvidenceWithClientRequestInternalAccessLevel(urlInput);
        retrieveInternalEvidenceWithClientRequestRestrictedAccessLevel(urlInput);
        retrieveRestrictedEvidenceWithClientRequestNOAccessLevel(urlInput);
        retrieveRestrictedEvidenceWithClientRequestUnrecognizedAccessLevel(urlInput);
    }
    
    private void retrieveRestrictedEvidenceWithClientRequestNOAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel(null);
        String EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }
    
    private void retrieveRestrictedEvidenceWithClientRequestUnrecognizedAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("junkValue");
        String EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }        
    
    private void retrieveInternalEvidenceWithClientRequestPublicAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("public");
        String EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }

    private void retrieveInternalEvidenceWithClientRequestInternalAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("internal");
        String EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
    }    
    
    private void retrieveInternalEvidenceWithClientRequestRestrictedAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("restricted");
        String EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVIDENCE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("78 KB", fileSizeFrResponse);
    }    
    
    private void evidenceCreateAndRecreateTest(UrlInputDto urlInput) {
        String VALUE_EVIDENCE_METADATA = "{  \"accessLevel\": \"internal\",  \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";        
        Map<String, String> evidenceParam = new HashMap<String, String>();
        String EVIDENCE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput); 
        //System.out.println(EVIDENCE_CREATE_WEBSCRIPT_URL);
        evidenceParam.put(ParameterKeys.URL.toString(),EVIDENCE_CREATE_WEBSCRIPT_URL);
        evidenceParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA);
        evidenceParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);        
        ClientResponse response = createDocument(evidenceParam);        
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
