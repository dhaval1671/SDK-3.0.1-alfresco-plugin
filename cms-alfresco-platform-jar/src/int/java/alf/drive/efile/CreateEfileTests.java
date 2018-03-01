package alf.drive.efile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EfileBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.drive.efile.Efile;

/**
 * The Class CreateEfileTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateEfileTests extends EfileBaseTest {

    /** The Constant MY_EFILE_PDF. */
    private static final String MY_EFILE_PDF = "my-efile.pdf";

    /** The log. */
    public static Logger LOG = Logger.getLogger(CreateEfileTests.class);    

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
     * Efile create and recreate test.
     */
    @Test
    public void efileCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString());
        urlInput.setFileName(CreateEfileTests.MY_EFILE_PDF);
        String EFILE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.efileCreateUrl(urlInput);
        String VALUE_EFILE_METADATA = "{     \"customProperties\": {         \"eFileProperty\": \"Property Value\"     } }";
        Map<String, String> efileParamMap = new HashMap<String, String>();
        efileParamMap.put(ParameterKeys.URL.toString(),EFILE_CREATE_WEBSCRIPT_URL);
        efileParamMap.put(ParameterKeys.METADATA.toString(),VALUE_EFILE_METADATA);
        efileParamMap.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EfileBaseTest.CONTENT_EFILE_ATTACHMENT);         
        testCreateEfile(efileParamMap);
        testDuplicateEfileCreation(efileParamMap);
    }    

    /**
     * Test create efile.
     *
     * @param efileParamMap the efile param map
     */
    private void testCreateEfile(Map<String, String> efileParamMap) {
        ClientResponse response = createDocument(efileParamMap);        
        String str = response.getEntity(String.class);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid document ID
        String validDocumentID = "/"+ Efile.TYPE +"/";
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
    }

    /**
     * Test duplicate efile creation.
     *
     * @param efileParamMap the efile param map
     */
    private void testDuplicateEfileCreation(Map<String, String> efileParamMap) {
        ClientResponse response = createDocument(efileParamMap); 
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(409, response.getStatus());
    }  

    /**
     * Try efile create with bad incoming json.
     */
    @Test
    public void tryEfileCreateWithBadIncomingJson() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EFILE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_EFILE_GID_ABC_777_123_TRADEMARKID.toString());
        urlInput.setFileName(CreateEfileTests.MY_EFILE_PDF);
        String EFILE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.efileCreateUrl(urlInput);        
        String VALUE_EFILE_METADATA = "{     \"customProperties\": [{         \"eFileProperty\": \"Property Value\"     } ]}";
        Map<String, String> efileParamMap = new HashMap<String, String>();
        efileParamMap.put(ParameterKeys.URL.toString(),EFILE_CREATE_WEBSCRIPT_URL);
        efileParamMap.put(ParameterKeys.METADATA.toString(),VALUE_EFILE_METADATA);
        efileParamMap.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EfileBaseTest.CONTENT_EFILE_ATTACHMENT);         
        ClientResponse response = createDocument(efileParamMap);        
        //String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(400, response.getStatus());
    }        

}
