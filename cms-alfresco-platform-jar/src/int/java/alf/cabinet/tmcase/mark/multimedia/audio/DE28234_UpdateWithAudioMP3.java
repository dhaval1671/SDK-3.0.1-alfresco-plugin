package alf.cabinet.tmcase.mark.multimedia.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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

import alf.integration.service.all.base.AudioMarkBaseTest;
import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * The Class CreateMultimediaMarkTest.
 *
 * @author stank
 */

public class DE28234_UpdateWithAudioMP3 extends AudioMarkBaseTest {

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        //System.out.println("setting up CreateMultimediaMarkTest.setup()");
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
     * Multimedia create and recreate test.
     */
    @Test
    public void audioMarkCreateAndRecreateTest(){
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("AudioMarkToBeUpdatedWithMPEGFile");
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_MMRK_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AudioMarkBaseTest.CONTENT_AUDIO_MRK_WAV_90KB);
        //mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AudioMarkBaseTest.CONTENT_AUDIO_MRK_MP3_434KB);
        testCreateAudioMark(mulMarkParam);
        retrieveAndCheckAudioMarkCreation(urlInput);
        tryToUpdateAudioMark(urlInput);
        retrieveAndCheckAudioMarkAfterUpdation(urlInput);
    }      

    private void retrieveAndCheckAudioMarkCreation(UrlInputDto urlInput) {
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
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "90 KB");
        hm.put("localhost:8080", "8 KB");
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "8 KB"); 
        hm.put("tmng-alfupgrade.sit.uspto.gov", "8 KB"); 
        hm.put("tm-alf-7.sit.uspto.gov:8080", "8 KB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "8 KB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "8 KB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "8 KB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "8 KB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "8 KB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "8 KB"); 
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "8 KB");
        
        verifyIfRetriveContentIsAccurate(hm, fileSize);
    }
    
    /**
     * Test create multimedia mark.
     *
     * @param mulMarkParam the mul mark param
     */
    private void testCreateAudioMark(Map<String, String> mulMarkParam) {
        ClientResponse response = createDocument(mulMarkParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
        //check for documentId url
        String docIdMultimediaPostFix = "\"documentId\":\"/case/(.*?)/mark/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdMultimediaPostFix));          
    }

    private void tryToUpdateAudioMark(UrlInputDto urlInput) {
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInput); 
        //System.out.println(MARK_UPDATE_WEBSCRIPT_URL);
        Map<String, String> mulMarkParam = new HashMap<String, String>();
                
        mulMarkParam.put(ParameterKeys.URL.toString(), MARK_UPDATE_WEBSCRIPT_URL);
        String VALUE_MRK_METADATA_ONE = "{\"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AudioMarkBaseTest.CONTENT_AUDIO_MRK_MP3_434KB); 
        ClientResponse response = CentralBase.updateDocument(mulMarkParam);        
        //String haystack = response.getEntity(String.class);
        //System.out.println(haystack);             
        assertEquals(200, response.getStatus());       
    }

    private void retrieveAndCheckAudioMarkAfterUpdation(UrlInputDto urlInput) {
        urlInput.setAccessLevel("internal");
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
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "434 KB");
        hm.put("localhost:8080", "433 KB");
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "433 KB"); 
        hm.put("tmng-alfupgrade.sit.uspto.gov", "433 KB"); 
        hm.put("tm-alf-7.sit.uspto.gov:8080", "433 KB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "433 KB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "433 KB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "433 KB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "433 KB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "433 KB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "433 KB"); 
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "433 KB");
        
        verifyIfRetriveContentIsAccurate(hm, fileSize);        
    }  

  

}
