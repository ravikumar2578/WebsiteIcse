package com.ExtramarksWebsite_TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JiraAutomation extends BaseTest {
static WebDriver driver;

@Test
	public static void createIssue() throws InterruptedException {
	
	System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
	// System.setProperty("webdriver.chrome.driver",
	// "D:\\Drivers\\chromedriver.exe");
	ChromeOptions options = new ChromeOptions();
	// Object obj=(Object)urlname;
	// options.addArguments("headless");
	options.addArguments("window-size=1366x768");
	// options.addArguments("incognito");
	// capabilities.setCapability("download.prompt_for_download", true);

	driver = new ChromeDriver(options);
         driver.get("https://jira-d180.extramarks.com");
         WebElement username=driver.findElement(By.xpath("//*[@name='os_username']"));
         username.sendKeys("vikrantsingh");
         WebElement password=driver.findElement(By.xpath("//*[@id='login-form-password']"));
         password.sendKeys("vikrant");
         WebElement login=driver.findElement(By.xpath("//*[@id='login']"));
         login.click();
         Thread.sleep(2000);
         WebElement createIssueLink=driver.findElement(By.xpath("//a[@id='create_link']"));
         createIssueLink.click();
         JavascriptExecutor js = ((JavascriptExecutor) driver);
      
         Thread.sleep(2000);
         
         WebElement selectProject=driver.findElement(By.xpath("//input[@id='project-field']"));
         selectProject.click();
         selectProject.sendKeys(Keys.CLEAR+"Automation Project"+Keys.ENTER);
         
        // selectProject.sendKeys(Keys.ENTER);
       Thread.sleep(2000);
         WebElement selectIssueType=driver.findElement(By.xpath("//*[@class='issue-setup-fields']//input[@id='issuetype-field']"));
         Actions ac=new Actions(driver);
         ac.moveToElement(selectIssueType);
         //selectIssueType.click();
        selectIssueType.sendKeys(Keys.CLEAR+"Bug"+Keys.ENTER);
        
         Thread.sleep(5000);
         WebElement summary=driver.findElement(By.xpath("//*[@class='content']//*[@id='summary']"));
         summary.click();
         
        summary.sendKeys("summary");
         //js.executeScript("arguments[0].value='summary2';", summary);
         //js.executeScript("document.getElementById('summary').value='summary'");
         Thread.sleep(2000);
         
         WebElement assignee=driver.findElement(By.xpath("//input[@id='assignee-field']"));
         assignee.sendKeys(Keys.CLEAR+"Vikrant Singh"+Keys.ENTER);
         
         WebElement selectLieofCode=driver.findElement(By.xpath("//input[@id='customfield_10700']"));
         selectLieofCode.sendKeys("0");
         
         
	}
}
