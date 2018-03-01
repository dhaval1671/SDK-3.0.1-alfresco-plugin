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
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * The Class CreateMultimediaMarkTest.
 *
 * @author stank
 */

public class CreateAudioMarkTest extends AudioMarkBaseTest {

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
        urlInput.setFileName(AudioMarkBaseTest.VALUE_AUDIO_MRK_FILE_NAME);
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_MMRK_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AudioMarkBaseTest.CONTENT_AUDIO_MRK_WMA_596KB);
        //mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AudioMarkBaseTest.CONTENT_AUDIO_MRK_MP3_434KB);
        testCreateAudioMark(mulMarkParam);
        retrieveAndCheckAudioMarkCreation(urlInput);
        testDuplicateAudioMarkCreation(mulMarkParam);
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
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "596 KB");
        hm.put("localhost:8080", "1 MB");
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "1 MB"); //[Not getting converted on DEV 5.1, size coming back is 595 KB]
        hm.put("tmng-alfupgrade.sit.uspto.gov", "1 MB"); //[Not getting converted on SIT 5.1, size coming back is 595 KB]
        hm.put("tm-alf-7.sit.uspto.gov:8080", "1 MB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "1 MB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "1 MB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "1 MB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "1 MB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "1 MB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "1 MB"); 
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "1 MB");
        
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

    /**
     * Test duplicate multimedia mark creation.
     *
     * @param mulMarkParam the mul mark param
     */
    private void testDuplicateAudioMarkCreation(Map<String, String> mulMarkParam) {
        ClientResponse response = createDocument(mulMarkParam); 
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(409, response.getStatus());
    }    

  

}
