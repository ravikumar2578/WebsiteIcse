package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.Mentor_SchedulePage;
import com.ExtramarksWebsite_Pages.Mentor_StudentPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.StudyPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class MentorStudentTest extends BaseTest {

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
		Object[][] data = DataUtil.getData(xls, "MentorStudentTest");
		return data;
	}

	@Test(dataProvider = "getData")
	public void MentorsStudent(Hashtable<String, String> data)
			throws InterruptedException, ClassNotFoundException, SQLException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		String expectedResult = "MentorStudentTest_PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
		Object resultPage = dp.openStudent();
		if (resultPage instanceof Mentor_StudentPage) {
			test.log(LogStatus.INFO, "StudentPage opens");
			expectedResult = "MentorStudentTest_PASS";
			System.out.println("StudentPage opens");
		}

		else {
			actualResult = "StudentPage not opens";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "StudentPage not open");
			System.out.println("StudentPage not opens");
		}
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		SQLConnector con = new SQLConnector();
		String database_userType = con.connectionsetup(
				"SELECT user_type_id FROM `user` WHERE `username`=" + data.get("Mobile"), "user_type_id");
		String database_email = con.connectionsetup("SELECT email FROM `user` WHERE `username`=" + data.get("Mobile"),
				"email");
		Thread.sleep(2000);
		if (database_email.equalsIgnoreCase(data.get("Email")) && database_userType.equalsIgnoreCase("1")) {
			boolean result = msp.AddStudent(data.get("Email"), data.get("Board"), data.get("Class"),
					data.get("Subject"));

			Thread.sleep(3000);
			lp.takeScreenShot();
			if (result) {
				actualResult = "MentorStudentTest_PASS";
			} else {
				actualResult = "MentorStudentTest_FAIL";
			}

		} else {
			test.log(LogStatus.INFO, "Email id is incorrect");
			System.out.println("Email id is incorrect");
			WebDriverWait wt = new WebDriverWait(driver, 30);
			boolean result = msp.AddStudent(data.get("Email"), data.get("Board"), data.get("Class"),
					data.get("Subject"));
			wt.until(ExpectedConditions.visibilityOf(msp.InvalidEmailMsg.get(0)));
			if (msp.InvalidEmailMsg.get(0).getText().trim().equalsIgnoreCase("Please enter Student Email")) {
				test.log(LogStatus.PASS, "Add Student - Email Vaildation Test Pass");
				System.out.println("Add Student - Email Vaildation Test Pass");
				expectedResult = "MentorStudentTest_PASS";
			} else {
				test.log(LogStatus.FAIL, "Add Student - Email Vaildation Test Fail");
				System.out.println("Add Student - Email Vaildation Test Fail");
				sAssert.fail("Add Student - Email Vaildation Test Fail");
				lp.takeScreenShot();
				actualResult = "Add Student - Email Vaildation Test Fail";
			}
		}
		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
			sAssert.fail("Got actual result as " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Mentors Student Test passed");
		}
		sAssert.assertAll();
	}
}
