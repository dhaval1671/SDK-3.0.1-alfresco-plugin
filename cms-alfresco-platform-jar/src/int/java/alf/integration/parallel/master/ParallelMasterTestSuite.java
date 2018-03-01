package alf.integration.parallel.master;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import alf.integration.parallel.ticrs.admin.softdelete.SimulateParallelTicrsAdminDelete;
import alf.integration.parallel.twentyfour.thread.SimulateTwentyFourParallelEvidenceCreate;
import alf.integration.parallel.twohundredfifty.thread.SimulateFiftyParallelEvidenceCreate;
import alf.integration.service.checks.PreliminaryHealthCheckTestSuite;
import alf.integration.service.cleanup.CleanupTestSuite;
import alf.intergration.healthstatus.AlfrescoHealthStatusTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * MasterTestSuite.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */


@RunWith(Suite.class)
@SuiteClasses({ CleanupTestSuite.class, PreliminaryHealthCheckTestSuite.class, AlfrescoHealthStatusTestSuite.class, 
    
        /*Parallel thread tests*/
        SimulateParallelTicrsAdminDelete.class,
        SimulateTwentyFourParallelEvidenceCreate.class,
        SimulateFiftyParallelEvidenceCreate.class

        
})
public class ParallelMasterTestSuite {

    /**
     * Suite.
     *
     * @return the test
     */
    public Test suite() {
        TestSuite suite = new TestSuite(ParallelMasterTestSuite.class.getName());
        // $JUnit-BEGIN$

        // $JUnit-END$
        return suite;
    }

}