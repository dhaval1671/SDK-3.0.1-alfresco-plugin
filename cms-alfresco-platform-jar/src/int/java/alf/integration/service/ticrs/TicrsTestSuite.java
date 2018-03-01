package alf.integration.service.ticrs;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code TicrsTestSuite} is a JUnit 4 test suite that will run all of the TicrsTestSuite test cases.
 * This suite can be run alone or can be executed by {@link TicrsTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    TicrsDeleteDocumentTests.class,
    TicrsRetrieveMigratedDocsFileNamesTests.class,
    TicrsDeleteDocumentPolicyTests.class,
    TicrsAdminDeleteMultimediaTests.class
})
public class TicrsTestSuite {
	//Junit 4 test suites do not have a class body
}
