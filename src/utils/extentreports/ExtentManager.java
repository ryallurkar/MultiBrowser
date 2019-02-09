package utils.extentreports;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

//OB: extentreports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			// Set HTML reporting file location
			String workingDir = System.getProperty("user.dir");
			extent = new ExtentReports(workingDir+File.separator+"test-output"+File.separator+"ExtentReportResults.html", true);
		}
		return extent;
	}
}
