package com.ExtramarksWebsite_Utils;

import java.util.Hashtable;

public class DataUtil {
	public static Object[][] getData(Xls_Reader xls, String testCaseName) {
		String sheetName = "Data";
		// reads data for only testCaseName

		int testStartRowNum = 1;
		// System.out.println(xls.getCellData(sheetName, 0, testStartRowNum));
		while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
			testStartRowNum++;
		}
		// System.out.println("Test starts from row - "+ testStartRowNum);
		int colStartRowNum = testStartRowNum + 1;
		int dataStartRowNum = testStartRowNum + 2;

		// calculate rows of data
		int rows = 0;
		while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
			rows++;
		}
		// System.out.println("Total rows are - "+rows );

		// calculate total cols

		int cols = 0;
		while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
			cols++;
		}
		// System.out.println("Total cols are - "+cols );
		Object[][] data = new Object[rows][1];
		// read the data
		int dataRow = 0;
		Hashtable<String, String> table = null;
		for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				String key = xls.getCellData(sheetName, cNum, colStartRowNum);
				String value = xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);
				// 0,0 0,1 0,2
				// 1,0 1,1
			}
			data[dataRow][0] = table;
			dataRow++;
		}
		return data;
	}
	// true - Y
	// false - N

	public static void setData(Xls_Reader xls, String testCaseName, int rowNum, String colName, String data) {
		String sheetName = "Data";
		// reads data for only testCaseName

		int testStartRowNum = 1;
		// System.out.println(xls.getCellData(sheetName, 0, testStartRowNum));
		while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
			testStartRowNum++;
		}
		// System.out.println("Test starts from row - "+ testStartRowNum);
		int colStartRowNum = testStartRowNum + 1;
		int dataStartRowNum = testStartRowNum + 2;
		// System.out.println("Total rows are - "+rows );
		int dataRow = dataStartRowNum + rowNum;
		boolean Writedata = xls.setCellData(sheetName, testCaseName, colName, rowNum, data);
		System.out.println(Writedata + " data is " + data + " at row Number " + dataRow);

	}

	public static boolean isTestRunnable(Xls_Reader xls, String testName) {
		int rows = xls.getRowCount("TestCases");
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tNameXLS = xls.getCellData("TestCases", "TCID", rNum);
			if (tNameXLS.equals(testName)) {// found
				String runmode = xls.getCellData("TestCases", "Runmode", rNum);
				if (runmode.equals("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public static boolean isTestDataRunnable(Xls_Reader xls, String testName) {
		int rows = xls.getRowCount("Data");
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tNameXLS = xls.getCellData("Data", "TCID", rNum);
			if (tNameXLS.equals(testName)) {// found
				String runmode = xls.getCellData("TestCases", "Runmode", rNum);
				if (runmode.equals("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
	}
	 public static Integer romanToDecimal(java.lang.String romanNumber) {
	        int decimal = 0;
	        int lastNumber = 0;
	        String romanNumeral = romanNumber.toUpperCase();
	        /* operation to be performed on upper cases even if user 
	           enters roman values in lower case chars */
	        for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
	            char convertToDecimal = romanNumeral.charAt(x);

	            switch (convertToDecimal) {
	                case 'M':
	                    decimal = processDecimal(1000, lastNumber, decimal);
	                    lastNumber = 1000;
	                    break;

	                case 'D':
	                    decimal = processDecimal(500, lastNumber, decimal);
	                    lastNumber = 500;
	                    break;

	                case 'C':
	                    decimal = processDecimal(100, lastNumber, decimal);
	                    lastNumber = 100;
	                    break;

	                case 'L':
	                    decimal = processDecimal(50, lastNumber, decimal);
	                    lastNumber = 50;
	                    break;
	                case 'X':
	                    decimal = processDecimal(10, lastNumber, decimal);
	                    lastNumber = 10;
	                    break;

	                case 'V':
	                    decimal = processDecimal(5, lastNumber, decimal);
	                    lastNumber = 5;
	                    break;

	                case 'I':
	                    decimal = processDecimal(1, lastNumber, decimal);
	                    lastNumber = 1;
	                    break;
	            }
	        }
	       // System.out.println(decimal);
			return decimal;
	    }

	    public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
	        if (lastNumber > decimal) {
	            return lastDecimal - decimal;
	        } else {
	            return lastDecimal + decimal;
	        }
	    }

}
