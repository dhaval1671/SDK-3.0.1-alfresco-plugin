package alf.cabinet.tmcase.cases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * {@code CaseDocumentsTestSuite} is a JUnit 4 test suite that will run all of the CaseDocumentsTestSuite test cases.
 * This suite can be run alone or can be executed by {@link CaseDocumentsTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    OneA_RetrieveCaseDocumentMetadataTests.class,
    OneB_RetrieveCaseDocumentMetadataTests.class, //This are dependent test on OneA.
    //SearchCaseFoldersTests.class

})
public class CaseDocumentsTestSuite {
	//Junit 4 test suites do not have a class body
}
