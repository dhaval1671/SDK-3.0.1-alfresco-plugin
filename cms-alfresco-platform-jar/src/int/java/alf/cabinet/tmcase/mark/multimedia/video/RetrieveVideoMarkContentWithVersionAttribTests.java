package alf.cabinet.tmcase.mark.multimedia.video;

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

import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TransformVideoMarkBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import junitx.framework.ListAssert;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveVideoMarkContentWithVersionAttribTests extends VideoMarkBaseTest{

	private static Logger log = Logger.getLogger(RetrieveVideoMarkContentWithVersionAttribTests.class);
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
    public void testRetrieveMmMarkContentWithVersionUrlParam() {
 
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777778_NUMBER.toString());
        urlInput.setFileName("AVXFileSpecimenMultimedia.evi");        
        Zero_V1_0_3MB_transformASFToMP4(urlInput);
        One_V1_1_3MB_transformEvidenceMPGToMP4(urlInput);    	
    	
    }

    /**  
     * @Title: One_transformASFToMP4  
     * @Description:   
     * @return  
     * @return UrlInputDto   
     * @throws  
     */ 
    private void Zero_V1_0_3MB_transformASFToMP4(UrlInputDto urlInput) {
        String VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC = "{  \"accessLevel\": \"restricted\", \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_ASF);        
        ClientResponse response = createDocument(mulMarkParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str);
        //StackTraceElement  ste = Thread.currentThread().getStackTrace()[1];        
        //System.out.print(ste.getMethodName() + " --> " + response.getStatus() + "\n");
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ urlInput.getDocType() +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
        //check for documentId url
        String docIdMultimediaPostFix = "\"documentId\":\"/case/(.*?)/"+urlInput.getDocType()+"/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdMultimediaPostFix));
        //fire retrieve content and check its content size.
        Map<String,String> hm = new HashMap<String,String>();
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "7.17 MB");
        hm.put("4_1_9_localhost:8080", "3 MB");
        hm.put("5_1_localhost:8080", "3 MB");
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "3 MB");
        hm.put("tmng-alfupgrade.sit.uspto.gov", "3 MB");
        hm.put("tm-alf-7.sit.uspto.gov:8080", "3 MB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "3 MB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "3 MB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "3 MB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "3 MB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "3 MB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "3 MB");
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "3 MB");
        testRetrieveMarkContent(urlInput, hm, "1.0");
    }  
    
    /**  
     * @Title: transformEvidenceASFToMPG  
     * @Description:   
     * @param urlInput  
     * @return void   
     * @throws  
     */ 
    private void One_V1_1_3MB_transformEvidenceMPGToMP4(UrlInputDto urlInput) {
        urlInput.setDocType(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        String metadata = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";        
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInput);         
        //System.out.println(MARK_UPDATE_WEBSCRIPT_URL); 
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(TransformVideoMarkBaseTest.CONTENT_MM_MPG);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadata));
        WebResource webResource = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        String str = response.getEntity(String.class);
        //StackTraceElement  ste = Thread.currentThread().getStackTrace()[1];        
        //System.out.print(ste.getMethodName() + " --> " + response.getStatus() + "\n");        
        //System.out.println(str);
        assertEquals(200, response.getStatus());   
        String haystack = str;
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ urlInput.getDocType() +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/"+urlInput.getDocType()+"/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdImagePostFix));     
        //fire retrieve content and check its content size.
        Map<String,String> hm = new HashMap<String,String>();
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "3.57 MB");
        hm.put("4_1_9_localhost:8080", "3 MB");
        hm.put("5_1_localhost:8080", "1 MB");
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "2 MB");
        hm.put("tmng-alfupgrade.sit.uspto.gov", "2 MB");
        hm.put("tm-alf-7.sit.uspto.gov:8080", "2 MB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "2 MB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "2 MB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "2 MB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "2 MB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "2 MB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "2 MB");
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "2 MB");        
        testRetrieveMarkContent(urlInput, hm, "1.1"); //[For SIT 5.1 = 2 MB]         
    }    
   
    private void testRetrieveMarkContent(UrlInputDto urlInput, Map<String, String> presummedSizeOfTheFileOnDifferentServerEnvs, String version) {
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput)+"?"+ "version="+ version +"&accessLevel=restricted";
        //System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //StackTraceElement  ste = Thread.currentThread().getStackTrace()[1];        
        //System.out.print(ste.getMethodName() + " --> File size: " + presummedSizeOfTheFile + " :: " + response.getStatus() + "\n");        
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        //System.out.println(fileSizeFrResponse);
        assertEquals(200, response.getStatus());
        // FirstMultimediaMark.avi  -> FirstMultimediaMark.mp4 file is of size 3.81 MB
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSizeFrResponse);
        MultivaluedMap<String, String> map = response.getHeaders();
        List<String> first = map.get("Cache-Control");
        List<String> second = new ArrayList<String>();
        second.add("no-cache, no-store, must-revalidate");
        List<String> unModifiableStringList = Collections.unmodifiableList(second);
        ListAssert.assertEquals(first, unModifiableStringList);
    }

}
