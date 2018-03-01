package alf.cabinet.legalproceeding.proceedingnumber;

import static org.junit.Assert.assertEquals;

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
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.cabinet.legalproceeding.BriefBaseTest;
import alf.integration.service.all.base.cabinet.legalproceeding.DecisionBaseTest;
import alf.integration.service.all.base.cabinet.legalproceeding.ExhibitBaseTest;
import alf.integration.service.all.base.cabinet.legalproceeding.OrderBaseTest;
import alf.integration.service.all.base.cabinet.legalproceeding.PleadingsBaseTest;
import alf.integration.service.all.base.cabinet.legalproceeding.UndesignatedBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;

/**
 * The Class RetrieveCaseDocumentMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveMetadataFrMultipleLegalProceedingNumberExtendedTests extends CentralBase {

    private static final String UNDESIGNATED_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF = "01_Undesignated_For_RetrieveLPFrMultipleLegalProceedingNosExtendedOne.pdf";
    private static final String PLEADING_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF = "02_Pleading_For_RetrieveLPFrMultipleLegalProceedingNosExtendedTwo.pdf";
    private static final String ORDER_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF = "03_Order_For_RetrieveLPFrMultipleLegalProceedingNosExtendedThree.pdf";
    private static final String DECISION_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF = "06_Decision_For_RetrieveLPFrMultipleLegalProceedingNosExtendedSix.pdf";
    private static final String BRIEF_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF = "07_Brief_For_RetrieveLPFrMultipleLegalProceedingNosExtendedSeven.pdf";
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

    private String[] proceedingNumbersList = {ParameterValues.VALUE_SERIAL_77777783_NUMBER.toString(),ParameterValues.VALUE_SERIAL_77777780_NUMBER.toString(), ParameterValues.VALUE_SERIAL_77777781_NUMBER.toString(), ParameterValues.VALUE_SERIAL_77777782_NUMBER.toString()};
    
    /**
     * Creates the case document for retrieval.
     */
    private void createLegalProceedingDocForRetrieval(){
         
        LegalProceedingUrlInputDto urlInputOne = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_BRIEF.getAlfrescoTypeName());
        urlInputOne.setProceedingNumber(proceedingNumbersList[0]);
        urlInputOne.setFileName(BRIEF_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF);
        String BRIEF_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInputOne);
        //System.out.println(BRIEF_CREATE_WEBSCRIPT_URL);
        String VALUE_BRIEF_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"internal\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> briefDocParam = new HashMap<String, String>();
        briefDocParam.put(ParameterKeys.URL.toString(), BRIEF_CREATE_WEBSCRIPT_URL);
        briefDocParam.put(ParameterKeys.METADATA.toString(), VALUE_BRIEF_METADATA);
        briefDocParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), BriefBaseTest.CONTENT_BRIEF_ATTACHMENT);
        ClientResponse responseOne = createDocument(briefDocParam);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseOne.getStatus());  

        LegalProceedingUrlInputDto urlInputTwo = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_DECISION.getAlfrescoTypeName());
        urlInputTwo.setProceedingNumber(proceedingNumbersList[0]);
        urlInputTwo.setFileName(DECISION_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF);
        String DECISION_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInputTwo);     
        //System.out.println(DECISION_CREATE_WEBSCRIPT_URL);
        String VALUE_DECISION_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"public\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> decisionParam = new HashMap<String, String>();        
        decisionParam.put(ParameterKeys.URL.toString(),DECISION_CREATE_WEBSCRIPT_URL);
        decisionParam.put(ParameterKeys.METADATA.toString(),VALUE_DECISION_METADATA);
        decisionParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), DecisionBaseTest.CONTENT_DECISION_ATTACHMENT);
        ClientResponse responseTwo = createDocument(decisionParam);
        //String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(strTwo);   
        assertEquals(201, responseTwo.getStatus()); 

        LegalProceedingUrlInputDto urlInputThree = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_EXHIBIT.getAlfrescoTypeName());
        urlInputThree.setProceedingNumber(proceedingNumbersList[1]);
        urlInputThree.setFileName("MyLegalProceeding_ThisDoesNOTExists.pdf");
        String EXHIBIT_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInputThree);     
        //System.out.println(EXHIBIT_CREATE_WEBSCRIPT_URL);
        String VALUE_EXHIBIT_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"restricted\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> exhibitParam = new HashMap<String, String>();        
        exhibitParam.put(ParameterKeys.URL.toString(),EXHIBIT_CREATE_WEBSCRIPT_URL);
        exhibitParam.put(ParameterKeys.METADATA.toString(), VALUE_EXHIBIT_METADATA);
        exhibitParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), ExhibitBaseTest.CONTENT_EXHIBIT_ATTACHMENT);
        ClientResponse responseThree = createDocument(exhibitParam);
        //String strThree = responseThree.getEntity(String.class);
        //System.out.println(strThree);   
        assertEquals(201, responseThree.getStatus()); 
        
        LegalProceedingUrlInputDto urlInputFive = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_ORDER.getAlfrescoTypeName());
        urlInputFive.setProceedingNumber(proceedingNumbersList[2]);
        urlInputFive.setFileName(ORDER_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF);
        String ORDER_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInputFive);     
        //System.out.println(ORDER_CREATE_WEBSCRIPT_URL);
        String VALUE_ORDER_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"internal\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> orderParam = new HashMap<String, String>();        
        orderParam.put(ParameterKeys.URL.toString(),ORDER_CREATE_WEBSCRIPT_URL);
        orderParam.put(ParameterKeys.METADATA.toString(), VALUE_ORDER_METADATA);
        orderParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), OrderBaseTest.CONTENT_ORDER_ATTACHMENT);
        ClientResponse responseFive = createDocument(orderParam);
        //String strFive = responseFive.getEntity(String.class);
        //System.out.println(strFive);   
        assertEquals(201, responseFive.getStatus());
        
        LegalProceedingUrlInputDto urlInputSix = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_PLEADING.getAlfrescoTypeName());
        urlInputSix.setProceedingNumber(proceedingNumbersList[2]);
        urlInputSix.setFileName(PLEADING_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF);
        String LEGALPROCEEDING_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInputSix);     
        //System.out.println(LEGALPROCEEDING_CREATE_WEBSCRIPT_URL);
        String VALUE_PLEADING_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"restricted\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> pleadingParam = new HashMap<String, String>();        
        pleadingParam.put(ParameterKeys.URL.toString(),LEGALPROCEEDING_CREATE_WEBSCRIPT_URL);
        pleadingParam.put(ParameterKeys.METADATA.toString(), VALUE_PLEADING_METADATA);
        pleadingParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), PleadingsBaseTest.CONTENT_LEGALPROCEEDING_ATTACHMENT);
        ClientResponse responseSix = createDocument(pleadingParam);
        //String strSix = responseSix.getEntity(String.class);
        //System.out.println(strSix);   
        assertEquals(201, responseSix.getStatus());
        
        LegalProceedingUrlInputDto urlInputSeven = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_UNDESIGNATED.getAlfrescoTypeName());
        urlInputSeven.setProceedingNumber(proceedingNumbersList[3]);
        urlInputSeven.setFileName(UNDESIGNATED_FOR_RETRIEVE_LP_FR_MULTIPLE_LEGAL_PROCEEDING_NOS_PDF);
        String UNDESIGNATED_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInputSeven);     
        //System.out.println(UNDESIGNATED_CREATE_WEBSCRIPT_URL);
        String VALUE_UNDESIGNATED_METADATA = "{  \"documentName\": \"testDoc.pdf\",  \"accessLevel\": \"public\",  \"proceedingType\": \"EXT\",    \"entryDate\": \"2014-04-23T13:42:24.962-04:00\",   \"identifier\": \"1234\",   \"migrationMethod\": \"ttab\",  \"migrationSource\": \"legacy\",    \"effectiveStartDate\": \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> undesignatedParam = new HashMap<String, String>();        
        undesignatedParam.put(ParameterKeys.URL.toString(), UNDESIGNATED_CREATE_WEBSCRIPT_URL);
        undesignatedParam.put(ParameterKeys.METADATA.toString(), VALUE_UNDESIGNATED_METADATA);
        undesignatedParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), UndesignatedBaseTest.CONTENT_UNDESIGNATED_ATTACHMENT);
        ClientResponse responseSeven = createDocument(undesignatedParam);
        //String strSeven = responseSeven.getEntity(String.class);
        //System.out.println(strSeven);   
        assertEquals(201, responseSeven.getStatus());
        
    }

    /**
     * Test retrieve case document metadata.
     */
    @Test
    public void testRetrieveLegalProceedingDocMetadata() {
        //Create the case documents
        createLegalProceedingDocForRetrieval();
        //Retrieve the case documents.
        String WEBSCRIPT_EXT = CentralBase.urlPrefixLegalProceeding + "multiple" + CaseOtherUrl.URL_POSTFIX;
        String urlRetrieveMetadataFrMultipleProceedingNumbers =  ALFRESCO_URL + WEBSCRIPT_EXT;        
        //System.out.println(urlRetrieveMetadataFrMultipleProceedingNumbers);
        
        // Restricted access level
        WebResource webResource = client.resource(urlRetrieveMetadataFrMultipleProceedingNumbers + "?accessLevel=restricted");
        assertAccessLevel(webResource, 13);
        
    }

    private String assertAccessLevel(WebResource webResource, int expectedDocuments) {
        String metadata = "[\"77777783\", \"77777780\", \"77777781\", \"77777782\"]";        
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse clientResponse = b.post(ClientResponse.class, metadata);
        //String strSeven = clientResponse.getEntity(String.class);
        //System.out.println(strSeven);  
        assertEquals(200, clientResponse.getStatus());
        String haystack = clientResponse.getEntity(String.class);
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
