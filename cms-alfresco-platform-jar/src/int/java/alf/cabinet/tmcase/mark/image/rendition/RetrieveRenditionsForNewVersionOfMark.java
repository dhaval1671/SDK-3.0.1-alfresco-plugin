package alf.cabinet.tmcase.mark.image.rendition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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

import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.MarkRenditions;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * The Class RetrieveMarkRenditionsTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveRenditionsForNewVersionOfMark extends MarkBaseTest {
	
    private static Logger LOG = Logger.getLogger(RetrieveRenditionsForNewVersionOfMark.class);

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
    public void updateMarkThenRetrieveItsRenditions() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName("updateMarkThenRetrieveItsRenditions");
        createMark(urlInput);  
        
        retrieveMarkRendition(urlInput);
        
        updateMark(urlInput);
        
        retrieveUpdateMarkRendition(urlInput);


    }

	private void createMark(UrlInputDto urlInput) {
		String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
	    Map<String, String> mrkParam = new HashMap<String, String>();
	    mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
	    mrkParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
	    mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG); //147 KB
	    //mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_PNG_BANANA);// 97 KB
	    ClientResponse response = createDocument(mrkParam);
	    String str = response.getEntity(String.class);
	    //System.out.println(str);   
	
	    assertEquals(201, response.getStatus());
	
	    String haystack = str;
	    String needle = "\"version\":\"1.0\"";
	    assertTrue(containsStringLiteral( haystack, needle));
	
	    //check for 'mark' type in documentId url
	    String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
	    assertTrue(containsStringLiteral( haystack, validDocumentID));
	}

	private void retrieveMarkRendition(UrlInputDto urlInput) {
		urlInput.setRendition(MarkRenditions.IMAGE_TO_PNG.getRenditionName());
	    String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);
	    // System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
	    WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
	    Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
	    b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
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
	    
	    assertEquals(200, response.getStatus());
	    //System.out.println("fileSize for original mark --> " + fileSize);
	    
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
	    verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);        
	}

	private void updateMark(UrlInputDto urlInput) {
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInput);         
        
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(MarkBaseTest.CONTENT_MRK_PNG_BANANA);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_ONE));
        WebResource webResource = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        String haystack = response.getEntity(String.class);
        //System.out.println("updateMark:: \n" + haystack);    
        assertEquals(200, response.getStatus());
        
        
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
    
        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
        
        retrieveUpdateMarkRendition(urlInput);
	}

	private void retrieveUpdateMarkRendition(UrlInputDto urlInput) {
		urlInput.setRendition(MarkRenditions.IMAGE_TO_PNG.getRenditionName());
	    String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);
	    // System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
	    WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
	    Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
	    b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
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
	    
	    assertEquals(200, response.getStatus());
	    //System.out.println("fileSize for updated mark --> " + fileSize);
	    
	    Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "98.8 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", "97 KB");
	    presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", "97 KB");        
	    verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);		
	}

}
