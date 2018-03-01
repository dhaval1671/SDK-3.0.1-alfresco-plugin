package alf.cabinet.tmcase.officeaction;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

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

import alf.integration.service.all.base.CentralBase;
import alf.integration.service.all.base.OfficeActionBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;

/**
 * The Class RetrieveOfficeActionContentTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class RetrieveOfficeActionContentTests extends OfficeActionBaseTest{

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
     * Test retrieve office action content.
     */
    @Test
    public void testRetrieveOfficeActionContent() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX + VALUE_OFFACTN_FILE_NAME;
        String OFFICEACTION_CONTENT_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT; 
        //System.out.println(OFFICEACTION_CONTENT_WEBSCRIPT_URL);
        WebResource webResource = client.resource(OFFICEACTION_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
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
        assertEquals("78 KB", fileSize);        
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve office action content for invalid serial number.
     */
    @Test
    public void testRetrieveOfficeActionContentForInvalidSerialNumber() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_INVALID_SERIAL_NUMBER_FORMAT1.toString() + OfficeActionBaseTest.URL_MIDFIX + VALUE_OFFACTN_FILE_NAME;
        String OFFICEACTION_CONTENT_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;        
        WebResource webResource = client.resource(OFFICEACTION_CONTENT_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(400, response.getStatus());
    }     

    /**
     * Test retrieve office action content with valid version.
     */
    @Test
    public void testRetrieveOfficeActionContentWithValidVersion() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX + VALUE_OFFACTN_FILE_NAME;
        String OFFICEACTION_CONTENT_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;         
        WebResource webResource = client.resource(OFFICEACTION_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_VALID);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(200, response.getStatus());
    }    

    /**
     * Test retrieve office action content with in valid version.
     */
    @Test
    public void testRetrieveOfficeActionContentWithInValidVersion() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX + VALUE_OFFACTN_FILE_NAME;
        String OFFICEACTION_CONTENT_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;          
        WebResource webResource = client.resource(OFFICEACTION_CONTENT_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_INVALID);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);   
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }     

}
