package alf.sporadic.parallel.threading.DE15251;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class CreateEvidenceThread extends EvidenceBaseTest implements Runnable{

	private int i;
	
    public CreateEvidenceThread(int i) {
    	this.i = i;
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

    @Test
    public void evidenceCreateAndRecreateTest(int i){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(getCaseNumberFromVariable(i));
        urlInput.setFileName(getFileNameFromVariable(i));
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        testCreateEvidence(eviParam);
    }

	private String getFileNameFromVariable(int i) {
		String evidenceFileName = "Evidence" + "_" + i + "_" + "File.pdf";
		return evidenceFileName;
	}

	private String getCaseNumberFromVariable(int i) {
		int caseNumber = 77777795;
		int calculatedCaseNumber = caseNumber + i;
		return Integer.toString(calculatedCaseNumber);
	}      
    
	private String getMixedBagFixedSizedCaseNumberFromVariable(int i) {
		List<String> hm = new ArrayList<String>();
		/*----------------*/
		hm.add("77777798");
		hm.add("77777799");
		hm.add("77777800");
		hm.add("77777801");
		/*----------------*/
		hm.add("77778798");
		hm.add("77778799");
		hm.add("77778800");
		hm.add("77778801");
		/*----------------*/
		hm.add("77877798");
		hm.add("77877799");
		hm.add("77877800");
		hm.add("77877801");
		/*----------------*/
		hm.add("77878798");
		hm.add("77878799");
		hm.add("77878800");
		hm.add("77878801");	
		/*----------------*/
		hm.add("77977798");
		hm.add("77977799");
		hm.add("77977800");
		hm.add("77977801");
		/*----------------*/
		hm.add("77978798");
		hm.add("77978799");
		hm.add("77978800");
		hm.add("77978801");	
		/*----------------*/		
		return hm.get(i);
	} 		
	
    /**
     * Test create evidence.
     *
     * @param eviParam the evi param
     */
    public void testCreateEvidence(Map<String, String> eviParam) {
        ClientResponse response = createDocument(eviParam);
        String str = response.getEntity(String.class);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());

        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Evidence.TYPE +"/";//;
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
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(getMixedBagFixedSizedCaseNumberFromVariable(i));
        urlInput.setFileName(getFileNameFromVariable(i));
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        testCreateEvidence(eviParam);
		
	}

}
