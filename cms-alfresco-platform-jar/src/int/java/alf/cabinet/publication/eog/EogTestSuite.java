package alf.cabinet.publication.eog;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * {@code NoteTestSuite} is a JUnit 4 test suite that will run all of the Note test cases.
 * This suite can be run alone or can be executed by {@link EogTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateEogTests.class,
    RetrieveEogContentTests.class,
    EogTestCreateUpdateMimeType.class

})
public class EogTestSuite {
	//Junit 4 test suites do not have a class body
}
