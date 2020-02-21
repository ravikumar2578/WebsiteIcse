package com.ExtramarksWebsite_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ExtramarksWebsite_Utils.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LaunchPage extends BasePage {
	public LaunchPage(WebDriver dr, ExtentTest t) {
		super(dr, t);
	}

	public void openpage() {
		driver.get(Constants.URL);
		PageFactory.initElements(driver, this);

	}

	/*
	 * public class LaunchPage extends BasePage {
	 * 
	 * public LaunchPage(WebDriver dr,ExtentTest t){ super(dr,t); }
	 */

	public LoginPage goToHomePage() throws InterruptedException {
		driver.manage().deleteAllCookies();
		driver.get(Constants.URL);
		LoginPage lp = new LoginPage(driver,test);
		Thread.sleep(2000);
		/*
		 * if(lp.isLogin.size()!=0) { test.log(LogStatus.INFO,
		 * "User is not logged out in previous Test");
		 * System.out.println("User is not logged out in previous Test");
		 * click(driver,lp.isLogin,0,"isLogin"); Thread.sleep(1000);
		 * click(driver,lp.isLoginLogout,0,"Logout"); }
		 */
		return lp;
	}

	public SignupPage goToDashboardPage() {
		driver.get(Constants.URL);
		SignupPage sp = new SignupPage(driver, test);
		PageFactory.initElements(driver, sp);
		// test.log(LogStatus.INFO, "Reached Emscc_Admin homepage");
		return sp;

	}

}
