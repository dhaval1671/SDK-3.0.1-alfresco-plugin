package alf.tmcontent.ais.ticrsdocument;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TicrsDocumentBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.TicrsRelatedUrl;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.model.TicrsDocument;
import junitx.framework.ListAssert;

/**
 * Retrieve TicrsDocument Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class TiffJpegRetrieveTicrsDocumentContentTests extends TicrsDocumentBaseTest {

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

    @Test
    public void testTiffContentAsPng() {
        //create tiff ticrsdocument
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setDocType(TicrsDocument.TYPE);
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("ticrsDocumentOne.tif");
        Map<String, String> ticrsDocumentParam = new HashMap<String, String>();
        ticrsDocumentParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TicrsDocumentBaseTest.CONTENT_TICRS_DOCUMENT_TIF);        
        ticrsDocumentCreateAndRecreateTest(urlInput, ticrsDocumentParam);
        String TICRS_DOCUMENT_RETRIEVE_CONTENT_WEBSCRIPT_URL = TicrsRelatedUrl.retrieveContentTicrsDocumentTifJpegUrl(urlInput);
        WebResource webResource = client.resource(TICRS_DOCUMENT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("31 KB", fileSizeFrResponse);
        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> first = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        second.add("image/png;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);
    }

    @Test
    public void testJpegContentAsPng() {
        //create jpg ticrsdocument
        UrlInputDto urlInput = new UrlInputDto();
        urlInput.setDocType(TicrsDocument.TYPE);
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("ticrssDocumentTwo.jpeg");
        Map<String, String> ticrsDocumentParam = new HashMap<String, String>();
        ticrsDocumentParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TicrsDocumentBaseTest.CONTENT_TICRS_DOCUMENT_JPG);        
        ticrsDocumentCreateAndRecreateTest(urlInput, ticrsDocumentParam);        
        String TICRS_DOCUMENT_RETRIEVE_CONTENT_WEBSCRIPT_URL = TicrsRelatedUrl.retrieveContentTicrsDocumentTifJpegUrl(urlInput);
        WebResource webResource = client.resource(TICRS_DOCUMENT_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        
        Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
        presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "14.7 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "181 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", "147 KB");        
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", "147 KB");         
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSizeFrResponse);        
        
        MultivaluedMap<String,String> map = response.getHeaders();
        List<String> first = map.get("Content-Type");
        List<String> second = new ArrayList<String>();
        second.add("image/png;charset=UTF-8");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);        
    }  
    
    private void ticrsDocumentCreateAndRecreateTest(UrlInputDto urlInput,  Map<String, String> ticrsDocumentParam){
        String SPECIMEN_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(SPECIMEN_CREATE_WEBSCRIPT_URL);
        ticrsDocumentParam.put(ParameterKeys.URL.toString(),SPECIMEN_CREATE_WEBSCRIPT_URL);
        ticrsDocumentParam.put(ParameterKeys.METADATA.toString(), VALUE_TICRS_DOCUMENT_METADATA);
        ClientResponse response = createDocument(ticrsDocumentParam);       
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        //check for valid docuemnt ID
        String validDocumentID = "/"+ TicrsDocument.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));  
    }

}
