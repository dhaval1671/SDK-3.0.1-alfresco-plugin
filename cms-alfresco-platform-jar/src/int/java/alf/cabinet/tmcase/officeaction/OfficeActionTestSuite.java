package alf.cabinet.tmcase.officeaction;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code OfficeActionTestSuite} is a JUnit 4 test suite that will run all of the OfficeActionTestSuite test cases.
 * This suite can be run alone or can be executed by {@link OfficeActionTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateOfficeActionTests.class,
    RetrieveOfficeActionContentTests.class,
    RetrieveOfficeActionMetadataTests.class,
    DeleteOfficeActionTest.class

})
public class OfficeActionTestSuite {
	//Junit 4 test suites do not have a class body
}
