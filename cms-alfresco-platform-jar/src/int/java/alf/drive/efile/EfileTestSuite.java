package alf.drive.efile;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code EfileTestSuite} is a JUnit 4 test suite that will run all of the Efile test cases.
 * This suite can be run alone or can be executed by {@link EfileTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateEfileTests.class,
    RetrieveEfileContentTests.class,
    RetrieveEfileMetadataTests.class,
    RetrieveEfileDocumentMetadataTests.class,
    DeleteEfileTests.class,
    CopyMultipleEfilesToMultipleSerialNumbersTests.class

})
public class EfileTestSuite {
	//Junit 4 test suites do not have a class body
}
