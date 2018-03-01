package alf.cabinet.tmcase.mark.multimedia.video.transform;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

/**
 * {@code NoteTestSuite} is a JUnit 4 test suite that will run all of the Note test cases.
 * This suite can be run alone or can be executed by {@link AllTests}. 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({

    TransformCreateVideoMarkTest.class,
    StackUpdateTransformVideoTest.class,
    TransformMatroskaVideo.class

})
public class TransformVideoTestSuite {
	//Junit 4 test suites do not have a class body
}
