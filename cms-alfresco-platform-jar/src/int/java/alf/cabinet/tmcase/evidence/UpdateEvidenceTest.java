package alf.cabinet.tmcase.evidence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.base.TmnguiBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseUpdateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author stank
 *
 */

public class UpdateEvidenceTest extends EvidenceBaseTest {

    /** The log. */
    private static Logger LOG = Logger.getLogger(UpdateEvidenceTest.class);

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
     * Test update evidence metadata first.
     */
    @Test
    public void testUpdateEvidenceMetadataFirst() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        //System.out.println(EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = VALUE_EVI_UPDATED_METADATA_ONE;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_EVI_UPDATED_METADATA_ONE));
            b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
            o = multiPart;
            try {
                multiPart.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, o);
        String haystack = response.getEntity(String.class);
        //System.out.println("Alfresco Layer: " + haystack);
        assertEquals(200, response.getStatus());
        String needle = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral(haystack, needle));
        
        testUpdateEvidenceMetadataWithLowerVersionAttributePresent();
        testUpdateEvidenceMetadataWithHigherVersionAttributePresent();
    }

    private void testUpdateEvidenceMetadataWithLowerVersionAttributePresent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            webResource.queryParam(ParameterKeys.VERSION.toString(), "1.0");
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = VALUE_EVI_UPDATED_METADATA_WITH_LOWER_VERSION_ATTRIB;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(),
                    VALUE_EVI_UPDATED_METADATA_WITH_LOWER_VERSION_ATTRIB));
            b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
            o = multiPart;
            try {
                multiPart.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, o);
        //String haystack = response.getEntity(String.class);
        // System.out.println(haystack);
        assertEquals(200, response.getStatus());
        /*
         * String needle =
         * "version(.*?) attribute is NOT allowed while updating metadata";
         * assertTrue(containsStringWithRegEx(haystack, needle));
         */

    }


    private void testUpdateEvidenceMetadataWithHigherVersionAttributePresent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            webResource.queryParam(ParameterKeys.VERSION.toString(), "1.9");
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = VALUE_EVI_UPDATED_METADATA_WITH_HIGHER_VERSION_ATTRIB;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(),
                    VALUE_EVI_UPDATED_METADATA_WITH_HIGHER_VERSION_ATTRIB));
            b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
            o = multiPart;
            try {
                multiPart.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.post(ClientResponse.class, o);
        //String haystack = response.getEntity(String.class);
        // System.out.println(haystack);
        assertEquals(200, response.getStatus());
        /*
         * String needle =
         * "version(.*?) attribute is NOT allowed while updating metadata";
         * assertTrue(containsStringWithRegEx( haystack, needle));
         */

    }

    /**
     * Test update evidence content.
     */
    @Test
    public void testUpdateEvidenceContent() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString())
                .fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(CONTENT_EVI_ATTACHMENT);
        } catch (FileNotFoundException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info(e.getMessage(), e);
            }
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        // multiPart.bodyPart(new FormDataBodyPart(PARAM_METADATA,
        // VALUE_METADATA));
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, multiPart);

        // String str = response.getEntity(String.class);
        // System.out.println(str);
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            assertEquals(415, response.getStatus());
        } else {
            assertEquals(400, response.getStatus());
        }
    }

    /**
     * Test update evidence content and metadata.
     */
    @Test
    public void testUpdateEvidenceContentAndMetadata() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_EVIDENCE_FILE_NAME);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInput);
        FormDataContentDisposition disposition = FormDataContentDisposition.name(ParameterKeys.CONTENT.toString()).fileName(ParameterKeys.FILE_NAME.toString()).build();
        FormDataMultiPart multiPart = new FormDataMultiPart();
        FileInputStream content = null;
        try {
            content = new FileInputStream(CONTENT_EVI_ATTACHMENT);
        } catch (FileNotFoundException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info(e.getMessage(), e);
            }
            LOG.error(e.getMessage(), e);
        }
        multiPart.bodyPart(new FormDataBodyPart(disposition, content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC));
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, multiPart);

        if (TmnguiBaseTest.runAgainstCMSLayer) {
            assertEquals(415, response.getStatus());
        } else {
            // Previously in alfresco we used to have the update url without
            // metadata to update evidence metadata. So when user passing the
            // metadata and content to this service, it was updating both. For
            // this there was a check
            // in alfresco if user passing content for evidence metadata update,
            // it was throwing 403. After the generic changes, we have specific
            // update evidence
            // metadata url and we don't need to handle this. In this case even
            // if
            // user sends content, system ignores and updates only metadata.
            //String str = response.getEntity(String.class);
            //System.out.println(str);
            assertEquals(200, response.getStatus());
        }

    }
    
    /**
     * Try updating evidence metadata when the original access level is public.
     */
    @Test
    public void tryUpdatingEvidenceMetadataWhenTheOriginalAccessLevelIsPublic() {
        String caseEvidenceFileName = "myEvidenceFileUno.pdf";
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(caseEvidenceFileName);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        
        ClientResponse responseOne = createDocument(eviParam);
        String str = responseOne.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, responseOne.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Evidence.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
        
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputTwo.setFileName(caseEvidenceFileName);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInputTwo);
        // System.out.println(EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = VALUE_EVI_UPDATED_METADATA_ONE;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL));
            b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
            o = multiPart;
            try {
                multiPart.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseTwo = b.post(ClientResponse.class, o);
        //String haystackTwo = responseTwo.getEntity(String.class);
        //System.out.println("Alfresco Layer: " + haystackTwo);
        assertEquals(200, responseTwo.getStatus());
        //String needleTwo = "\"version\":\"1.1\"";
        //assertTrue(containsStringLiteral(haystackTwo, needleTwo));
    }    
    
    /**
     * Try updating evidence metadata access level to public when the original access level is set to internal.
     */
    @Test
    public void tryUpdatingEvidenceMetadataAccessLevelToPublicWhenTheOriginalAccessLevelIsSetToInternal() {
        String caseEvidenceFileName = "myEvidenceFileJack.pdf";
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(caseEvidenceFileName);
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_INTERNAL);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        
        ClientResponse responseOne = createDocument(eviParam);
        String str = responseOne.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, responseOne.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Evidence.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          
        
        UrlInputDto urlInputTwo = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInputTwo.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInputTwo.setFileName(caseEvidenceFileName);
        String EVI_UPDATE_WEBSCRIPT_URL = CaseUpdateUrl.updateEvidenceMetadataUrl(urlInputTwo);
        // System.out.println(EVI_UPDATE_WEBSCRIPT_URL);
        WebResource webResource = client.resource(EVI_UPDATE_WEBSCRIPT_URL);
        Builder b = null;
        Object o = null;
        if (TmnguiBaseTest.runAgainstCMSLayer) {
            b = webResource.type(MediaType.APPLICATION_JSON);
            o = VALUE_EVI_UPDATED_METADATA_ONE;
        } else {
            FormDataMultiPart multiPart = new FormDataMultiPart();
            multiPart.bodyPart(new FormDataBodyPart(ParameterKeys.METADATA.toString(), VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC));
            b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
            o = multiPart;
            try {
                multiPart.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse responseTwo = b.post(ClientResponse.class, o);
        String haystackTwo = responseTwo.getEntity(String.class);
        //System.out.println("Alfresco Layer: " + haystackTwo);
        assertEquals(200, responseTwo.getStatus());
        String needleTwo = "\"version\":\"1.1\"";
        assertTrue(containsStringLiteral(haystackTwo, needleTwo));
    }     

}
