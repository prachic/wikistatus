package com.prj.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.spark.api.java.JavaRDD;

/**
 * @author Prachi Chaurasia
 * Connection class for MySQL. 
 */
public class DBConnector {

	private static final String PIPE = "|";
	
	// ToDo : Configure using a properties
	private static String tableName = "raw";
	private static String ipAddress = "127.0.0.1";
	private static String userId = "root";
	private static String password = "password";
	private static String databaseName = "wikistats";
	
	public static void main(String[] args) {
		String url = getConnectionURL();
		System.out.println("url: " + url);
		connect(url, tableName);
	}

	/**
	 * @return Connection URL for MySQL
	 */
	private static String getConnectionURL() {
		StringBuilder url = new StringBuilder();
		String port = ":3306";
		String urlType = "jdbc:mysql://";
		String timezone = "?serverTimezone=EST5EDT";
		url.append(urlType).append(ipAddress).append(port).append("/").append(databaseName).append(timezone);
		
		return url.toString();
	}

	/**
	 * Test class for connection.
	 * @param url
	 * @param tableName
	 */
	private static void connect(String url, String tableName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, userId, password);

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
			System.out.println("Displaying contents for table -> " + tableName + ", row count -> " + rs.getFetchSize());
			while (rs.next())
				System.out.println(rs.getString(1) + PIPE + rs.getString(2) + PIPE + rs.getInt(3) + PIPE  + rs.getInt(3));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static int insertData(JavaRDD<String> data) {
		int rowCount = 0;
		// Insert rows
		
		// 1. Generate script for each record
		
		// 2. Open connection
		
		// 3. Bulk insert or insert at row level 
		
		// 4. commit transaction
		
		// 5. Close connection
	
		return rowCount;
	}

}
