package alf.cabinet.tmcase.accessLevel.content;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.NoticeBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveContentUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Notice;

/**
 * Retrieve Notice Content test cases.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RestrictedNoticeRetrieveContentTests extends CentralBase {

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
     * Test retrieve notice content.
     */
    @Test
    public void createAndRetrveRestrictedNoticeConWithClientReqAccessLevelPublic() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTICE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777784_NUMBER.toString());
        urlInput.setFileName("AccessLevelRestrictedNotice_RteveCont.pdf");
        
        //create notice
        noticeCreateAndRecreateTest(urlInput);
        
        retrieveRestrictedNoticeWithClientRequestPublicAccessLevel(urlInput); 
        retrieveRestrictedNoticeWithClientRequestInternalAccessLevel(urlInput);
        retrieveRestrictedNoticeWithClientRequestRestrictedAccessLevel(urlInput);
        retrieveRestrictedNoticeWithClientRequestNOAccessLevel(urlInput);
        retrieveRestrictedNoticeWithClientRequestUnrecognizedAccessLevel(urlInput);
    }
    
    private void retrieveRestrictedNoticeWithClientRequestNOAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel(null);
        String NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }
    
    private void retrieveRestrictedNoticeWithClientRequestUnrecognizedAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("junkValue");
        String NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }    

    private void retrieveRestrictedNoticeWithClientRequestPublicAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("public");
        String NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }

    private void retrieveRestrictedNoticeWithClientRequestInternalAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("internal");
        String NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(403, response.getStatus());
    }    
    
    private void retrieveRestrictedNoticeWithClientRequestRestrictedAccessLevel(UrlInputDto urlInput) {
        urlInput.setAccessLevel("restricted");
        String NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL = CaseRetrieveContentUrl.retrieveContentGenericUrl(urlInput);
        //System.out.println(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(NOTICE_RETRIEVE_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        String fileSizeFrResponse = FileUtils.byteCountToDisplaySize(response.getLength());
        assertEquals(200, response.getStatus());
        assertEquals("78 KB", fileSizeFrResponse);
    }    
    
    private void noticeCreateAndRecreateTest(UrlInputDto urlInput) {
        String VALUE_NOTICE_METADATA = "{     \"docSubType\": \"Notice\",     \"docCategory\": \"Migrated\",     \"documentAlias\": \"DocNameForTmngUiDisplay\",     \"modifiedByUserId\": \"CreateNoticeIntegraTestV_1_0\",     \"sourceSystem\": \"TMNG\",     \"sourceMedia\": \"electronic\",     \"sourceMedium\": \"upload\",     \"accessLevel\": \"restricted\",     \"scanDate\": \"2014-04-23T13:42:24.962-04:00\",     \"mailDate\": \"2014-04-23T13:42:24.962-04:00\" }";        
        Map<String, String> noticeParam = new HashMap<String, String>();
        String NOTICE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput); 
        //System.out.println(NOTICE_CREATE_WEBSCRIPT_URL);
        noticeParam.put(ParameterKeys.URL.toString(),NOTICE_CREATE_WEBSCRIPT_URL);
        noticeParam.put(ParameterKeys.METADATA.toString(),VALUE_NOTICE_METADATA);
        noticeParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoticeBaseTest.CONTENT_NOTICE_ATTACHMENT);        
        ClientResponse response = createDocument(noticeParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));
        //check for valid docuemnt ID
        String validDocumentID = "/"+ Notice.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));  
    }    



}
