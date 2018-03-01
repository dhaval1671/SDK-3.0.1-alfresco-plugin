package alf.documentlibrary.webcapture;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code EvidenceTestSuite} is a JUnit 4 test suite that will run all of the Evidence test cases.
 * This suite can be run alone or can be executed by {@link WebcaptureTestSuite}. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    
	//list the test suites or tests to be run
    CreateWebcaptureTests.class,
    RetrieveFolderContentOfWebcaptureThruEvidenceLibAPIsTests.class,
    DeleteWebcaptureTests.class,
    RenameWebcaptureTest.class

})
public class WebcaptureTestSuite {
	//Junit 4 test suites do not have a class body
}
