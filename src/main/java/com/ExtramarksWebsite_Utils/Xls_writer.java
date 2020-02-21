package com.ExtramarksWebsite_Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xls_writer {

	Workbook workbook = null;
	Sheet sheet = null;
	
	public FileInputStream openExcel(String path, String sheetName) throws IOException {
		
		FileInputStream fio =openStreamReader(path);

		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = path.substring(path.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			workbook = new XSSFWorkbook(fio);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			workbook = new HSSFWorkbook(fio);

		}

//Read excel sheet by sheet name    

		sheet = workbook.getSheet(sheetName);
		
		return fio;
	}
	
	public  void closeStreamReader(FileInputStream fio) throws IOException {
		fio.close();
	}
	private  FileInputStream openStreamReader(String path) throws IOException {
		FileInputStream fio=new FileInputStream(path);
    return fio;
	}
	
	
	public  void ExcelData(String path, String sheetName, String dataToWrite) throws IOException {

//Get the current count of rows in excel file
		FileInputStream fio= openExcel(path,sheetName);
		int rowCount = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getLastCellNum();
		System.out.println(rowCount);
		System.out.println(colCount);
		closeStreamReader(fio);
//Create a loop over the cell of newly created Row

		String cellText = "";
           Row row;
           Cell cell;
		for (int i = 0; i < rowCount + 1; i++) {
			FileInputStream fio2= openExcel(path,sheetName);
			
			// Get the first row from the sheet
			row = sheet.getRow(i);

			for (int j = 0; j < row.getLastCellNum(); j++) {

				// Read the excel data

				cell = row.getCell(j);
				if (cell == null) {
					cellText = "";
					System.out.println(cellText);
				} else {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						cellText = cell.getStringCellValue();
						System.out.println(cellText);
					} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
							|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
						cellText = String.valueOf(cell.getNumericCellValue());
						System.out.println(cellText);
					}
				}
			}
				// Write the data in excel file
		
				// Check the row is null

				if (row == null) {
					row = sheet.createRow(i);
				}

				// check the cell is null

				cell = row.getCell(colCount);
				if (cell == null) {
					cell = row.createCell(colCount);
				}
				// Fill data in row
				if (i == 0) {
					cell.setCellValue("Result");
				} else {
					cell.setCellValue(dataToWrite);
				}
				closeStreamReader(fio2);
				// Create an object of FileOutputStream class to create write data in excel file
				FileOutputStream fileOut = new FileOutputStream(path);
				// write data in the excel file
				workbook.write(fileOut);
				// close output stream
				fileOut.close();
		}
	}

	public static void main(String Args[]) throws IOException {

		// Create an array with the data in the same order in which you expect to be
		// filled in excel file

		String valueToWrite = "noida3";

		// Create an object of current class
		String path = System.getProperty("user.dir") + "\\TestData.xlsx";
		Xls_writer objExcelFile = new Xls_writer();

		// Write the file using file name, sheet name and the data to be filled

		objExcelFile.ExcelData(path, "Sheet1", valueToWrite);

	}

}
