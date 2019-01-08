package TestCases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import BaseDriver.BasePage;
import ExtentReports.ExtentReport;
import excelconfig.ReadExcelData;
import excelconfig.WriteExcelData;
import xmlconfig.ReadXML;
import xmlconfig.WriteXML;

public class OpenPageTest {

	static WebDriver driver;
	BasePage base;

	//Selection of browser type to be worked upon
	@Parameters({"URL","Browser_Type"})
	@BeforeSuite(enabled = false)
	public void OpenPage(String url, String Browser_Type) {
		
		if(Browser_Type.equalsIgnoreCase("Chrome")) {
			System.setProperty("WebDriver.chrome.driver", "geckodriver.exe");
			driver = new ChromeDriver();
			
		}else if(Browser_Type.equalsIgnoreCase("FireFox")) {
			System.setProperty("WebDriver.firefox.marionette", "chromedriver.exe");
			driver = new FirefoxDriver();	
			
		}else if(Browser_Type.equalsIgnoreCase("InternetExplorer")) {
			System.setProperty("WebDriver.ie.driver", "IEDriverServer.exe");
			driver = new FirefoxDriver();
			
		}else {
			System.out.println("Other browsers are not yet supported by the system..!! Please choose either of Chrome, FireFox or IE");
		}
		
		driver.get(url);

		driver.manage().window().maximize();
	}
	
	@Parameters({ "username", "password"})
	@Test(enabled=true, priority=1)
	public void Login(String username, String password) {
		
		base = new BasePage(driver);
		base.Login_Page(username, password);
	}

	@Parameters({ "FilePath", "FileName_Excel", "SheetName", "FilePath_extent", "FileName_extent" })
	@Test(enabled = true, priority=2)
	public void ReadDataFromExcel(String FilePath, String FileName, String SheetName, String FilePath_extent, String FileName_extent) throws IOException {

			Map<String, String> DataFromExcel = new HashMap<String, String>();
			
			DataFromExcel = ReadExcelData.ReadExcel(FilePath, FileName, SheetName);
			
			ExtentTest test = ExtentReport.ExtentRep(FilePath_extent, FileName_extent);
			
			int count=0;
			
			for(Entry<String, String> entry : DataFromExcel.entrySet()) {
				
				if(entry.getKey().equalsIgnoreCase("FirstRow") && entry.getValue().equalsIgnoreCase("FirstCell_1")) {
					test.log(LogStatus.PASS, "Data in rows are as expected");
					count++;
					break;
				}				
			}
			if(count!=1) {
				test.log(LogStatus.FAIL, "Data in rows are NOT as expected");
			}
			
			ExtentReports ext = ExtentReport.extent;
			
			ExtentReport.AfterMethod(ext, test);
		
			WriteExcelData.WriteExcel(FilePath, FileName, SheetName);
	}

	@Parameters({ "FilePath", "FileName_XML", "FileName_Prop", "FilePath_Prop", "FilePath_extent", "FileName_extent"})
	@Test(enabled = true, priority=3)
	public void ReadDataFromXML(String FilePath, String FileName_XML, String FileName_Prop, String FilePath_Prop,
			String FilePath_extent, String FileName_extent) throws Exception {
		
		ExtentTest test = ExtentReport.ExtentRep(FilePath_extent, FileName_extent);

		String Output = ReadXML.ReadXMLData(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);

		if(Output.equalsIgnoreCase("10")) {
			
			test.log(LogStatus.PASS, "Output is as expected..!!");
		}else {
			
			test.log(LogStatus.FAIL, "Output is not as expected..!!");
		}
		
		ExtentReports ext = ExtentReport.extent;
		
		ExtentReport.AfterMethod(ext, test);
		
		WriteXML.WriteXMLData(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);
	}
	
	@AfterSuite(enabled = false)
	public void ClosePages() {

		driver.close();
		driver.quit();
	}

}
