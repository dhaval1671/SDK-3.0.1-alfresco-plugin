package alf.cabinet.tmcase.mark.image.rendition;

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

import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.MarkRenditions;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class RetrieveMarkRenditionsTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class MORMarkRenditionsTests extends MarkBaseTest {

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
     * Test retrieve mark rendition_80_65.
     */
    @Test
    public void testRetrieveMorMarkRendition_80_65_SingleMarkPresent() {
    	
    	//create mark for retrieval
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777787_NUMBER.toString());
        urlInputOne.setFileName("MORFileOne.jpg");
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParam);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseOne.getStatus());
   	
        // retrieve the MOR rendition    	
        urlInputOne.setFileName("mor");
        urlInputOne.setRendition(MarkRenditions.MARK_TINY_80X65.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInputOne);
        //System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        // String strTwo = response.getEntity(String.class);
        // System.out.println(strTwo);
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testRetrieveMorMarkRendition_MultipleMarkPresent_Mrk() {
    	//create mark for retrieval
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777789_NUMBER.toString());
        urlInputOne.setFileName("MORFileOne.jpg");
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParam);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseOne.getStatus());
        
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777789_NUMBER.toString());
        urlInputTwo.setFileName("MORFileTwo.jpg");
        String MARK_CREATE_WEBSCRIPT_URL_TWO = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);       
        Map<String, String> mrkParamTwo = new HashMap<String, String>();
        mrkParamTwo.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL_TWO);
        mrkParamTwo.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParamTwo.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseTwo = createDocument(mrkParamTwo);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseTwo.getStatus()); 
        
        UrlInputDto urlInputThree = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputThree.setSerialNumber(ParameterValues.VALUE_SERIAL_77777789_NUMBER.toString());
        urlInputThree.setFileName("MRK_MORFileTwo.jpg");
        String MARK_CREATE_WEBSCRIPT_URL_THREE = CaseCreateUrl.returnGenericCreateUrl(urlInputThree);       
        Map<String, String> mrkParamThree = new HashMap<String, String>();
        mrkParamThree.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL_THREE);
        mrkParamThree.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParamThree.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_PNG_BANANA);
        ClientResponse responseThree = createDocument(mrkParamThree);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseThree.getStatus());        
   	
        // retrieve the MOR rendition    	
        urlInputThree.setFileName("mor");
        urlInputThree.setRendition(MarkRenditions.MARK_TINY_80X65.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInputThree);
        // System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        // String strTwo = response.getEntity(String.class);
        // System.out.println(strTwo);
        assertEquals(200, response.getStatus());
    }   
    
    @Test
    public void testRetrieveMorMarkRendition_MultipleMarkPresent_Uspto() {
    	//create mark for retrieval
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777790_NUMBER.toString());
        urlInputOne.setFileName("MORFileOne.jpg");
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParam);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseOne.getStatus());
        
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777790_NUMBER.toString());
        urlInputTwo.setFileName("USPTO-IMAGE-MARK.jpg");
        String MARK_CREATE_WEBSCRIPT_URL_TWO = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);       
        Map<String, String> mrkParamTwo = new HashMap<String, String>();
        mrkParamTwo.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL_TWO);
        mrkParamTwo.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParamTwo.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_PNG_BANANA);
        ClientResponse responseTwo = createDocument(mrkParamTwo);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseTwo.getStatus()); 
   	
        // retrieve the MOR rendition    	
        urlInputTwo.setFileName("mor");
        urlInputTwo.setRendition(MarkRenditions.MARK_TINY_80X65.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInputTwo);
         //System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        // String strTwo = response.getEntity(String.class);
        // System.out.println(strTwo);
        assertEquals(200, response.getStatus());
    } 
    
    @Test
    public void testRetrieveMorMarkRendition_MultipleMarkPresent_Mrk_Uspto() {
    	//create mark for retrieval
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777791_NUMBER.toString());
        urlInputOne.setFileName("Mrk_MORFileOne.jpg");
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse responseOne = createDocument(mrkParam);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseOne.getStatus());
        
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777791_NUMBER.toString());
        urlInputTwo.setFileName("USPTO-IMAGE-MARK.jpg");
        String MARK_CREATE_WEBSCRIPT_URL_TWO = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);       
        Map<String, String> mrkParamTwo = new HashMap<String, String>();
        mrkParamTwo.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL_TWO);
        mrkParamTwo.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParamTwo.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_PNG_BANANA);
        ClientResponse responseTwo = createDocument(mrkParamTwo);
        //String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
        assertEquals(201, responseTwo.getStatus()); 
   	
        // retrieve the MOR rendition    	
        urlInputTwo.setFileName("mor");
        urlInputTwo.setRendition(MarkRenditions.MARK_TINY_80X65.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInputTwo);
        // System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        // String strTwo = response.getEntity(String.class);
        // System.out.println(strTwo);
        assertEquals(200, response.getStatus());
    }

	@Test
	public void testRetrieveMorMarkRendition_80_65_MultipleMarkPresent() {
		//create mark for retrieval
	    UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
	    urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777788_NUMBER.toString());
	    urlInputOne.setFileName("MORFileOne.jpg");
	    String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInputOne);       
	    Map<String, String> mrkParam = new HashMap<String, String>();
	    mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
	    mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
	    mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
	    ClientResponse responseOne = createDocument(mrkParam);
	    //String strOne = responseOne.getEntity(String.class);
	    //System.out.println(strOne);   
	    assertEquals(201, responseOne.getStatus());
	    
	    UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
	    urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777788_NUMBER.toString());
	    urlInputTwo.setFileName("MORFileTwo.jpg");
	    String MARK_CREATE_WEBSCRIPT_URL_TWO = CaseCreateUrl.returnGenericCreateUrl(urlInputTwo);       
	    Map<String, String> mrkParamTwo = new HashMap<String, String>();
	    mrkParamTwo.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL_TWO);
	    mrkParamTwo.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
	    mrkParamTwo.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
	    ClientResponse responseTwo = createDocument(mrkParamTwo);
	    //String strOne = responseOne.getEntity(String.class);
	    //System.out.println(strOne);   
	    assertEquals(201, responseTwo.getStatus());        
	
	    // retrieve the MOR rendition    	
	    urlInputTwo.setFileName("mor");
	    urlInputTwo.setRendition(MarkRenditions.MARK_TINY_80X65.getRenditionName());
	    String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInputTwo);
	    // System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
	    WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
	    Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
	    b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
	    ClientResponse response = b.get(ClientResponse.class);
	    // String strTwo = response.getEntity(String.class);
	    // System.out.println(strTwo);
	    assertEquals(404, response.getStatus());
	}    

}
