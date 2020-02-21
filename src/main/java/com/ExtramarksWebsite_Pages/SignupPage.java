package com.ExtramarksWebsite_Pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SignupPage extends BasePage {
	public SignupPage(WebDriver dr, ExtentTest t) {
		super(dr, t);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='register']")
	WebElement SignUp;

	@FindBy(xpath = "//*[@id='student']/a")
	WebElement student;

	@FindBy(xpath = "//*[@id='stuparent']/a")
	WebElement parent;

	@FindBy(xpath = "//*[@id='stuteacher']/a")
	WebElement mentor;

	@FindBy(xpath = "//*[@id='mobilediv']//div[@title]")
	WebElement country;

	@FindBy(xpath = "//*[@id='mobilediv']//ul/li")
	List<WebElement> countrydropdown;

	@FindBy(id = "y_1")
	WebElement CheckboxYes;

	@FindBy(id = "display_name")
	WebElement Name;

	@FindBy(id = "mobile")
	WebElement MobileNo;

	@FindBy(id = "city")
	List<WebElement> City;

	@FindBy(id = "s2id_autogen1_search")
	WebElement CitySendkeys;

	@FindBy(xpath = "//select[@id='city']")
	WebElement CityDropDwn;

	@FindBy(id = "cityArea")
	List<WebElement> Locality;
	
	@FindBy(xpath = "//button[@name='submit']")
	WebElement Submit;

	@FindBy(xpath = "//a[@id='reg_otp-verify']")
	WebElement VerifyBtn;

	@FindBy(xpath = "//div[@class='pincode-input-container']")
	public WebElement otp;

	@FindBy(xpath = "//*[@id='errorMessage_mobile']")
	public WebElement dupUserError;

	@FindBy(xpath = "//*[@id='otp-verify']//*[@class='pincode-input-container']/input")
	public List<WebElement> otpInput;

	@FindBy(xpath = "//*[@id='reg_otp-verify']")
	public List<WebElement> otpVerify;

	public boolean signup(String name, String mobile, String city, String userType, String code,String locality)
			throws InterruptedException, SQLException, ClassNotFoundException {
		SignUp.click();
		Thread.sleep(3000);
		test.log(LogStatus.INFO, "Selecting UserTypes: " + userType);
		if (userType.equals("Student")) {
			student.click();
			Thread.sleep(3000);
			CheckboxYes.click();
		} else if (userType.equals("Parent")) {
			parent.click();
			Thread.sleep(3000);
		} else {
			mentor.click();
			Thread.sleep(3000);
		}

		test.log(LogStatus.INFO, "Entering name: " + name);
		Name.sendKeys(name);

		test.log(LogStatus.INFO, "Selecting Country Code: " + code);
		country.click();

		for (WebElement codes : countrydropdown) {
			if (codes.getAttribute("data-dial-code").equalsIgnoreCase(code)) {
				System.out.println(code);
				codes.click();
				break;
			}
		}
		test.log(LogStatus.INFO, "Entering mobile no. " + mobile);
		MobileNo.sendKeys(mobile);
		if (City.size() != 0) {

			if (City.get(0).isDisplayed()) {
				test.log(LogStatus.INFO, "Selecting City " + city);
				City.get(0).click();
				City.get(0).sendKeys(city);
				City.get(0).sendKeys(Keys.ENTER);
			}
		}
		if (Locality.size() != 0) {

			if (Locality.get(0).isDisplayed()) {
				test.log(LogStatus.INFO, "Selecting City " + locality);
				Locality.get(0).click();
				Locality.get(0).sendKeys(locality);
				Locality.get(0).sendKeys(Keys.ENTER);
			}
		}
		// Select citysel= new Select (City);
		// citysel.selectByValue(city);

		Submit.click();
		Thread.sleep(5000);
		takeScreenShot();
		boolean signup = false;
		// if(title.equals("Extramarks India"))
		if (otpInput.get(0).isDisplayed()) {
			signup = true;
		} else {

		}
		return signup;
	}
}
