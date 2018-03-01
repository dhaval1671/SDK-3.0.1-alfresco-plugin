package alf.cabinet.tmcase.attachment;

import static org.junit.Assert.assertEquals;

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
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

import alf.integration.service.all.base.AttachmentBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class DeleteAttachmentTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class DeleteAttachmentTest extends AttachmentBaseTest {

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
     * Attachment delete test.
     */
    @Test
    public void deleteInternalAttachment() {
        String caseId = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        String firstFile = "attachment-one.pdf";

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_ATTACHMENT.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId);
        urlInput.setFileName(firstFile);
        //Create
        String ATTCH_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);           
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), ATTCH_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_ATTACHMENT_METADATA_ACCESS_INTERNAL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AttachmentBaseTest.CONTENT_ATTACHMENT);
        ClientResponse responseCreate = createDocument(eviParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseCreate.getStatus());        

        // Delete
        String ATTCH_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);          
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);
        WebResource webResource = client.resource(ATTCH_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseDelete = b.delete(ClientResponse.class);
        //String str = responseDelete.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, responseDelete.getStatus());
    }
    
    @Test
    public void tryTodeleteAccessLevelPublicAttachment(){
        String caseId = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        String firstFile = "attachment-two.pdf";

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_ATTACHMENT.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId);
        urlInput.setFileName(firstFile);
        // Create
        String ATTCH_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);           
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), ATTCH_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_ATTACHMENT_METADATA_ACCESS_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AttachmentBaseTest.CONTENT_ATTACHMENT);
        ClientResponse responseCreate = createDocument(eviParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseCreate.getStatus());         
        
        //Delete
        String ATTCH_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);
        //System.out.println(ATTCH_DELETE_WEBSCRIPT_URL);
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);
        WebResource webResource = client.resource(ATTCH_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseDelete = b.delete(ClientResponse.class);
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        assertEquals(403, responseDelete.getStatus());      
    }    


   /**
     * Attachment delete test cross folders.
     */
    @Test
    public void tryToDeleteNoteUsingDeleteAttachmentApi() {

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        urlInput.setFileName("attachment-three.pdf");
        
        //Create
        String ATTCH_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);           
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), ATTCH_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_ATTACHMENT_METADATA_ACCESS_INTERNAL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AttachmentBaseTest.CONTENT_ATTACHMENT);
        ClientResponse responseCreate = createDocument(eviParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseCreate.getStatus());        
        
        
        //Delete
        String ATTCH_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);          
        
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(ATTCH_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseDelete = b.delete(ClientResponse.class);
        //String str = responseDelete.getEntity(String.class);
        //System.out.println(str);
        assertEquals(400, responseDelete.getStatus());
    }

