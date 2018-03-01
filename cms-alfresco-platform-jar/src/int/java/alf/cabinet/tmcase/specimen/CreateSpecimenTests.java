package alf.cabinet.tmcase.specimen;

import static org.junit.Assert.assertEquals;
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

import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.all.base.SpecimenBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Specimen;

/**
 * The Class CreateSpecimenTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateSpecimenTests extends SpecimenBaseTest {

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

    /**
     * Specimen create and recreate test.
     */
    @Test
    public void specimenCreateAndRecreateTest(){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_SPECIMEN.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_SPECIMEN_FILE_NAME);
        String SPECIMEN_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);
        //System.out.println(SPECIMEN_CREATE_WEBSCRIPT_URL);
        Map<String, String> specimenParam = new HashMap<String, String>();
        specimenParam.put(ParameterKeys.URL.toString(),SPECIMEN_CREATE_WEBSCRIPT_URL);
        specimenParam.put(ParameterKeys.METADATA.toString(),VALUE_SPECIMEN_METADATA);
        specimenParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), SpecimenBaseTest.CONTENT_SPECIMEN_ATTACHMENT);

        testCreateSpecimen(specimenParam);
        testDuplicateSpecimenCreation(specimenParam);
    }

    /**
     * Test create specimen.
     *
     * @param specimenParam the specimen param
     */
    private void testCreateSpecimen(Map<String, String> specimenParam) {

        ClientResponse response = createDocument(specimenParam);       

        String str = response.getEntity(String.class);
        //System.out.println(str);
        assertEquals(201, response.getStatus());
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Specimen.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate specimen creation.
     *
     * @param specimenParam the specimen param
     */
    private void testDuplicateSpecimenCreation(Map<String, String> specimenParam) {
        ClientResponse response = createDocument(specimenParam);         
        assertEquals(409, response.getStatus());
    }  

}
