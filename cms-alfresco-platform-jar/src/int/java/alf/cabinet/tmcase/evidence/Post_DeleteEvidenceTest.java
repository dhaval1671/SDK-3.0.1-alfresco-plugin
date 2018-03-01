package alf.cabinet.tmcase.evidence;

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
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.LegacyBaseTest;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.all.base.NoteBaseTest;
import alf.integration.service.all.base.OfficeActionBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.UrlHelper;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class DeleteEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class Post_DeleteEvidenceTest extends EvidenceBaseTest {

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
     * Evidence delete test.
     */
    @Test
    public void evidenceDeleteTest() {
        String caseId = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        String firstFile = "non-assoc-evi-one.pdf";
        String secondFile = "non-assoc-evi-two.pdf";        
        testCreateEvidenceToBeDeleted(caseId, firstFile);
        testCreateEvidenceToBeDeleted(caseId, secondFile);
        testDeleteEvidence(caseId, firstFile, secondFile);
    }

    /**
     * Test create evidence to be deleted.
     *
     * @param serialNumber
     *            the serial number
     * @param fileName
     *            the file name
     */
    public void testCreateEvidenceToBeDeleted(String serialNumber, String fileName) {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(serialNumber);
        urlInput.setFileName(fileName);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);           
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
    }

    /**
     * Test delete evidence.
     *
     * @param caseId the case id
     * @param firstFile the first file
     * @param secondFile the second file
     */
    private void testDeleteEvidence(String caseId, String firstFile, String secondFile) {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);          
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId
                + "/evidence/"+firstFile+"\"     },     {         \"documentId\": \"/case/"
                + caseId + "/evidence/"+secondFile+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.post(ClientResponse.class, metadata);
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        assertEquals(200, response.getStatus());

        // String haystack = response.getEntity(String.class);
        // String needle = "\"version\":\"1.0\"";
        // assertTrue(contains( haystack, needle));
    }

    /**
     * Evidence delete test cross folders.
     */
    @Test
    public void evidenceDeleteTestCrossFolders() {
        testCreateEvidenceToBeDeleted(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString(), "non-assoc-evi-three.pdf");
        testCreateEvidenceToBeDeleted(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString(), "non-assoc-evi-four.pdf");
        testCreateEvidenceToBeDeleted(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString(), "junkEvi.pdf");
        testDeleteEvidenceCrossFolders();
    }

    /**
     * Test delete evidence cross folders.
     */
    private void testDeleteEvidenceCrossFolders() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);          
        
        String metadata = "[     {         \"documentId\": \"/case/" + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()
                + "/evidence/non-assoc-evi-three.pdf\"     },     {         \"documentId\": \"/case/"
                + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + "/evidence/non-assoc-evi-four.pdf\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        
        //String haystack = response.getEntity(String.class);        
        //System.out.println(haystack);
        
        assertEquals(403, response.getStatus());

        // String haystack = response.getEntity(String.class);
        // String needle = "\"version\":\"1.0\"";
        // assertTrue(contains( haystack, needle));
    }

    /**
     * Test delete evidence that doesnt exist.
     */
    @Test
    public void testDeleteEvidenceThatDoesntExist() {
        String caseId = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);             
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId
                + "/evidence/non-assoc-evi-one1.pdf\"     },     {         \"documentId\": \"/case/"
                + caseId + "/evidence/non-assoc-evi-two1.pdf\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Test delete mark that exist using evidence service.
     */
    @Test
    public void testDeleteMarkThatExistUsingEvidenceService() {
        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(caseId77777777);
        String filePrefix = "Mark_For_Delete";
        String ext = ".jpg";
        String fileName = filePrefix + ext;
        urlInputOne.setFileName(fileName);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);
        // System.out.println("Image Mark Creation URL:: " +
        // MARK_CREATE_WEBSCRIPT_URL);
        // Create Mark for deletion
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), MarkBaseTest.VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParam);
        // String strOne = responseOne.getEntity(String.class);
        // System.out.println(strOne);
        assertEquals(201, responseOne.getStatus());

        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(caseId77777777);
        String filePrefixTwo = "MultimediaMark_For_Delete";
        String extTwo = ".avi";
        String fileName2 = filePrefixTwo + extTwo;
        urlInputTwo.setFileName(fileName2);
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);
        // System.out.println("Multimedia mark creation URL:: " +
        // MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(), VideoMarkBaseTest.VALUE_MMRK_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), VideoMarkBaseTest.CONTENT_MMRK_AVI);
        ClientResponse responseTwo = createDocument(mulMarkParam);
         //String strTwo = responseTwo.getEntity(String.class);
         //System.out.println(strTwo);
        assertEquals(201, responseTwo.getStatus());

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);         

        String metadata = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                    + "/mark/uspto-image-mark"+ext+"\"     },     {         \"documentId\": \"/case/"
                    + caseId77777777 + "/mark/uspto-multimedia-mark"+ extTwo +"\"     } ]";
        } else {
            metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                    + "/mark/"+ fileName +"\"     },     {         \"documentId\": \"/case/"
                    + caseId77777777 + "/mark/"+ fileName2 +"\"     } ]";
        }
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseThree = b.post(ClientResponse.class, metadata);
        // String strThree = responseThree.getEntity(String.class);
        // System.out.println(strThree);
        assertEquals(403, responseThree.getStatus());
    }

    /**
     * Test delete office action that exist using evidence service.
     */
    @Test
    public void testDeleteOfficeActionThatExistUsingEvidenceService() {

        Map<String, String> offActnParam = new HashMap<String, String>();
        String fileName = "OfficeAction_For_Delete.pdf";
        String OFFACT_WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case
                + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX
                + fileName;
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = ALFRESCO_URL + OFFACT_WEBSCRIPT_EXT;
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam
                .put(ParameterKeys.METADATA.toString(), OfficeActionBaseTest.VALUE_OFFICEACTION_METADATA_WITHOUT_EVI_DOC_LIST);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        ClientResponse responseOne = createDocument(offActnParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseOne.getStatus());

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);         
        
        String metadata = "[     {         \"documentId\": \"/case/" + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()
                + "/office-action/"+fileName+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        // String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(403, response.getStatus());
    }

    /**
     * Test delete note that exist using evidence service.
     */
    @Test
    public void testDeleteNoteThatExistUsingEvidenceService() {

        Map<String, String> noteParam = new HashMap<String, String>();
        String fileName = "Note_For_Delete.pdf";
        String NOTE_WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case
                + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + NoteBaseTest.URL_MIDFIX + fileName;
        String NOTE_CREATE_WEBSCRIPT_URL = ALFRESCO_URL + NOTE_WEBSCRIPT_EXT;
        noteParam.put(ParameterKeys.URL.toString(), NOTE_CREATE_WEBSCRIPT_URL);
        noteParam.put(ParameterKeys.METADATA.toString(), NoteBaseTest.VALUE_NOTE_METADATA);
        noteParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        ClientResponse responseOne = createDocument(noteParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseOne.getStatus());

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);         
        
        String metadata = "[     {         \"documentId\": \"/case/" + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()
                + "/note/"+fileName+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.post(ClientResponse.class, metadata);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(403, responseTwo.getStatus());
    }

    /**
     * Test delete legacy that exist using evidence service.
     */
    @Test
    public void testDeleteLegacyThatExistUsingEvidenceService() {
        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        Map<String, String> legacyParam = new HashMap<String, String>();
        String fileName = "Legacy_For_Delete.pdf";
        String LGCY_WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case
                + caseId77777777 + LegacyBaseTest.URL_MIDFIX_CREATE
                + fileName;
        String alfUrl = UrlHelper.pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        String LEGACY_CREATE_WEBSCRIPT_URL = alfUrl + LGCY_WEBSCRIPT_EXT;
        legacyParam.put(ParameterKeys.URL.toString(), LEGACY_CREATE_WEBSCRIPT_URL);
        legacyParam.put(ParameterKeys.METADATA.toString(), LegacyBaseTest.VALUE_LGCY_METADATA);
        legacyParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), LegacyBaseTest.CONTENT_LGCY_ATTACHMENT);
        ClientResponse responseOne = createDocument(legacyParam);
        // String str = responseOne.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseOne.getStatus());
        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);           
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                + "/legacy/"+fileName+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.post(ClientResponse.class, metadata);

        // String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(403, responseTwo.getStatus());
    }

    /**
     * Evidence delete file_ where file name without extension_ test.
     */
    @Test
    public void evidenceDeleteFile_WhereFileNameWithoutExtension_Test() {
        String fileNameFive = "non-assoc-evi-five";
        testCreateEvidenceToBeDeleted(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString(), fileNameFive);
        String fileNameSix = "non-assoc-evi-six";
        testCreateEvidenceToBeDeleted(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString(), fileNameSix);
        testDeleteEvidenceWhereFileNameWithoutExtension(fileNameFive, fileNameSix);
    }

    /**
     * Test delete evidence where file name without extension.
     */
    private void testDeleteEvidenceWhereFileNameWithoutExtension(String fileNameFive, String fileNameSix) {

        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInput);        
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                + "/evidence/"+fileNameFive+"\"     },     {         \"documentId\": \"/case/"
                + caseId77777777 + "/evidence/"+fileNameSix+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.post(ClientResponse.class, metadata);

        // String haystack = response.getEntity(String.class);
        // System.out.println(haystack);
        assertEquals(200, response.getStatus());

    }
    
    /**
     * Test try to delete evi for which access level is public.
     */
    @Test
    public void testTryToDeleteEviForWhichAccessLevelIsPublic(){
        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        String firstFile = "non-assoc-seven-evi.pdf";
        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        urlInput.setFileName(firstFile);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);           
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocument(eviParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus()); 
        
        //Now try to delete this evidence
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.postDeleteEvidenceUrl(urlInputTwo);        
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                + "/evidence/"+firstFile+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.post(ClientResponse.class, metadata);

        //String haystack = responseTwo.getEntity(String.class);
        //System.out.println(haystack);
        assertEquals(403, responseTwo.getStatus());        
        
    }

}
