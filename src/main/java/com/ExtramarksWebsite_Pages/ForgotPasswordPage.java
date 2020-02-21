package com.ExtramarksWebsite_Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ForgotPasswordPage extends BasePage {

	public ForgotPasswordPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
	}

	@FindBy(xpath = "//a[@class='forgot-password']")
	public WebElement ForgotPassword;

	@FindBy(xpath = "//input[@id='fgtemail']")
	WebElement enterMobile;

	@FindBy(xpath = "//button[@type='button' and @class='btn btn-banner blue']")
	WebElement ResetPasswordBtn;

	@FindBy(xpath = "//ul[@id='navigation-top']//a[@class='signin']")
	public WebElement SignIn;

	@FindBy(xpath = "//*[@id='erroremailonforgot']")
	public WebElement msgForgot;

	@FindBy(xpath = "//*[@id='forgot-password-modal']//*[@id='enter_email_p']")
	public WebElement validationMsg;

	public Object forgotPassword(String mobile) throws InterruptedException {
		Thread.sleep(2000);
		try {
			click(this.ForgotPassword, "ForgotPassword");
			sendKeys(enterMobile, "Mobile", mobile);
			click(this.ResetPasswordBtn, "ResetPasswordBtn");
			Thread.sleep(3000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.textToBePresentInElement(this.msgForgot,
					"Your submission is successful and you will receive a sms for a new Password."));
			return new LoginPage(driver, test);
		} catch (Exception e) {
			return this;
		}

	}

}
