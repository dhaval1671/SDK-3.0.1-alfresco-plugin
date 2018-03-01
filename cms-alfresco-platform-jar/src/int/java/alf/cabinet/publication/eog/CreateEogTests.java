package alf.cabinet.publication.eog;

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

import alf.integration.service.all.base.EogBaseTest;
import alf.integration.service.all.base.EogParameterValues;
import alf.integration.service.all.base.NoteBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.EogUrlInputDto;
import alf.integration.service.url.helpers.publication.PublicationCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkPublicationTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.publication.officialgazette.Eog;

/**
 * A simple class demonstrating how to run out-of-container tests loading
 * Alfresco application context.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */

public class CreateEogTests extends EogBaseTest {

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
    public void eogCreateAndRecreateTest() {
        Map<String, String> eogParam = new HashMap<String, String>();
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName(VALUE_EOG_FILE_NAME);
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);
        //System.out.println(EOG_CREATE_WEBSCRIPT_URL);
        eogParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        //eogParam.put(ParameterKeys.METADATA.toString(), VALUE_NOTE_METADATA);
        eogParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        testCreateEog_HappyPath(eogParam);
        testDuplicateEogCreation(eogParam);
    }

    /**
     * Test create note.
     *
     * @param noteParam
     *            the note param
     */
    private void testCreateEog_HappyPath(Map<String, String> noteParam) {

        ClientResponse response = createDocument(noteParam);

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\"";
        assertFalse(containsStringLiteral(haystack, needle));

        // check for valid docuemnt ID
        String validDocumentID = "/publication/(.*?)/" + Eog.TYPE + "/";// ;
        assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx(haystack, validDocumentID));

    }

    /**
     * Test duplicate note creation.
     *
     * @param noteParam
     *            the note param
     */
    private void testDuplicateEogCreation(Map<String, String> noteParam) {
        ClientResponse response = createDocument(noteParam);
        assertEquals(409, response.getStatus());
    }

    /**
     * Eog create with invalid date.
     */
    @Test
    public void eogCreateWithInvalidDate() {
        Map<String, String> eogParam = new HashMap<String, String>();
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150631.toString());
        urlInput.setFileName(VALUE_EOG_FILE_NAME);
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);
        eogParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        eogParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        ClientResponse response = createDocument(eogParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(400, response.getStatus());
    }
    
    /**
     * Eog create with metadata parameter.
     */
    @Test
    public void eogCreateWithMetadataParameter() {
        Map<String, String> eogParam = new HashMap<String, String>();
        EogUrlInputDto urlInput = new EogUrlInputDto(TradeMarkPublicationTypes.TYPE_EOG.getAlfrescoTypeName());
        urlInput.setSerialNumber(EogParameterValues.DATE_20150609.toString());
        urlInput.setFileName(VALUE_EOG_FILE_NAME_TWO);
        String EOG_CREATE_WEBSCRIPT_URL = PublicationCreateUrl.createEogUrl(urlInput);
        eogParam.put(ParameterKeys.URL.toString(), EOG_CREATE_WEBSCRIPT_URL);
        eogParam.put(ParameterKeys.METADATA.toString(), VALUE_EOG_METADATA);
        eogParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), NoteBaseTest.CONTENT_NOTE_TTACHMENT);
        ClientResponse response = createDocument(eogParam);
        // String str = response.getEntity(String.class);
        // System.out.println(str);
        assertEquals(400, response.getStatus());
    }    

}
