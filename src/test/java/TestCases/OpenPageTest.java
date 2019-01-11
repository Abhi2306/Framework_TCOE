package TestCases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import BaseDriver.BasePage;
import ExtentReports.ExtentReport;
import excelconfig.ReadExcelData;
import excelconfig.WriteExcelData;
import log4j_utility.Log;

public class OpenPageTest {

	WebDriver driver;
	BasePage base;
	Properties prop;

	// Selection of browser type to be worked upon
	//@Parameters({ "URL", "Browser_Type" })
	//@BeforeTest
	public void OpenPage(String url, String Browser_Type) {

		DOMConfigurator.configure("log4j.xml");
		Log.StartTestCase("Test_Case_001");

		if (Browser_Type.equalsIgnoreCase("Chrome")) {
			System.setProperty("WebDriver.chrome.driver", "geckodriver.exe");
			driver = new ChromeDriver();

		} else if (Browser_Type.equalsIgnoreCase("FireFox")) {
			System.setProperty("WebDriver.firefox.marionette", "chromedriver.exe");
			driver = new FirefoxDriver();

		} else if (Browser_Type.equalsIgnoreCase("InternetExplorer")) {
			System.setProperty("WebDriver.ie.driver", "IEDriverServer.exe");
			driver = new FirefoxDriver();

		} else {
			System.out.println(
					"Other browsers are not yet supported by the system..!! Please choose either of Chrome, FireFox or IE");
		}

		//driver.get(url);

		//driver.manage().window().maximize();
	}

	@Parameters({ "username", "password" })
	@Test(enabled = false, priority = 1)
	public void Login(String username, String password) {

		base = new BasePage(driver);
		base.Login_Page(username, password);
	}

	@Parameters({ "FilePath", "FileName_Excel", "SheetName", "FilePath_extent", "FileName_extent" })
	@Test(enabled = true, priority = 2)
	public void ReadDataFromExcel(String FilePath, String FileName, String SheetName, String FilePath_extent,
			String FileName_extent) throws IOException {

		DOMConfigurator.configure("log4j.xml");
		Log.StartTestCase("Test_Case_001");
		
		Map<String, String> DataFromExcel = new HashMap<String, String>();

		DataFromExcel = ReadExcelData.ReadExcel(FilePath, FileName, SheetName);

		String TestName = "Read Excel Test";
		ExtentTest test = ExtentReport.ExtentRep(FilePath_extent, FileName_extent, TestName);

		int count = 0;

		for (Entry<String, String> entry : DataFromExcel.entrySet()) {

			if (entry.getValue().equalsIgnoreCase("Khatod")) {
				test.log(LogStatus.PASS, "Data in rows are as expected");
				Log.Info("Data in rows are as expected");
				count++;
				break;
			}
		}
		if (count != 1) {
			test.log(LogStatus.FAIL, "Data in rows are NOT as expected");
			Log.error("Data in rows are NOT as expected");
		}

		ExtentReports ext = ExtentReport.extent;

		ExtentReport.AfterMethod(ext, test, TestName);
	}

	@Parameters({ "FilePath", "FileName_Excel", "SheetName", "FilePath_extent", "FileName_extent" })
	@Test(enabled = true, priority = 3)
	public void WriteData(String FilePath, String FileName, String SheetName, String FilePath_extent,
			String FileName_extent) throws IOException {

		WriteExcelData.WriteExcel(FilePath, FileName, SheetName);

	}

	@AfterSuite
	public void ClosePages() {

		driver.close();
		Log.Info("Browser Closed");
		driver.quit();
		Log.EndTestCase("Test_Case_001");
	}

}
