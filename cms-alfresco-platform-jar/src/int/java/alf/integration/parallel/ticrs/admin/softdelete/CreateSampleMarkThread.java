package alf.integration.parallel.ticrs.admin.softdelete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.MarkBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class CreateSampleMarkThread extends EvidenceBaseTest implements Runnable{

	private int i;
	private UrlInputDto urlInput;
	
    public CreateSampleMarkThread(int i) {
    	this.i = i;
	}

	public CreateSampleMarkThread(int i2, UrlInputDto urlInput) {
	    this.i = i2;
	    this.urlInput = urlInput;
	}

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
     * Test create evidence.
     *
     * @param eviParam the evi param
     */
    public void testCreateMark(Map<String, String> eviParam) {
        ClientResponse response = createDocument(eviParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ MarkDoc.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID));        
    }

	@Override
	public void run() {
		//Pause for 4 seconds
        try {
			Thread.sleep(100-i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        //Print a message
/*        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(getMixedBagFixedSizedCaseNumberFromVariable(i));
        urlInput.setFileName(getFileNameFromVariable(i));*/
        
        String MRK_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(MRK_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), MRK_CREATE_WEBSCRIPT_URL);
        String VALUE_MRK_METADATA_ONE = "{     \"accessLevel\": \"public\",     \"documentAlias\": \"nickname\",     \"createdByUserId\": \"CoderX\",     \"modifiedByUserId\": \"metFor1.2\",     \"mailDate\": \"2013-06-25T07:41:19.000-04:00\",     \"loadDate\": \"2013-07-16T07:41:19.000-04:00\",     \"drawingAcceptanceIndicator\": true, \"scanDate\": \"2013-06-16T05:33:22.000-04:00\",     \"effectiveStartDate\": \"2013-07-06T00:00:00.000-00:00\",     \"sourceSystem\": \"TICRS\",     \"sourceMedia\": \"E\",     \"sourceMedium\": \"Email\",  \"documentName\": \"myTM\" }";        
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_MRK_METADATA_ONE);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), MarkBaseTest.CONTENT_MRK_TIF);
        testCreateMark(eviParam);
		
	}

}
