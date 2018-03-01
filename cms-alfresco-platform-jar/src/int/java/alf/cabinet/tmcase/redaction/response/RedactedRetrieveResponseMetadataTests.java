package alf.cabinet.tmcase.redaction.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionEvidenceBaseTest;
import alf.integration.service.all.base.RedactionResponseBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Response;

/**
 * The Class RedactedRetrieveResponseMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RedactedRetrieveResponseMetadataTests extends RedactionResponseBaseTest {

    /** The client. */
    public static Client client = null;

    /** The serial number78. */
    public static String serialNumber78 = ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString();

    /** The serial number79. */
    public static String serialNumber79 = ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString();

    /**
     * Sets the up.
     */
    @BeforeClass
    public static void setUp() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));
        // createResponseDocVersionStackWithOriginalVersionOnTop(serialNumber78);
        // createResponseDocVersionStackWithRedactedVersionOnTop(serialNumber79);
    }

    /**
     * Tear down.
     */
    @AfterClass
    public static void tearDown() {

    }

    /**
     * Test redacted retrieve metadata default flavour with original version on
     * top.
     */
    @Test
    public void testRedactedRetrieveMetadataDefaultFlavourWithOriginalVersionOnTop() {
        // This test case should fetch latest redacted document.
        // Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber78);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String redactedRetrieveResponseMetadata = CaseRetrieveMetadataUrl.getRedactedRetrieveMetadata(urlInput);
        // System.out.println(redactedRetrieveResponseMetadata);
        WebResource webResource = client.resource(redactedRetrieveResponseMetadata);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test redacted retrieve metadata default flavour with redacted version on
     * top.
     */
    @Test
    public void testRedactedRetrieveMetadataDefaultFlavourWithRedactedVersionOnTop() {
        // This test case should fetch latest redacted document.
        // Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber79);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        String redactedRetrieveResponseMetadata = CaseRetrieveMetadataUrl.getRedactedRetrieveMetadata(urlInput);
        // System.out.println(redactedRetrieveResponseMetadata);
        WebResource webResource = client.resource(redactedRetrieveResponseMetadata);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        // System.out.println(str);
        String haystack = str;
        // check for redacted version which should be 1.2 and Original version
        // is on the top ie 1.3.
        String creationTime = "\"version\"(.*?)\"1.1\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, creationTime));
        // check for modifiedByUserId it should be 'redactedResponse'
        String modificationTime = "\"modifiedByUserId\"(.*?)\"redactedResponse\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, modificationTime));
        String docType = "\"documentType\"(.*?)\"" + Response.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, docType));
        String originalDocumentVersion = "\"originalDocumentVersion\"(.*?)\"(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, originalDocumentVersion));
        String redactionLevel = "\"redactionLevel\"(.*?)\"(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, redactionLevel));
    }

    /**
     * Test redacted retrieve metadata with flavour as original with redacted
     * version on top.
     */
    @Test
    public void testRedactedRetrieveMetadataWithFlavourAsOriginalWithRedactedVersionOnTop() {
        // This test case should fetch latest Original document.
        // Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber79);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        urlInput.setFlavour(RedactionEvidenceBaseTest.ORIGINAL);
        String redactedRetrieveResponseMetadata = CaseRetrieveMetadataUrl
                .getRedactedRetrieveMetadataFlavourOriginal(urlInput);
        // System.out.println(redactedRetrieveResponseMetadata);
        WebResource webResource = client.resource(redactedRetrieveResponseMetadata);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        // System.out.println(str);
        String haystack = str;
        // check for redacted version which should be 1.1 and Original version
        // is on the bottom ie 1.0.
        String creationTime = "\"version\"(.*?)\"1.0\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, creationTime));
        // check for modifiedByUserId it should be 'redactedResponse'
        String modificationTime = "\"modifiedByUserId\"(.*?)\"originalResponse\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, modificationTime));
        String docType = "\"documentType\"(.*?)\"" + Response.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, docType));
    }

    /**
     * Test redacted retrieve metadata with flavour as original with original
     * version on top.
     */
    @Test
    public void testRedactedRetrieveMetadataWithFlavourAsOriginalWithOriginalVersionOnTop() {
        // This test case should fetch latest Original document.
        // Before fetching create Response Doc version stack.
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber78);
        urlInput.setFileName(REGULAR_RESPONSE_TO_BE_REDACTED);
        urlInput.setFlavour(RedactionEvidenceBaseTest.ORIGINAL);
        String redactedRetrieveResponseMetadata = CaseRetrieveMetadataUrl
                .getRedactedRetrieveMetadataFlavourOriginal(urlInput);
        // System.out.println(redactedRetrieveResponseMetadata);
        WebResource webResource = client.resource(redactedRetrieveResponseMetadata);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String str = response.getEntity(String.class);
        // System.out.println(str);
        String haystack = str;
        // check for redacted version which should be 1.1 and Original version
        // is on the bottom ie 1.0.
        String creationTime = "\"version\"(.*?)\"1.4\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, creationTime)); //This is breaking on SIT 5.1 Jul/28/2017 TODO
        // check for modifiedByUserId it should be 'redactedResponse'
        String modificationTime = "\"modifiedByUserId\"(.*?)\"originalResponse\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, modificationTime));
        String docType = "\"documentType\"(.*?)\"" + Response.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, docType));
    }

}
