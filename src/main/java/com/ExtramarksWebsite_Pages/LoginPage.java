package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.ExtramarksWebsite_Utils.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = Constants.LOGIN_USERNAME)
	public WebElement usernameField;

	@FindBy(id = Constants.LOGIN_PASSWORD)
	public WebElement passwordField;

	@FindBy(id = Constants.LOGIN_BUTTON)
	public WebElement Submit_button;

	@FindBy(id = "errorusernameLogin")
	public List<WebElement> Error_Username;

	@FindBy(id = "errorpasswdLogin")
	public WebElement Error_Password;

	@FindBy(xpath = "//*[@id='navbar']//ul[@id='navigation-top']//a[@class='pl-user-info dropdown-toggle']")
	public List<WebElement> isLogin;

	@FindBy(xpath = "//*[@id='navigation-top']//a[contains(text(),'LOGOUT')]")
	public List<WebElement> isLoginLogout;

	@FindBy(xpath = "loginFailed")
	public List<WebElement> LoginFailed;

	public Object doLogin(String username, String password) throws InterruptedException {
		WebDriverWait wt = new WebDriverWait(driver, 60);
		// Entering username
		sendKeys(this.usernameField, "UserName", username);
		// Entering Password
		sendKeys(this.passwordField, "Password", password);
		// Click on sign button
		click(this.Submit_button, "Submit_button");
		Thread.sleep(3000);

		// Waiting for Alert Presence
		try {
			wt.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			if (alert.getText().equalsIgnoreCase(
					"Your last session was not logged out properly. With this login we are destroying all your previous login.")) {
				test.log(LogStatus.INFO, "Your last session was not logged out properly");
				System.out.println("Your last session was not logged out properly");
				Reporter.log("Your last session was not logged out properly");
			}
			alert.accept();
		} catch (Exception e) {

		}
		// validate the login
		// System.out.println("test data");
		Thread.sleep(5000);
		// System.out.println(driver.getTitle());
		String title = driver.getTitle();
		// System.out.println(title);
		boolean login = false;
		if (title.trim().equalsIgnoreCase("Extramarks India"))
			login = true;
		else
			login = false;

		if (!login) {
			test.log(LogStatus.INFO, "Login Failed");
			System.out.println("Login Failed");
			Reporter.log("Login Failed");
			LoginPage loginPage = new LoginPage(driver, test);
			PageFactory.initElements(driver, loginPage);
			loginPage.takeScreenShot();
			return loginPage;
		} else {
			test.log(LogStatus.INFO, "Login successful");
			System.out.println("Login successful");
			Reporter.log("Login successful");
			DashBoardPage dp = new DashBoardPage(driver, test);
			PageFactory.initElements(driver, dp);
			dp.takeScreenShot();
			return dp;
		}

	}
}
