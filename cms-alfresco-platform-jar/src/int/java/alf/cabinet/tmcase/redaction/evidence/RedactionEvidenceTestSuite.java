package alf.cabinet.tmcase.redaction.evidence;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code ReceiptTestSuite} is a JUnit 4 test suite that will run all of the ReceiptTestSuite test cases.
 * This suite can be run alone or can be executed by {@link RedactionEvidenceTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    RedactUpdateToEvidenceTests.class,
    RedactedRetrieveEvidenceContentTests.class,
    RedactedRetrieveEvidenceMetadataTests.class,
    RestoreEvidenceTests.class

})

public class RedactionEvidenceTestSuite {
	//Junit 4 test suites do not have a class body
}
