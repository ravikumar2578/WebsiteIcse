package com.ExtramarksWebsite_TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ExtramarksWebsite_Utils.Constants;

public class Checksum_genrate {

	public static void main(String[] args) throws Exception {
WebDriver driver;
		System.setProperty("webdriver.chrome.driver",Constants.CHROME_PATH );
		//System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver.exe");
		Excel_opration exop= new Excel_opration();
		Excel_opration.setExcelFile("C:\\Users\\e4814\\Desktop\\checksum.xlsx", "data");
		
		String student_id=exop.getCellData(1, 0);
		System.out.println(student_id);
		String student_type=exop.getCellData(1, 1);
		String board_id=exop.getCellData(1, 2);
		String class_id=exop.getCellData(1, 3);
		String action=exop.getCellData(1, 4);
		String salt=exop.getCellData(1, 5);
		String api_key=exop.getCellData(1, 6);
		driver = new ChromeDriver();
		driver.get("https://www.md5hashgenerator.com/");
		String checksumdata=student_id+":"+student_type+":"+board_id+":"+class_id+":"+action +":"+salt+":"+api_key;
		driver.findElement(By.xpath("//*[@id='string']")).sendKeys(checksumdata);
		driver.findElement(By.xpath("//*[@id='cap']/button")).click();
	}

}
