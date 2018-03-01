package alf.cabinet.tmcase.mark.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class AllMarksVersionedMetadataTests extends MarkBaseTest {

    private static Logger LOG = Logger.getLogger(AllMarksVersionedMetadataTests.class);
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
     * Test retrieve image mark version history from multiple serial number.
     */
    @Test
    public void testRetrieveImageMarkVersionHistoryFromMultipleSerialNumber() {

        String caseSerialNumber = ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString();
        
        String firstMrkFileName = "First_AllMarkVersionedMetadata.jpg";
        String secondMrkFileName = "Second_AllMarkVersionedMetadata.jpg";        
        createLocalMarksAndUpdateThem(caseSerialNumber, firstMrkFileName); 
        createLocalMarksAndUpdateThem(caseSerialNumber, secondMrkFileName);    
        
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(caseSerialNumber);
        String RETRIEVE_ALL_MARKS_VERSIONED_METADATA_WEBSCRIPT_URL = CaseOtherUrl.retrieveAllMarksVersionedMetadataUrl(urlInput);
        //System.out.println(RETRIEVE_ALL_MARKS_VERSIONED_METADATA_WEBSCRIPT_URL);
        WebResource webResource = client.resource(RETRIEVE_ALL_MARKS_VERSIONED_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
        String haystack = str;
        
        String needleZero = "\"documentAlias\":\"VersionOnePointZero\"";
        assertTrue(containsStringOfGivenRegExForSuppliedCount(haystack, needleZero, 2));         
        
        String needleOne = "\"documentAlias\":\"VersionOnePointOne\"";
        assertTrue(containsStringOfGivenRegExForSuppliedCount(haystack, needleOne, 2));    
        
        String needleTwo = "\"documentAlias\":\"VersionOnePointTwo\"";
        assertTrue(containsStringOfGivenRegExForSuppliedCount(haystack, needleTwo, 2));         

    }

    private void createLocalMarksAndUpdateThem(String string, String valueMrkFileName) {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(string);
        urlInput.setFileName(valueMrkFileName);
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        String valueMrkMetadataOne = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"VersionOnePointZero\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        mrkParam.put(ParameterKeys.METADATA.toString(),valueMrkMetadataOne);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        
        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(201, response.getStatus());
        String haystack = str;
        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/mark/(.*?)\"";//;
        assertTrue(containsStringOfGivenRegExForSuppliedCount( haystack, docIdImagePostFix, 1));
        
        updateMarkMetadata(string, valueMrkFileName);
    }
    
    private void updateMarkMetadata(String string, String valueMrkFileName) {
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(string);
        urlInputOne.setFileName(valueMrkFileName);
        String UPDATE_MARK_METADATA_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateMetadataUrl(urlInputOne); 
        //ystem.out.println("MARK_UPDATE_WEBSCRIPT_URL:: " + UPDATE_MARK_METADATA_WEBSCRIPT_URL);
        FormDataMultiPart multiPart = new FormDataMultiPart();
        String valueMrkMetadataOne = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"VersionOnePointOne\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), valueMrkMetadataOne));
        WebResource webResource = client.resource(UPDATE_MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMarkMetadataOne:: \n" + haystack);  
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
        
        updateMarkContentAndMetadata(string, valueMrkFileName);
    }    
    
    private void updateMarkContentAndMetadata(String string, String valueMrkFileName) {
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(string);
        urlInputOne.setFileName(valueMrkFileName);
        String valueMrkMetadataTwo = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"VersionOnePointTwo\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";        
        ClientResponse response = updateMarkContentAndMetadata(urlInputOne, valueMrkMetadataTwo);
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMarkContentAndMetadata:: \n" + haystack);        
        String needle = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral( haystack, needle));
    }    

    private ClientResponse updateMarkContentAndMetadata(UrlInputDto urlInputOne, String metadata) {
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);         
    
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(MarkBaseTest.CONTENT_MRK_TIF);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadata));
        WebResource webResource = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        assertEquals(200, response.getStatus());
        return response;
    }    
  
}
