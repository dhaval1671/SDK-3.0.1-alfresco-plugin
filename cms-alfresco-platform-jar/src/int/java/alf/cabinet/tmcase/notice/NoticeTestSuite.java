package alf.cabinet.tmcase.notice;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code NoticeTestSuite} is a JUnit 4 test suite that will run all of the NoticeTestSuite test cases.
 * This suite can be run alone or can be executed by {@link NoticeTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateNoticeTests.class,
    RetrieveNoticeContentTests.class,
    RetrieveNoticeMetadataTests.class

})
public class NoticeTestSuite {
	//Junit 4 test suites do not have a class body
}
