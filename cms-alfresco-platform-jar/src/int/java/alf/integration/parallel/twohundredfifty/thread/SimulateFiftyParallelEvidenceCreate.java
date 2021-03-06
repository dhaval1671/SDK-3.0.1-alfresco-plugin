package alf.integration.parallel.twohundredfifty.thread;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import alf.integration.service.all.base.EvidenceBaseTest;

/**
 * The Class CreateEvidenceTest.
 *
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 */
public class SimulateFiftyParallelEvidenceCreate extends EvidenceBaseTest {

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
    }    

    /**
     * Parallel Evidence create test.
     * @throws InterruptedException 
     */
    @Test
    public void createEvidence50ParallelThreadTest() throws InterruptedException{
    	CreateEvidenceFiftyThread cet = null;
    	/*Load of 50 thread is always handled grace fully..*/
    	/*Load of 65 Parallel thread is doing fine, but failing at 70 thread. */
    	/*Load of 60 also fails someitmes..sometimes passes..*/
		int threadCount = 50;
		callbackArray = new Callback[threadCount];
		for(int i=0; i<threadCount; i++){
			callbackArray[i] = new Callback();
			cet = new CreateEvidenceFiftyThread(i, callbackArray[i]);
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
