package alf.cabinet.publication.idm;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * {@code NoteTestSuite} is a JUnit 4 test suite that will run all of the Note test cases.
 * This suite can be run alone or can be executed by {@link IdmTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateIdmTests.class,
    RetrieveIdmContentTests.class,
    DeleteIdmTests.class,
    IdmTestCreateUpdateMimeType.class


})
public class IdmTestSuite {
	//Junit 4 test suites do not have a class body
}
