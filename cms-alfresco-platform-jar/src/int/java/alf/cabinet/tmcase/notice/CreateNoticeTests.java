package alf.cabinet.tmcase.notice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.NoticeBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Notice;

/**
 * The Class CreateNoticeTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateNoticeTests extends NoticeBaseTest{

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
     * Notice create and recreate test.
     */
    @Test
    public void noticeCreateAndRecreateTest(){
        Map<String, String> noticeParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_NOTICE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_NOTICE_FILE_NAME);
        String NOTICE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput); 
        //System.out.println(NOTICE_CREATE_WEBSCRIPT_URL);
        noticeParam.put(ParameterKeys.URL.toString(),NOTICE_CREATE_WEBSCRIPT_URL);
        noticeParam.put(ParameterKeys.METADATA.toString(),VALUE_NOTICE_METADATA);
        noticeParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoticeBaseTest.CONTENT_NOTICE_ATTACHMENT);        
        testCreateNotice(noticeParam);
        testDuplicateNoticeCreation(noticeParam);
    }    

    /**
     * Test create notice.
     *
     * @param noticeParam the notice param
     */
    private void testCreateNotice(Map<String, String> noticeParam) {
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

    /**
     * Test duplicate notice creation.
     *
     * @param noticeParam the notice param
     */
    private void testDuplicateNoticeCreation(Map<String, String> noticeParam) {
        ClientResponse response = createDocument(noticeParam);         
        assertEquals(409, response.getStatus());
    }  

}
