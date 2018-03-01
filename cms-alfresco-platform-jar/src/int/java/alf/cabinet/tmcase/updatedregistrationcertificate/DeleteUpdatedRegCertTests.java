package alf.cabinet.tmcase.updatedregistrationcertificate;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.UpdatedRegCertBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseDeleteUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.UpdatedRegistrationCertificate;

/**
 * The Class CreateRegistrationCertificateTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class DeleteUpdatedRegCertTests extends UpdatedRegCertBaseTest {

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
    public void uRCCreateAndRecreateTest() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_UPDATED_REGISTRATION_CERTIFICATE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_URC_FILE_NAME);
        String REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        // System.out.println(REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        Map<String, String> registrationCertificateParam = new HashMap<String, String>();
        registrationCertificateParam.put(ParameterKeys.URL.toString(), REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        registrationCertificateParam.put(ParameterKeys.METADATA.toString(), VALUE_URC_DELETE_METADATA);
        registrationCertificateParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(),
                UpdatedRegCertBaseTest.CONTENT_URC_ATTACHMENT);

        testCreateURC(registrationCertificateParam);
        testDeleteRegistrationCertificate(urlInput);

    }

    /**
     * Test create registrationCertificate.
     *
     * @param registrationCertificateParam
     *            the registrationCertificate param
     */
    private void testCreateURC(Map<String, String> registrationCertificateParam) {

        ClientResponse response = createDocument(registrationCertificateParam);

        String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;

        // check for valid docuemnt ID
        String validDocumentID = "/" + UpdatedRegistrationCertificate.TYPE + "/";// ;
        assertTrue(containsStringLiteral(haystack, validDocumentID));
    }

    private void testDeleteRegistrationCertificate(UrlInputDto urlInput) {
        String REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL = CaseDeleteUrl.genericDeleteUrl(urlInput);

        WebResource webResource = client.resource(REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.delete(ClientResponse.class);
        assertEquals(200, response.getStatus());

    }
}
