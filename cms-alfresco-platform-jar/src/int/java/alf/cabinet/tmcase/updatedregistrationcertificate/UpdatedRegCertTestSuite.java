package alf.cabinet.tmcase.updatedregistrationcertificate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code RegistrationCertificateTestSuite} is a JUnit 4 test suite that will
 * run all of the RegistrationCertificateTestSuite test cases. This suite can be
 * run alone or can be executed by {@link UpdatedRegCertTestSuite}.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        // list the test suites or tests to be run
        CreateUpdatedRegCertTests.class, UpdateUpdatedRegCertTest.class, RetrieveUpdatedRegCertContentTests.class,
        RetrieveUpdatedRegCertMetadataTests.class, DeleteUpdatedRegCertTests.class })
public class UpdatedRegCertTestSuite {
    // Junit 4 test suites do not have a class body
}
