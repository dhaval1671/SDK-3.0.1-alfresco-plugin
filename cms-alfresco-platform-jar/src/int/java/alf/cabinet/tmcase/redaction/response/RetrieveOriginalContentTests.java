package alf.cabinet.tmcase.redaction.response;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionResponseBaseTest;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Response;

/**
 * Retrieve Response Content test cases.
 * 
 * @author Zorina Simeonova 
 *
 */

public class RetrieveOriginalContentTests extends RedactionResponseBaseTest {

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
     * Test retrieve original content for invalid serial number.
     */
    @Test
    public void testRetrieveOriginalContentForInvalidSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString());
        urlInput.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME);
        String retrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContentFlavourOriginal(urlInput);
        WebResource webResource = client.resource(retrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve original content response dosnt exists.
     */
    @Test
    public void testRetrieveOriginalContentResponseDosntExists() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(ResponseBaseTest.INVALID_FILE_NAME);
        String retrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContentFlavourOriginal(urlInput);
        WebResource webResource = client.resource(retrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

	/**
	 * Test retrieve original response content.
	 */
	@Test
	public void testRetrieveOriginalResponseContent() {
		createOriginalResponse();
		createRedactedVersionofOriginalResponse();
		retrieveOriginalResponseContent();
	}

	/**
	 * Creates the original response.
	 */
	private void createOriginalResponse() {
		Map<String, String> responseParam = new HashMap<String, String>();
		UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
		urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
		urlInput.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME_TWO);
		String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateResponsePut(urlInput);
		responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
		responseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
		responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ResponseBaseTest.CONTENT_RESPONSE_ATTACHMENT);
		ClientResponse response = createDocument(responseParam);
		String str = response.getEntity(String.class);
		assertEquals(201, response.getStatus());
		String haystack = str;
		String needle = "\"version\":\"1.0\"";
		assertTrue(containsStringLiteral(haystack, needle));
		String validDocumentID = "/" + Response.TYPE + "/";
		assertTrue(containsStringLiteral(haystack, validDocumentID));
	}

	/**
	 * Creates the redacted versionof original response.
	 */
	private void createRedactedVersionofOriginalResponse() {
		Map<String, String> redactedResponseParam = new HashMap<String, String>();
		UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
		urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
		urlInput.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME_TWO);
		String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
		redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
		redactedResponseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
		redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RedactionResponseBaseTest.CONTENT_CREATE_REDACTED_RESPONSE);
		ClientResponse redactedResponse = updateDocument(redactedResponseParam);
        //String str = redactedResponse.getEntity(String.class);
        //System.out.println(str); 		
		assertEquals(200, redactedResponse.getStatus());
	}

	/**
	 * Retrieve original response content.
	 */
	private void retrieveOriginalResponseContent() {
		UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
		urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
		urlInput.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME_TWO);
		String RedactedRetrieveResponseContent = CaseRetrieveContentUrl.getRedactedRetrieveContentFlavourOriginal(urlInput);
		WebResource webResource = client.resource(RedactedRetrieveResponseContent);
		ClientResponse response = webResource
				.type(MediaType.MULTIPART_FORM_DATA_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(ClientResponse.class);
		assertEquals(200, response.getStatus());
	}

}
