package TestCases;

import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ExtentReports.ExtentReport;
import log4j_utility.Log;
import xmlconfig.ReadXML;
import xmlconfig.WriteXML;

public class XMLTestCase {

	Properties prop;	
	
	@BeforeSuite
	public void InitializeLog4j() {
		
		DOMConfigurator.configure("log4j.xml");
		Log.StartTestCase("Test_Case_001");
	}
	
	@Parameters({ "FilePath", "FileName_XML", "FileName_Prop", "FilePath_Prop", "FilePath_extent", "FileName_extent" })
	@Test(enabled = true, priority = 1)
	public void ReadDataXML(String FilePath, String FileName_XML, String FileName_Prop, String FilePath_Prop,
			String FilePath_extent, String FileName_extent) throws Exception {

		String TestName = "Read XML Test";
		ExtentTest test = ExtentReport.ExtentRep(FilePath_extent, FileName_extent, TestName);

		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);
		
		String Output = ReadXML.ReadXMLData(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);
		
		System.out.println("1. "+Output);

		if (Output.equalsIgnoreCase(prop.getProperty("tag_name_read_value"))) {

			test.log(LogStatus.PASS, "Output is as expected..!!");
			Log.Info("Output is as expected..!!");
		} else {

			test.log(LogStatus.FAIL, "Output is not as expected..!!");
			Log.Info("Output is NOT as expected..!!");
		}

		ExtentReports ext = ExtentReport.extent;
		ExtentReport.AfterMethod(ext, test, TestName);
	}
	
	@Parameters({ "FilePath", "FileName_XML", "FileName_Prop", "FilePath_Prop", "FilePath_extent", "FileName_extent" })
	@Test(enabled = true, priority = 2)
	public void WriteDataXML(String FilePath, String FileName_XML, String FileName_Prop, String FilePath_Prop,
			String FilePath_extent, String FileName_extent) throws XPathExpressionException,
			ParserConfigurationException, IOException, SAXException, TransformerException {

		int[] count_array = WriteXML.WriteXMLData(FilePath, FilePath_Prop, FileName_XML, FileName_Prop);
		
		int count_add = count_array[0];
		int count_del_ele = count_array[1];
		int count_upd_att = count_array[2];
		int count_upd_ele = count_array[3];
		
		System.out.println("3. "+count_add+" "+count_del_ele+" "+count_upd_att+" "+count_upd_ele);
		
		String TestName = "Write XML Test";
		ExtentTest test = ExtentReport.ExtentRep(FilePath_extent, FileName_extent, TestName);
		
		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);
		
		if(count_add > 0 || count_del_ele > 0 || count_upd_att > 0 || count_upd_ele > 0 ) {
			
			if(prop.getProperty("add_element_xml").equalsIgnoreCase("Y") && count_add>0) {
				
				Log.Info("Element added Successfully..!!");
				test.log(LogStatus.PASS, "Element added Successfully..!!");
			}
			if(prop.getProperty("update_element_xml").equalsIgnoreCase("Y") && count_upd_ele>0) {
				
				Log.Info("Element updated Successfully..!!");
				test.log(LogStatus.PASS, "Element updated Successfully..!!");
			}
			if(prop.getProperty("update_attribute_xml").equalsIgnoreCase("Y") && count_upd_att>0) {
				
				Log.Info("Attribute updated Successfully..!!");
				test.log(LogStatus.PASS, "Attribute updated Successfully..!!");
			}
			if(prop.getProperty("delete_element").equalsIgnoreCase("Y") && count_del_ele>0) {
				
				Log.Info("Element deleted Successfully..!!");
				test.log(LogStatus.PASS, "Element deleted Successfully..!!");
			}
		}else {
			
			String message = "Either NO Operations are to be performed or Issue with one of them, please check count :\n Add Operation :" + count_add
					+ "\n Update Element :" + count_upd_ele + "\n Update Attribute :" + count_upd_att
					+ "\n Delete Element :" + count_del_ele;
			
			Log.error(message);
			test.log(LogStatus.ERROR, message);
		}	
		
		ExtentReports ext = ExtentReport.extent;
		ExtentReport.AfterMethod(ext, test, TestName);
	}

}
