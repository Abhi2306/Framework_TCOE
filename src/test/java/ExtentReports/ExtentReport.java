package ExtentReports;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import log4j_utility.Log;

public class ExtentReport {

	public static ExtentReports extent;

	public static ExtentTest ExtentRep(String FilePath_extent, String FileName_extent, String TestName) {
		
		//Giving file location for storing the results in report
		extent = new ExtentReports(System.getProperty("user.dir")+"\\test-output\\"+FileName_extent,true);
		
		Log.Info("Extent reports are generating for "+TestName+"..!!");
		
		//Addition info for the reports
		extent.addSystemInfo("Host Name", "Abhishek");
		extent.addSystemInfo("Environment", "QA");
		extent.addSystemInfo("User Name", "Abhishek Khatod");
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\Extent_Config.xml"));
		
		//System.out.println(System.getProperty("user.dir")+"\\test-output\\"+FileName_extent);
		
		//instance of ExtentTest for starting the test
		ExtentTest test = extent.startTest(TestName);
		Log.Info(TestName+" is started..!!");
		
		return test;
	}
	
	
	public static void AfterMethod(ExtentReports extent_1, ExtentTest test_1, String TestName) {
		
		extent_1.endTest(test_1);
		extent_1.flush();
		extent_1.close();
		
		Log.Info("Report is completely generated for "+TestName+"..!!");
	}
}
