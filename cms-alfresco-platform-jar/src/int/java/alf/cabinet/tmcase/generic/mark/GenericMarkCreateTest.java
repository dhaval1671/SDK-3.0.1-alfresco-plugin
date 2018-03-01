package alf.cabinet.tmcase.generic.mark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import alf.integration.service.all.base.GenericMarkBaseTest;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * This class test the create of new generic mark type.
 *
 * @author stank
 */

public class GenericMarkCreateTest extends GenericMarkBaseTest {

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {

    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }

    /**
     * Mark create and recreate test.
     */
    @Test
    public void markCreateAndRecreateTest() {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(GENERIC_MRK_FILE_NAME);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        // System.out.println(MARK_CREATE_WEBSCRIPT_URL);
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_PNG);

        testCreateMark(mrkParam);
        testDuplicateMarkCreation(mrkParam);
    }

    /**
     * Test create mark.
     *
     * @param mrkParam
     *            the mrk param
     */
    private void testCreateMark(Map<String, String> mrkParam) {

        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));

        // check for 'mark' type in documentId url
        String validDocumentID = "/" + MarkDoc.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));

    }

    /**
     * Test duplicate mark creation.
     *
     * @param mrkParam
     *            the mrk param
     */
    private void testDuplicateMarkCreation(Map<String, String> mrkParam) {

        ClientResponse response = createDocument(mrkParam);
        assertEquals(409, response.getStatus());
    }

    /**
     * Simulate image mark migration from legacy world.
     */
    @Test
    public void simulateImageMarkMigrationFromLegacyWorld() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(GENERIC_SIM_MRK_FILE_NAME);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), METADATA_SIMULATE_IMAGE_MARK_MIGRATION);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_PNG);
        simulateImageMarkMigrationFromLegacyWorldCreate(mrkParam, urlInput);
    }

    /**
     * Simulate image mark migration from legacy world create.
     *
     * @param mrkParam the mrk param
     * @param urlInput the url input
     */
    private void simulateImageMarkMigrationFromLegacyWorldCreate(Map<String, String> mrkParam, UrlInputDto urlInput) {
        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));
        // check for 'mark' type in documentId url
        String validDocumentID = "/" + MarkDoc.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseTwo = b.get(ClientResponse.class);
        String strTwo = responseTwo.getEntity(String.class);
        // System.out.println(strTwo);
        assertEquals(200, responseTwo.getStatus());
        String haystackTwo = strTwo;
        String needleTwo = "\"documentAlias\":";
        assertTrue(containsStringLiteral(haystackTwo, needleTwo));
        // verify the attribute value documentType for image mark
        String docTypeOfImgMrk = "\"documentType\"(.*?)\"" + MarkDoc.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, docTypeOfImgMrk));
        String imageMarkDocumentName = "\"documentName\"(.*?)\"" + urlInput.getFileName() + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, imageMarkDocumentName));
        String legacyCategory = "\"legacyCategory\"(.*?)\"Migrated\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, legacyCategory));
        String docCode = "\"docCode\"(.*?)\"MRK\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, docCode));
        String migrationMethod = "\"migrationMethod\"(.*?)\"LZL\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, migrationMethod));
        String migrationSource = "\"migrationSource\"(.*?)\"TICRS\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystackTwo, migrationSource));
    }

    /**
     * Creates the pcm metadata mark.
     */
    @Test
    public void createPCMMetadataMark() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(PCM_MRK_FILE_NAME);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), VALUE_MRK_PCM_METADATA);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);

        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        // /System.out.println(str);

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));

        // check for 'mark' type in documentId url
        String validDocumentID = "/" + MarkDoc.TYPE + "/";
        assertTrue(containsStringLiteral(haystack, validDocumentID));

        retrievePCMMarkMetadata(urlInput);
    }

    /**
     * Retrieve pcm mark metadata.
     *
     * @param urlInput the url input
     * @return void
     * @Title: retrievePCMMarkMetadata
     * @Description: 
     */
    private void retrievePCMMarkMetadata(UrlInputDto urlInput) {
        String MARK_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveGenericMarkMetadataUrl(urlInput);
        WebResource webResource = client.resource(MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needle = "\"documentAlias\":";
        assertTrue(containsStringLiteral(haystack, needle));

        // verify the attribute value documentType for image mark
        String docTypeOfImgMrk = "\"documentType\"(.*?)\"" + MarkDoc.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, docTypeOfImgMrk));

        String imageMarkDocumentName = "\"documentName\"(.*?)\"" + urlInput.getFileName() + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, imageMarkDocumentName));

        String pcmABSN = "\"pcmABSN\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, pcmABSN));

        String pcmAFN = "\"pcmAFN\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, pcmAFN));

        String pcmNoteNum = "\"pcmNoteNum\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, pcmNoteNum));

        String pcmSeqNum = "\"pcmSeqNum\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, pcmSeqNum));
    }

}
