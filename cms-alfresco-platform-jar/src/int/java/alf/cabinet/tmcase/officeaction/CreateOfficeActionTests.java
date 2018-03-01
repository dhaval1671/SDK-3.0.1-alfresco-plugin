package alf.cabinet.tmcase.officeaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.NoteBaseTest;
import alf.integration.service.all.base.OfficeActionBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class CreateOfficeActionTests extends OfficeActionBaseTest {

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
     * Office action create with mismatching evi case no77777778.
     */
    @Test
    public void officeActionCreateWithMismatchingEviCaseNo77777778() {
        // This test case validate the understanding based on which the create
        // office-action api has been developed.
        // ie, while creating an office-action inside a given serial number the
        // incoming association of evidence file has to match
        // the serial number of office-action.
        // In-other words the incoming associated evidence files needs to exists
        // inside the same office-action case number.
        testCreateSampleEviOneForOffActn78();
        testCreateSampleEviTwoForOffActn78();
        testCreateOfficeAction77();
    }

    /**
     * Test create sample evi one for off actn78.
     */
    public void testCreateSampleEviOneForOffActn78() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        urlInput.setFileName("x-search-found.pdf");
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
    }

    /**
     * Test create sample evi two for off actn78.
     */
    public void testCreateSampleEviTwoForOffActn78() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        urlInput.setFileName("my-findings.pdf");
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Test create office action77.
     */
    public void testCreateOfficeAction77() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_OFFACTN_FILE_NAME_TWO);
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), VALUE_OFFICEACTION_METADATA_TWO);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        ClientResponse response = createDocument(offActnParam);
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test create sample evi one for off actn.
     */
    public void testCreateSampleEviOneForOffActn() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("x-search-found.pdf");
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Test create sample evi two for off actn.
     */
    public void testCreateSampleEviTwoForOffActn() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("my-findings.pdf");
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        assertEquals(201, response.getStatus());
    }

    /**
     * Office action create and recreate test for77777777.
     */
    @Test
    public void officeActionCreateAndRecreateTestFor77777777() {
        testCreateSampleEviOneForOffActn();
        testCreateSampleEviTwoForOffActn();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_OFFACTN_FILE_NAME);
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), VALUE_OFFICEACTION_METADATA);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        testCreateOfficeAction(offActnParam);
        testDuplicateOfficeActionCreation(offActnParam);
        testCreateOfficeActionWithDifferentNewName();
    }

    /**
     * Test create office action.
     *
     * @param offActnParam
     *            the off actn param
     */
    public void testCreateOfficeAction(Map<String, String> offActnParam) {
        ClientResponse response = createDocument(offActnParam);

        String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));

        // check for valid docuemnt ID
        String validDocumentID = "/" + OfficeAction.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    /**
     * Test duplicate office action creation.
     *
     * @param offActnParam
     *            the off actn param
     */
    public void testDuplicateOfficeActionCreation(Map<String, String> offActnParam) {

        ClientResponse response = createDocument(offActnParam);
        assertEquals(409, response.getStatus());
    }

    /**
     * Test create office action with different new name.
     */
    private void testCreateOfficeActionWithDifferentNewName() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_OFFACTN_FILE_NAME_THREE);
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), VALUE_OFFICEACTION_METADATA);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        ClientResponse response = createDocument(offActnParam);
        assertEquals(201, response.getStatus());
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        String haystack = response.getEntity(String.class);
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));
    }

    /**
     * Office action create with note type as association.
     */
    @Test
    public void officeActionCreateWithNoteTypeAsAssociation() {
        testCreateSampleNoteForOffActn();
        testCreateOfficeActionWithNoteAssoc();
    }

    /**
     * Test create sample note for off actn.
     */
    public void testCreateSampleNoteForOffActn() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("NoteForOffActnAssoc.pdf");
        String NOTE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> noteParam = new HashMap<String, String>();
        noteParam.put(ParameterKeys.URL.toString(), NOTE_CREATE_WEBSCRIPT_URL);
        noteParam.put(ParameterKeys.METADATA.toString(), NoteBaseTest.VALUE_NOTE_METADATA);
        noteParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        ClientResponse response = createDocument(noteParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
    }

    /**
     * Test create office action with note assoc.
     */
    public void testCreateOfficeActionWithNoteAssoc() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_OFFACTN_FILE_NAME_TWO);
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), VALUE_OFFICEACTION_METADATA_THREE);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        ClientResponse response = createDocument(offActnParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        // assertEquals(403, response.getStatus());
        // After New Framework changes its returning 400. We can change to 403
        // if this is an issue.
        assertEquals(400, response.getStatus());
    }

    /**
     * Test create office action with defective evidence case document id.
     */
    @Test
    public void testCreateOfficeActionWithDefectiveEvidenceCaseDocumentId() {
        String DEFECTIVE_OFFICEACTION_METADATA = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"evidenceList\": [         {             \"documentId\": \"/case//evidence/x-search-found.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }         }     ] }";
        // create office-actionn file with evidence association.
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        urlInputTwo.setFileName(VALUE_OFFACTN_FILE_NAME);
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), DEFECTIVE_OFFICEACTION_METADATA);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        ClientResponse responseTwo = createDocument(offActnParam);
        // String str = responseTwo.getEntity(String.class);
        // System.out.println(str);
        assertEquals(400, responseTwo.getStatus());
    }

}
