package alf.cabinet.tmcase.officeaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

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
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.OfficeAction;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class RetrieveOfficeActionMetadataTests extends OfficeActionBaseTest {

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
     * Test retrieve office action metadata.
     */
    @Test
    public void testRetrieveOfficeActionMetadata() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX + VALUE_OFFACTN_FILE_NAME + OfficeActionBaseTest.URL_POSTFIX;
        String OFFICEACTION_METADATA_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;          
        WebResource webResource = client.resource(OFFICEACTION_METADATA_WEBSCRIPT_URL);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(200, response.getStatus());

        String haystack = str;

        //check for creationTime's presence
        String creationTime = "\"creationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, creationTime));

        //check for modificationTime's presence
        String modificationTime = "\"modificationTime\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, modificationTime));

        //check for OfficeAction document's size presence
        String size = "\"documentSize\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, size));

        //check for mimetype's presence
        String mimetype = "\"mimeType\"(.*?)\"(.+?)\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, mimetype));

        String needle = "\"documentAlias\"";
        assertTrue(containsStringLiteral( haystack, needle));  

        String docType = "\"documentType\"(.*?)\""+ OfficeAction.TYPE + "\"";
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( haystack, docType));          
    }

    /**
     * Test retrieve office action metadata with valid version.
     */
    @Test
    public void testRetrieveOfficeActionMetadataWithValidVersion() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX + VALUE_OFFACTN_FILE_NAME + OfficeActionBaseTest.URL_POSTFIX;
        String OFFICEACTION_METADATA_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;         
        WebResource webResource = client.resource(OFFICEACTION_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_VALID);
        Builder b = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
        b = b.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = b.get(ClientResponse.class);
        //String str = response.getEntity(String.class);
        //System.out.println(str);         
        assertEquals(200, response.getStatus());
    }

    /**
     * Test retrieve office action metadata with in valid version.
     */
    @Test
    public void testRetrieveOfficeActionMetadataWithInValidVersion() {
        String WEBSCRIPT_EXT = CentralBase.urlPrefixCmsRestV1Case + ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString() + OfficeActionBaseTest.URL_MIDFIX + VALUE_OFFACTN_FILE_NAME + OfficeActionBaseTest.URL_POSTFIX;
        String OFFICEACTION_METADATA_WEBSCRIPT_URL = ALFRESCO_URL + WEBSCRIPT_EXT;          
        WebResource webResource = client.resource(OFFICEACTION_METADATA_WEBSCRIPT_URL);
        webResource = webResource.queryParam(ParameterKeys.VERSION.toString(), VALUE_VERSION_INVALID);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

}
