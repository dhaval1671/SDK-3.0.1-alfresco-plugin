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
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;

/**
 * The Class RetrieveCaseDocumentMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class OneB_RetrieveProceedingNumberDocMetadataTests extends CentralBase {

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
     * Test retrieve case document metadata.
     */
    @Test
    public void testRetrieveCaseDocumentMetadata() {
        LegalProceedingUrlInputDto lpUrlInput = new LegalProceedingUrlInputDto();
        lpUrlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777779_NUMBER.toString());
        createLegalProceedingDocumentForRetrieval(lpUrlInput);
        List<OneB_FilterKeyValueDto> testDto = getTestDto(lpUrlInput);        
        for(OneB_FilterKeyValueDto fkvd:testDto){
            WebResource webResource = client.resource(fkvd.getUrl());
            validateRequest(webResource, fkvd); //failing needs to be fixed [for SIT 5.1 Jul/17/2017]
        }
    }

    private String validateRequest(WebResource webResource, OneB_FilterKeyValueDto fkvd) {
        //System.out.println("----------START------------");
        //System.out.println("URL:: " + fkvd.getUrl());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse clientResponse = b.get(ClientResponse.class);
        String haystack = clientResponse.getEntity(String.class);
        //System.out.println(haystack);
        //System.out.println("Expecting Server response code::" + fkvd.getResponseCode() + " ### " + "Actual Server response code::" + clientResponse.getStatus());
        assertEquals(fkvd.getResponseCode(), clientResponse.getStatus());
        
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
		    //System.out.println("Expected document count::" + fkvd.getDocumentCount() + " ### " + "Actual document count recieved::" + documentList.size());
			assertEquals(fkvd.getDocumentCount(), documentList.size());
		}
        //System.out.println("----------END------------");
        return haystack;
	}
    
    private static List<OneB_FilterKeyValueDto> getTestDto(LegalProceedingUrlInputDto urlInput) {
        String WEBSCRIPT_URL = LegalProceedingUrl.retrieveAllProceedingNumberDocMetadata(urlInput);
        List<OneB_FilterKeyValueDto> urls = new ArrayList<OneB_FilterKeyValueDto>();
        int enumLength = OneB_FilterKeyValueCombinations.values().length;
        OneB_FilterKeyValueCombinations[] arry = OneB_FilterKeyValueCombinations.values();
        for (int i = 1; i<=enumLength; i++) {
            StringBuffer sb = new StringBuffer(WEBSCRIPT_URL);  
            if(arry[i-1].getFilterKey() == null){
                if(arry[i-1].getFilterValue() == null){
                    if(arry[i-1].getAccessLevel() == null){
                       //nothing identified to do here, so far.
                    }else{
                        sb.append("?"+OneB_FilterKeyValueCombinations.ACCESS_LEVEL+"="+arry[i-1].getAccessLevel());
                    }
                    
                }else{
                    sb.append("?"+OneB_FilterKeyValueCombinations.FILTER_VALUE+"="+arry[i-1].getFilterValue());
                    if(arry[i-1].getAccessLevel() == null){
                        //nothing identified to do here, so far.
                    }else{
                        sb.append("&"+OneB_FilterKeyValueCombinations.ACCESS_LEVEL+"="+arry[i-1].getAccessLevel());
                    }
                }
            }else{
                sb.append("?"+OneB_FilterKeyValueCombinations.FILTER_KEY+"="+arry[i-1].getFilterKey());
                if(arry[i-1].getFilterValue() == null){
                    if(arry[i-1].getAccessLevel() == null){
                        //nothing identified to do here, so far.
                    }else{
                        sb.append("&"+OneB_FilterKeyValueCombinations.ACCESS_LEVEL+"="+arry[i-1].getAccessLevel());
                    }
                }else{
                  sb.append("&"+OneB_FilterKeyValueCombinations.FILTER_VALUE+"="+arry[i-1].getFilterValue()); 
                  if(arry[i-1].getAccessLevel() == null){
                      //nothing identified to do here, so far.
                  }else{
                      sb.append("&"+OneB_FilterKeyValueCombinations.ACCESS_LEVEL+"="+arry[i-1].getAccessLevel());
                  }
                }                
            }
          //System.out.println(i+"       ::" + sb.toString());
          OneB_FilterKeyValueDto fkvd = new OneB_FilterKeyValueDto();
          fkvd.setUrl(sb.toString());
          fkvd.setResponseCode(arry[i-1].getResponseCode());
          fkvd.setDocumentCount(arry[i-1].getDocumentCount());
          urls.add(fkvd);
          }
        return urls;
    }
    
    /**
     * Creates the case document for retrieval.
     * @param urlInput 
     */
    private void createLegalProceedingDocumentForRetrieval(LegalProceedingUrlInputDto urlInput){

        LegalProceedingUrlInputDto urlInputOne = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_BRIEF.getAlfrescoTypeName());
        urlInputOne.setProceedingNumber(urlInput.getProceedingNumber());
        urlInputOne.setFileName(BriefBaseTest.VALUE_BRIEF_FILE_NAME);
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
        urlInputTwo.setProceedingNumber(urlInput.getProceedingNumber());
        urlInputTwo.setFileName(DecisionBaseTest.VALUE_DECISION_FILE_NAME);
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
        urlInputThree.setProceedingNumber(urlInput.getProceedingNumber());
        urlInputThree.setFileName(ExhibitBaseTest.VALUE_EXHIBIT_FILE_NAME);
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

    }    

}
