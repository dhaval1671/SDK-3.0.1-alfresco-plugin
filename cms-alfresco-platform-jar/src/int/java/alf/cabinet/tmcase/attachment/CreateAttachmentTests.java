package alf.cabinet.tmcase.attachment;

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

import alf.integration.service.all.base.AttachmentBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Attachment;

/**
 * The Class CreateAttachmentTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateAttachmentTests extends AttachmentBaseTest {

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
     * Attachment create and recreate test.
     */
    @Test
    public void attachmentCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_ATTACHMENT.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_ATTACHMENT_FILE_NAME);
        String ATTACHMENT_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(ATTACHMENT_CREATE_WEBSCRIPT_URL);
        Map<String, String> attachmentParam = new HashMap<String, String>();
        attachmentParam.put(ParameterKeys.URL.toString(),ATTACHMENT_CREATE_WEBSCRIPT_URL);
        attachmentParam.put(ParameterKeys.METADATA.toString(),VALUE_ATTACHMENT_METADATA_ACCESS_PUBLIC);
        attachmentParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), AttachmentBaseTest.CONTENT_ATTACHMENT);

        testCreateAttachment(attachmentParam);
        testDuplicateAttachmentCreation(attachmentParam);
    }

    /**
     * Test create attachment.
     *
     * @param attachmentParam the attachment param
     */
    private void testCreateAttachment(Map<String, String> attachmentParam) {

        ClientResponse response = createDocument(attachmentParam);       

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Attachment.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate attachment creation.
     *
     * @param attachmentParam the attachment param
     */
    private void testDuplicateAttachmentCreation(Map<String, String> attachmentParam) {
        ClientResponse response = createDocument(attachmentParam);         
        assertEquals(409, response.getStatus());
    }  

}
