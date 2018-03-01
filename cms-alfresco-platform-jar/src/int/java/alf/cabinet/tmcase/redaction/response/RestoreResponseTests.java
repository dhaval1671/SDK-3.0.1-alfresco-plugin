package alf.cabinet.tmcase.redaction.response;

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
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * Retrieve Response Content test cases.
 * 
 * @author Zorina Simeonova
 *
 */

public class RestoreResponseTests extends RedactionResponseBaseTest {

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
     * Test restore to original file not found url.
     */
    @Test
    public void testRestoreToOriginalFileNotFoundURL() {
        assertEquals(HttpStatus.NOT_FOUND.value(), restoreToOriginalUrl(ParameterValues.VALUE_SERIAL_77777781_NUMBER.toString()));
    }

    /**
     * Test restore to original url.
     */
    @Test
    public void testRestoreToOriginalURL() {
        createOriginalResponse();
        createRedactedVersionofOriginalResponse();
        assertEquals(200, restoreToOriginalUrl(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString()));
    }

    /**
     * Test restore to original no redacted files url.
     */
    @Test
    public void testRestoreToOriginalNoRedactedFilesURL() {
        assertEquals(400, restoreToOriginalUrl(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString()));
    }

    /**
     * Creates the original response.
     */
    private void createOriginalResponse() {
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME);
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateResponsePut(urlInput);
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_ORIGINAL_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ResponseBaseTest.CONTENT_RESPONSE_ATTACHMENT);
        ClientResponse response = createDocument(responseParam);
        assertEquals(201, response.getStatus());

    }

    /**
     * Creates the redacted versionof original response.
     */
    private void createRedactedVersionofOriginalResponse() {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInput.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME);
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        // System.out.println(REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionResponseBaseTest.CONTENT_CREATE_REDACTED_RESPONSE);
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);
        assertEquals(200, redactedResponse.getStatus());

    }

    /**
     * Restore to original url.
     *
     * @param sn
     *            the sn
     * @return the int
     */
    private int restoreToOriginalUrl(String sn) {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(sn);
        urlInput.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME);
        String RedactedRetrieveResponseContent = CaseOtherUrl.deleteRestoreRedactedToOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .delete(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        return response.getStatus();
    }

}
