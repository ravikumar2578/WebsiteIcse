package com.ExtramarksWebsite_TestCases;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;

//@Listeners(AllureListener.class)	
public class LoginTest extends BaseTest {
	public static int testDataRow = 1;
	String UserType;

	@BeforeMethod(alwaysRun = true)
	public void init(Method method, ITestResult itr) {
		rep = ExtentManager.getInstance();
		String testMethodName = method.getName();

		if (testDataRow == 1) {
			UserType = "Student";
		} else if (testDataRow == 2) {
			UserType = "Parent";
		} else if (testDataRow == 3) {
			UserType = "Mentor";
		} else if (testDataRow > 3) {
			UserType = "Invalid User";
		}
		test = rep.startTest(testMethodName + " For " + UserType);
		testDataRow = testDataRow + 1;
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
		Object[][] data = DataUtil.getData(xls, "LoginTest");
		return data;
	}

	@Test(dataProvider = "getData", invocationCount = 1, testName = "VerifyLogin")
	public void doLogin(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Login_Pass";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		Reporter.log("Login test started");
		if (!DataUtil.isTestRunnable(xls, "LoginTest") || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test");
			Reporter.log("Skipping the test");
			throw new SkipException("skipping the test");
		}
		// LaunchPage launch = null;
		if (driver == null) {
			openBrowser(browser);
		}
		LaunchPage launch = new LaunchPage(driver, test);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		LoginPage lp = launch.goToHomePage();
		test.log(LogStatus.INFO, "Extramarks Website Home Page");
		Reporter.log("Extramarks Website Home Page");
		lp.takeScreenShot();
		WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
		click(signin, "signin");
		Object loginResultPage = lp.doLogin(data.get("Username"), data.get("Password"));
		SQLConnector con = new SQLConnector();
		String database_userType = con.connectionsetup(
				"SELECT user_type_id FROM `user` WHERE `username`=" + data.get("Username"), "user_type_id");
		String database_userPassword = con
				.connectionsetup("SELECT password FROM `user` WHERE `username`=" + data.get("Username"), "password");
		Thread.sleep(2000);
		if (loginResultPage instanceof DashBoardPage) {
			if (data.get("Password").equalsIgnoreCase("123456")) {
				if (database_userPassword.equalsIgnoreCase("e10adc3949ba59abbe56e057f20f883e")) {
					actualResult = "Login_Pass";
					if (database_userType.equalsIgnoreCase("1")) {
						System.out.println("Student is Logged in Successfully");
						test.log(LogStatus.INFO, "Student is Logged in Successfully");
					} else if (database_userType.equalsIgnoreCase("2")) {
						System.out.println("Parent is Logged in Successfuuly");
						test.log(LogStatus.INFO, "Parent is Logged in Successfully");
					} else {
						System.out.println("Teacher is Logged in Successfully");
						test.log(LogStatus.INFO, "Teacher is Logged in Successfully");
					}

				} else {
					actualResult = "Login_fail_User_Not_Exists";
				}
			}
			if (data.get("Password").equalsIgnoreCase("12345678")) {
				if (database_userPassword.equalsIgnoreCase("e10adc3949ba59abbe56e057f20f883e")) {
					actualResult = "Login_Pass";
					if (database_userType.equalsIgnoreCase("1")) {
						System.out.println("Student is Logged in Successfuuly");
						test.log(LogStatus.INFO, "Student is Logged in Successfuuly");
					} else if (database_userType.equalsIgnoreCase("2")) {
						System.out.println("Parent is Logged in Successfuuly");
						test.log(LogStatus.INFO, "Parent is Logged in Successfuuly");
					} else {
						System.out.println("Teacher is Logged in Successfuuly");
						test.log(LogStatus.INFO, "Teacher is Logged in Successfully");
					}
				} else {
					actualResult = "Login_fail_User_Not_Exists";
				}
			}
		} else {

			if (data.get("Password").equalsIgnoreCase("123456")) {
				if (database_userPassword.equalsIgnoreCase("e10adc3949ba59abbe56e057f20f883e")) {
					actualResult = "Login_fail_User_Not_Navigated_to_Dashboard";
				} else {
					String errorMEssage = "Please login by registered identity";
					String Login_Error = "Please enter valid email/username and password";
					String loginFailed = "Please enter valid email/username and password";
					if (lp.Error_Username.size() != 0) {
						if (errorMEssage.equals(lp.Error_Username.get(0).getText().trim())) {
							actualResult = "Login_Pass";
							assertEquals(lp.Error_Username.get(0).getText().trim(), errorMEssage,
									"Verifying Login Validation Message", sAssert);
						}
					} else {
						if (lp.LoginFailed.size() != 0) {
							if (Login_Error.equals(lp.LoginFailed.get(0).getText().trim())) {
								actualResult = "Login_Pass";
								assertEquals(lp.LoginFailed.get(0).getText().trim(), Login_Error,
										"Verifying Login Validation Message", sAssert);
							}
						}
					}

				}
			} else if (data.get("Password").equalsIgnoreCase("12345678")) {
				if (database_userPassword.equalsIgnoreCase("e10adc3949ba59abbe56e057f20f883e")) {
					actualResult = "Login_fail_User_Not_Navigated_to_Dashboard";
				} else {
					String errorMEssage = "Please login by registered identity";
					String Login_Error = "Please enter valid email/username and password";
					String loginFailed = "Please enter valid email/username and password";
					if (lp.Error_Username.size() != 0) {
						if (errorMEssage.equals(lp.Error_Username.get(0).getText().trim())) {
							actualResult = "Login_Pass";
							assertEquals(lp.Error_Username.get(0).getText().trim(), errorMEssage,
									"Verifying Login Validation Message", sAssert);
						}
					} else {
						if (lp.LoginFailed.size() != 0) {
							if (Login_Error.equals(lp.LoginFailed.get(0).getText().trim())) {
								actualResult = "Login_Pass";
								assertEquals(lp.LoginFailed.get(0).getText().trim(), Login_Error,
										"Verifying Login Validation Message", sAssert);

							}
						}
					}

				}
			}

		}

		assertEquals(actualResult, expectedResult,
				"Verifying " + data.get("UserType") + "Verifying Login Test Functionality", sAssert);
		try {
			sAssert.assertAll();
		} catch (Exception e) {

		}
		// System.out.println("EndingTest");
	}
}
