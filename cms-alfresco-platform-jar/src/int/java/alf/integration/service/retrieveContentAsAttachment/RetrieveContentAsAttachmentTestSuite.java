package alf.integration.service.retrieveContentAsAttachment;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code ReceiptTestSuite} is a JUnit 4 test suite that will run all of the ReceiptTestSuite test cases.
 * This suite can be run alone or can be executed by {@link RetrieveContentAsAttachmentTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    RetrieveTeasPdfContentAsAttachmentTests.class,
    RetrieveTicrsDocumentContentAsAttachmentTests.class,
})

public class RetrieveContentAsAttachmentTestSuite {
	//Junit 4 test suites do not have a class body
}
