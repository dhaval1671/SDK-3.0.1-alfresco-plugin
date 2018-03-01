package alf.cabinet.tmcase.generic.mark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
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
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.GenericMarkBaseTest;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;
import junitx.framework.ListAssert;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class GenericMarkRetrieveContentTests extends GenericMarkBaseTest {

    private static final String FILESIZE_31_KB = "31 KB";
    private static final String FILESIZE_147_KB = "147 KB";
    /** The log. */
    private static Logger LOG = Logger.getLogger(GenericMarkRetrieveContentTests.class);    
    
    /**
     * Sets the up.
     */
    @Before
    public void initialise() throws InterruptedException {
        Thread.sleep(2000);
    }
    
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
        /* method known to be empty */
    }

    /**
     * Test retrieve mark content.
     */
    @Test
    public void testRetrieveMarkContent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(GENERIC_MRK_FILE_NAME);
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        // System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        // System.out.println(fileSize);
        assertEquals(200, response.getStatus());
        // This is Generic_1_Mark.bmp -> 1_Mark1.0.png file being retrieved ie why 3 KB
        //Original file size 17 KB -> converted to PNG file size 31 KB
        //assertEquals("31 KB", fileSizeFrResponse);
        
        Map<String,String> hm = new HashMap<String,String>();
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "596 KB");
        hm.put("localhost:8080", FILESIZE_31_KB);
        hm.put("tmng-alf-1.dev.uspto.gov:8080", FILESIZE_31_KB);
        hm.put("tmng-alfupgrade.sit.uspto.gov", FILESIZE_31_KB); //[sometimes this file is coming back as 3 KB]
        hm.put("tm-alf-7.sit.uspto.gov:8080", FILESIZE_31_KB); //[sometimes this file is coming back as 3 KB]
        hm.put("tm-alf-8.sit.uspto.gov:8080", FILESIZE_31_KB); //[sometimes this file is coming back as 3 KB]
        hm.put("tmng-alfupgrade.fqt.uspto.gov", FILESIZE_31_KB);
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", FILESIZE_31_KB);
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", FILESIZE_31_KB);
        hm.put("tmng-alfupgrade.pvt.uspto.gov", FILESIZE_31_KB);
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", FILESIZE_31_KB); 
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", FILESIZE_31_KB);
        
        verifyIfRetriveContentIsAccurate(hm, fileSizeFrResponse);        
        
        
        MultivaluedMap<String, String> map = response.getHeaders();
        List<String> first = map.get("Cache-Control");
        List<String> second = new ArrayList<String>();
        second.add("no-cache, no-store, must-revalidate");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);

    }



    /**
     * Test retrieve mark content for invalid serial number.
     */
    @Test
    public void testRetrieveMarkContentForInvalidSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString());
        urlInput.setFileName(GENERIC_MRK_FILE_NAME);
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve mark content with valid version.
     */
    @Test
    public void testRetrieveMarkContentWithValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(GENERIC_MRK_FILE_NAME);
        urlInput.setVersion(VALUE_VERSION_VALID);
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), urlInput.getVersion());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve mark content with in valid version.
     */
    @Test
    public void testRetrieveMarkContentWithInValidVersion() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(GENERIC_MRK_FILE_NAME);
        urlInput.setVersion(VALUE_VERSION_INVALID);
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), urlInput.getVersion());
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Retrieve mark with valid version.
     */
    @Test
    public void retrieveMarkWithValidVersion(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("MarkFileForVersionTest.jpg");
        urlInput.setVersion("1.0");
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);        
        createSampleMark(MARK_CREATE_WEBSCRIPT_URL, urlInput);        
    }

    /**
     * Creates the sample mark.
     *
     * @param MARK_CREATE_WEBSCRIPT_URL the mark create webscript url
     * @param urlInput the url input
     */
    private void createSampleMark(String MARK_CREATE_WEBSCRIPT_URL, UrlInputDto urlInput) {
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);        
        
        ClientResponse responseOne = createDocument(mrkParam);
        String strOne = responseOne.getEntity(String.class);
        //System.out.println(strOne);   
    
        assertEquals(201, responseOne.getStatus());        
        String haystackOne = strOne;
        String needleOne = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystackOne, needleOne));
    
        //check for 'mark' type in documentId url
        String validDocumentIDOne = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystackOne, validDocumentIDOne));

        // ### retrieve mark version 1.0 content
        retrieveMarkAndVerifyItsVersion(urlInput);        
    }

    /**
     * Retrieve mark and verify its version.
     *
     * @param urlInput the url input
     */
    private void retrieveMarkAndVerifyItsVersion(UrlInputDto urlInput) {
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        //System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResourceOne = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        Builder bOne = webResourceOne.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        //bOne = bOne.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseTwo = bOne.get(ClientResponse.class);
        //String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(strTwo);  
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(responseTwo.getLength());
        // System.out.println(fileSize);
        assertEquals(200, responseTwo.getStatus());
        //Original file size 14 KB -> converted to PNG file size 181 KB
        Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
        presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "14.7 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "181 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", FILESIZE_147_KB);        
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", FILESIZE_147_KB);
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", FILESIZE_147_KB);         
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSizeFrResponse);
        // ### update mark content to version 1.1
        updateMarkToVersionOneDotOne(urlInput);
        // ### retrieve and verify mark content to version 1.1
        retrieveMarkAndVerifyContentOfVersionOneDotOne(urlInput);         
    }
    
    /**
     * Update mark to version one dot one.
     *
     * @param urlInput the url input
     */
    private void updateMarkToVersionOneDotOne(UrlInputDto urlInput) {
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInput);         
    
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(MarkBaseTest.CONTENT_MRK_TIF);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE));
        WebResource webResourceTwo = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResourceTwo.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseThree = b.post(ClientResponse.class, multiPart);
        assertEquals(200, responseThree.getStatus());
        String haystackTwo = responseThree.getEntity(String.class);
        //System.out.println(haystackTwo);        
        String needleTwo = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystackTwo, needleTwo));
        //check for 'mark' type in documentId url
        String validDocumentIDTwo = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystackTwo, validDocumentIDTwo));

    }

    /**
     * Retrieve mark and verify content of version one dot one.
     *
     * @param urlInput the url input
     */
    private void retrieveMarkAndVerifyContentOfVersionOneDotOne(UrlInputDto urlInput) {
        urlInput.setVersion("1.1");
        String MARK_CRONTENT_WEBSCRIPT_URL_Three = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        //System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResourceOneThree = client.resource(MARK_CRONTENT_WEBSCRIPT_URL_Three);
        Builder bThree = webResourceOneThree.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        bThree = bThree.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseFour = bThree.get(ClientResponse.class);
        //String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(strTwo);  
        String fileSizeFrResponseTwo = FileUtils.byteCountToDisplaySize(responseFour.getLength());
        // System.out.println(fileSize);
        assertEquals(200, responseFour.getStatus());
        //Original file size 17 KB -> converted to PNG file size 31 KB
        assertEquals(FILESIZE_31_KB, fileSizeFrResponseTwo);
    }    
}
