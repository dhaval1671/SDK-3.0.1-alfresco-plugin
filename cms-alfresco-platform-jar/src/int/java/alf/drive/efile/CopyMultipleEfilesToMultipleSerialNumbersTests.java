package alf.drive.efile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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

import alf.integration.service.all.base.EfileBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.drive.efile.Efile;


/**
 * Retrieve Efile Content test cases.
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CopyMultipleEfilesToMultipleSerialNumbersTests extends EfileBaseTest {

    /** The Constant _1ST_EFILE_PDF. */
    private static final String _1ST_EFILE_PDF = "1st-efile.pdf";
    
    /** The Constant _2ST_EFILE_PDF. */
    private static final String _2ST_EFILE_PDF = "2st-efile.pdf";
    
    /** The Constant _3ST_EFILE_PDF. */
    private static final String _3ST_EFILE_PDF = "3st-efile.pdf";
    
    /** The Constant _4TH_EFILE_PDF. */
    private static final String _4TH_EFILE_PDF = "4th-efile.pdf";

    /** The Constant _5TH_EFILE_PDF. */
    private static final String _5TH_EFILE_PDF = "5th-efile.pdf";

    /** The Constant _6TH_EFILE_PDF. */
    private static final String _6TH_EFILE_PDF = "6th-efile.pdf";

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

    /**
     * Copy and recopy multiple efiles to multiple serial numbers happy path.
     */
    @Test
    public void copyAndRecopyMultipleEfilesToMultipleSerialNumbersHappyPath(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        String url = CaseOtherUrl.efileCopyMultipleEfilesToMultipleSerialNumbersUrl(urlInput);        
        String metadata = "[     {         \"documentId\": \"/drive/efile/" + ParameterValues.VALUE_EFILE_MJK_777_008_TRADEMARKID.toString() + "/"+CopyMultipleEfilesToMultipleSerialNumbersTests._1ST_EFILE_PDF+"\",         \"serialNumbers\": [             " + ParameterValues.VALUE_SERIAL_77777801_NUMBER.toString() + ",             " + ParameterValues.VALUE_SERIAL_77777802_NUMBER.toString() + "         ],         \"documentType\": \"receipt\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\"         }     },     {         \"documentId\": \"/drive/efile/"+ ParameterValues.VALUE_EFILE_UPR_777_009_TRADEMARKID.toString() +"/"+ CopyMultipleEfilesToMultipleSerialNumbersTests._2ST_EFILE_PDF +"\",         \"serialNumbers\": [             " + ParameterValues.VALUE_SERIAL_77777803_NUMBER.toString() + ",             " + ParameterValues.VALUE_SERIAL_77777804_NUMBER.toString() + "         ],         \"documentType\": \"receipt\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\"         }     } ]";        
        copyMultipleEfilesToMultipleSerialNumbersHappyPath(urlInput, url, metadata );
        recopyMultipleEfilesToMultipleSerialNumbersHappyPath(urlInput, url, metadata);
    }

    /**
     * Creates the set of multiple files.
     *
     * @param flag the flag
     */
    private void createSetOfMultipleFiles(boolean flag) {
        createMultipleEfiles(ParameterValues.VALUE_EFILE_MJK_777_008_TRADEMARKID.toString(), CopyMultipleEfilesToMultipleSerialNumbersTests._1ST_EFILE_PDF);
        createMultipleEfiles(ParameterValues.VALUE_EFILE_UPR_777_009_TRADEMARKID.toString(), CopyMultipleEfilesToMultipleSerialNumbersTests._2ST_EFILE_PDF);
        if(flag){
        createMultipleEfiles(ParameterValues.VALUE_EFILE_MJK_777_008_TRADEMARKID.toString(), CopyMultipleEfilesToMultipleSerialNumbersTests._3ST_EFILE_PDF);
        }
    }    
    
    /**
     * Copy multiple efiles to multiple serial numbers happy path.
     *
     * @param urlInput the url input
     * @param url the url
     * @param metadata the metadata
     */
    private void copyMultipleEfilesToMultipleSerialNumbersHappyPath(UrlInputDto urlInput, String url, String metadata){
        boolean createThirdFile = true;
        createSetOfMultipleFiles(createThirdFile);
        WebResource webResource = client.resource(url);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needle = "\"version\"(.*?)\"(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, needle));     
        String stDocCoppiedToSerNum = "\"documentId\"(.*?)\"/case/" + ParameterValues.VALUE_SERIAL_77777801_NUMBER.toString() + "/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._1ST_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, stDocCoppiedToSerNum));
        String ndDocCoppiedToSerNum = "\"documentId\"(.*?)\"/case/" + ParameterValues.VALUE_SERIAL_77777802_NUMBER.toString() + "/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._1ST_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, ndDocCoppiedToSerNum));       
        String trdDocCoppiedToSerNum = "\"documentId\"(.*?)\"/case/"+ ParameterValues.VALUE_SERIAL_77777803_NUMBER.toString() + "/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._2ST_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, trdDocCoppiedToSerNum));       
        String thDocCoppiedToSerNum = "\"documentId\"(.*?)\"/case/" + ParameterValues.VALUE_SERIAL_77777804_NUMBER.toString() + "/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._2ST_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, thDocCoppiedToSerNum));       
    }

    /**
     * Recopy multiple efiles to multiple serial numbers happy path.
     *
     * @param urlInput the url input
     * @param url the url
     * @param metadata the metadata
     */
    private void recopyMultipleEfilesToMultipleSerialNumbersHappyPath(UrlInputDto urlInput, String url, String metadata) {
        boolean createThirdFile = false;
        createSetOfMultipleFiles(createThirdFile);
        WebResource webResource = client.resource(url);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(409, response.getStatus());
    }

    /**
     * Copy multiple efiles to multiple serial numbers un happy path for doc type notice.
     */
    @Test
    public void copyMultipleEfilesToMultipleSerialNumbersUnHappyPathForDocTypeNotice(){
        createMultipleFilesUnHappyPathForDocTypeNotice();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        String EFILE_COPY_MULTIPLE_FILES_TO_MULTIPLE_SERIAL_NUMBERS_WEBSCRIPT_URL = CaseOtherUrl.efileCopyMultipleEfilesToMultipleSerialNumbersUrl(urlInput);        
        String metadata = "[     {         \"documentId\": \"/drive/efile/"+ ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() +"/efileWillNotBeMoved.pdf\",         \"serialNumbers\": [             " + ParameterValues.VALUE_SERIAL_77777801_NUMBER.toString() + ",             " + ParameterValues.VALUE_SERIAL_77777802_NUMBER.toString() + "         ],         \"documentType\": \"notice\",         \"metadata\": {             \"modifiedByUserId\": \"UserXYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\"         }     } ]";
        WebResource webResource = client.resource(EFILE_COPY_MULTIPLE_FILES_TO_MULTIPLE_SERIAL_NUMBERS_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(400, response.getStatus());
    }

    /**
     * Creates the multiple files un happy path for doc type notice.
     */
    private void createMultipleFilesUnHappyPathForDocTypeNotice(){
        createMultipleEfiles(ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString(), "efileWillNotBeMoved.pdf");
    }    

    /**
     * Creates the multiple efiles.
     *
     * @param efileTrademarkId the efile trademark id
     * @param fileName the file name
     */
    private void createMultipleEfiles(String efileTrademarkId, String fileName) {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        urlInput.setSerialNumber(efileTrademarkId);
        urlInput.setFileName(fileName);
        String EFILE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.efileCreateUrl(urlInput);        
        String VALUE_EFILE_METADATA = "{ \"customProperties\": { \"eFileProperty\": \"Property Value\" } }";
        Map<String, String> efileParamMap = new HashMap<String, String>();
        efileParamMap.put(ParameterKeys.URL.toString(),EFILE_CREATE_WEBSCRIPT_URL);
        efileParamMap.put(ParameterKeys.METADATA.toString(),VALUE_EFILE_METADATA);
        if(CopyMultipleEfilesToMultipleSerialNumbersTests._6TH_EFILE_PDF.equalsIgnoreCase(fileName)){
            efileParamMap.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EfileBaseTest.CONTENT_EFILE_PARTIAL + "1_Efile1.0.pdf");
        }else{
            efileParamMap.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EfileBaseTest.CONTENT_EFILE_PARTIAL + fileName);
        }
        ClientResponse response = createDocument(efileParamMap);        
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid document ID
        String validDocumentID = "/"+ Efile.TYPE +"/";
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
    } 
    
    /**
     * Copy multiple efiles to multiple alpha numeric serial numbers happy path.
     */
    @Test
    public void copyMultipleEfilesToMultipleAlphaNumericSerialNumbersHappyPath(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        String url = CaseOtherUrl.efileCopyMultipleEfilesToMultipleSerialNumbersUrl(urlInput);        
        String metadata = "[     {         \"documentId\": \"/drive/efile/"+ ParameterValues.VALUE_EFILE_GID_DEF_778_456_TRADEMARKID.toString() +"/"+CopyMultipleEfilesToMultipleSerialNumbersTests._4TH_EFILE_PDF+"\",         \"serialNumbers\": [             \"A7777801\",             \"A7777802\"         ],         \"documentType\": \"receipt\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\"         }     },     {         \"documentId\": \"/drive/efile/"+ ParameterValues.VALUE_EFILE_GEH_778_001_TRADEMARKID.toString() +"/"+CopyMultipleEfilesToMultipleSerialNumbersTests._5TH_EFILE_PDF+"\",         \"serialNumbers\": [             \"B7777803\",             \"B7777804\"         ],         \"documentType\": \"receipt\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\"         }     } ]";        
        copyMultipleEfilesToMultipleAlphaNumericSerialNumbersHappyPath(urlInput, url, metadata );
    }    
    
    /**
     * Copy multiple efiles to multiple alpha numeric serial numbers happy path.
     *
     * @param urlInput the url input
     * @param url the url
     * @param metadata the metadata
     */
    private void copyMultipleEfilesToMultipleAlphaNumericSerialNumbersHappyPath(UrlInputDto urlInput, String url, String metadata) {
        createSetOfMultipleFilesAlphaNumeric();
        WebResource webResource = client.resource(url);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
        String haystack = str;
        String needle = "\"version\"(.*?)\"(.+?)";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, needle));     
        String stDocCoppiedToSerNum = "\"documentId\"(.*?)\"/case/A7777801/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._4TH_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, stDocCoppiedToSerNum));
        String ndDocCoppiedToSerNum = "\"documentId\"(.*?)\"/case/A7777802/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._4TH_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, ndDocCoppiedToSerNum));       
        String trdDocCoppiedToSerNum = "\"documentId\"(.*?)\"/case/B7777803/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._5TH_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, trdDocCoppiedToSerNum));       
        String thDocCoppiedToSerNum  = "\"documentId\"(.*?)\"/case/B7777804/receipt/"+CopyMultipleEfilesToMultipleSerialNumbersTests._5TH_EFILE_PDF+"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, thDocCoppiedToSerNum));       
    }   
    
    /**
     * Creates the set of multiple files alpha numeric.
     */
    private void createSetOfMultipleFilesAlphaNumeric(){
        createMultipleEfiles(ParameterValues.VALUE_EFILE_GID_DEF_778_456_TRADEMARKID.toString(), CopyMultipleEfilesToMultipleSerialNumbersTests._4TH_EFILE_PDF);
        createMultipleEfiles(ParameterValues.VALUE_EFILE_GEH_778_001_TRADEMARKID.toString(), CopyMultipleEfilesToMultipleSerialNumbersTests._5TH_EFILE_PDF);
    }    
    
    /**
     * Copy efile to empty array of serial numbers.
     */
    @Test
    public void copyEfileToEmptyArrayOfSerialNumbers(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        String url = CaseOtherUrl.efileCopyMultipleEfilesToMultipleSerialNumbersUrl(urlInput);        
        String metadata = "[     {         \"documentId\": \"/drive/efile/"+ ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString() +"/"+CopyMultipleEfilesToMultipleSerialNumbersTests._6TH_EFILE_PDF+"\",         \"serialNumbers\": [],         \"documentType\": \"signature\",         \"metadata\": {             \"modifiedByUserId\": \"User XYZ\",             \"documentAlias\": \"nickname\",             \"sourceSystem\": \"TMNG\",             \"sourceMedia\": \"electronic\",             \"sourceMedium\": \"upload\",             \"accessLevel\": \"public\",             \"scanDate\": \"2014-04-23T13:42:24.962-04:00\"         }     } ]";        
        assertCopyEfileToEmptyArrayOfSerialNumbers(urlInput, url, metadata );
    }   
    
    /**
     * Creates the efile for empty serial number array.
     */
    private void createEfileForEmptySerialNumberArray(){
        createMultipleEfiles(ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString(), CopyMultipleEfilesToMultipleSerialNumbersTests._6TH_EFILE_PDF);
    }      
    
    /**
     * Assert copy efile to empty array of serial numbers.
     *
     * @param urlInput the url input
     * @param url the url
     * @param metadata the metadata
     */
    private void assertCopyEfileToEmptyArrayOfSerialNumbers(UrlInputDto urlInput, String url, String metadata){
        createEfileForEmptySerialNumberArray();
        WebResource webResource = client.resource(url);
        Builder b = webResource.type(MediaType.APPLICATION_JSON_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, metadata);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
    }       

}
