package com.ExtramarksWebsite_TestCases;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.ForgotPasswordPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ForgotPasswordTest extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void init(Method method) {
		rep = ExtentManager.getInstance();
		String testMethodName = method.getName();
		test = rep.startTest(testMethodName);
	}

	@AfterMethod(alwaysRun = true)
	public void logOut(ITestResult itr) throws InterruptedException {

		try {
			logStatus(itr);
			DashBoardPage dp = new DashBoardPage(driver, test);
			Thread.sleep(1000);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", dp.SettingsIcon.get(0));
			click(dp.SettingsIcon, 0, "Settingicon");
			click(dp.LogOut, 0, "Logout");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rep.endTest(test);
			rep.flush();
			if (driver != null) {
				driver.quit();
				driver = null;
			}
		}
	}

	@AfterTest(alwaysRun = true)
	public void quit() throws InterruptedException {
		System.out.println("Test Execution finished");
		test.log(LogStatus.INFO, "Test Execution finished");
		Reporter.log("Test Execution finished");
	}

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "ForgotPasswordTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1)
	public void forgotPassword(Hashtable<String, String> data)
			throws InterruptedException, SQLException, ClassNotFoundException {
		String expectedResult = "ForgotPassword_PASS";
		String actualResult = "";
		SoftAssert sAssert = new SoftAssert();
		String browser = data.get("Browser");
		openBrowser(browser);
		LaunchPage launch = new LaunchPage(driver, test);
		launch.openpage();
		ForgotPasswordPage fp = new ForgotPasswordPage(driver, test);
		SQLConnector con = new SQLConnector();
		String queryUsername = "select username from user where username in ('" + data.get("RegisteredMobile/Email")
				+ "')";
		String database_username = con.connectionsetup(queryUsername, "username");
		if (database_username.equalsIgnoreCase(data.get("RegisteredMobile/Email"))) {

			PageFactory.initElements(driver, fp);
			WebDriverWait wt = new WebDriverWait(driver, 20);
			wt.until(ExpectedConditions.elementToBeClickable(fp.SignIn));

			click(fp.SignIn, "SignIn");
			Object forgotPasswordResult = fp.forgotPassword(data.get("RegisteredMobile/Email"));
			if (forgotPasswordResult instanceof LoginPage) {
				System.out.println("ForgotPassword done successfully");
				test.log(LogStatus.INFO, "ForgotPassword done successfully");

				String query = "SELECT `sms_text` FROM `t_sms_log` WHERE `user_id` in (select `user_id` from user where username in ('"
						+ data.get("RegisteredMobile/Email") + "') order by id desc)";
				String database_sms_text = con.connectionsetup(query, "sms_text");
				String OTP = database_sms_text.substring(29, 35);
				System.out.println("OTP : " + OTP);
				test.log(LogStatus.INFO, "OTP : " + OTP);
				Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
				DataUtil.setData(xls, "ForgotPasswordTest", 1, "Password", OTP);
				LoginPage lp =new LoginPage(driver,test);
				Object loginResultPage = lp.doLogin(data.get("RegisteredMobile/Email"), OTP);
				if (loginResultPage instanceof DashBoardPage) {
					actualResult = "ForgotPassword_PASS";
				} else {
					actualResult = "ForgotPassword_Unable to Login with created Password_Fail";
				}

			} else {
				System.out.println("ForgotPassword Fail");
				test.log(LogStatus.FAIL, "ForgotPassword Fail");
				actualResult = "ForgotPassword Fail";
				sAssert.fail("ForgotPassword Fail");
				fp.takeScreenShot();
			}

		} else

		{
			System.out.println("Mobile Number is not Exists");
			test.log(LogStatus.FAIL, "Mobile Number is not Exists");
			Thread.sleep(4000);
			if (fp.validationMsg.getText()
					.equalsIgnoreCase("Enter your registered Mobile Number/E-mail ID to reset password:")) {
				actualResult = "ForgotPassword_PASS";
				test.log(LogStatus.FAIL, "ForgotPassword_Username_Validation_on_Forgot_Password_Pass");
				System.out.println("ForgotPassword_Username_Validation_on_Forgot_Password_Pass");
				Reporter.log("ForgotPassword_Username_Validation_on_Forgot_Password_Pass");
			} else {
				actualResult = "ForgotPassword_Username_Validation_on_Forgot_Password_Fail";
				sAssert.fail("ForgotPassword_Username_Validation_on_Forgot_Password_Fail");
				System.out.println("ForgotPassword_Username_Validation_on_Forgot_Password_Fail");
				test.log(LogStatus.FAIL, "ForgotPassword_Username_Validation_on_Forgot_Password_Fail");
				fp.takeScreenShot();
			}
		}
	}
}
