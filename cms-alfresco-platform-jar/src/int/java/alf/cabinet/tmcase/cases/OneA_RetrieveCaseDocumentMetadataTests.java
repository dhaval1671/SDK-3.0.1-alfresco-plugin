package alf.cabinet.tmcase.cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import alf.integration.service.all.base.LegacyBaseTest;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.all.base.NoteBaseTest;
import alf.integration.service.all.base.NoticeBaseTest;
import alf.integration.service.all.base.OfficeActionBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.ReceiptBaseTest;
import alf.integration.service.all.base.ResponseBaseTest;
import alf.integration.service.all.base.SignatureBaseTest;
import alf.integration.service.all.base.SummaryBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class RetrieveCaseDocumentMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class OneA_RetrieveCaseDocumentMetadataTests extends CentralBase {

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
     * Creates the case document for retrieval.
     */
    private void createCaseDocumentForRetrieval(){
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputOne.setFileName(MarkBaseTest.VALUE_MRK_FILE_NAME);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);         

        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), MarkBaseTest.VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParam);
        //String str = responseOne.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, responseOne.getStatus());  

        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputTwo.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);        
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(), VideoMarkBaseTest.VALUE_MMRK_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), VideoMarkBaseTest.CONTENT_MMRK_AVI);  
        ClientResponse responseThree = createDocument(mulMarkParam);        
        //String str = responseThree.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, responseThree.getStatus());  

        UrlInputDto urlInputThree = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputThree.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputThree.setFileName(EvidenceBaseTest.EVIDENCE_FILE_NAME_FOUR);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThree);          
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(), EvidenceBaseTest.VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse responseTwo = createDocument(eviParam);
        //String str = responseTwo.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, responseTwo.getStatus());        

        UrlInputDto urlInputFour = new UrlInputDto(TradeMarkDocumentTypes.TYPE_LEGACY.getAlfrescoTypeName());
        urlInputFour.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputFour.setFileName(LegacyBaseTest.VALUE_LGCY_FILE_NAME);
        String LEGACY_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateLegacyUrl(urlInputFour);          
        Map<String, String> legacyParam = new HashMap<String, String>();
        legacyParam.put(ParameterKeys.URL.toString(), LEGACY_CREATE_WEBSCRIPT_URL);
        legacyParam.put(ParameterKeys.METADATA.toString(), LegacyBaseTest.VALUE_LGCY_METADATA);
        legacyParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), LegacyBaseTest.CONTENT_LGCY_ATTACHMENT);
        ClientResponse responseFour = createDocument(legacyParam);
        //String str = responseFour.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, responseFour.getStatus());

        UrlInputDto urlInputFive = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTE.getAlfrescoTypeName());
        urlInputFive.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputFive.setFileName(NoteBaseTest.VALUE_NOTE_FILE_NAME);
        String NOTE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputFive);         
        Map<String, String> noteParam = new HashMap<String, String>();
        noteParam.put(ParameterKeys.URL.toString(), NOTE_CREATE_WEBSCRIPT_URL);
        noteParam.put(ParameterKeys.METADATA.toString(), NoteBaseTest.VALUE_NOTE_METADATA);
        noteParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        ClientResponse responseFive = createDocument(noteParam);
        //String str = responseFive.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, responseFive.getStatus()); 

        UrlInputDto urlInputSix = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTICE.getAlfrescoTypeName());
        urlInputSix.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputSix.setFileName(NoticeBaseTest.VALUE_NOTICE_FILE_NAME);
        String NOTICE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputSix);         
        Map<String, String> noticeParam = new HashMap<String, String>();        
        noticeParam.put(ParameterKeys.URL.toString(), NOTICE_CREATE_WEBSCRIPT_URL);
        noticeParam.put(ParameterKeys.METADATA.toString(), NoticeBaseTest.VALUE_NOTICE_METADATA);
        noticeParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoticeBaseTest.CONTENT_NOTICE_ATTACHMENT);        
        ClientResponse responseSix = createDocument(noticeParam);        
        //String str = responseSix.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, responseSix.getStatus());

        String VALUE_OFFICEACTION_METADATA = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"evidenceList\": [         {             \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString()+"/evidence/x-search-found.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }},             {                 \"documentId\": \"/case/"+ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString()+"/evidence/my-findings.pdf\",                 \"metadata\": {                     \"accessLevel\": \"public\",                     \"evidenceGroupNames\": [                         \"working\",                         \"sent\"                     ]                 }             }         ]     }";        

        UrlInputDto urlInputSeven = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputSeven.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputSeven.setFileName("x-search-found.pdf");
        String EVI_CREATE_XSEARCH_PDF = CaseCreateUrl.returnGenericCreateUrl(urlInputSeven);         
        Map<String, String> eviParamOne = new HashMap<String, String>();
        eviParamOne.put(ParameterKeys.URL.toString(), EVI_CREATE_XSEARCH_PDF);
        eviParamOne.put(ParameterKeys.METADATA.toString(), OfficeActionBaseTest.VALUE_EVIDENCE_METADATA);        
        eviParamOne.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse responseEviOne = createDocument(eviParamOne);          
        assertEquals(201, responseEviOne.getStatus());        

        UrlInputDto urlInputEight = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputEight.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputEight.setFileName("my-findings.pdf");
        String EVI_CREATE_MYFIND_PDF = CaseCreateUrl.returnGenericCreateUrl(urlInputEight);         
        Map<String, String> eviParamTwo = new HashMap<String, String>();
        eviParamTwo.put(ParameterKeys.URL.toString(), EVI_CREATE_MYFIND_PDF);
        eviParamTwo.put(ParameterKeys.METADATA.toString(),OfficeActionBaseTest.VALUE_EVIDENCE_METADATA);        
        eviParamTwo.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse responseEviTwo = createDocument(eviParamTwo);         
        assertEquals(201, responseEviTwo.getStatus());        

        UrlInputDto urlInputNine = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInputNine.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputNine.setFileName(OfficeActionBaseTest.VALUE_OFFACTN_FILE_NAME);
        String OFFICEACTION_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputNine);         
        Map<String, String> offActnParam = new HashMap<String, String>();
        offActnParam.put(ParameterKeys.URL.toString(), OFFICEACTION_CREATE_WEBSCRIPT_URL);
        offActnParam.put(ParameterKeys.METADATA.toString(), VALUE_OFFICEACTION_METADATA); 
        offActnParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OfficeActionBaseTest.CONTENT_OFFICEACTION_ATTACHMENT);
        ClientResponse responseSeven = createDocument(offActnParam);
        //String str = responseSeven.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, responseSeven.getStatus());

        UrlInputDto urlInputTen = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SUMMARY.getAlfrescoTypeName());
        urlInputTen.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputTen.setFileName(SummaryBaseTest.VALUE_SUMMARY_FILE_NAME);
        String SUMMARY_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputTen);        
        Map<String, String> summaryParam = new HashMap<String, String>();
        summaryParam.put(ParameterKeys.URL.toString(), SUMMARY_CREATE_WEBSCRIPT_URL);
        summaryParam.put(ParameterKeys.METADATA.toString(), SummaryBaseTest.VALUE_SUMMARY_METADATA);
        summaryParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SummaryBaseTest.CONTENT_SUMMARY_ATTACHMENT); 
        ClientResponse responseEight = createDocument(summaryParam);            
        //String str = responseEight.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, responseEight.getStatus()); 

        UrlInputDto urlInputEleven = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RESPONSE.getAlfrescoTypeName());
        urlInputEleven.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputEleven.setFileName(ResponseBaseTest.VALUE_RESPONSE_FILE_NAME);
        String RESPONSE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateResponsePut(urlInputEleven);        
        Map<String, String> responseParam = new HashMap<String, String>();
        responseParam.put(ParameterKeys.URL.toString(), RESPONSE_CREATE_WEBSCRIPT_URL);
        responseParam.put(ParameterKeys.METADATA.toString(), ResponseBaseTest.VALUE_RESPONSE_METADATA);
        responseParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ResponseBaseTest.CONTENT_RESPONSE_ATTACHMENT); 
        ClientResponse responseEleven = createDocument(responseParam);            
        //String str = responseEight.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, responseEleven.getStatus());    

        UrlInputDto urlInputTwelve = new UrlInputDto(TradeMarkDocumentTypes.TYPE_RECEIPT.getAlfrescoTypeName());
        urlInputTwelve.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputTwelve.setFileName(ReceiptBaseTest.VALUE_RECEIPT_FILE_NAME);
        String RECEIPT_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputTwelve);        
        Map<String, String> receiptParam = new HashMap<String, String>();
        receiptParam.put(ParameterKeys.URL.toString(), RECEIPT_CREATE_WEBSCRIPT_URL);
        receiptParam.put(ParameterKeys.METADATA.toString(), ReceiptBaseTest.VALUE_RECEIPT_METADATA);
        receiptParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ReceiptBaseTest.CONTENT_RECEIPT_ATTACHMENT); 
        ClientResponse responseTwelve = createDocument(receiptParam);            
        //String str = responseTwelve.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, responseTwelve.getStatus());    
        
        UrlInputDto urlInputThirteen = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInputThirteen.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputThirteen.setFileName(SignatureBaseTest.VALUE_SIGNATURE_FILE_NAME);
        String SIGNATURE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThirteen); 
        Map<String, String> signatureParam = new HashMap<String, String>();
        signatureParam.put(ParameterKeys.URL.toString(), SIGNATURE_CREATE_WEBSCRIPT_URL);
        signatureParam.put(ParameterKeys.METADATA.toString(), SignatureBaseTest.VALUE_SIGNATURE_METADATA);
        signatureParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SignatureBaseTest.CONTENT_SIGNATURE_ATTACHMENT); 
        ClientResponse responseThirteen = createDocument(signatureParam);            
        //String str = responseThirteen.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, responseThirteen.getStatus());        
        
        
        // Create a public document
        urlInputThirteen = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInputThirteen.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputThirteen.setFileName("P-" + SignatureBaseTest.VALUE_SIGNATURE_FILE_NAME);
        SIGNATURE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThirteen); 
        signatureParam = new HashMap<String, String>();
        signatureParam.put(ParameterKeys.URL.toString(), SIGNATURE_CREATE_WEBSCRIPT_URL);
        signatureParam.put(ParameterKeys.METADATA.toString(), SignatureBaseTest.VALUE_SIGNATURE_METADATA);
        signatureParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SignatureBaseTest.CONTENT_SIGNATURE_ATTACHMENT); 
        responseThirteen = createDocument(signatureParam);            
        assertEquals(201, responseThirteen.getStatus()); 
        
        // Create an internal document
        urlInputThirteen = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInputThirteen.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputThirteen.setFileName("I-" + SignatureBaseTest.VALUE_SIGNATURE_FILE_NAME);
        SIGNATURE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThirteen); 
        signatureParam = new HashMap<String, String>();
        signatureParam.put(ParameterKeys.URL.toString(), SIGNATURE_CREATE_WEBSCRIPT_URL);
        signatureParam.put(ParameterKeys.METADATA.toString(), SignatureBaseTest.VALUE_SIGNATURE_METADATA_INTERNAL);
        signatureParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SignatureBaseTest.CONTENT_SIGNATURE_ATTACHMENT); 
        responseThirteen = createDocument(signatureParam);            
        assertEquals(201, responseThirteen.getStatus()); 
        
        // Create a restricted document
        urlInputThirteen = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInputThirteen.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputThirteen.setFileName("R-" + SignatureBaseTest.VALUE_SIGNATURE_FILE_NAME);
        SIGNATURE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThirteen); 
        signatureParam = new HashMap<String, String>();
        signatureParam.put(ParameterKeys.URL.toString(), SIGNATURE_CREATE_WEBSCRIPT_URL);
        signatureParam.put(ParameterKeys.METADATA.toString(), SignatureBaseTest.VALUE_SIGNATURE_METADATA_RESTRICTED);
        signatureParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SignatureBaseTest.CONTENT_SIGNATURE_ATTACHMENT);
        responseThirteen = createDocument(signatureParam);            
        assertEquals(201, responseThirteen.getStatus()); 
        
        // Create a restricted document
        urlInputThirteen = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInputThirteen.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputThirteen.setFileName("NO-" + SignatureBaseTest.VALUE_SIGNATURE_FILE_NAME);
        SIGNATURE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThirteen); 
        signatureParam = new HashMap<String, String>();
        signatureParam.put(ParameterKeys.URL.toString(), SIGNATURE_CREATE_WEBSCRIPT_URL);
        signatureParam.put(ParameterKeys.METADATA.toString(), SignatureBaseTest.VALUE_SIGNATURE_METADATA_NO_AL);
        signatureParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SignatureBaseTest.CONTENT_SIGNATURE_ATTACHMENT);
        responseThirteen = createDocument(signatureParam);            
        assertEquals(201, responseThirteen.getStatus()); 
        
        // Create a restricted document
        urlInputThirteen = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInputThirteen.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        urlInputThirteen.setFileName("WRONG-" + SignatureBaseTest.VALUE_SIGNATURE_FILE_NAME);
        SIGNATURE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputThirteen); 
        signatureParam = new HashMap<String, String>();
        signatureParam.put(ParameterKeys.URL.toString(), SIGNATURE_CREATE_WEBSCRIPT_URL);
        signatureParam.put(ParameterKeys.METADATA.toString(), SignatureBaseTest.VALUE_SIGNATURE_METADATA_WRONG_AL);
        signatureParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SignatureBaseTest.CONTENT_SIGNATURE_ATTACHMENT);
        responseThirteen = createDocument(signatureParam);            
        assertEquals(400, responseThirteen.getStatus()); 
        
    }

    /**
     * Test retrieve case document metadata.
     */
    @Test
    public void testRetrieveCaseDocumentMetadata() {
        //Create the case documents
        createCaseDocumentForRetrieval();

        //Retrieve the case documents.
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString());
        String WEBSCRIPT_URL = CaseOtherUrl.retrieveCaseDocumentMetadata(urlInput);         

        // Restricted access level
        WebResource webResource = client.resource(WEBSCRIPT_URL + "?accessLevel=restricted");
        assertAccessLevel(webResource, 17);
        
        // Internal access level
        webResource = client.resource(WEBSCRIPT_URL + "?accessLevel=internal");
        assertAccessLevel(webResource, 16);
        
        // Public access level
        webResource = client.resource(WEBSCRIPT_URL + "?accessLevel=public");
        assertAccessLevel(webResource, 12);
        
        // Wrong access level
        webResource = client.resource(WEBSCRIPT_URL + "?accessLevel=abcd");
        assertAccessLevel(webResource, 12);
        
        // Empty access level
        webResource = client.resource(WEBSCRIPT_URL + "?accessLevel=");
        assertAccessLevel(webResource, 12);
        
        // No Access Level
        webResource = client.resource(WEBSCRIPT_URL);
        String haystack = assertAccessLevel(webResource, 12);
        //System.out.println(haystack); 

        //check for presence of image mark
        String imgMark = "\"documentId\"(.+?)\"/case/(.*?)/mark/(.*?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, imgMark)); 

        //check for presence of evidence
        String evidence = "/case/[0-9]{8}/evidence/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, evidence));

        //check for presence of note
        String note = "/case/[0-9]{8}/note/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, note));  

        //check for presence of notice
        String notice = "/case/[0-9]{8}/notice/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, notice));

        //check for presence of legacy
        String legacy = "/case/[0-9]{8}/legacy/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, legacy));  

        //check for presence of office-action
        String offActn = "/case/[0-9]{8}/office-action/(.+?)";
        assertFalse(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, offActn));   

        //check for presence of summary
        String summary = "/case/[0-9]{8}/summary/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, summary));    

        //check for presence of response
        String response = "/case/[0-9]{8}/response/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, response)); 

        //check for presence of receipt
        String receipt = "/case/[0-9]{8}/receipt/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, receipt));    
        
        //check for presence of signature
        String signature = "/case/[0-9]{8}/signature/(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, signature));     
        
        String needle = "\"documentAlias\"";
        assertTrue(containsStringLiteral( haystack, needle));     
        
    }

    private String assertAccessLevel(WebResource webResource, int expectedDocuments) {
		Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse clientResponse = b.get(ClientResponse.class);
        String haystack = clientResponse.getEntity(String.class);
        assertEquals(200, clientResponse.getStatus());
        
		ObjectMapper mapper = new ObjectMapper();
        List<?> documentList = new ArrayList<Object>();
        try {
			documentList = (List<?>) mapper.readValue(haystack, List.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			assertEquals(expectedDocuments, documentList.size());
		}
        return haystack;
	}    

}
