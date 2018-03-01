package alf.cabinet.tmcase.cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.NoteBaseTest;
import alf.integration.service.all.base.NoticeBaseTest;
import alf.integration.service.all.base.OfficeActionBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class SearchCaseFoldersTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class SearchCaseFoldersTests extends CentralBase {

    /** The client. */
    public Client client = null;

    /** The Constant CONTENT_EVI_ATTACHMENT. */
    public static final String CONTENT_EVI_ATTACHMENT = "src//test//resources//search//Evidence_For_Search.txt";
    
    /** The Constant CONTENT_NOTE_ATTACHMENT. */
    public static final String CONTENT_NOTE_ATTACHMENT = "src//test//resources//search//Note_For_Search.doc";
    
    /** The Constant CONTENT_NOTICE_ATTACHMENT. */
    public static final String CONTENT_NOTICE_ATTACHMENT = "src//test//resources//search//Notice_For_Search.pdf";
    
    /** The Constant CONTENT_OFFICEACTION_ATTACHMENT. */
    public static final String CONTENT_OFFICEACTION_ATTACHMENT = "src//test//resources//search//OfficeAction_For_Search.pdf";

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
     * Creates the case document for retrieval.
     */
    private void createCaseDocumentForRetrieval(){
        //Create Evidence document
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777781_NUMBER.toString());
        urlInputOne.setFileName("Evidence_For_Search.txt");
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);        

        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), CONTENT_EVI_ATTACHMENT);
        ClientResponse responseTwo = createDocument(eviParam);
        //String strOne = responseTwo.getEntity(String.class);
        //System.out.println(strOne);  
        assertEquals(201, responseTwo.getStatus());        

        //Create Note Document
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777781_NUMBER.toString());
        urlInputTwo.setFileName("Note_For_Search.doc");
        String NOTE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);         
        Map<String, String> noteParam = new HashMap<String, String>();
        noteParam.put(ParameterKeys.URL.toString(), NOTE_CREATE_WEBSCRIPT_URL);
        noteParam.put(ParameterKeys.METADATA.toString(), NoteBaseTest.VALUE_NOTE_METADATA);
        noteParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), CONTENT_NOTE_ATTACHMENT);
        ClientResponse responseFive = createDocument(noteParam);
        //String strTwo = responseFive.getEntity(String.class);
        //System.out.println(strTwo); 
        assertEquals(201, responseFive.getStatus()); 

        //Create Notice Document
        UrlInputDto urlInputThree = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTICE.getAlfrescoTypeName());
        urlInputThree.setSerialNumber(ParameterValues.VALUE_SERIAL_77777781_NUMBER.toString());
        urlInputThree.setFileName("Notice_For_Search.pdf");
        String NOTICE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThree);         
        Map<String, String> noticeParam = new HashMap<String, String>();
        noticeParam.put(ParameterKeys.URL.toString(), NOTICE_CREATE_WEBSCRIPT_URL);
        noticeParam.put(ParameterKeys.METADATA.toString(), NoticeBaseTest.VALUE_NOTICE_METADATA);
        noticeParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), CONTENT_NOTICE_ATTACHMENT);        
        ClientResponse responseSix = createDocument(noticeParam);        
        //String strThree = responseSix.getEntity(String.class);
        //System.out.println(strThree); 
        assertEquals(201, responseSix.getStatus());

        //Create Office-Action
        UrlInputDto urlInputFour = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInputFour.setSerialNumber(ParameterValues.VALUE_SERIAL_77777782_NUMBER.toString());
        urlInputFour.setFileName("OfficeAction_For_Search.pdf");
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputFour);       
        Map<String, String> offActnParam = new HashMap<String, String>();        
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), OfficeActionBaseTest.VALUE_OFFICEACTION_METADATA_WITHOUT_EVI_DOC_LIST); 
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), CONTENT_OFFICEACTION_ATTACHMENT);        
        ClientResponse response = createDocument(offActnParam);
        //String strFour = response.getEntity(String.class);
        //System.out.println(strFour);  
        assertEquals(201, response.getStatus());        

    }
    
    @Test
    public void testCreateSampleDocsForSearchCaseFoldersRestApi() {
        //Create the case documents
        createCaseDocumentForRetrieval();
    }

    /**
     * Test search case folders rest api.
     */
    @Test
    public void testSearchCaseFoldersRestApi() {

        //Create the case documents
        createCaseDocumentForRetrieval();

        //Introducing 20 seconds delay as solr has index the newly created files.
        try {
            Thread.sleep(50000); //15000 milliseconds is fifteen second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //Search top level case folder
        UrlInputDto urlInput = new UrlInputDto();
        String SEARCH_CASE_FOLDER_WEBSCRIPT_URL = CaseOtherUrl.searchAtTopLevelCaseFolder(urlInput);          
        //System.out.println(SEARCH_CASE_FOLDER_WEBSCRIPT_URL);
        String metadata = "{   \"pagination\" : {     \"from\" : 0,      \"size\" : 4     },    \"query\": {     \"match\" : \"GTK2\"   } }";

        WebResource webResource = client.resource(SEARCH_CASE_FOLDER_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(200, response.getStatus());

        String haystack = str;

        String eviDoc = "\"documentId\"(.*?)\"/case/77777781/evidence/Evidence_For_Search.txt\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, eviDoc));     

        String noticeDoc = "\"documentId\"(.*?)\"/case/77777781/notice/Notice_For_Search.pdf\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, noticeDoc)); 

        String officeActnDoc = "\"documentId\"(.*?)\"/case/77777782/office-action/OfficeAction_For_Search.pdf\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, officeActnDoc)); 

        String noteDoc = "\"documentId\"(.*?)\"/case/77777781/note/Note_For_Search.doc\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, noteDoc));  

        String docName = "\"documentName\"";
        assertTrue(containsStringLiteral(haystack, docName));

        String docAlias = "\"documentAlias\"";
        assertTrue(containsStringLiteral(haystack, docAlias));        

        String accessLevel = "\"accessLevel\"";
        assertTrue(containsStringLiteral(haystack, accessLevel)); 

        String mailDate = "\"mailDate\"";
        assertTrue(containsStringLiteral(haystack, mailDate));         

        String mimeType = "\"mimeType\"";
        assertTrue(containsStringLiteral(haystack, mimeType));  
        
        String documentType = "\"documentType\"";
        assertTrue(containsStringLiteral(haystack, documentType));        

    }    

}
