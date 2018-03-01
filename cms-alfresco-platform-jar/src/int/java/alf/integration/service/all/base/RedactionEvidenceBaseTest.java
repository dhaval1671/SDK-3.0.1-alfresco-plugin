package alf.integration.service.all.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * The Class RedactionEvidenceBaseTest.
 */
public class RedactionEvidenceBaseTest extends CentralBase {

    /** The log. */
    public static Logger LOG = Logger.getLogger(RedactionEvidenceBaseTest.class);

    /** The Constant REGULAR_EVIDENCE_TO_BE_REDACTED. */
    public static final String REGULAR_EVIDENCE_TO_BE_REDACTED = "2a_RegularEvidence_to_be_redacted.pdf";

    /** The Constant CONTENT_REGULAR_EVIDENCE_TO_BE_REDACTED. */
    public static final String CONTENT_REGULAR_EVIDENCE_TO_BE_REDACTED = "src//test//resources//redaction//evidence//2a_RegularEvidence_to_be_redacted.pdf";

    /** The Constant CONTENT_CREATE_REDACTED_EVIDENCE. */
    public static final String CONTENT_CREATE_REDACTED_EVIDENCE = "src//test//resources//redaction//evidence//2b_Create_RedactedEvidence.pdf";

    /** The Constant CONTENT_UPDATE_REDACTED_EVIDENCE. */
    public static final String CONTENT_UPDATE_REDACTED_EVIDENCE = "src//test//resources//redaction//evidence//2c_Update_RedactedEvidence.pdf";

    /** The Constant CONTENT_UPDATE_REGULAR_EVIDENCE_TO_BE_REDACTED. */
    public static final String CONTENT_UPDATE_REGULAR_EVIDENCE_TO_BE_REDACTED = "src//test//resources//redaction//evidence//2d_Updated_RegularEvidence.pdf";

    /** The Constant VALUE_REDACTED_EVIDENCE_METADATA. */
    public static final String VALUE_REDACTED_EVIDENCE_METADATA = "{ \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"redactedEvidence\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"internal\",     \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\": \"Full\" }";

    /** The Constant CREATE_ORIGINAL_EVIDENCE_METADATA. */
    public static final String CREATE_ORIGINAL_EVIDENCE_METADATA = "{ \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"originalEvidence\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"internal\",     \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\": \"Full\" }";

    /** The Constant UPDATE_ORIGINAL_EVIDENCE_METADATA. */
    public static final String UPDATE_ORIGINAL_EVIDENCE_METADATA = "{ \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"originalEvidence\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"internal\",     \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\": \"Full\" }";

    /** The Constant UPDATE_ORIGINAL_EVIDENCE_METADATA_TWO. */
    public static final String UPDATE_ORIGINAL_EVIDENCE_METADATA_TWO = "{ \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"originalEvidence\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"internal\",     \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\": \"Partial\" }";

