package alf.cabinet.tmcase.withdrawal;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code NoticeTestSuite} is a JUnit 4 test suite that will run all of the NoticeTestSuite test cases.
 * This suite can be run alone or can be executed by {@link WithdrawalTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateWithdrawalTests.class,
    RetrieveWithdrawalContentTests.class,
    RetrieveWithdrawalMetadataTests.class

})
public class WithdrawalTestSuite {
	//Junit 4 test suites do not have a class body
}
