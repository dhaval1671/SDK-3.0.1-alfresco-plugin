package alf.cabinet.tmcase.specimen;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import alf.cabinet.tmcase.receipt.ReceiptTestSuite;

/**
 * {@code ReceiptTestSuite} is a JUnit 4 test suite that will run all of the ReceiptTestSuite test cases.
 * This suite can be run alone or can be executed by {@link ReceiptTestSuite}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateSpecimenTests.class,
    RetrieveSpecimenContentTests.class,
    RetrieveSpecimenMetadataTests.class,
    SpecimenRedactionTests.class

})
public class SpecimenTestSuite {
	//Junit 4 test suites do not have a class body
}
