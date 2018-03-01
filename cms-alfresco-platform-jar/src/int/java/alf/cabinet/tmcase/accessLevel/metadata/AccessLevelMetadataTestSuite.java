package alf.cabinet.tmcase.accessLevel.metadata;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code EfileTestSuite} is a JUnit 4 test suite that will run all of the cleanup test cases.
 * This suite can be run alone or can be executed by {@link AccessLevelMetadataTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    InternalEvidenceRetrieveMetadataTests.class,
    PublicSpecimenRetrieveMetadataTests.class,
    RestrictedNoticeRetrieveMetadataTests.class,
    AccessLevelOtherTests.class
})

public class AccessLevelMetadataTestSuite {
	//Junit 4 test suites do not have a class body
}