    /**
     * Creates the evidence doc version stack with redacted version on top.
     *
     * @param serialNumber
     *            the serial number
     */
    protected static void createEvidenceDocVersionStackWithRedactedVersionOnTop(String serialNumber) {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber);
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);

        createRegularEvidence(urlInput, "1.0");
        testCreateRedactedEvidence(urlInput, "1.1");
    }

    /**
     * Creates the evidence doc version stack with original version on top.
     *
     * @param serialNumber
     *            the serial number
     */
    public static void createEvidenceDocVersionStackWithOriginalVersionOnTop(String serialNumber) {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber);
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);

        createRegularEvidence(urlInput, "1.0");
        testCreateRedactedEvidence(urlInput, "1.1");
        testUpdateRedactedEvidence(urlInput, "1.2");
        restoreToOriginal(serialNumber, "1.3");
        updateRegularEvidence(urlInput, "1.4");
    }

    /**
     * Restore to original.
     *
     * @param sn the sn
     * @param version the version
     */
    private static void restoreToOriginal(String sn, String version) {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(sn);
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);
        String RedactedRetrieveResponseContent = CaseOtherUrl.deleteRestoreRedactedToOriginal(urlInput);
        WebResource webResource = client.resource(RedactedRetrieveResponseContent);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .delete(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }

    /**
     * Creates the regular evidence.
     *
     * @param urlInput
     *            the url input
     * @param versionNumberToExpect
     *            the version number to expect
     */
    static void createRegularEvidence(UrlInputDto urlInput, String versionNumberToExpect) {
        Map<String, String> responseParam = new HashMap<String, String>();
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), CREATE_ORIGINAL_EVIDENCE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionEvidenceBaseTest.CONTENT_REGULAR_EVIDENCE_TO_BE_REDACTED);
        ClientResponse response = createDocument(responseParam);
        String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"" + versionNumberToExpect + "\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Evidence.TYPE + "/";
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Test create redacted evidence.
     *
     * @param urlInput
     *            the url input
     * @param versionNumberToExpect
     *            the version number to expect
     */
    private static void testCreateRedactedEvidence(UrlInputDto urlInput, String versionNumberToExpect) {
        Map<String, String> redactedEvidenceParam = new HashMap<String, String>();
        String REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        // System.out.println(REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.URL.toString(), REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.METADATA.toString(), VALUE_REDACTED_EVIDENCE_METADATA);
        redactedEvidenceParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionEvidenceBaseTest.CONTENT_CREATE_REDACTED_EVIDENCE);
        ClientResponse redactedEvidence = CentralBase.updateDocument(redactedEvidenceParam);
        String str = redactedEvidence.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, redactedEvidence.getStatus());
        String haystack = str;
        String needle = "\"version\":\"" + versionNumberToExpect + "\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Evidence.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Test update redacted evidence.
     *
     * @param urlInput
     *            the url input
     * @param versionNumberToExpect
     *            the version number to expect
     */
    private static void testUpdateRedactedEvidence(UrlInputDto urlInput, String versionNumberToExpect) {
        Map<String, String> redactedEvidenceParam = new HashMap<String, String>();
        String REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        redactedEvidenceParam.put(ParameterKeys.URL.toString(), REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.METADATA.toString(), UPDATE_ORIGINAL_EVIDENCE_METADATA);
        redactedEvidenceParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionEvidenceBaseTest.CONTENT_CREATE_REDACTED_EVIDENCE);
        ClientResponse redactedEvidence = CentralBase.updateDocument(redactedEvidenceParam);
        String str = redactedEvidence.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, redactedEvidence.getStatus());
        String haystack = str;
        String needle = "\"version\":\"" + versionNumberToExpect + "\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Evidence.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Update regular evidence.
     *
     * @param urlInput
     *            the url input
     * @param versionNumberToExpect
     *            the version number to expect
     */
    static void updateRegularEvidence(UrlInputDto urlInput, String versionNumberToExpect) {
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        // System.out.println("updateRegularEvidence --> " +
        // EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = UPDATE_ORIGINAL_EVIDENCE_METADATA_TWO;

        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), UPDATE_ORIGINAL_EVIDENCE_METADATA_TWO));
            b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
            o = multiPart;
            try {
                multiPart.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, o);
        String haystackTwo = response.getEntity(String.class);
        //System.out.println(haystackTwo);
        assertEquals(200, response.getStatus());
        String needle = "\"version\":\"" + versionNumberToExpect + "\"";
        assertTrue(containsStringLiteral(haystackTwo, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Evidence.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystackTwo, validDocumentID));

    }

    /**
     * Update redacted evidence so asto make it original.
     *
     * @param redactedEvidenceParam
     *            the redacted evidence param
     * @return the client response
     */
    protected static ClientResponse updateRedactedEvidenceSoAstoMakeItOriginal(Map<String, String> redactedEvidenceParam) {
        String urlValue = redactedEvidenceParam.get(ParameterKeys.URL.toString());
        String metadataValue = redactedEvidenceParam.get(ParameterKeys.METADATA.toString());
        String contentAttachmentValue = redactedEvidenceParam.get(ParameterKeys.CONTENT_ATTACHEMENT.toString());
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
     * The main method.
     *
     * @param arg0
     *            the arguments
     */
    public static void main(String[] arg0) {
        createEvidenceDocVersionStackWithOriginalVersionOnTop(alf.cabinet.tmcase.redaction.evidence.RedactedRetrieveEvidenceContentTests.serialNumber78);
    }

    /** The Constant REDACTED. */
    public static final String REDACTED = "redaction";

    /** The Constant ORIGINAL. */
    public static final String ORIGINAL = "original";

}