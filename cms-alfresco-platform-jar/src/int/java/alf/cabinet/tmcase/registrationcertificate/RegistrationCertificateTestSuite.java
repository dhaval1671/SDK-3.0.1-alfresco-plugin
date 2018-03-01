package alf.cabinet.tmcase.registrationcertificate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code RegistrationCertificateTestSuite} is a JUnit 4 test suite that will
 * run all of the RegistrationCertificateTestSuite test cases. This suite can be
 * run alone or can be executed by {@link RegistrationCertificateTestSuite}.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        // list the test suites or tests to be run
        CreateRegistrationCertificateTests.class, RetrieveRegistrationCertificateMetadataTests.class,
        RetrieveRegistrationCertificateContentTests.class, UpdateRegistrationCertificateTest.class,
        DeleteRegistrationCertificateTests.class })
public class RegistrationCertificateTestSuite {
    // Junit 4 test suites do not have a class body
}
