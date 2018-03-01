package alf.documentlibrary.evidencebank.twoa;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code EvidenceTestSuite} is a JUnit 4 test suite that will run all of the Evidence test cases.
 * This suite can be run alone or can be executed by {@link DocumentLibraryTestSuite}. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run

    RetrieveFileFr2AEvidenceLibTests.class,
    RetrieveFolderContentFr2AEvidenceLibTests.class,

})
public class DocumentLibraryTestSuite {
	//Junit 4 test suites do not have a class body
}
