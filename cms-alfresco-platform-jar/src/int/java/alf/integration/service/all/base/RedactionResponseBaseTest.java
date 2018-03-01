package alf.integration.service.all.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Response;

/**
 * The Class RedactionResponseBaseTest.
 */
public class RedactionResponseBaseTest extends CentralBase {

    /** The log. */
    public static Logger LOG = Logger.getLogger(RedactionResponseBaseTest.class);

    /** The Constant REGULAR_RESPONSE_TO_BE_REDACTED. */
    public static final String REGULAR_RESPONSE_TO_BE_REDACTED = "2a_RegularResponse_to_be_redacted.pdf";

    /** The Constant RESPONSE_TO_BE_REDACTED_TWO. */
    public static final String RESPONSE_TO_BE_REDACTED_TWO = "RedactUpdateToResponseTests_createRegularResponse.pdf";

    /** The Constant RESPONSE_TO_BE_REDACTED_DOES_NOT_EXITS. */
    public static final String RESPONSE_TO_BE_REDACTED_DOES_NOT_EXITS = "Response_to_be_redactedDoesNOTExits.pdf";

    /** The Constant CONTENT_REGULAR_RESPONSE_TO_BE_REDACTED. */
    public static final String CONTENT_REGULAR_RESPONSE_TO_BE_REDACTED = "src//test//resources//redaction//response//2a_RegularResponse_to_be_redacted.pdf";

    /** The Constant CONTENT_CREATE_REDACTED_RESPONSE. */
    public static final String CONTENT_CREATE_REDACTED_RESPONSE = "src//test//resources//redaction//response//2b_Create_RedactedResponse.pdf";

    /** The Constant CONTENT_UPDATE_REDACTED_RESPONSE. */
    public static final String CONTENT_UPDATE_REDACTED_RESPONSE = "src//test//resources//redaction//response//2c_Update_RedactedResponse.pdf";

    /** The Constant CONTENT_UPDATE_REGULAR_RESPONSE_TO_BE_REDACTED. */
    public static final String CONTENT_UPDATE_REGULAR_RESPONSE_TO_BE_REDACTED = "src//test//resources//redaction//response//2d_Updated_RegularResponse.pdf";

    /** The Constant VALUE_REDACTED_RESPONSE_METADATA. */
    public static final String VALUE_REDACTED_RESPONSE_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"redactedResponse\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\", \"redactionLevel\": \"Partial\" }";

    /** The Constant VALUE_ORIGINAL_RESPONSE_METADATA. */
    public static final String VALUE_ORIGINAL_RESPONSE_METADATA = "{\"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"originalResponse\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"accessLevel\" : \"public\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\", \"redactionLevel\": \"FULL\" }";

    /**
     * Creates the response doc version stack with redacted version on top.
     *
     * @param serialNumber
     *            the serial number
     */
    protected static void createResponseDocVersionStackWithRedactedVersionOnTop(String serialNumber) {
        createRegularResponse(serialNumber, "1.0");
        testCreateRedactedResponse(serialNumber, "1.1");
    }

    /**
     * Creates the response doc version stack with original version on top.
     *
     * @param serialNumber
     *            the serial number
     */
    protected static void createResponseDocVersionStackWithOriginalVersionOnTop(String serialNumber) {
        createRegularResponse(serialNumber, "1.0");
        testCreateRedactedResponse(serialNumber, "1.1");
        testUpdateRedactedResponse(serialNumber, "1.2");
        restoreToOriginal(serialNumber, "1.3");
        updateRegularResponse(serialNumber, "1.4");

    }

    /**
     * Restore to original.
     *
     * @param sn the sn
     * @param version the version
     */
    private static void restoreToOriginal(String sn, String version) {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(sn);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String RedactedRetrieveResponseContent = CaseOtherUrl.deleteRestoreRedactedToOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .delete(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }

    /**
     * Update redacted response so asto make it original.
     *
     * @param redactedResponseParam
     *            the redacted response param
     * @return the client response
     */
    protected static ClientResponse updateRedactedResponseSoAstoMakeItOriginal(Map<String, String> redactedResponseParam) {
        String urlValue = redactedResponseParam.get(ParameterKeys.URL.toString());
        String metadataValue = redactedResponseParam.get(ParameterKeys.METADATA.toString());
        String contentAttachmentValue = redactedResponseParam.get(ParameterKeys.CONTENT_ATTACHEMENT.toString());
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString())
                .fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(contentAttachmentValue);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadataValue));
        WebResource webResource = client.resource(urlValue);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        return response;
    }

    /**
     * Test create redacted response.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumberToExpect
     *            the version number to expect
     */
    private static void testCreateRedactedResponse(String serialNumber, String versionNumberToExpect) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        // System.out.println(REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), VALUE_REDACTED_RESPONSE_METADATA);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionResponseBaseTest.CONTENT_CREATE_REDACTED_RESPONSE);
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);
        String str = redactedResponse.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, redactedResponse.getStatus());
        String haystack = str;
        String needle = "\"version\":\"" + versionNumberToExpect + "\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Response.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Test update redacted response.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumberToExpect
     *            the version number to expect
     */
    private static void testUpdateRedactedResponse(String serialNumber, String versionNumberToExpect) {
        Map<String, String> redactedResponseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        redactedResponseParam.put(ParameterKeys.URL.toString(), REDACTED_RESPONSE_CREATE_WEBSCRIPT_URL);
        redactedResponseParam.put(ParameterKeys.METADATA.toString(), VALUE_REDACTED_RESPONSE_METADATA);
        redactedResponseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionResponseBaseTest.CONTENT_UPDATE_REDACTED_RESPONSE);
        ClientResponse redactedResponse = updateDocument(redactedResponseParam);
        String str = redactedResponse.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, redactedResponse.getStatus());
        String haystack = str;
        String needle = "\"version\":\"" + versionNumberToExpect + "\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Response.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Creates the regular response.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumberToExpect
     *            the version number to expect
     */
    static void createRegularResponse(String serialNumber, String versionNumberToExpect) {
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateResponsePut(urlInput);
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), VALUE_ORIGINAL_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionResponseBaseTest.CONTENT_REGULAR_RESPONSE_TO_BE_REDACTED);
        ClientResponse response = createDocument(responseParam);
        String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"" + versionNumberToExpect + "\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Response.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Update regular response.
     *
     * @param serialNumber
     *            the serial number
     * @param versionNumberToExpect
     *            the version number to expect
     */
    static void updateRegularResponse(String serialNumber, String versionNumberToExpect) {
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String RESPONSE_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.getUpdateResponsePost(urlInput);
        // System.out.println(RESPONSE_UPDATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_UPDATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), VALUE_ORIGINAL_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionResponseBaseTest.CONTENT_UPDATE_REGULAR_RESPONSE_TO_BE_REDACTED);
        ClientResponse response = updateRedactedResponseSoAstoMakeItOriginal(responseParam);
        //String str = response.getEntity(String.class);
        assertEquals(200, response.getStatus());
    }

    /**
     * Instantiates a new redaction response base test.
     */
    public RedactionResponseBaseTest() {
        super();
    }

}