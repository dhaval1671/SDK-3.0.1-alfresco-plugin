package alf.cabinet.tmcase.legacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;

import alf.integration.service.all.base.LegacyBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.all.base.ParameterValues;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Legacy;

/**
 * The Class CreateLegacyTests.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateLegacyTests extends LegacyBaseTest{

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {

    }    

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
    }    

    /**
     * Legacy create and recreate test.
     */
    @Test
    public void legacyCreateAndRecreateTest(){

        Map<String, String> legacyParam = new HashMap<String, String>();
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_LEGACY.getAlfrescoTypeName());
        urlInput.setSerialNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_LGCY_FILE_NAME);
        String LEGACY_CREATE_WEBSCRIPT_URL = CaseCreateUrl.getCreateLegacyUrl(urlInput);       
        //System.out.println(LEGACY_CREATE_WEBSCRIPT_URL);
        legacyParam.put(ParameterKeys.URL.toString(),LEGACY_CREATE_WEBSCRIPT_URL);
        legacyParam.put(ParameterKeys.METADATA.toString(),VALUE_LGCY_METADATA);
        legacyParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), LegacyBaseTest.CONTENT_LGCY_ATTACHMENT);        

        testCreateLegacy(legacyParam);
        testDuplicateLegacyCreation(legacyParam);
    }    

    /**
     * Test create legacy.
     *
     * @param legacyParam the legacy param
     */
    private void testCreateLegacy(Map<String, String> legacyParam) {

        ClientResponse response = createDocument(legacyParam);        
        String str = response.getEntity(String.class);
        //System.out.println(str); 
        String haystack = str;

        assertEquals(201, response.getStatus());        

        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Legacy.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));  

    }

    /**
     * Test duplicate legacy creation.
     *
     * @param legacyParam the legacy param
     */
    private void testDuplicateLegacyCreation(Map<String, String> legacyParam) {
        ClientResponse response = createDocument(legacyParam);           
        assertEquals(409, response.getStatus());

    }  

}
