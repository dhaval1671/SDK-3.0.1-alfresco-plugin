package alf.unit.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code UnitMasterTestSuite} is a JUnit 4 test suite that will run all of the
 * Note test cases. This suite can be run alone or can be executed by
 * {@link UnitMasterTestSuite}.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        // list the test suites or tests to be run

        _01_IntializationTest.class, _02_EfileServiceTest.class, _03_NoteDocServiceTest.class, _04_AttachmentServiceTest.class,
        _05_DocumentRedactionServiceTest.class, _06_EvidenceDocServiceTest.class, _07_LegacyDocServiceTest.class, _08_NoticeDocServiceTest.class,
        _09_OfficeActionDocServiceTest.class, _10_ReceiptDocServiceTest.class, _11_ResponseDocServiceTest.class,
        _12_SignatureDocServiceTest.class, _13_SummaryDocServiceTest.class, _14_EfileSubmissionServiceTest.class, _15_CaseServiceTest.class,
        _16_RegistrationCertificateDocServiceTest.class, _17_WithdrawalServiceTest.class

})
public class UnitMasterTestSuite {
    // Junit 4 test suites do not have a class body
}
