package com.ExtramarksWebsite_TestCases;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
import com.ExtramarksWebsite_Pages.Mentor_StudentPage;
import com.ExtramarksWebsite_Pages.Parent_MyChildPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.SchedulePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;
import com.thoughtworks.selenium.webdriven.commands.IsAlertPresent;

public class ParentMyChildTest extends BaseTest {
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
		Object[][] data = DataUtil.getData(xls, "ParentMyChildTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, enabled = false)
	public void parentAddChild(Hashtable<String, String> data) throws InterruptedException {
		SoftAssert sAssert = new SoftAssert();
		Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);

		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		Thread.sleep(5000);
		LoginPage lp = new LoginPage(driver, test);

		String expectedResult = "PASS";
		String actualResult = "";

		String url = driver.getCurrentUrl();
		String actual_url = "http://testautomation-www.extramarks.com/user/teacher-dashboard/3";

		DashBoardPage dp = new DashBoardPage(driver, test);
		if (url.equals(actual_url) && dp.ParentTab() == 0) {
			System.out.println("Logged in but not on parent page");
			test.log(LogStatus.INFO, "Logged in but not on parent page");
			actualResult = "Login_Fail";

			test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
			Assert.fail("got actual result as= " + actualResult);
		}

		int TotalChild = msp.StudentList.size();
		System.out.println("Total Child =" + TotalChild);

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(dp.MyChild));
		Object resultPage = dp.openMyChild();
		Parent_MyChildPage pmc = new Parent_MyChildPage(driver, test);
		pmc.AddChild(data.get("ChildEmail"));
		pmc.AddChildNew(data.get("ChildName"), data.get("ChildUserId"), data.get("Password"), data.get("SchoolName"));

		if (resultPage instanceof Parent_MyChildPage) {
			test.log(LogStatus.INFO, "MyChildPage opens");
			actualResult = "PASS";
			System.out.println("MyChildPage opens");
		} else {
			actualResult = "FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "MyChildPage not open");
			System.out.println("MyChildPage not opens");
		}
		if (pmc.errorAddChild.size() != 0) {
			assertEquals(pmc.errorAddChild.get(0).getText().trim(), "Child username already exist",
					"Verifying Child Already Exsist", sAssert);
		} else {
			try {
				driver.switchTo().alert().accept();
				test.log(LogStatus.INFO, "New Child Added");
				actualResult = "DashboardTest_PASS";
			} catch (Exception e2) {
				test.log(LogStatus.INFO, "No Message Alert Found for adding child");
				System.out.println("No Message Alert Found for adding child");
				actualResult = "Add_Child_Fail";
			}
		}

		Thread.sleep(5000);
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 2, enabled = true)
	public void parentMyChild(Hashtable<String, String> data) throws InterruptedException, IOException {
		SoftAssert sAssert = new SoftAssert();
		Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		Thread.sleep(5000);
		LoginPage lp = new LoginPage(driver, test);
		String expectedResult = "Mychild_PASS";
		String actualResult = "";
		Thread.sleep(3000);
		DashBoardPage dp = new DashBoardPage(driver, test);
		// dp.MyChild.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", dp.MyChild);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		dp.takeScreenShot();
		Thread.sleep(5000);
		Parent_MyChildPage pmcp = new Parent_MyChildPage(driver, test);
		int TotalChild = pmcp.childList.size();
		System.out.println("Total Child =" + TotalChild);
		test.log(LogStatus.INFO, "Total Child =" + TotalChild);
		if (TotalChild != 0) {
			for (int i = 0; i < TotalChild; i++) {
				pmcp.childViewProfileBtn.get(i).click();
				HashMap<String, String> profileData = new HashMap<String, String>();
				profileData = pmcp.myChildProfile();
				pmcp.myChildSchedule();
				SchedulePage sp = new SchedulePage(driver, test);
				System.out.println(data.get("Title") + data.get("Class") + data.get("Subject"));
				sp.clickAddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
				sp.takeScreenShot();
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(5000);
				dp.ViewActivities();
				dp.takeScreenShot();
				Thread.sleep(3000);
				js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
				pmcp.myChildScheduleFilter("Jan 2020", "Test");
				dp.takeScreenShot();
				Thread.sleep(5000);
				driver.navigate().back();
			}
		}
		sAssert.assertAll();
	}

}
