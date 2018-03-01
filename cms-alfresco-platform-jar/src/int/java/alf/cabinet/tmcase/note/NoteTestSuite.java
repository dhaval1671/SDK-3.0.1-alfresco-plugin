package alf.cabinet.tmcase.note;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * {@code NoteTestSuite} is a JUnit 4 test suite that will run all of the Note test cases.
 * This suite can be run alone or can be executed by {@link NoteTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateNoteTests.class,
    RetrieveNoteContentTests.class,
    RetrieveNoteMetadataTests.class

})
public class NoteTestSuite {
	//Junit 4 test suites do not have a class body
}
