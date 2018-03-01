package alf.cabinet.tmcase.mark.multimedia.video;

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

import alf.integration.service.all.base.VideoMarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.TransformVideoMarkBaseTest;
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

public class TryCreateNonTransformableVideoMarkTest extends VideoMarkBaseTest{

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
    public void multimediaCreateAndRecreateTest(){
        Map<String, String> mulMarkParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("createNonTransformableMultimediaFile.3gp");
        String MM_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);         
        //System.out.println(MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.URL.toString(), MM_CREATE_WEBSCRIPT_URL);
        mulMarkParam.put(ParameterKeys.METADATA.toString(),VALUE_MMRK_METADATA);
        mulMarkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), TransformVideoMarkBaseTest.TRANSFORM_FAILING_CONTENT_MM_3GP);     
        testCreateMultimediaMark(mulMarkParam, urlInput);
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
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
        //check for documentId url
        String docIdMultimediaPostFix = "\"documentId\":\"/case/(.*?)/mark/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdMultimediaPostFix));  
        
        verifyItsFileSize(urlInput);
    }

    private void verifyItsFileSize(UrlInputDto urlInput) {
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
        hm.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "684 KB");//[For localhost this value was set to 210 KB, before Aug/14/2017]
        hm.put("localhost:8080", "684 KB");//[For localhost this value was set to 121 KB, before Aug/14/2017]
        hm.put("tmng-alf-1.dev.uspto.gov:8080", "684 KB");
        hm.put("tmng-alfupgrade.sit.uspto.gov", "684 KB");
        hm.put("tm-alf-7.sit.uspto.gov:8080", "684 KB");
        hm.put("tm-alf-8.sit.uspto.gov:8080", "684 KB");
        hm.put("tmng-alfupgrade.fqt.uspto.gov", "684 KB");
        hm.put("tmng-alf-1.fqt.uspto.gov:8080", "684 KB");
        hm.put("tmng-alf-2.fqt.uspto.gov:8080", "684 KB");
        hm.put("tmng-alfupgrade.pvt.uspto.gov", "684 KB");
        hm.put("tmng-alf-3.pvt.uspto.gov:8080", "684 KB");
        hm.put("tmng-alf-4.pvt.uspto.gov:8080", "684 KB");
        // assertEquals("660 KB", fileSize); // coming 281 KB
        verifyIfRetriveContentIsAccurate(hm, fileSize);
		
	}
    
}
