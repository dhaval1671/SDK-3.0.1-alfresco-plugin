package alf.cabinet.tmcase.legacy;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code LegacyTestSuite} is a JUnit 4 test suite that will run all of the Note test cases.
 * This suite can be run alone or can be executed by {@link LegacyTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateLegacyTests.class,
    RetrieveLegacyContentTests.class,
    RetrieveLegacyMetadataTests.class

})
public class LegacyTestSuite {
	//Junit 4 test suites do not have a class body
}
