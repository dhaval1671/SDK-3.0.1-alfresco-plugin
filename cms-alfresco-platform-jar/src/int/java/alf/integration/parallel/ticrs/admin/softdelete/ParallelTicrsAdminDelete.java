package alf.integration.parallel.ticrs.admin.softdelete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

import alf.integration.service.all.base.EvidenceBaseTest;
import alf.integration.service.dtos.UrlInputDto;
import alf.integration.service.url.helpers.TicrsRelatedUrl;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class ParallelTicrsAdminDelete extends EvidenceBaseTest implements Runnable{

	private int i;
    private UrlInputDto urlInput;	
	
    public ParallelTicrsAdminDelete(int i) {
    	this.i = i;
	}
    
    public ParallelTicrsAdminDelete(int i2, UrlInputDto urlInput) {
        this.i = i2;
        this.urlInput = urlInput;
    }    
    
    static private ArrayList<Integer> possibleResponseCodes = new ArrayList<Integer>();
    static {
        possibleResponseCodes.add(200);
        possibleResponseCodes.add(404);
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

	@Override
	public void run() {
	    
	    
		//Pause for 4 seconds
        try {
			Thread.sleep(100-i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        //Thread t = Thread.currentThread();
        //String name = t.getName();
        //System.out.println("ThreadName=" + name);        
        
        //delete using ticrs api.
        //String TICRS_ADMIN_DELETE_WEBSCRIPT_URL = ALFRESCO_URL + CentralBase.urlPrefixCmsRestV1AdminCase + urlInput.getSerialNumber() + "/" + urlInput.getFileName();
        String TICRS_ADMIN_DELETE_WEBSCRIPT_URL = TicrsRelatedUrl.ticrsAdminDeleteUrl(urlInput);
        //System.out.println("DELETE : " + TICRS_ADMIN_DELETE_WEBSCRIPT_URL);
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getState().setCredentials(null, null, -1, ALF_ADMIN_LOGIN_USER_ID, ALF_ADMIN_LOGIN_PWD);
        ApacheHttpClient client = ApacheHttpClient.create(config);
        WebResource webResource = client.resource(TICRS_ADMIN_DELETE_WEBSCRIPT_URL);
        Builder b = webResource.type(MediaType.APPLICATION_JSON);
        ClientResponse responseTwo = b.delete(ClientResponse.class);        
        String strTwo = responseTwo.getEntity(String.class);
        //System.out.println(responseTwo.getStatus() + " :: " + strTwo);
        
        int sizeOfList = possibleResponseCodes.size();
        if(2 == sizeOfList){
            //System.out.println("sizeOfList:" + sizeOfList + ": ThreadName=" + name + " : " + responseTwo.getStatus());
            //System.out.println(strTwo);
            assertEquals(200, responseTwo.getStatus()); 
            String docIdImagePostFix = "\"documentId\":\"/case/"+urlInput.getSerialNumber()+"/mark/"+urlInput.getFileName()+"\"";//;
            assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( strTwo, docIdImagePostFix)); 
            possibleResponseCodes.remove(new Integer("200"));
        }else if(1 == sizeOfList){
            //System.out.println("sizeOfList:" + sizeOfList + ": ThreadName=" + name + " : " + responseTwo.getStatus());
            //System.out.println(strTwo);
            assertEquals(404, responseTwo.getStatus()); 
            String docIdImagePostFix = "Requested File (.*?) Not Found";//;
            assertTrue(containsOneOrMoreOccurencesOfProvidedStringOfGivenRegEx( strTwo, docIdImagePostFix));
            possibleResponseCodes.remove(new Integer("404"));
        }
        
	}

}
