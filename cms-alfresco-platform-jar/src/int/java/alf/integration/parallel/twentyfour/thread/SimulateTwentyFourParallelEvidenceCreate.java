package alf.integration.parallel.twentyfour.thread;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.parallel.base.Callback;
import alf.integration.service.all.base.EvidenceBaseTest;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class SimulateTwentyFourParallelEvidenceCreate extends EvidenceBaseTest {

	private Callback[] callbackArray = null;
	
    public Callback[] getCallbackArray() {
		return callbackArray;
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

    /**
     * Parallel Evidence create test. For DE17961
     * @throws InterruptedException 
     */
    @Test
    public void createEvidence24ParallelThreadTest() throws InterruptedException{
    	CreateEvidenceThread cet = null;
    	
		int threadCount = CreateEvidenceThread.getResouceData().size();
		callbackArray = new Callback[threadCount];
		for(int i=0; i<threadCount; i++){
			callbackArray[i] = new Callback();
			cet = new CreateEvidenceThread(i, callbackArray[i]);
			Thread t1 = new Thread(cet);
			t1.start();
			//System.out.println("i from for loop :" + i);
		}   
		
		//Pause for 10 seconds
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		
		for(int i=0; i<threadCount; i++){
			Callback[] callbackArray = this.getCallbackArray(); 
			Callback cb = callbackArray[i];
			//System.out.println("\n" + cb.getInvocationUrl());
			//System.out.print(cb.getResponseStatus()+ " --> " + cb.getResponseMessage() + "\n");
			assertEquals(201, Integer.parseInt(cb.getResponseStatus()));
/*			if(201 == Integer.parseInt(cb.getResponseStatus())){
				System.out.println("\n" + cb.getInvocationUrl());
				System.out.print(cb.getResponseStatus()+ " --> " + cb.getResponseMessage() + "\n");				
			}*/
		} 		
    	
    }   

 
}
