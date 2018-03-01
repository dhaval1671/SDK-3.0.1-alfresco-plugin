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
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * 
 * @author stank
 *
 */

public class UpdateMarkTest extends MarkBaseTest{

    /** The log. */
    private static Logger LOG = Logger.getLogger(UpdateMarkTest.class);

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
     * Mark update test.
     */
    @Test
    public void markUpdateTest(){
        createMark();
        testUpdateMarkContent();
        testUpdateMarkContentAndMetadata();
    }      

    /**  
     * @Title: createMark  
     * @Description:     
     * @return void   
     * @throws  
     */ 
     private void createMark() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(UPDATE_MRK_FILE_NAME);
        createMarkForUpdateTestCases(urlInput, VALUE_MRK_METADATA_ONE);         
        
    }

    private void createMarkForUpdateTestCases(UrlInputDto urlInput, String metadata) {
        String MARK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), MARK_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), metadata);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_JPG);
        
        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        //System.out.println("createMark:: \n" + str);   

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));

        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/mark/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdImagePostFix));
    }

    /**
     * Test update mark content.
     */
    private void testUpdateMarkContent() {
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputOne.setFileName(MarkBaseTest.UPDATE_MRK_FILE_NAME);
        String MARK_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);          
    
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(CONTENT_MRK_JPG);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        WebResource webResource = client.resource(MARK_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);     
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        //String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMarkContent:: \n" + haystack);          
        assertEquals(400, response.getStatus());
    }

    /**
     * Test update mark content and metadata.
     */
    private void testUpdateMarkContentAndMetadata() {
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputOne.setFileName(MarkBaseTest.UPDATE_MRK_FILE_NAME);
        ClientResponse response = updateMarkContentAndMetadata(urlInputOne, VALUE_MRK_METADATA_ONE);
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMarkContentAndMetadata:: \n" + haystack);        
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
    
        //check for 'mark' type in documentId url
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
    
        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/mark/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdImagePostFix));          
    
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
    
    /**
     * Test case  updateMarkMetadataShouldNOTChangetheVersion(), should NOT change the version while it tries to update the mark metadata,
     * as it does NOT have     "migrationMethod": "LZL", in the original metadata that was used to create the mark.
     */    
    @Test
    public void updateMarkMetadataShouldNOTChangetheVersion(){
        
        String VALUE_MRK_METADATA_TWO = "{     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"isMarkAccepted\": true,     \"documentCode\": \"MRK\",     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",     \"migrationMethod\": \"LZL\",     \"migrationSource\": \"TICRS\",     \"documentName\": \"myTM\",     \"documentAlias\": \"DocNameForTmngUiDisplay\" }";        
        
        //Create mark
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputOne.setFileName("UpdateMrkFileMetadataFlavourTwo.jpg");
        createMarkForUpdateTestCases(urlInputOne, VALUE_MRK_METADATA_TWO); 
           
        
        //Update the mark metadata
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputTwo.setFileName("UpdateMrkFileMetadataFlavourTwo.jpg");
        String UPDATE_MARK_METADATA_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateMetadataUrl(urlInputTwo); 
        //ystem.out.println("MARK_UPDATE_WEBSCRIPT_URL:: " + UPDATE_MARK_METADATA_WEBSCRIPT_URL);
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_MRK_METADATA_TWO));
        WebResource webResource = client.resource(UPDATE_MARK_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseTwo = b.post(ClientResponse.class, multiPart);
        String haystackTwo = responseTwo.getEntity(String.class);
        //System.out.println("testUpdateMarkMetadataOne:: \n" + haystack);  
        String needleTwo = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystackTwo, needleTwo));        
    }
    
    @Test
    public void updateJustContentAndSeeIfTheVersionForMarkChanges(){
        
        String VALUE_MRK_METADATA_TWO = "{     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"isMarkAccepted\": true,     \"documentCode\": \"MRK\",     \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",     \"migrationMethod\": \"LZL\",     \"migrationSource\": \"TICRS\",     \"documentName\": \"myTM\",     \"documentAlias\": \"DocNameForTmngUiDisplay\" }";
        //Create mark
        UrlInputDto urlInputOne = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInputOne.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputOne.setFileName("UpdateMrkFileMetadataFlavourThree.jpg");
        createMarkForUpdateTestCases(urlInputOne, VALUE_MRK_METADATA_TWO);         
        
        ClientResponse responseOne = updateMarkContentAndMetadata(urlInputOne, VALUE_MRK_METADATA_TWO);
        String haystackOne = responseOne.getEntity(String.class);
        //System.out.println("testUpdateMarkContentAndMetadata:: \n" + haystack);        
        String needleOne = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystackOne, needleOne));
    
        ClientResponse responseTwo = updateMarkContentAndMetadata(urlInputOne, VALUE_MRK_METADATA_TWO);
        String haystackTwo = responseTwo.getEntity(String.class);
        //System.out.println("testUpdateMarkContentAndMetadata:: \n" + haystack);        
        String needleTwo = "\"version\":\"1.2\"";
        assertTrue(containsStringLiteral( haystackTwo, needleTwo));       
    }

}
