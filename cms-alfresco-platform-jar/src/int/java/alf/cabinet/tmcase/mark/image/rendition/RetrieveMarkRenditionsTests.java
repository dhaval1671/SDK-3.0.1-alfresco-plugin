package alf.cabinet.tmcase.mark.image.rendition;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseOtherUrl;
import gov.uspto.trademark.cms.repo.constants.MarkRenditions;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;

/**
 * The Class RetrieveMarkRenditionsTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveMarkRenditionsTests extends MarkBaseTest {

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
     * Test retrieve mark rendition_80_65.
     */
    @Test
    public void testRetrieveMarkRendition_80_65() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_TINY_80X65.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);
        // System.out.println(MARK_RENDITION_WEBSCRIPT_URL);
        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
         //String str = response.getEntity(String.class);
         //System.out.println(str);
        
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);         
        assertEquals(200, response.getStatus());
        
        Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
        presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "??");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", "8 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", "8 KB");        
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);        
    }

    /**
     * Test retrieve valid mark rendition_80_65 with bad serial number.
     */
    @Test
    public void testRetrieveValidMarkRendition_80_65_withBadSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT2.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_TINY_80X65.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve mark rendition_100_100.
     */
    @Test
    public void testRetrieveMarkRendition_100_100() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_SMALL_100X100.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, response.getStatus());
        
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);         
        assertEquals(200, response.getStatus());
        
        Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
        presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "??");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", "18 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", "18 KB");        
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);            
    }

    /**
     * Test retrieve valid mark rendition_100_100 with bad serial number.
     */
    @Test
    public void testRetrieveValidMarkRendition_100_100_withBadSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT2.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_SMALL_100X100.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve mark rendition_160_130.
     */
    @Test
    public void testRetrieveMarkRendition_160_130() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_THUMBNAIL_160X130.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);         
        assertEquals(200, response.getStatus());
        
        Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
        presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "??");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", "29 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", "29 KB");        
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);          
    }

    /**
     * Test retrieve valid mark rendition_160_130 with bad serial number.
     */
    @Test
    public void testRetrieveValidMarkRendition_160_130_withBadSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT2.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_THUMBNAIL_160X130.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve mark rendition_320_260.
     */
    @Test
    public void testRetrieveMarkRendition_320_260() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_LARGE_320X260.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, response.getStatus());
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);         
        assertEquals(200, response.getStatus());
        
        Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
        presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "??");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", "106 KB"); //ImageMagick alfresco default(6.9.1) giving size 107 KB, while ImgMgk Portable(7.0.7) giving as 106KB
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", "107 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", "107 KB");        
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);          
    }

    /**
     * Test retrieve valid mark rendition_320_260 with bad serial number.
     */
    @Test
    public void testRetrieveValidMarkRendition_320_260_withBadSerialNumber() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT2.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.MARK_LARGE_320X260.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(400, response.getStatus());
    }

    /**
     * Test retrieve mark rendition_ image_to_ png.
     */
    @Test
    public void testRetrieveMarkRendition_Image_to_Png() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition(MarkRenditions.IMAGE_TO_PNG.getRenditionName());
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(200, response.getStatus());
        InputStream inputStream = response.getEntity(InputStream.class);
        byte[] byteSize = null;
        try {
            byteSize= org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
        } 

        String fileSize = null;
        if (null != byteSize) {
            fileSize = FileUtils.byteCountToDisplaySize(byteSize.length);
        }
        //System.out.println(fileSize);         
        assertEquals(200, response.getStatus());
        
        Map<String,String> presummedSizeOfTheFileOnDifferentServerEnvs = new HashMap<String,String>();
        presummedSizeOfTheFileOnDifferentServerEnvs.put("ORIGINAL_WINDOWS_OS_CONTENT_SIZE", "??");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("4_1_9_localhost:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("5_1_localhost:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.dev.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.sit.uspto.gov", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-7.sit.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tm-alf-8.sit.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.fqt.uspto.gov", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-1.fqt.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-2.fqt.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alfupgrade.pvt.uspto.gov", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-3.pvt.uspto.gov:8080", "147 KB");
        presummedSizeOfTheFileOnDifferentServerEnvs.put("tmng-alf-4.pvt.uspto.gov:8080", "147 KB");        
        verifyIfRetriveContentIsAccurate(presummedSizeOfTheFileOnDifferentServerEnvs, fileSize);              
    }

    /**
     * Test retrieve mark rendition_80_65_ return400 for invalid rendition name
     * name.
     */
    @Test
    public void testRetrieveMarkRendition_80_65_Return400ForInvalidRenditionNameName() {
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_MARK.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MRK_FILE_NAME);
        urlInput.setRendition("markFunky-80X65-PNG");
        String MARK_RENDITION_WEBSCRIPT_URL = CaseOtherUrl.getRetrieveImageMarkRenditionUrl(urlInput);

        WebResource webResource = client.resource(MARK_RENDITION_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);
        ClientResponse response = b.get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

}
