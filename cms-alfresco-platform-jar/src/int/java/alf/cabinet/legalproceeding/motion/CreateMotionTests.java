package alf.cabinet.legalproceeding.motion;

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
import alf.integration.service.all.base.cabinet.legalproceeding.MotionBaseTest;
import alf.integration.service.dtos.LegalProceedingUrlInputDto;
import alf.integration.service.url.helpers.tmcase.LegalProceedingUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkLegalProceedingTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.legalproceeding.Motion;

/**

 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */

public class CreateMotionTests extends MotionBaseTest {

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
     * Motion create and recreate test.
     */
    @Test
    public void motionCreateAndRecreateTest(){
        LegalProceedingUrlInputDto urlInput = new LegalProceedingUrlInputDto(TradeMarkLegalProceedingTypes.TYPE_MOTION.getAlfrescoTypeName());
        urlInput.setProceedingNumber(ParameterValues.VALUE_SERIAL_77777777_NUMBER.toString());
        urlInput.setFileName(VALUE_MOTION_FILE_NAME);
        String MOTION_CREATE_WEBSCRIPT_URL = LegalProceedingUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(MOTION_CREATE_WEBSCRIPT_URL);        
        Map<String, String> motionParam = new HashMap<String, String>();        
        motionParam.put(ParameterKeys.URL.toString(),MOTION_CREATE_WEBSCRIPT_URL);
        motionParam.put(ParameterKeys.METADATA.toString(),VALUE_MOTION_METADATA);
        motionParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MotionBaseTest.CONTENT_MOTION_ATTACHMENT);        

        testCreateMotion(motionParam);
        testDuplicateMotionCreation(motionParam);
    }    

    /**
     * Test create motion.
     *
     * @param motionParam the motion param
     */
    private void testCreateMotion(Map<String, String> motionParam) {

        ClientResponse response = createDocument(motionParam);            

        String str = response.getEntity(String.class);
        //System.out.println(str); 
        assertEquals(201, response.getStatus());
        String haystack = str;

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Motion.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));          

    }

    /**
     * Test duplicate motion creation.
     *
     * @param motionParam the motion param
     */
    private void testDuplicateMotionCreation(Map<String, String> motionParam) {

        ClientResponse response = createDocument(motionParam);         
        assertEquals(409, response.getStatus());

    }  

}
