package com.ExtramarksWebsite_TestCases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ExtramarksWebsite_Utils.Constants;

public class SQLConnector {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		connectionsetup("select *from t_sms_log ","OTPMessage");
	}

	public static String connectionsetup(String query, String column) throws ClassNotFoundException, SQLException {
		// Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
		// String dbUrl = "jdbc:mysql://10.0.3.99/website_ver2";
		String Columnvalue = "";
		/*
		 * String dbUrl = "jdbc:mysql://10.0.20.107/phpmyadmin";
		 */
		// test-automation server
		String dbUrl = Constants.DATABASE_URL;
		String username = Constants.DATABASE_USERNAME;
		String password = Constants.DATABASE_PASSWORD;
		
		// Database Password
		// String password = "website";
		// String password = "KU8wHAyyacdnYpp9FJqCbECbBhfFQxReC5DLUZ";
		// Query to Execute
		// Load mysql jdbc driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Create Connection to DB
		Connection con = DriverManager.getConnection(dbUrl, username, password);

		// Create Statement Object
		Statement stmt = con.createStatement();

		// Execute the SQL Query. Store results in ResultSet

		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()) {
		// While Loop to iterate through all data and print results
		List li = new ArrayList();
		/*
		 * int RowNumber=rs.getRow(); System.out.println(" total row >"+RowNumber); for
		 * (int row=1;row<=RowNumber;row++){ rs.absolute(-row);// for last row in Table
		 * int count = rs.getMetaData().getColumnCount();
		 * System.out.println("columncount >"+count); for(int i=1;i<=count;i++){ String
		 * ColumnNAME=rs.getMetaData().getColumnName(i); String
		 * ColName=i+" "+"ColName >" +ColumnNAME;
		 * 
		 * //System.out.println(ColName); String Columnvalue=rs.getString(i);
		 * System.out.println("ROW"+row+ ":"+ ColumnNAME + ":"+" "+Columnvalue);
		 * 
		 * }
		 */
		// last entry in Database
		
		rs.absolute(-1);// for last row in Table
		int count = rs.getMetaData().getColumnCount();
		System.out.println("columncount >" + count);
		for (int i = 1; i <= count; i++) {
			String columnName = rs.getMetaData().getColumnName(i);
			if (columnName.equalsIgnoreCase(column)) {
				Columnvalue = rs.getString(i);
				//System.out.println(columnName + " : " + Columnvalue);
				break;
			}
		}
	}else {
		
	}
		//String OTPMessage = rs.getString(5);
	//	String MobileNumber = rs.getString(3);

		/**
		 * Scanner input = new Scanner(System.in); System.out.print("Enter Mobile Number
		 * > "); String inputString = input.nextLine(); System.out.print("You entered :
		 * "); System.out.println(inputString);
		 * 
		 */

		//System.out.println("mobile no. > " + MobileNumber);
		//System.out.println("SMS Text >" + OTPMessage);
	//	int OTP = Integer.parseInt(OTPMessage.replaceAll("\\D", ""));

		//System.out.println("Required OTP for Registration > " + OTP);

		/*
		 * if (inputString==MobileNumber){ System.out.println(OTPMessage); int OTP =
		 * Integer.parseInt(OTPMessage.replaceAll("\\D", ""));
		 * 
		 * 
		 * System.out.println("Required OTP for Registration : " + OTP);
		 * 
		 * } else { System.out.println(" mobile comparision is failed"); }
		 */
		/*
		 * rs.absolute(-2);
		 * 
		 * 
		 * String OTP1= rs.getString(5); System.out.println(OTP1); for(String
		 * w:OTP1.split("\\s",0)){ li.add(w); //System.out.println(w); } int
		 * Arraysize=li.size(); //System.out.println(Arraysize); String
		 * otpnumber=(String) li.get(4); System.out.println("OTP FOR REGISTRATION :" +
		 * otpnumber);
		 */

		/*
		 * while (rs.next()){ //while (rs.absolute(3)){ String myName = rs.getString(1);
		 * String myAge = rs.getString(2); System. out.println(myName+"  "+myAge);
		 * 
		 * 
		 * String OTP= rs.getString(5);
		 * 
		 * System.out.println(OTP); }
		 */
		// closing DB Connection
		con.close();
		return Columnvalue;
	}

}
