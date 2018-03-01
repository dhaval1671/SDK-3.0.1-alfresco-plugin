package alf.cabinet.tmcase.redaction.evidence;

import static org.junit.Assert.assertEquals;

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
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionEvidenceBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * Retrieve Evidence Content test cases.
 * 
 * @author Zorina Simeonova
 *
 */

public class RestoreEvidenceTests extends RedactionEvidenceBaseTest {

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

    /**
     * Test restore to original url.
     */
    @Test
    public void testRestoreToOriginalURL() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(EvidenceBaseTest.EVIDENCE_FILE_NAME_THREE);        
        createOriginalEvidence(urlInput);
        createRedactedVersionofOriginalEvidence(urlInput);
        restoreToOriginalUrl(urlInput);
        testRestoreToOriginalNoRedactedFilesURL(urlInput);
    }

    /**
     * Creates the original evidence.
     *
     * @param urlInput the url input
     */
    private void createOriginalEvidence(UrlInputDto urlInput) {
        Map<String, String> responseParam = new HashMap<String, String>();
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(responseParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Creates the redacted versionof original evidence.
     *
     * @param urlInput the url input
     */
    private void createRedactedVersionofOriginalEvidence(UrlInputDto urlInput) {
        Map<String, String> redactedEvidenceParam = new HashMap<String, String>();
        String REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        // System.out.println(REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.URL.toString(), REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        redactedEvidenceParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RedactionEvidenceBaseTest.CONTENT_CREATE_REDACTED_EVIDENCE);
        ClientResponse redactedEvidence = CentralBase.updateDocument(redactedEvidenceParam);
        //String str = redactedEvidence.getEntity(String.class);
        //System.out.println(str);        
        assertEquals(200, redactedEvidence.getStatus());
    }

    /**
     * Restore to original url.
     *
     * @param urlInput the url input
     */
    private void restoreToOriginalUrl(UrlInputDto urlInput) {
        String RedactedRetrieveEvidenceContent = CaseOtherUrl.deleteRestoreRedactedToOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveEvidenceContent);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response =  b.delete(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);               
        assertEquals(200, response.getStatus());        
    }

    /**
     * Test restore to original no redacted files url.
     *
     * @param urlInput the url input
     */
    public void testRestoreToOriginalNoRedactedFilesURL(UrlInputDto urlInput) {
        String RedactedRetrieveEvidenceContent = CaseOtherUrl.deleteRestoreRedactedToOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveEvidenceContent);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response =  b.delete(ClientResponse.class);  
        //String str = response.getEntity(String.class);
        //System.out.println(str);          
        assertEquals(400, response.getStatus()); 
    }   

    /**
     * Test restore to original file not found url.
     */
    @Test
    public void testRestoreToOriginalFileNotFoundURL() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInput.setFileName(EvidenceBaseTest.EVIDENCE_FILE_NAME_THREE);          
        String restoreEvidenceFile = CaseOtherUrl.deleteRestoreRedactedToOriginal(urlInput);
        WebResource webResource = client.resource(restoreEvidenceFile);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response =  b.delete(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);           
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());         
    }    

}
