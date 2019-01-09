package Utilities;

import org.apache.log4j.Logger;

public class Log {

	static Logger Log_instance = Logger.getLogger(Log.class.getName());
	
	public static void StartTestCase(String TestCaseName) {
		
		Log_instance.info("****************************************************************************************");
		 
		Log_instance.info("****************************************************************************************");
		 
		Log_instance.info("$$$$$$$$$$$$$$$$$$$$$                 "+TestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		 
		Log_instance.info("****************************************************************************************");
		 
		Log_instance.info("****************************************************************************************"); 
	}
	
	public static void EndTestCase(String TestCaseName) {
		
		Log_instance.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
		 
		Log_instance.info("X");
		 
		Log_instance.info("X");
		 
		Log_instance.info("X");
		 
		Log_instance.info("X");
	}
	
	public static void Info(String message) {
		
		Log_instance.info(message);
	}
	
	public static void Warn(String message) {
	
		Log_instance.info(message);
	}
	
	public static void error(String message) {
		
		Log_instance.info(message);
	}
	
	public static void debug(String message) {
	
		Log_instance.info(message);
	}
}
