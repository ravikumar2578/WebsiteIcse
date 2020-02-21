package com.ExtramarksWebsite_TestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.SignupPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class SignUpTest extends BaseTest {
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
		Object[][] data = DataUtil.getData(xls, "SignUpTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, invocationCount = 1)
	public void signUp(Hashtable<String, String> data)
			throws InterruptedException, ClassNotFoundException, SQLException, IOException {
		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "Signup_Pass";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "SignUp test started");
		Reporter.log("SignUp test started");

		if (!DataUtil.isTestRunnable(xls, "SignUpTest") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

			throw new SkipException("Skipping the test");
		}

		openBrowser(browser);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		test.log(LogStatus.INFO, "Browser Opened");
		Reporter.log("Browser Opened");
		LaunchPage launch = new LaunchPage(driver, test);
		// HomePage hp = lp.goToHomePage();
		SignupPage sp = launch.goToDashboardPage();
		test.log(LogStatus.INFO, "Extramarks Website Home Page");
		sp.takeScreenShot();

		test.log(LogStatus.INFO, "Trying to Signup");
		boolean result = sp.signup(data.get("Name"), data.get("Mobile"), data.get("City"), data.get("UserType"),
				data.get("CountryCode"),data.get("Locality"));
		String regUsernameQuery = "SELECT * FROM user where username=" + data.get("Mobile") + "";
	 if (result == true) {
		SQLConnector con = new SQLConnector();
		String regUsername = con.connectionsetup(regUsernameQuery,"username");
		System.out.println("Signup - Waiting for OTP Verification");
		test.log(LogStatus.PASS, "Signup - Waiting for OTP Verification");
		sp.takeScreenShot();
	
		String smsTextquery = "SELECT * FROM t_sms_log where mobile_number='+91-" + data.get("Mobile") + "'";
	
		String OTPMessage = con.connectionsetup(smsTextquery,"sms_text");
			System.out.println("SMS Text >" + OTPMessage);
			String OTP = OTPMessage.substring(17, 23);
			System.out.println("Required OTP for Registration > " + OTP);
			if (sp.otpInput.size() != 0) {
				for (int i = 0; i < sp.otpInput.size(); i++) {
					char otpchar = OTP.charAt(i);
					String otp = Character.toString(otpchar);
					sp.otpInput.get(i).sendKeys(otp);
					Thread.sleep(2000);
				}
				sp.otpVerify.get(0).click();
				Thread.sleep(2000);
			}
		
		String title = driver.getTitle();
		System.out.println(title);
		boolean signup = false;
		if (title.equals("Extramarks India")) {
			actualResult = "Signup_Pass";
				int codes = Integer.parseInt(data.get("CountryCode"));
				switch (codes) {
				case 263:
					if (data.get("Mobile").length() > 6 || data.get("Mobile").length() < 9) {
						actualResult = "Signup_fail";
						System.out.println("Incorrect Mobile Number, but User Signed UP");
						test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
						sAssert.fail("Incorrect Mobile Number, but User Signed UP");

					}

					break;
				case 27:
					if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
						actualResult = "Signup_fail";
						System.out.println("Incorrect Mobile Number, but User Signed UP");
						test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
						sAssert.fail("Incorrect Mobile Number, but User Signed UP");
					}
					break;

				case 63:
					if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 14) {
						if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
							actualResult = "Signup_fail";
							System.out.println("Incorrect Mobile Number, but User Signed UP");
							test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
							sAssert.fail("Incorrect Mobile Number, but User Signed UP");
						}
					}
					break;
				case 91:
					if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 10) {
						if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
							actualResult = "Signup_fail";
							System.out.println("Incorrect Mobile Number, but User Signed UP");
							test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
							sAssert.fail("Incorrect Mobile Number, but User Signed UP");
						}
					}
				default:
					if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
						actualResult = "Signup_fail";
						System.out.println("Incorrect Mobile Number, but User Signed UP");
						test.log(LogStatus.PASS, "Incorrect Mobile Number, but User Signed UP");
						sAssert.fail("Incorrect Mobile Number, but User Signed UP");
					}
				}
		} else {
			actualResult = "Signup_fail_for_Correct Mobile Number";
				if (regUsername.equalsIgnoreCase(data.get("Mobile"))) {
					if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number already registered")) {
						System.out.println("Mobile Number alrady registered");
						test.log(LogStatus.PASS, "Mobile Number alrady registered");
						Reporter.log("Mobile Number alrady registered");
						actualResult = "Signup_Pass";

					} else {
						System.out.println("Mobile Number alrady registered, not getting error message on UI");
						test.log(LogStatus.FAIL, "Mobile Number alrady registered, not getting error message on UI");
						sAssert.fail("Mobile Number alrady registered, not getting error message on UI");
						actualResult = "Signup_Fail";
					}
				} else {
					int codes = Integer.parseInt(data.get("CountryCode"));
					switch (codes) {
					case 263:
						if (data.get("Mobile").length() > 6 || data.get("Mobile").length() < 9) {
							if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
								System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
								test.log(LogStatus.PASS, "Incorrect Mobile Number, On UI- Getting Error Message");
								actualResult = "Signup_Pass";
							} else {
								System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
								test.log(LogStatus.FAIL, "Incorrect Mobile Number, On UI- not Getting Error Mesage ");
								sAssert.fail("Incorrect Mobile Numer, On UI- not Getting Error Mesage ");

							}
						} else {
							sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
							actualResult = "Signup_fail";
						}

						break;
					case 27:
						if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
							if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
								System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
								test.log(LogStatus.PASS, "Incorrect Mobile Numer, On UI- Getting Error Message");
								actualResult = "Signup_Pass";
							} else {
								System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
								test.log(LogStatus.FAIL, "Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
								sAssert.fail("Incorrect Mobile Number, On UI- not Getting Error Mesage ");

							}
						} else {
							sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
							actualResult = "Signup_fail";
						}
						break;

					case 63:
						if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 14) {
							if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
								if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
									System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
									test.log(LogStatus.PASS, "Incorrect Mobile Number, On UI- Getting Error Message");
									actualResult = "Signup_Pass";
								} else {
									System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
									test.log(LogStatus.FAIL,
											"Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
									sAssert.fail("Incorrect Mobile Number, On UI- not Getting Error Mesage ");

								}
							}
						} else {
							sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
							actualResult = "Signup_fail";
						}
						break;
					case 91:
						if (data.get("Mobile").length() > 10 || data.get("Mobile").length() < 10) {
							if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
								System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
								test.log(LogStatus.PASS, "Incorrect Mobile Number, On UI- Getting Error Message");
								actualResult = "Signup_Pass";
							} else {
								System.out.println("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
								test.log(LogStatus.FAIL, "Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
								sAssert.fail("Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
							}
						} else {
							sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
							actualResult = "Signup_fail";
						}
					default:
						if (data.get("Mobile").length() > 9 || data.get("Mobile").length() < 9) {
							if (sp.dupUserError.getText().equalsIgnoreCase("Mobile number entered is incorrect")) {
								System.out.println("Incorrect Mobile Number, On UI- Getting Error Message");
								test.log(LogStatus.PASS, "Incorrect Mobile Number, On UI- Getting Error Message");
								actualResult = "Signup_Pass";
							} else {
								System.out.println("Incorrect Mobile Numer, On UI- not Getting Error Mesage ");
								test.log(LogStatus.FAIL, "Incorrect Mobile Number, On UI- not Getting Error Mesage ");
								sAssert.fail("Incorrect Mobile Number, On UI- not Getting Error Mesage ");
								actualResult = "Signup_Fail";

							}
						} else {
							sAssert.fail("Unable to register correct Mobile Number, getting error on UI");
							actualResult = "Signup_fail";
						}
					}
				}

			
		}
	}
		assertEquals(actualResult, expectedResult, "Verifying SignUp Test Functionality", sAssert);
		sAssert.assertAll();

	}

}
