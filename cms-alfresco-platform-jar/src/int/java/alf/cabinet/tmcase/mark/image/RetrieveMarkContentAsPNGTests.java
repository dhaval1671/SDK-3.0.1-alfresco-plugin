package alf.cabinet.tmcase.mark.image;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.FileUtils;
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
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import junitx.framework.ListAssert;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveMarkContentAsPNGTests extends MarkBaseTest{


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
     * Test retrieve mark content.
     */
    @Test
    public void testRetrieveTifMarkContentAsPNG() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("testRetrieveTifMarkContentAsPNG");
        createTifMark(urlInput);
        retrieveTifMark(urlInput);
    }

    /**  
     * @Title: createTifMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void createTifMark(UrlInputDto urlInput) {
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_TIF);
        ClientResponse response = createDocument(mrkParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, response.getStatus());
    }

    /**  
     * @Title: retrieveTifMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void retrieveTifMark(UrlInputDto urlInput) {
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        //System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);          
        assertEquals(200, response.getStatus());        
        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> responseContentType = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        //Original --> image/tiff;charset=UTF-8
        second.add("image/png;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(unModifiableStringList, responseContentType);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        assertEquals("31 KB", fileSizeFrResponse);
        
    }

    @Test
    public void testRetrieveJpegMarkContentAsPNG() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("testRetrieveJpegMarkContentAsPNG()");
        createJpegMark(urlInput);
        retrieveJpegMark(urlInput);
    }    
    
    /**  
     * @Title: createJpegMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void createJpegMark(UrlInputDto urlInput) {
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        ClientResponse response = createDocument(mrkParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, response.getStatus());
    }

    /**  
     * @Title: retrieveJpegMark  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void retrieveJpegMark(UrlInputDto urlInput) {
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        //System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);          
        assertEquals(200, response.getStatus());    
        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> responseContentType = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        //Original --> image/jpeg;charset=UTF-8
        second.add("image/png;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(unModifiableStringList, responseContentType);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        assertEquals("181 KB", fileSizeFrResponse);
    }

}
