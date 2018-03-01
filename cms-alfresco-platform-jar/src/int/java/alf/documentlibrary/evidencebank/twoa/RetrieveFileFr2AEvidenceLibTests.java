package alf.documentlibrary.evidencebank.twoa;

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

import alf.integration.service.all.base.EvidenceBankBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveFileFr2AEvidenceLibTests extends EvidenceBankBaseTest{

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
     * Test retrieve file fr2 a evidence lib.
     */
    @Test
    public void testRetrieveFileFr2AEvidenceLib() {

        String WEBSCRIPT_EXT = EVI_BANK_CMS_REST_PREFIX + "/libraries/evidences/content/file-path/2a/BambooConstructMaterials/2Def-Bamboo.jpg";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;              

        WebResource webResource = client.resource(WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        webResource = webResource.queryParam(PARAM_PATH, VALUE_PATH);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
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
        assertEquals(200, response.getStatus());
        assertEquals("230 KB", fileSize);    
    }

    /**
     * Test retrieve file fr2 a evidence lib with wrong path.
     */
    @Test
    public void testRetrieveFileFr2AEvidenceLibWithWrongPath() {

        String WEBSCRIPT_EXT = EVI_BANK_CMS_REST_PREFIX + "/libraries/evidences/content/file-path/BambooConstructMaterials/2Def-Bamboo.jpg";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;         

        WebResource webResource = client.resource(WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.SERIAL_NUMBER.toString(),ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), VALUE_EVIDENCE_FILE_NAME);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_OCTET_STREAM);        
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }    

}
