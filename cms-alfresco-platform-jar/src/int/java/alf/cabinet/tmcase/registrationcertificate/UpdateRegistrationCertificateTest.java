package alf.cabinet.tmcase.registrationcertificate;

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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.RegistrationCertificateBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.RegistrationCertificate;

/**
 * 
 * @author stank
 *
 */

public class UpdateRegistrationCertificateTest extends RegistrationCertificateBaseTest {

    /** The log. */
    private static Logger LOG = Logger.getLogger(UpdateRegistrationCertificateTest.class);

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
     * RegistrationCertificate update test.
     */
    @Test
    public void registrationCertificateUpdateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_REGISTRATION_CERTIFICATE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(UPDATE_REGCERT_FILE_NAME);        
        createRegistrationCertificateForUpdateTestCases(urlInput, VALUE_REGCERT_METADATA_ONE);
        testUpdateRegistrationCertificateContent(urlInput);
        testUpdateRegistrationCertificateContentAndMetadata(urlInput);
    }      

    private void createRegistrationCertificateForUpdateTestCases(UrlInputDto urlInput, String metadata) {
        String REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);       
        Map<String, String> mrkParam = new HashMap<String, String>();
        mrkParam.put(ParameterKeys.URL.toString(), REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        mrkParam.put(ParameterKeys.METADATA.toString(), metadata);
        mrkParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), RegistrationCertificateBaseTest.UPDATE_CONTENT_REGCERT_PDF);
        
        ClientResponse response = createDocument(mrkParam);
        String str = response.getEntity(String.class);
        //System.out.println("createRegistrationCertificate:: \n" + str);   

        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for 'registrationCertificate' type in documentId url
        String validDocumentID = "/"+ RegistrationCertificate.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));

        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/registration-certificate/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdImagePostFix));
    }

    /**
     * Test update registrationCertificate content.
     */
    private void testUpdateRegistrationCertificateContent(UrlInputDto urlInputOne) {
        String REGISTRATION_CERTIFICATE_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);          
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(UPDATE_CONTENT_REGCERT_PDF);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        WebResource webResource = client.resource(REGISTRATION_CERTIFICATE_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);     
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        //String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateRegistrationCertificateContent:: \n" + haystack);          
        assertEquals(400, response.getStatus());
    }

    /**
     * Test update registrationCertificate content and metadata.
     */
    private void testUpdateRegistrationCertificateContentAndMetadata(UrlInputDto urlInputOne) {
        ClientResponse response = updateRegistrationCertificateContentAndMetadata(urlInputOne, VALUE_REGCERT_METADATA_ONE);
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateRegistrationCertificateContentAndMetadata:: \n" + haystack);        
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
    
        //check for 'registrationCertificate' type in documentId url
        String validDocumentID = "/"+ RegistrationCertificate.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));
    
        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/registration-certificate/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdImagePostFix));          
    
    }

    private ClientResponse updateRegistrationCertificateContentAndMetadata(UrlInputDto urlInputOne, String metadata) {
        String REGISTRATION_CERTIFICATE_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);         
    
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(RegistrationCertificateBaseTest.UPDATE_CONTENT_REGCERT_PDF);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), metadata));
        WebResource webResource = client.resource(REGISTRATION_CERTIFICATE_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        assertEquals(200, response.getStatus());
        return response;
    }

}
