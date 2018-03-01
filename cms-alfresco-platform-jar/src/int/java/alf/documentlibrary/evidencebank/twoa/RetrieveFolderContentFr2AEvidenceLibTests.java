package alf.documentlibrary.evidencebank.twoa;

import static org.junit.Assert.assertEquals;

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

import alf.integration.service.all.base.EvidenceBankBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import gov.uspto.trademark.cms.repo.helpers.PathResolver;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveFolderContentFr2AEvidenceLibTests extends EvidenceBankBaseTest{

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD));        
    }    

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {

    }    

    /**
     * Test retrieve folder content fr2 a evidence lib.
     */
    @Test
    public void testRetrieveFolderContentFr2AEvidenceLib() {

        String WEBSCRIPT_EXT = EVI_BANK_CMS_REST_PREFIX + "/" + PathResolver.EVIDENCE_LIBRARY_FOLDER_PATH_PREFIX + "2a/Beeswax";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;          

        WebResource webResource = client.resource(WEBSCRIPT_URL);
        webResource = webResource.queryParam(PARAM_PATH, VALUE_PATH_FROM_FOLDER);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve folder content fr2 a evidence lib with wrong path.
     */
    @Test
    public void testRetrieveFolderContentFr2AEvidenceLibWithWrongPath() {

        String WEBSCRIPT_EXT = EVI_BANK_CMS_REST_PREFIX + "/" + PathResolver.EVIDENCE_LIBRARY_FOLDER_PATH_PREFIX + "2a/Beeswax";
        String WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;          

        WebResource webResource = client.resource(WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.FILE_NAME.toString(), VALUE_EVIDENCE_FILE_NAME);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);        
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(200, response.getStatus());
    }    

}
