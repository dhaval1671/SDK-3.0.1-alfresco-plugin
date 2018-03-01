package alf.drive.eogtemplate;

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

import alf.integration.service.all.base.EogTemplateBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class CreateRegistrationCertificateTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateEogTemplateTests extends EogTemplateBaseTest {

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
    public void eogTemplateCreateTest() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_REGISTRATION_CERTIFICATE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(EOG_TEMPLATE_FILE_NAME);
        Map<String, String> eogTemplateParam = new HashMap<String, String>();
        eogTemplateParam.put(ParameterKeys.URL.toString(), CaseCreateUrl.getEogTemplateCreateUrl(urlInput));
        eogTemplateParam.put(ParameterKeys.METADATA.toString(), EOGTEMPLATE_METADATA);
        eogTemplateParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EogTemplateBaseTest.CONTENT_EFILE_ATTACHMENT);

        testCreateEogTemplate(eogTemplateParam);
    }

    /**
     * Test create registrationCertificate.
     *
     * @param registrationCertificateParam
     *            the registrationCertificate param
     */
    private void testCreateEogTemplate(Map<String, String> eogTemplateParam) {

        ClientResponse response = createDocument(eogTemplateParam);

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral(haystack, needle));

    }

}
