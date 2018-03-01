package alf.cabinet.tmcase.redaction.withdrawal;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionWithdrawalBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.AccessLevel;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Withdrawal;

/**
 * 
 * @author Sanjay Tank
 *
 */

public class RetrieveOriginalMetadataTests extends RedactionWithdrawalBaseTest {

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
     * Test retrieve original response metadata.
     */
    @Test
    public void testRetrieveOriginalWithdrawalMetadata() {
        createOriginalWithdrawal();
        createRedactedVersionofOriginalWithdrawal();
        retrieveOriginalWithdrawalMetadata();
        retrieveOriginalWithdrawalMetadataWithInvalidAccessLevel();
    }

    /**
     * Creates the original response.
     */
    private void createOriginalWithdrawal() {
        String VALUE_WITHDRAWAL_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateResponseIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"internal\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\", \"redactionLevel\": \"PARTIAL\" }";
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_WITHDRAWAL.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(RedactionWithdrawalBaseTest.VALUE_WITHDRAWAL_FILE_NAME_TWO);
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), VALUE_WITHDRAWAL_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RedactionWithdrawalBaseTest.CONTENT_WITHDRAWAL_ATTACHMENT);
        ClientResponse response = createDocument(responseParam);
        String str = response.getEntity(String.class);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));
        String validDocumentID = "/" + Withdrawal.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Creates the redacted versionof original response.
     */
    private void createRedactedVersionofOriginalWithdrawal() {
        String VALUE_WITHDRAWAL_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateResponseIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"restricted\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\", \"redactionLevel\": \"PARTIAL\" }";
        Map<String, String> redactedWithdrawalParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_WITHDRAWAL.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(RedactionWithdrawalBaseTest.VALUE_WITHDRAWAL_FILE_NAME_TWO);
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        redactedWithdrawalParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedWithdrawalParam.put(ParameterKeys.METADATA.toString(), VALUE_WITHDRAWAL_METADATA);
        redactedWithdrawalParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionWithdrawalBaseTest.CONTENT_CREATE_REDACTED_RESPONSE);
        ClientResponse redactedWithdrawal = updateDocument(redactedWithdrawalParam);
        assertEquals(200, redactedWithdrawal.getStatus());

    }

    /**
     * Retrieve original response metadata.
     */
    private void retrieveOriginalWithdrawalMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_WITHDRAWAL.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(RedactionWithdrawalBaseTest.VALUE_WITHDRAWAL_FILE_NAME_TWO);
        urlInput.setAccessLevel(AccessLevel.INTERNAL.toString());
        String RedactedRetrieveWithdrawalContent = CaseRetrieveMetadataUrl.getRedactedRetrieveMetadataFlavourOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveWithdrawalContent);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }
    
    private void retrieveOriginalWithdrawalMetadataWithInvalidAccessLevel() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_WITHDRAWAL.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(RedactionWithdrawalBaseTest.VALUE_WITHDRAWAL_FILE_NAME_TWO);
        urlInput.setAccessLevel(AccessLevel.PUBLIC.toString());
        String RedactedRetrieveWithdrawalContent = CaseRetrieveMetadataUrl.getRedactedRetrieveMetadataFlavourOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveWithdrawalContent);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }    

}
