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
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.UpdatedRegCertBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseDeleteUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.UpdatedRegistrationCertificate;

/**
 * The Class CreateRegistrationCertificateTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class DE15548 extends UpdatedRegCertBaseTest {

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
    
    @Test
    public void de15548(){
   
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_UPDATED_REGISTRATION_CERTIFICATE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_URC_FILE_NAME);
        
        uRCCreateTest(urlInput);
        updateURCMetadata(urlInput);
        testDeleteRegistrationCertificate(urlInput);
    }

    
    

    private void uRCCreateTest(UrlInputDto urlInput){
        String REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        Map<String, String> registrationCertificateParam = new HashMap<String, String>();
        registrationCertificateParam.put(ParameterKeys.URL.toString(),REGISTRATION_CERTIFICATE_CREATE_WEBSCRIPT_URL);
        String VALUE_URC_METADATA_ONE = "{ \"accessLevel\" : \"public\",   \"documentAlias\": \"DocNameForTmngUiDisplay\", \"modifiedByUserId\" : \"CreateApplicationIntegraTestV_1_0\",   \"sourceSystem\" : \"TMNG\",   \"sourceMedia\" : \"electronic\",   \"sourceMedium\" : \"upload\",   \"scanDate\" : \"2014-04-23T13:42:24.962-04:00\" }";        
        registrationCertificateParam.put(ParameterKeys.METADATA.toString(),VALUE_URC_METADATA_ONE);
        registrationCertificateParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), UpdatedRegCertBaseTest.CONTENT_URC_ATTACHMENT);
        testCreateURC(registrationCertificateParam);
    }
    
    private void updateURCMetadata(UrlInputDto urlInputOne) {
        String REGISTRATION_CERTIFICATE_UPDATE_METADATA_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateMetadataUrl(urlInputOne);         
        FormDataMultiPart multiPart = new FormDataMultiPart();
        String valueMrkMetadataOne = "{     \"accessLevel\": \"internal\",     \"documentAlias\": \"VersionOnePointOne\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), valueMrkMetadataOne));
        WebResource webResource = client.resource(REGISTRATION_CERTIFICATE_UPDATE_METADATA_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        assertEquals(200, response.getStatus());
        String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateMetadata:: \n" + haystack);  
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral( haystack, needle));
    } 
    
    private void testDeleteRegistrationCertificate(UrlInputDto urlInput) {
        String DELETE_WEBSCRIPT_URL = CaseDeleteUrl.genericDeleteUrl(urlInput);
        WebResource webResource = client.resource(DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.delete(ClientResponse.class);
        String haystack = response.getEntity(String.class);
        System.out.println("testDeleteRegistrationCertificate:: \n" + haystack);  
        assertEquals(200, response.getStatus());
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



}
