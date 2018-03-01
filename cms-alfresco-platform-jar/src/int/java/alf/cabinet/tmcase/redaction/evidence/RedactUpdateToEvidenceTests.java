package alf.cabinet.tmcase.redaction.evidence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
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
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RedactionEvidenceBaseTest;
import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * The Class RedactUpdateToEvidenceTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RedactUpdateToEvidenceTests extends RedactionEvidenceBaseTest {

    /** The log. */
    public static Logger LOG = Logger.getLogger(RedactUpdateToEvidenceTests.class);

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
     * Creates the regular evidence.
     */
    private void createRegularEvidence() {
        Map<String, String> responseParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);
        String EVIDENCE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        responseParam.put(ParameterKeys.URL.toString(), EVIDENCE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionEvidenceBaseTest.CONTENT_REGULAR_EVIDENCE_TO_BE_REDACTED);
        ClientResponse response = createDocument(responseParam);
        String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Evidence.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Redacted evidence create and recreate test.
     */
    @Test
    public void redactedEvidenceCreateAndRecreateTest() {
        createRegularEvidence();
        testCreateRedactedEvidence();
        testUpdateRedactedEvidence();
        doRegularUpdateOfMetadataWithRedactionnLevelFlagNoneofEvidence();
        doRegularUpdateMetadataOfEvidence();

    }

    /**
     * Do regular update of metadata with redactionn level flag noneof evidence.
     * This should return 400 as System should not allow to update evidence when
     * it has redacted.
     */
    private void doRegularUpdateOfMetadataWithRedactionnLevelFlagNoneofEvidence() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        // System.out.println(EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = EvidenceBaseTest.VALUE_EVIDENCE_METADATA_WITH_REDACTIONFLAG_NONE;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(),
                    EvidenceBaseTest.VALUE_EVIDENCE_METADATA_WITH_REDACTIONFLAG_NONE));
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
        // String haystack = response.getEntity(String.class);
        // System.out.println("Alfresco Layer: " + haystack);
        assertEquals(400, response.getStatus());
    }

    /**
     * Do regular update metadata of evidence.
     */
    private void doRegularUpdateMetadataOfEvidence() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        // System.out.println(EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = EvidenceBaseTest.VALUE_EVI_UPDATED_METADATA_ONE;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(),
                    EvidenceBaseTest.VALUE_EVI_UPDATED_METADATA_ONE));
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
        //String haystack = response.getEntity(String.class);
        // System.out.println("Alfresco Layer: " + haystack);
        assertEquals(400, response.getStatus());
        // String needle = "\"version\":\"1.3\"";
        // assertTrue(containsStringLiteral(haystack, needle));
    }

    /**
     * Test create redacted evidence.
     */
    private void testCreateRedactedEvidence() {
        Map<String, String> redactedEvidenceParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);
        String REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        // System.out.println(REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.URL.toString(), REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        redactedEvidenceParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionEvidenceBaseTest.CONTENT_CREATE_REDACTED_EVIDENCE);
        ClientResponse redactedEvidence = CentralBase.updateDocument(redactedEvidenceParam);
        String str = redactedEvidence.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, redactedEvidence.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Evidence.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Test update redacted evidence.
     */
    private void testUpdateRedactedEvidence() {
        Map<String, String> redactedEvidenceParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(REGULAR_EVIDENCE_TO_BE_REDACTED);
        String REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL = CaseOtherUrl.createOrUpdateRedactPost(urlInput);
        redactedEvidenceParam.put(ParameterKeys.URL.toString(), REDACTED_EVIDENCE_CREATE_WEBSCRIPT_URL);
        redactedEvidenceParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        redactedEvidenceParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                RedactionEvidenceBaseTest.CONTENT_UPDATE_REDACTED_EVIDENCE);
        ClientResponse redactedEvidence = CentralBase.updateDocument(redactedEvidenceParam);
        String str = redactedEvidence.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, redactedEvidence.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for valid docuemnt ID
        String validDocumentID = "/" + Evidence.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

}
