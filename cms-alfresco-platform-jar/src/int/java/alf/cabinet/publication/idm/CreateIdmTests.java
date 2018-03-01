package alf.cabinet.publication.idm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import alf.integration.service.all.base.IdmBaseTest;
import alf.integration.service.all.base.NoteBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.IdmUrlInputDto;
import alf.integration.service.url.helpers.publication.PublicationCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.idmanual.Idm;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class CreateIdmTests extends IdmBaseTest {

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
     * Note create and recreate test.
     */
    @Test
    public void idmCreateAndRecreateTest() {
        Map<String, String> idmParam = new HashMap<String, String>();
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME);
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);
        idmParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        //idmParam.put(ParameterKeys.METADATA.toString(), VALUE_IDM_METADATA);
        idmParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), IdmBaseTest.CONTENT_IDM_ATTACHMENT);
        testCreateIdm(idmParam);
        testDuplicateIdmCreation(idmParam);
    }

    /**
     * Test create note.
     *
     * @param noteParam
     *            the note param
     */
    public void testCreateIdm(Map<String, String> noteParam) {

        ClientResponse response = createDocument(noteParam);

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\"";
        assertFalse(containsStringLiteral(haystack, needle));

        // check for valid docuemnt ID
        String validDocumentID = "/publication/(.*?)/" + Idm.TYPE + "/";// ;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, validDocumentID));        

    }

    /**
     * Test duplicate note creation.
     *
     * @param noteParam
     *            the note param
     */
    public void testDuplicateIdmCreation(Map<String, String> noteParam) {
        ClientResponse response = createDocument(noteParam);
        assertEquals(409, response.getStatus());
    }
    
    /**
     * Idm create with metadata parameter.
     */
    @Test
    public void idmCreateWithMetadataParameter() {
        Map<String, String> idmParam = new HashMap<String, String>();
        IdmUrlInputDto urlInput = new IdmUrlInputDto(TradeMarkPublicationTypes.TYPE_IDM.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_IDM_FILE_NAME_TWO);
        String IDM_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createIdmanualUrl(urlInput);
        idmParam.put(ParameterKeys.URL.toString(), IDM_CREATE_WEBSCRIPT_URL);
        idmParam.put(ParameterKeys.METADATA.toString(), VALUE_IDM_METADATA);
        idmParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        ClientResponse response = createDocument(idmParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(400, response.getStatus());
    }      

}
