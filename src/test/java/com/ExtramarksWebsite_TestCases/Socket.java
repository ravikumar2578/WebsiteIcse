package com.ExtramarksWebsite_TestCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ExtramarksWebsite_Utils.Constants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Socket {
	static Workbook workbook = null;
	static Sheet sheet = null;
	static String requestmsg = null;
	static WebDriver driver;
	public static FileInputStream openExcel(String path, String sheetName) throws IOException {
		
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
	
	public static  void closeStreamReader(FileInputStream fio) throws IOException {
		fio.close();
	}
	private static  FileInputStream openStreamReader(String path) throws IOException {
		FileInputStream fio=new FileInputStream(path);
    return fio;
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {

		System.out.println("Start Automation");
		System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.Websocketking.com/");

		WebElement cnt = driver.findElement(By.xpath("//input[@type='text']"));
		cnt.clear();
		cnt.sendKeys("wss://080q8d744m.execute-api.us-east-1.amazonaws.com/AlexSocketv1_0");

		WebElement connect = driver.findElement(By.xpath("//*[@class='btn-primary px-4 h-8 rounded-r-lg ']"));
		connect.click();



		// Write the file using file name, sheet name and the data to be filled

		String path = "C:\\Users\\E8837\\Desktop\\Socket.xlsx";
		String sheetName = "Sheet1";
		//// loop

		FileInputStream fio = openExcel(path, sheetName);
		int rowCount = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getLastCellNum();
		System.out.println(rowCount);
		System.out.println(colCount);
		closeStreamReader(fio);
//Create a loop over the cell of newly created Row

		
		Row row;
		Cell cell;
		for (int i = 290; i < 320; i++) {
			FileInputStream fio2 = openExcel(path, sheetName);

			// Get the first row from the sheet
			row = sheet.getRow(i);
         if(row!=null) {
			for (int j = 0; j < 1; j++) {
				// Read the excel data

				cell = row.getCell(j);
				if (cell == null) {
				
					System.out.println(requestmsg);
				} else {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						requestmsg = cell.getStringCellValue();
						System.out.println(requestmsg);
					} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
							|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
						requestmsg = String.valueOf(cell.getNumericCellValue());
						System.out.println(requestmsg);
					}
				}
			}

			WebElement Blank = driver.findElement(By.xpath("//*[@class='ace_text-input']"));
			Blank.clear();
			String Input1 = "{\"action\":\"onMessage\",\"pdu_data\":{\"query_string\":\"";
			String Input2 = "\"},\"session_attributes\":{\"board_id\":\"180\",\"class_id\":\"37\",\"user_id\":\"10064988\",\"platform\":\"web\",\"app_name\":\"em_website\",\"phrase\":\"Hi Alex\",\"keyword\":\"Hi Alex\",\"intent_action\":\"unknown\"}}";
			String Input = Input1 + requestmsg + Input2;
			Blank.sendKeys(Input);

			WebElement send = driver.findElement(By.xpath("//*[@class='py-1 px-4 mr-2 btn-primary rounded ']"));
			send.click();
			Thread.sleep(2000);
			WebElement output = driver.findElement(By.xpath("(//pre[@class='whitespace-pre-wrap'])[1]"));
			String response = output.getText();
			System.out.println(response);

			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(response);

			// System.out.println(json.toString());
			System.out.println("Response  Message is:" + json.get("message"));
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
				if(json.get("message") != null) {
				cell.setCellValue(json.get("message").toString());
				}
			}
			closeStreamReader(fio2);
			// Create an object of FileOutputStream class to create write data in excel file
			FileOutputStream fileOut = new FileOutputStream(path);
			// write data in the excel file
			workbook.write(fileOut);
			// close output stream
			fileOut.close();
		}}
	}
}
