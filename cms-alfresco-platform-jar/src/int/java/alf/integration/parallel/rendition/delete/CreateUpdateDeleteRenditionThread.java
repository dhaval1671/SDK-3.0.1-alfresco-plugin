package alf.integration.parallel.rendition.delete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

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

import alf.integration.parallel.base.Callback;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.MarkRenditions;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class CreateUpdateDeleteRenditionThread extends MarkBaseTest implements Runnable{

    
    private static final String FILESIZE_147_KB = "147 KB";
    private static final Logger log =  Logger.getLogger(Thread.currentThread().getStackTrace()[TMConstants.ONE].getClassName());
	private int i;
	
	public CreateUpdateDeleteRenditionThread(int i, Callback callback) {
    	this.i = i;
	}
	
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
        /* method known to be empty */
    }    

	private String getFileNameFromVariable(int i) {
		
		//Thread thread = Thread.currentThread();
		//String evidenceFileName = "Parallel"+ thread.getName() +"Evidence" + "_" + i + "_" + "File.pdf";		
		
		String evidenceFileName = "ParallelThreadMarkRenditionDelete" + "_" + i + "_" + "File.pdf";
		return evidenceFileName;
	}

	private String getCaseNumberFromVariable(int i) {
		int caseNumber = 77777795;
		int calculatedCaseNumber = caseNumber + i;
		return Integer.toString(calculatedCaseNumber);
	}      
    
	@Override
	public void run() {
		
		Thread thread = Thread.currentThread();
		 
		//System.out.println("START: " + thread.getName() + " (" + thread.getId() + ")");		
		
		//Pause for tiny time.
        try {
			Thread.sleep(100-i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        //Print a message
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(getCaseNumberFromVariable(this.i));
        urlInput.setFileName(getFileNameFromVariable(this.i));
        createMark(urlInput);  
        
        retrieveMarkRendition(urlInput);
        
        updateMark(urlInput);
        
        retrieveUpdateMarkRendition(urlInput, thread.getName());
		
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
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);        
    }

    private void updateMark(UrlInputDto urlInput) {
        String markUpdateWebscriptUrl = CaseUpdateUrl.genericUpdateContentUrl(urlInput);         
        
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        try(
            FileInputStream content = new FileInputStream(MarkBaseTest.CONTENT_MRK_PNG_BANANA);     
           ) {
            multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        } 
        
        String valueMrkMetadataOne = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), valueMrkMetadataOne));
        WebResource webResource = client.resource(markUpdateWebscriptUrl);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        String haystack = response.getEntity(String.class);
        /** System.out.println("updateMark:: \n" + haystack); */    
        assertEquals(200, response.getStatus());
        
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
    
        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
        
        //retrieveUpdateMarkRendition(urlInput);
    }

    private void retrieveUpdateMarkRendition(UrlInputDto urlInput, String threadName) {
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
        System.out.println(threadName + " :: " + "fileSize for updated mark --> " + fileSize);
        //this.threadName = "emptyThreaName";
        
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
