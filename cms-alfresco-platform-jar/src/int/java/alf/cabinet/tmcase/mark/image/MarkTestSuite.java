package alf.cabinet.tmcase.mark.image;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

import alf.cabinet.tmcase.mark.image.rendition.MORMarkRenditionsTests;
import alf.cabinet.tmcase.mark.image.rendition.RetrieveMarkRenditionsTests;
import alf.cabinet.tmcase.mark.image.rendition.RetrieveRenditionsForNewVersionOfMark;

/**
 * {@code NoteTestSuite} is a JUnit 4 test suite that will run all of the Note test cases.
 * This suite can be run alone or can be executed by {@link AllTests}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	//list the test suites or tests to be run
    CreateMarkTest.class,
    UpdateMarkTest.class,
    RetrieveMarkContentTests.class,
    RetrieveMarkMetadataTests.class,
    RetrieveMarkRenditionsTests.class,
    RetrieveMarkMetadataWithGoodSerialNumberTests.class,
    RetrieveMarkMetadataWithBadSerialNumberTests.class,
    AllMarksVersionedMetadataTests.class,
    MORMarkRenditionsTests.class,
    RetrieveRenditionsForNewVersionOfMark.class

})
public class MarkTestSuite {
	//Junit 4 test suites do not have a class body
}
