package alf.cabinet.tmcase.mark.multimedia.video.transform;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TransformVideoMarkBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import junitx.framework.ListAssert;

/**
 * The Class CreateMultimediaMarkTest.
 *
 * @author stank
 */

public class CreateMatroskaVideoAsRegularMark extends TransformVideoMarkBaseTest{

    private static final String FILESIZE_903_KB = "903 KB";
    private static final Logger log = Logger.getLogger(CreateMatroskaVideoAsRegularMark.class);
    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        log.debug("### Executing " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ####");
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
       /* intentionally empty*/ 
    }    

    /** 
     * Matroska files - mkv files mime type is detected by alfresco apis as application/x-matroska
     * for this reason they are NOT being treated as regular video multimedia files and hence 
     * just handled like a regular mark file. ie without the creation of multimedia folder inside the case folder.
     * */
    @Test
    public void matroskaFilesAreNotTreatedAsMultimediaFiles(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("MKVToMP4SpecimenMultimedia.spe");        
        uploadMatroskaAsRegularMarkFile(urlInput);
    }

    private void uploadMatroskaAsRegularMarkFile(UrlInputDto urlInput) {
        String value_evidence_metadata_access_level_public = "{  \"accessLevel\": \"restricted\", \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"User XYZ\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",    \"documentCode\": \"EVI\",     \"mailDate\": \"2013-06-16T05:33:22.000-04:00\",     \"documentEndDate\": null,     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"direction\": \"Incoming\",     \"evidenceSourceType\": \"OA\",     \"evidenceSourceTypeId\": \"123\",     \"evidenceSourceUrl\": \"http://local/evidence/source/url\",     \"evidenceGroupNames\": [         \"trash\",         \"suggested\",         \"abcd\"     ],     \"evidenceCategory\": \"nexis-lexis\", \"redactionLevel\":\"Partial\" }";
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),value_evidence_metadata_access_level_public);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.CONTENT_MM_MKV);        
        ClientResponse response = createDocument(mulMarkParam);      
        String str = response.getEntity(String.class);
        /*
         * System.out.println(str);
         * StackTraceElement  ste = Thread.currentThread().getStackTrace()[1];        
         * System.out.print(ste.getMethodName() + " --> " + response.getStatus() + "\n");
         */        
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ urlInput.getDocType() +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
        //check for documentId url
        String docIdMultimediaPostFix = "\"documentId\":\"/case/(.*?)/"+urlInput.getDocType()+"/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdMultimediaPostFix));
        /*
          fire retrieve content and check its content size.
          This test case is intentionally kept broken just to remind the limitation of Tika api.
          Filesize of original is 903KB but filesize of transformed MP4 is 622KB, 
          but as tika api is NOT detecting this video file
          I recieve 903 KB original file size, actually I should recieve 622 KB of transformed MP4 file size, 
          current ffmpeg tool is NOT transforming the given input multimedia.
        */
        Map<String,String> hm = new HashMap<String,String>();
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", FILESIZE_903_KB);
        hm.put("4_1_9_localhost:8080", FILESIZE_903_KB);
        hm.put("5_1_localhost:8080", FILESIZE_903_KB);        
        hm.put("tmng-alf-1.dev.uspto.gov:8080", FILESIZE_903_KB);
        hm.put("tmng-alfupgrade.sit.uspto.gov", FILESIZE_903_KB);
        hm.put("tm-alf-7.sit.uspto.gov:8080", FILESIZE_903_KB);
        hm.put("tm-alf-8.sit.uspto.gov:8080", FILESIZE_903_KB);
        hm.put("tmng-alfupgrade.fqt.uspto.gov", FILESIZE_903_KB);
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", FILESIZE_903_KB);
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", FILESIZE_903_KB);
        hm.put("tmng-alfupgrade.pvt.uspto.gov", FILESIZE_903_KB);
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", FILESIZE_903_KB);
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", FILESIZE_903_KB);         
        testRetrieveMarkContent(urlInput, hm);        
    }

    private void testRetrieveMarkContent(UrlInputDto urlInput, Map<String, String> presummedSizeOfTheFileOnDifferentServerEnvs) {
        String MARK_CRONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveGenericMarkContentUrl(urlInput);
        //System.out.println(MARK_CRONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_CRONTENT_WEBSCRIPT_URL+"?accessLevel=restricted");
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //StackTraceElement  ste = Thread.currentThread().getStackTrace()[1];        
        //System.out.print(ste.getMethodName() + " --> File size: " + presummedSizeOfTheFile + " :: " + response.getStatus() + "\n");        
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        //System.out.println(fileSizeFrResponse);
        assertEquals(TMConstants.TWO_HUNDRED, response.getStatus());
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
