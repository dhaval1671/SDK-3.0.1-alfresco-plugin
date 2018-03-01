package alf.integration.service.cleanup;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code EfileTestSuite} is a JUnit 4 test suite that will run all of the
 * cleanup test cases. This suite can be run alone or can be executed by
 * {@link CleanupTestSuite}.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        // list the test suites or tests to be run
        CleanupCaseData.class, EfileCleanupTestData.class, EogCleanupTestData.class, IdmCleanupTestData.class,
        EogTemplateCleanupTestData.class, MadridCleanupTestData.class, LegalProceedingCleanupTestData.class,
        EvidenceBankCleanupTestData.class
})
public class CleanupTestSuite {
    // Junit 4 test suites do not have a class body
}
