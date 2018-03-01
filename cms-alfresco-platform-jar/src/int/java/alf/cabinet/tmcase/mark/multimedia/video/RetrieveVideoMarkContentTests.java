package alf.cabinet.tmcase.mark.multimedia.video;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
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

import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveVideoMarkContentTests extends VideoMarkBaseTest{

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
     * Test retrieve mm mark content.
     */
    @Test
    public void testRetrieveMmMarkContent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        //System.out.println(MM_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MM_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);        
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);        
        assertEquals(200, response.getStatus());
        
        
        Map<String,String> hm = new HashMap<String,String>();
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "660 KB");
        hm.put("localhost:8080", "281 KB");
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "302 KB");
        hm.put("tmng-alfupgrade.sit.uspto.gov", "302 KB");
        hm.put("tm-alf-7.sit.uspto.gov:8080", "302 KB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "302 KB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "302 KB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "302 KB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "302 KB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "302 KB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "302 KB"); //PVT Env is NOT coverting this multimedia file and returning size as 660 KB, but why it is NOT converting?
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "302 KB");//PVT Env is NOT coverting this multimedia file and returning size as 660 KB, but why it is NOT converting?
        
        // assertEquals("660 KB", fileSize); // coming 281 KB
        verifyIfRetriveContentIsAccurate(hm, fileSize);
        
    }
    
    /**
     * Test retrieve mm mark content for latest version.
     */
    @Test
    public void testRetrieveMmMarkContentForLatestVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);        
        WebResource webResource = client.resource(MM_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }   

    /**
     * Test retrieve mm mark content for invalid serial number.
     */
    @Test
    public void testRetrieveMmMarkContentForInvalidSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);        
        WebResource webResource = client.resource(MM_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(400, response.getStatus());
    }     

    /**
     * Test retrieve mm mark content with valid version.
     */
    @Test
    public void testRetrieveMmMarkContentWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);          
        //System.out.println(MM_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MM_CONTENT_WEBSCRIPT_URL);
        if(TmnguiBaseTest.runAgainstCMSLayer){
            //Do nothing
        }
        else{
            webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), VALUE_MMRK_FILE_NAME);
            webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_VALID);   
        }        
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
    }    

    /**
     * Test retrieve mm mark content with in valid version.
     */
    @Test
    public void testRetrieveMmMarkContentWithInValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VideoMarkBaseTest.VALUE_MMRK_FILE_NAME);
        String MM_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);          
        //System.out.println(MM_CONTENT_WEBSCRIPT_URL);        
        WebResource webResource = client.resource(MM_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), VALUE_MMRK_FILE_NAME);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_INVALID);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }     

}
