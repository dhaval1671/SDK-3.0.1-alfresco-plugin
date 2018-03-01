package alf.cabinet.tmcase.mark.multimedia.video.transform;

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

import alf.cabinet.tmcase.mark.image.UpdateMarkTest;
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
 * The Class CreateMultimediaMarkTest.
 *
 * @author stank
 */

public class TransformCreateVideoMarkTest extends TransformVideoMarkBaseTest{

    private static Logger log = Logger.getLogger(UpdateMarkTest.class);
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
    public void transformASFToMP4MarkAndRetrieve3MBNewMp4File(){
        String VALUE_MMRK_FILE_NAME = "TransformTestCaseFirstMultimediaMark.avi";
        String VALUE_MMRK_METADATA = "{  \"accessLevel\": \"restricted\", \"loadDate\": \"2000-01-01T17:42:24.962Z\",     \"modifiedByUserId\": \"User XYZ\",     \"documentAlias\": \"nickname\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"documentName\": \"image\",     \"documentCode\": \"MRK\",     \"mailDate\": \"2014-04-23T17:42:24.962Z\", \"scanDate\": \"2014-04-23T17:42:24.962Z\",     \"serialNumber\": \"87654321\",     \"direction\": \"Incoming\",     \"category\": \"Drawing\",     \"migrationProps\": {         \"migrationMethod\": \"lazy-loader\",         \"migrationSource\": \"tcim\"     },     \"isColorMark\": true,     \"isThreeDimensional\": false,     \"isMarkCropped\": true,     \"isMarkAccepted\": true,     \"effectiveStartDate\": \"2014-04-23T17:42:24.962Z\",     \"effectiveEndDate\": null,     \"imageProps\": null,     \"multimediaProps\": {         \"multimediaStartTime\": \"00:04:05.678\",         \"multimediaComment\": \"No comments\",         \"videoCodec\": \"DivX\",         \"videoCompressionType\": \"H.264\",         \"audioCodec\": \"FFmpeg\",         \"audioCompressionType\": \"MPEG-1\",         \"multimediaDuration\": \"01:02:03.123\"     } }";
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MMRK_FILE_NAME);
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_MMRK_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_ASF);        
        testCreateMultimediaMark(mulMarkParam, urlInput);
        testDuplicateMultimediaMarkCreation(mulMarkParam);
        testUpdateMarkContentAndMetadata(urlInput);        
    }      

    /**
     * Test update mark content and metadata.
     */
    private void testUpdateMarkContentAndMetadata(UrlInputDto urlInputOne) {
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";        
        ClientResponse response = updateMarkContentAndMetadata(urlInputOne, VALUE_MRK_METADATA_ONE);
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMarkContentAndMetadata:: \n" + haystack);        
        String needle = "\"version\":\"1.1\"";
        
        assertTrue(containsStringLiteral( haystack, needle));
    
        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ urlInputOne.getDocType() +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
    
        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/"+urlInputOne.getDocType()+"/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdImagePostFix));     
        
        //fire retrieve content and check its content size.
        //[For localhost=281 KB][For SIT 5.1=302 KB] 
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
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "302 KB");//PVT NOT converting this file returing 3 MB.
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "302 KB");         
        testRetrieveMarkContent(urlInputOne, hm);        
    
    }

    private ClientResponse updateMarkContentAndMetadata(UrlInputDto urlInputOne, String metadata) {
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);         
        //System.out.println(MARK_UPDATE_WEBSCRIPT_URL); 
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(TransformVideoMarkBaseTest.CONTENT_MM_AVI);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadata));
        WebResource webResource = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        //Do NOT enable below lines as you will face problems in the parent method.
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        return response;
    }

    /**
     * Test create multimedia mark.
     *
     * @param mulMarkParam the mul mark param
     */
    private void testCreateMultimediaMark(Map<String, String> mulMarkParam, UrlInputDto urlInput) {
        ClientResponse response = createDocument(mulMarkParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
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
        hm.put("localhost:8080", "3 MB");
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
        testRetrieveMarkContent(urlInput, hm);        
    }

    private void testRetrieveMarkContent(UrlInputDto urlInput, Map<String, String> presummedSizeOfTheFileOnDifferentServerEnvs) {
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        //System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL+"?accessLevel=restricted");
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
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
    
    /**
     * Test duplicate multimedia mark creation.
     *
     * @param mulMarkParam the mul mark param
     */
    private void testDuplicateMultimediaMarkCreation(Map<String, String> mulMarkParam) {
        ClientResponse response = createDocument(mulMarkParam);         
        assertEquals(409, response.getStatus());
    }    
    
    @Test
    public void transformASFToMP4Evidence(){
        String VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC = "{  \"accessLevel\": \"restricted\", \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("EvidenceMultimedia.evi");
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_ASF);        
        testCreateMultimediaMark(mulMarkParam, urlInput);
    }  
    
    @Test
    public void transformASFToMP4Specimen(){
        String VALUE_SPECIMEN_METADATA = "{\"accessLevel\" : \"restricted\",   \"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateSpecimenIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",  \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("SpecimenMultimedia.spi");
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_SPECIMEN_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_ASF);        
        testCreateMultimediaMark(mulMarkParam, urlInput);
    }     
    
    @Test    
    public void transformNoticeMultimedia(){
        String VALUE_SPECIMEN_METADATA = "{  \"accessLevel\": \"public\",  \"docSubType\": \"Notice\",     \"docCategory\": \"Migrated\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"CreateNoticeIntegraTestV_1_0\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",   \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"mailDate\": \"2014-04-23T13:42:24.962-04:00\" }";
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTICE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("NoticeMultimedia.ntc");
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_SPECIMEN_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_ASF);        
        ClientResponse response = createDocument(mulMarkParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
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
        hm.put("localhost:8080", "7 MB");
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "7 MB");
        hm.put("tmng-alfupgrade.sit.uspto.gov", "7 MB");
        hm.put("tm-alf-7.sit.uspto.gov:8080", "7 MB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "7 MB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "7 MB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "7 MB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "7 MB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "7 MB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "7 MB");
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "7 MB");         
        testRetrieveMarkContent(urlInput, hm);
    }  
    
}
