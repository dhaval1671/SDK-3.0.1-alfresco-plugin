package alf.cabinet.tmcase.cases;

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
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.LegacyBaseTest;
import alf.integration.service.all.base.OfficeActionBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class RetrieveCaseDocumentMetadataTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class OneB_RetrieveCaseDocumentMetadataTests extends CentralBase {

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
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777786_NUMBER.toString());
        createCaseDocumentForRetrieval(urlInput);
        List<OneB_FilterKeyValueDto> testDto = getTestDto(urlInput);        
        for(OneB_FilterKeyValueDto fkvd:testDto){
            WebResource webResource = client.resource(fkvd.getUrl());
            validateRequest(webResource, fkvd);
        }
    }

    private String validateRequest(WebResource webResource, OneB_FilterKeyValueDto fkvd) {
        //System.out.println("----------START------------");
        //System.out.println("URL:: " + fkvd.getUrl());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse clientResponse = b.get(ClientResponse.class);
        String haystack = clientResponse.getEntity(String.class);
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
    
    private static List<OneB_FilterKeyValueDto> getTestDto(UrlInputDto urlInput) {
        String WEBSCRIPT_URL = CaseOtherUrl.retrieveCaseDocumentMetadata(urlInput);
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
    private void createCaseDocumentForRetrieval(UrlInputDto urlInput){

        UrlInputDto urlInputFour = new UrlInputDto(TradeMarkDocumentTypes.TYPE_LEGACY.getAlfrescoTypeName());
        urlInputFour.setSerialNumber(urlInput.getSerialNumber());
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

        String VALUE_OFFICEACTION_METADATA = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"evidenceList\": [         {             \"documentId\": \"/case/"+urlInput.getSerialNumber()+"/evidence/x-search-found.pdf\",             \"metadata\": {                 \"accessLevel\": \"public\",                 \"evidenceGroupNames\": [                     \"sent\",                     \"suggested\"                 ]             }},             {                 \"documentId\": \"/case/"+urlInput.getSerialNumber()+"/evidence/my-findings.pdf\",                 \"metadata\": {                     \"accessLevel\": \"public\",                     \"evidenceGroupNames\": [                         \"working\",                         \"sent\"                     ]                 }             }         ]     }";        

        UrlInputDto urlInputSeven = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputSeven.setSerialNumber(urlInput.getSerialNumber());
        urlInputSeven.setFileName("x-search-found.pdf");
        String EVI_CREATE_XSEARCH_PDF = CaseCreateUrl.returnGenericCreateUrl(urlInputSeven);         
        Map<String, String> eviParamOne = new HashMap<String, String>();
        eviParamOne.put(ParameterKeys.URL.toString(), EVI_CREATE_XSEARCH_PDF);
        eviParamOne.put(ParameterKeys.METADATA.toString(), OfficeActionBaseTest.VALUE_EVIDENCE_METADATA);        
        eviParamOne.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse responseEviOne = createDocument(eviParamOne);          
        assertEquals(201, responseEviOne.getStatus());        

        UrlInputDto urlInputEight = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputEight.setSerialNumber(urlInput.getSerialNumber());
        urlInputEight.setFileName("my-findings.pdf");
        String EVI_CREATE_MYFIND_PDF = CaseCreateUrl.returnGenericCreateUrl(urlInputEight);         
        Map<String, String> eviParamTwo = new HashMap<String, String>();
        eviParamTwo.put(ParameterKeys.URL.toString(), EVI_CREATE_MYFIND_PDF);
        eviParamTwo.put(ParameterKeys.METADATA.toString(),OfficeActionBaseTest.VALUE_EVIDENCE_METADATA);        
        eviParamTwo.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        ClientResponse responseEviTwo = createDocument(eviParamTwo);         
        assertEquals(201, responseEviTwo.getStatus());        

        UrlInputDto urlInputNine = new UrlInputDto(TradeMarkDocumentTypes.TYPE_OFFICE_ACTION.getAlfrescoTypeName());
        urlInputNine.setSerialNumber(urlInput.getSerialNumber());
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

    }    

}
