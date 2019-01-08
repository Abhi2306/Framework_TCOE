package Utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import xmlconfig.ReadXML;

public class DBConnectivity {

	static Properties prop;

	public static ArrayList<Map<String,Object>> CreateDBConnection(String FilePath_Prop, String FileName_Prop)
			throws SQLException, ClassNotFoundException, IOException {

		//For reading data from properties file
		prop = ReadXML.Read_Data_From_Properties(FilePath_Prop, FileName_Prop);

		String URL = prop.getProperty("URL");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String Connection_Choice = prop.getProperty("Connection_Choice");

		//Setting up connection with DB
		Connection con = DriverManager.getConnection(URL, username, password);
		
		//Choosing MySql or Oracle
		if (Connection_Choice.equalsIgnoreCase("mysql")) {
			Class.forName("com.mysql.jdbc.driver");
		} else if (Connection_Choice.equalsIgnoreCase("Oracle")) {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} else {
			System.out.println(
					"Apologies for the inconvenience..!! Please choose either SQL or Oracle connectivty for DB connections..!!");
		}

		Statement stmt = con.createStatement();

		//Executing the query provided
		ResultSet result = stmt.executeQuery(prop.getProperty("Query"));
		
		//Using Result set meta data class so that count of column and column name can be retrieved
		ResultSetMetaData rsmd = result.getMetaData();
		
		//Column count of the result
		int column_count = rsmd.getColumnCount();
		
		//Array List of type Map is used for storing the results
		ArrayList<Map<String, Object>> queryResult = new ArrayList<Map<String,Object>>();

		//Iterating through the result obtained from query
		while (result.next()) {
			
			//Creating instance of HashMap for storing the values
			Map<String, Object> row_result = new HashMap<String, Object>();

			//Iterating through the data based on column count
			for(int i=0;i<column_count;i++) {
				
				//Putting data in HashMap
				row_result.put(rsmd.getColumnName(i),result.getObject(i));
			}
			
			//Adding data in Array List
			queryResult.add(row_result);
		}

		con.close();
		return queryResult;
	}
}
