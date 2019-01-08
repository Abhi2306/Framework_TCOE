package ExtentReports;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReport {

	public static ExtentReports extent;

	public static ExtentTest ExtentRep(String FilePath_extent, String FileName_extent) {
		
		//Giving file location for storing the results in report
		extent = new ExtentReports(FilePath_extent+"\\"+FileName_extent,false);
		
		//Addition info for the reports
		extent.addSystemInfo("Host Name", "Abhishek");
		extent.addSystemInfo("Environment", "QA");
		extent.addSystemInfo("User Name", "Abhishek Khatod");
		extent.loadConfig(new File(FilePath_extent+"\\"+FileName_extent));
		
		System.out.println(FilePath_extent+"\\"+FileName_extent);
		
		//instance of ExtentTest for starting the test
		ExtentTest test = extent.startTest("Start test");
		
		return test;
	}
	
	
	public static void AfterMethod(ExtentReports extent_1, ExtentTest test_1) {
		
		extent_1.endTest(test_1);
		extent_1.flush();
		extent_1.close();
	}
}
