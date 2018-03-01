package alf.integration.parallel.twentyfour.thread;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.parallel.base.Callback;
import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.all.base.ParameterKeys;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.tmcase.CaseCreateUrl;
import alf.integration.service.url.helpers.tmcase.CaseRetrieveMetadataUrl;
import gov.uspto.trademark.cms.repo.constants.TradeMarkDocumentTypes;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class CreateEvidenceThread extends EvidenceBaseTest implements Runnable{

	private int i;
	private Callback callback;
	
	public CreateEvidenceThread(int i, Callback callback) {
    	this.i = i;
    	this.callback = callback;
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
        /* method known to be empty */
    }    

    @Test
    public void evidenceCreateAndRecreateTest(int i){
        UrlInputDto urlInput = new UrlInputDto(TradeMarkDocumentTypes.TYPE_EVIDENCE.getAlfrescoTypeName());
        urlInput.setSerialNumber(getCaseNumberFromVariable(i));
        urlInput.setFileName(getFileNameFromVariable(i));
        String EVI_CREATE_WEBSCRIPT_URL = CaseCreateUrl.returnGenericCreateUrl(urlInput);     
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        testCreateEvidence(null, eviParam);
    }

	private String getFileNameFromVariable(int i) {
		
		//Thread thread = Thread.currentThread();
		//String evidenceFileName = "Parallel"+ thread.getName() +"Evidence" + "_" + i + "_" + "File.pdf";		
		
		String evidenceFileName = "ParallelThread01Evidence" + "_" + i + "_" + "File.pdf";
		return evidenceFileName;
	}

	private String getCaseNumberFromVariable(int i) {
		int caseNumber = 77777795;
		int calculatedCaseNumber = caseNumber + i;
		return Integer.toString(calculatedCaseNumber);
	}      
    
	private String getMixedBagFixedSizedCaseNumberFromVariable(int i) {
		List<String> hm = getResouceData();		
		return hm.get(i);
	}

	protected static List<String> getResouceData() {
		List<String> hm = new ArrayList<String>();
		/*----------------*/
		hm.add("80177798");
		hm.add("80177799");
		hm.add("80177800");
		hm.add("80177801");
		/*----------------*/
		hm.add("80178798");
		hm.add("80178799");
		hm.add("80178800");
		hm.add("80178801");
		/*----------------*/
		hm.add("80277798");
		hm.add("80277799");
		hm.add("80277800");
		hm.add("80277801");
		/*----------------*/
		hm.add("80278798");
		hm.add("80278799");
		hm.add("80278800");
		hm.add("80278801");	
		/*----------------*/
		hm.add("80377798");
		hm.add("80377799");
		hm.add("80377800");
		hm.add("80377801");
		/*----------------*/
		hm.add("80378798");
		hm.add("80378799");
		hm.add("80378800");
		hm.add("80378801");	
		/*----------------*/
		return hm;
	} 		
	
    /**
     * Test create evidence.
     * @param urlInput 
     *
     * @param eviParam the evi param
     */
    public void testCreateEvidence(UrlInputDto urlInput, Map<String, String> eviParam) {
        ClientResponse response = createDocument(eviParam);
        String str = response.getEntity(String.class);
        Callback cb = this.callback;
        cb.setResponseStatus(Integer.toString(response.getStatus()));
        cb.setResponseMessage(str);
        //System.out.println(str);  
        assertEquals(201, response.getStatus());

        
        String haystack = str;
        String needle = "\"version\":\"1.0\"";
        assertTrue(containsStringLiteral( haystack, needle));

        //check for valid docuemnt ID
        String validDocumentID = "/"+ Evidence.TYPE +"/";//;
        assertTrue(containsStringLiteral( haystack, validDocumentID)); 
        
		retrieveEvidence(urlInput, eviParam);
    }

	private void retrieveEvidence(UrlInputDto urlInput2, Map<String, String> eviParam) {
	        String EVI_METADATA_WEBSCRIPT_URL = CaseRetrieveMetadataUrl.retrieveDocumentMetadata(urlInput2);
	        //System.out.println(EVI_METADATA_WEBSCRIPT_URL);
	        WebResource webResource = client.resource(EVI_METADATA_WEBSCRIPT_URL);
	        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
	        assertEquals(200, response.getStatus());
	        //String str = response.getEntity(String.class);
	        //System.out.println(response.getStatus() + " --> " + str);	
	        
			//Thread thread = Thread.currentThread();
			//System.out.println("END: " + thread.getName() + " (" + thread.getId() + ")"); 
	}

	@Override
	public void run() {
		
		//Thread thread = Thread.currentThread();
		//System.out.println("START: " + thread.getName() + " (" + thread.getId() + ")");		
		
		//Pause for tiny time.
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
        //System.out.println(EVI_CREATE_WEBSCRIPT_URL);
        Callback cb = this.callback;
        cb.setInvocationUrl(EVI_CREATE_WEBSCRIPT_URL);
        Map<String, String> eviParam = new HashMap<String, String>();
        eviParam.put(ParameterKeys.URL.toString(), EVI_CREATE_WEBSCRIPT_URL);
        eviParam.put(ParameterKeys.METADATA.toString(),VALUE_EVIDENCE_METADATA_ACCESS_LEVEL_PUBLIC);
        eviParam.put(ParameterKeys.CONTENT_ATTACHEMENT.toString(), EvidenceBaseTest.CONTENT_EVI_ATTACHMENT);
        testCreateEvidence(urlInput, eviParam);
		
	}

}
