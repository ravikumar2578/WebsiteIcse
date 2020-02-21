package com.ExtramarksWebsite_Pages;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProfilePage extends BasePage {

	@FindBy(xpath = "//a[@class='btn btn-border-orange']")
	List<WebElement> EditProfile;
	@FindBy(xpath = "//input[@id='pincode']")
	WebElement Pincode;
	@FindBy(xpath = "//a[@class='btn btn-border-orange orange text-white']")
	WebElement Save;
	@FindBy(id = "schedule_date10")
	WebElement Date;
	@FindBy(xpath = "//span[@id='dob-date']")
	WebElement DOB;
	@FindBy(xpath = "//div[@class='datetimepicker-years']")
	WebElement CurrentYear;

	@FindBy(xpath = "//div[@class='datetimepicker-months']")
	WebElement CurrentMonth;

	@FindBy(xpath = "//input[@id='pl_current_password']")
	WebElement currentPassword;

	@FindBy(xpath = "//input[@id='pl_new_password']")
	WebElement newPassword;

	@FindBy(xpath = "//input[@id='pl_confirm_password']")
	WebElement confirmPassword;

	@FindBy(xpath = "//a[@id='change-password']")
	WebElement savePassword;

	
	@FindBy(xpath = "//*[@id='ajax_confirmerrormsg']")
	public WebElement savePasswordSuccess;
	
	public ProfilePage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	/*
	 * public void SelectDate(WebElement calendar, String year, String monthName,
	 * String day, WebDriver driver) { DOB.click(); String currentYear =
	 * CurrentYear.getText(); if(!currentYear.equals(year)); { do {
	 * driver.findElement(By.
	 * xpath("//div[@style='display: block;']//th[@class='prev']")).click(); }
	 * while(!CurrentYear.getText().equals(year)); }
	 * 
	 * String currentMonth=CurrentMonth.getText();
	 * if(!currentMonth.equals(monthName)) { do { driver.findElement(By.
	 * xpath("//div[@style='display: block;']//th[@class='prev']")).click(); }
	 * while(!CurrentMonth.getText().equals(monthName)); }
	 * 
	 * 
	 * }
	 */

	public Object editProfile() throws InterruptedException {

		EditProfile.get(0).click();
		Thread.sleep(3000);
		WebDriverWait wt = new WebDriverWait(driver, 30);
		ProfilePage pg = new ProfilePage(driver, test);
		wt.until(ExpectedConditions.visibilityOf(pg.Save));
		
		DOB.click();
		// if(CurrentYear!=year)
		test.log(LogStatus.INFO, "Entering DOB");
		Date.sendKeys("2000-06-13");
		Thread.sleep(1000);
		Pincode.clear();
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Entering Pincode");
		Pincode.sendKeys("201301");
		Save.click();
		if (pg.EditProfile.size() != 0)
			return pg;
		else {
			return null;
		}

	}

	public void changePassword(Hashtable<String, String> data) throws InterruptedException {

		EditProfile.get(0).click();
		Thread.sleep(3000);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		ProfilePage pg = new ProfilePage(driver, test);
		wt.until(ExpectedConditions.visibilityOf(currentPassword));
		currentPassword.clear();
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Entering CurrentPassword");
		currentPassword.sendKeys(data.get("CurrentPassword"));
		newPassword.clear();
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Entering NewPassword");
		newPassword.sendKeys(data.get("NewPassword"));
		confirmPassword.clear();
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Entering ConfirmPassword");
		confirmPassword.sendKeys(data.get("ConfirmPassword"));
		savePassword.click();

		

	}
}
