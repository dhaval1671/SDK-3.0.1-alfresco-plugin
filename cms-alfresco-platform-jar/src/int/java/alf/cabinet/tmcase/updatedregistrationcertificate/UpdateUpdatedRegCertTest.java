package alf.cabinet.tmcase.updatedregistrationcertificate;

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
import alf.integration.service.all.base.UpdatedRegCertBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.UpdatedRegistrationCertificate;

/**
 * 
 * @author stank
 *
 */

public class UpdateUpdatedRegCertTest extends UpdatedRegCertBaseTest {

    /** The log. */
    private static Logger LOG = Logger.getLogger(UpdateUpdatedRegCertTest.class);

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
    public void uRCUpdateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_UPDATED_REGISTRATION_CERTIFICATE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(UPDATE_URC_FILE_NAME);        
        createURCForUpdateTestCases(urlInput, VALUE_URC_METADATA_ONE);
        updateURCMetadata(urlInput);
        testUpdateURCContent(urlInput);
        updateURCContentAndMetadata(urlInput);
    }      

    private void createURCForUpdateTestCases(UrlInputDto urlInput, String metadata) {
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
        String validDocumentID = "/"+ UpdatedRegistrationCertificate.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));

        //check for documentId url
        String docIdImagePostFix = "\"documentId\":\"/case/(.*?)/updated-registration-certificate/(.*?)\"";//;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docIdImagePostFix));
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
    
    /**
     * Test update registrationCertificate content.
     */
    private void testUpdateURCContent(UrlInputDto urlInputOne) {
        String REGISTRATION_CERTIFICATE_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.genericUpdateContentUrl(urlInputOne);          
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(UPDATE_CONTENT_URC_PDF);
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

    private ClientResponse updateURCContentAndMetadata(UrlInputDto urlInputOne) {
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
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_URC_METADATA_ONE));
        WebResource webResource = client.resource(REGISTRATION_CERTIFICATE_UPDATE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, multiPart);
        //String haystack = response.getEntity(String.class);
        //System.out.println("testUpdateURCContent:: \n" + haystack);        
        assertEquals(400, response.getStatus());
        return response;
    }

}
