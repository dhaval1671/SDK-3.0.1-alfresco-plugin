package alf.integration.service.master;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The Class JunitCoreTestRunner.
 */
public class JunitCoreTestRunner {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		Result result = JUnitCore.runClasses(MasterTestSuite.class);
		for (Failure fail : result.getFailures()) {
			System.out.println(fail.toString());
		}
		if (result.wasSuccessful()) {
			System.out.println("All tests finished successfully...");
		}
	}
}