/*    *//**
     * Test delete evidence that doesnt exist.
     *//*
    @Test
    public void testDeleteAttachmentThatDoesntExist() {
        String caseId = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);             
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId
                + "/evidence/non-assoc-evi-one1.pdf\"     },     {         \"documentId\": \"/case/"
                + caseId + "/evidence/non-assoc-evi-two1.pdf\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.delete(ClientResponse.class, metadata);

        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    *//**
     * Test delete mark that exist using evidence service.
     *//*
    @Test
    public void testDeleteMarkThatExistUsingAttachmentService() {
        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(caseId77777777);
        urlInputOne.setFileName("Mark_For_Delete.bmp");
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateImageMarkUrl(urlInputOne);
        // System.out.println("Image Mark Creation URL:: " +
        // MARK_CREATE_WEBSCRIPT_URL);
        // Create Mark for deletion
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), MarkBaseTest.VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_GIF);
        ClientResponse responseOne = createDocTypeNewUrlFormat(mrkParam);
        // String strOne = responseOne.getEntity(String.class);
        // System.out.println(strOne);
        assertEquals(201, responseOne.getStatus());

        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(caseId77777777);
        urlInputTwo.setFileName("MultimediaMark_For_Delete.avi");
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.createMultimediaMarkUrl(urlInputTwo);
        // System.out.println("Multimedia mark creation URL:: " +
        // MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(), MultimediaMarkBaseTest.VALUE_MMRK_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MultimediaMarkBaseTest.CONTENT_MM_3GP);
        ClientResponse responseTwo = createDocTypeNewUrlFormat(mulMarkParam);
        // String strTwo = responseTwo.getEntity(String.class);
        // System.out.println(strTwo);
        assertEquals(201, responseTwo.getStatus());

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);         

        String metadata = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                    + "/mark/uspto-image-mark.gif\"     },     {         \"documentId\": \"/case/"
                    + caseId77777777 + "/mark/uspto-multimedia-mark.3gp\"     } ]";
        } else {
            metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                    + "/mark/Mark_For_Delete.bmp\"     },     {         \"documentId\": \"/case/"
                    + caseId77777777 + "/mark/MultimediaMark_For_Delete.avi\"     } ]";
        }
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseThree = b.delete(ClientResponse.class, metadata);
        // String strThree = responseThree.getEntity(String.class);
        // System.out.println(strThree);
        assertEquals(403, responseThree.getStatus());
    }

    *//**
     * Test delete office action that exist using evidence service.
     *//*
    @Test
    public void testDeleteOfficeActionThatExistUsingAttachmentService() {

        Map<String, String> offActnParam = new HashMap<String, String>();
        String OFFACT_WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case
                + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX
                + "OfficeAction_For_Delete.pdf";
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = ALFRESCO_URL + OFFACT_WEBSCRIPT_EXT;
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam
                .put(ParameterKeys.METADATA.toString(), OfficeActionBaseTest.VALUE_OFFICEACTION_METADATA_WITHOUT_EVI_DOC_LIST);
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        ClientResponse responseOne = createDocTypeNewUrlFormat(offActnParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseOne.getStatus());

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);         
        
        String metadata = "[     {         \"documentId\": \"/case/" + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()
                + "/office-action/OfficeAction_For_Delete.pdf\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.delete(ClientResponse.class, metadata);

        // String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(403, response.getStatus());
    }

    *//**
     * Test delete note that exist using evidence service.
     *//*
    @Test
    public void testDeleteNoteThatExistUsingAttachmentService() {

        Map<String, String> noteParam = new HashMap<String, String>();
        String NOTE_WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case
                + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + NoteBaseTest.URL_MIDFIX + "Note_For_Delete.pdf";
        String NOTE_CREATE_WEBSCRIPT_URL = ALFRESCO_URL + NOTE_WEBSCRIPT_EXT;
        noteParam.put(ParameterKeys.URL.toString(), NOTE_CREATE_WEBSCRIPT_URL);
        noteParam.put(ParameterKeys.METADATA.toString(), NoteBaseTest.VALUE_NOTE_METADATA);
        noteParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        ClientResponse responseOne = createDocTypeNewUrlFormat(noteParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseOne.getStatus());

        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);         
        
        String metadata = "[     {         \"documentId\": \"/case/" + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString()
                + "/note/Note_For_Delete.pdf\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.delete(ClientResponse.class, metadata);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(403, responseTwo.getStatus());
    }

    *//**
     * Test delete legacy that exist using evidence service.
     *//*
    @Test
    public void testDeleteLegacyThatExistUsingAttachmentService() {
        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        Map<String, String> legacyParam = new HashMap<String, String>();
        String LGCY_WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case
                + caseId77777777 + LegacyBaseTest.URL_MIDFIX_CREATE
                + "Legacy_For_Delete.pdf";
        String alfUrl = UrlHelper.pointToAlfrescoUrlInsteadOfCmsUrl(ALFRESCO_URL, CODE_LAYER_ON_IP_ADDRESS, BACKEND_ALFRESCO_HOST);
        String LEGACY_CREATE_WEBSCRIPT_URL = alfUrl + LGCY_WEBSCRIPT_EXT;
        legacyParam.put(ParameterKeys.URL.toString(), LEGACY_CREATE_WEBSCRIPT_URL);
        legacyParam.put(ParameterKeys.METADATA.toString(), LegacyBaseTest.VALUE_LGCY_METADATA);
        legacyParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), LegacyBaseTest.CONTENT_LGCY_ATTACHMENT);
        ClientResponse responseOne = createDocTypeNewUrlFormat(legacyParam);
        // String str = responseOne.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, responseOne.getStatus());
        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);           
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                + "/legacy/Legacy_For_Delete.pdf\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.delete(ClientResponse.class, metadata);

        // String str = response.getEntity(String.class);
        // System.out.println(str);

        assertEquals(403, responseTwo.getStatus());
    }

    *//**
     * Attachment delete file_ where file name without extension_ test.
     *//*
    @Test
    public void evidenceDeleteFile_WhereFileNameWithoutExtension_Test() {
        testCreateAttachmentToBeDeleted(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString(), "non-assoc-evi-five");
        testCreateAttachmentToBeDeleted(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString(), "non-assoc-evi-six");
        testDeleteAttachmentWhereFileNameWithoutExtension();
    }

    *//**
     * Test delete evidence where file name without extension.
     *//*
    public void testDeleteAttachmentWhereFileNameWithoutExtension() {

        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInput);        
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                + "/evidence/non-assoc-evi-five\"     },     {         \"documentId\": \"/case/"
                + caseId77777777 + "/evidence/non-assoc-evi-six\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse response = b.delete(ClientResponse.class, metadata);

        // String haystack = response.getEntity(String.class);
        // System.out.println(haystack);
        assertEquals(200, response.getStatus());

    }
    
    *//**
     * Test try to delete evi for which access level is public.
     *//*
    @Test
    public void testTryToDeleteEviForWhichAccessLevelIsPublic(){
        String caseId77777777 = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        String firstFile = "non-assoc-seven-evi.pdf";
        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseId77777777);
        urlInput.setFileName(firstFile);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.createAttachmentUrl(urlInput);           
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AttachmentBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse response = createDocTypeNewUrlFormat(eviParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus()); 
        
        //Now try to delete this evidence
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(caseId77777777);
        String EVI_DELETE_WEBSCRIPT_URL = CaseOtherUrl.deleteAttachmentUrl(urlInputTwo);        
        
        String metadata = "[     {         \"documentId\": \"/case/" + caseId77777777
                + "/evidence/"+firstFile+"\"     } ]";

        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);

        WebResource webResource = client.resource(EVI_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.delete(ClientResponse.class, metadata);

        // String haystack = responseTwo.getEntity(String.class);
        // System.out.println(haystack);
        assertEquals(403, responseTwo.getStatus());        
        
    }
*/
}
