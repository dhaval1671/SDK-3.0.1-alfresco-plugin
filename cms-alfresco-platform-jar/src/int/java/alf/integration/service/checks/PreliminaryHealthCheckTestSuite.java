package alf.integration.service.checks;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code PreliminaryHealthCheckTestSuite} is a JUnit 4 test suite that will run all of the Note test cases.
 * This suite can be run alone or can be executed by {@link PreliminaryHealthCheckTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    ValidateTopLevelFoldersWithJson.class
})
public class PreliminaryHealthCheckTestSuite {
	//Junit 4 test suites do not have a class body
}
