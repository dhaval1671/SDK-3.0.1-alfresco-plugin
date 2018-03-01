package alf.cabinet.tmcase.evidence;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code EvidenceTestSuite} is a JUnit 4 test suite that will run all of the Evidence test cases.
 * This suite can be run alone or can be executed by {@link EvidenceTestSuite}. 
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateEvidenceTest.class,
    UpdateEvidenceTest.class,
    RetrieveEvidenceContentTests.class,
    RetrieveEvidenceMetadataTests.class,
    //DeleteEvidenceTest.class,
    //DeleteEvidenceAssociatedWithOfficeActionTest.class,
    CopyEvidenceToTrgtTest.class,
    BulkMetadataUpdateEvidenceTest.class,
    TestCreateUpdateMimeType.class,
    Post_DeleteEvidenceTest.class,
    Post_DeleteEvidenceAssociatedWithOfficeActionTest.class
})
public class EvidenceTestSuite {
	//Junit 4 test suites do not have a class body
}
