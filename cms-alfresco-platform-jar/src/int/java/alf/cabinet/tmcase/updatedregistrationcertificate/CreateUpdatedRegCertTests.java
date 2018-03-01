package alf.cabinet.tmcase.updatedregistrationcertificate;

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
import alf.integration.service.all.base.UpdatedRegCertBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.UpdatedRegistrationCertificate;

/**
 * The Class CreateRegistrationCertificateTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateUpdatedRegCertTests extends UpdatedRegCertBaseTest {

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
     * RegistrationCertificate create and recreate test.
     */
    @Test
    public void uRCCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_UPDATED_REGISTRATION_CERTIFICATE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_URC_FILE_NAME);
        String REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        Map<String, String> registrationCertificateParam = new HashMap<String, String>();
        registrationCertificateParam.put(ParameterKeys.URL.toString(),REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        registrationCertificateParam.put(ParameterKeys.METADATA.toString(),VALUE_URC_METADATA);
        registrationCertificateParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), UpdatedRegCertBaseTest.CONTENT_URC_ATTACHMENT);

        testCreateURC(registrationCertificateParam);
        testDuplicateURCCreation(registrationCertificateParam);
    }

    /**
     * Test create registrationCertificate.
     *
     * @param registrationCertificateParam the registrationCertificate param
     */
    private void testCreateURC(Map<String, String> registrationCertificateParam) {

        ClientResponse response = createDocument(registrationCertificateParam);       

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ UpdatedRegistrationCertificate.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
    }

    /**
     * Test duplicate registrationCertificate creation.
     *  RC and uRC are the only two doc type which have slightly different business requirements.
     *  US16436: eOG: Create an "Updated Registration Certificate"
     *  TA46055:Enhance RC to accept multiple creates, US16496: eOG: Enhance RC create service to accept a duplicate create
     *  TA46057:Enhance URC to accept multiple creates, US16496: eOG: Enhance RC create service to accept a duplicate create
     *  TA46058:JUnit RC, US16496: eOG: Enhance RC create service to accept a duplicate create
     *  TA46059:JUnit URC, US16496: eOG: Enhance RC create service to accept a duplicate create    
     * @param registrationCertificateParam the registrationCertificate param
     */
    private void testDuplicateURCCreation(Map<String, String> registrationCertificateParam) {
        ClientResponse response = createDocument(registrationCertificateParam);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
    }  

}
