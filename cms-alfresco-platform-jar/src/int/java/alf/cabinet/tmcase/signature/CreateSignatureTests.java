package alf.cabinet.tmcase.signature;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.SignatureBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Signature;

/**
 * The Class CreateSignatureTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateSignatureTests extends SignatureBaseTest {

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
     * Signature create and recreate test.
     */
    @Test
    public void signatureCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SIGNATURE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_SIGNATURE_FILE_NAME);
        String SIGNATURE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(SIGNATURE_CREATE_WEBSCRIPT_URL);
        Map<String, String> signatureParam = new HashMap<String, String>();
        signatureParam.put(ParameterKeys.URL.toString(),SIGNATURE_CREATE_WEBSCRIPT_URL);
        signatureParam.put(ParameterKeys.METADATA.toString(),VALUE_SIGNATURE_METADATA);
        signatureParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SignatureBaseTest.CONTENT_SIGNATURE_ATTACHMENT);

        testCreateSignature(signatureParam);
        testDuplicateSignatureCreation(signatureParam);
    }

    /**
     * Test create signature.
     *
     * @param signatureParam the signature param
     */
    private void testCreateSignature(Map<String, String> signatureParam) {

        ClientResponse response = createDocument(signatureParam);       

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Signature.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate signature creation.
     *
     * @param signatureParam the signature param
     */
    private void testDuplicateSignatureCreation(Map<String, String> signatureParam) {
        ClientResponse response = createDocument(signatureParam);         
        assertEquals(409, response.getStatus());
    }  

}
