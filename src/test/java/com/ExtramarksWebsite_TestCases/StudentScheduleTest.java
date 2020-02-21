package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.SchedulePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentScheduleTest extends BaseTest {
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
		Object[][] data = DataUtil.getData(xls, "StudentScheduleTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 2, enabled = true, invocationCount = 1)
	public void studentAddSchedule(Hashtable<String, String> data) throws IOException, InterruptedException, ParseException {
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		SoftAssert sAssert = new SoftAssert();
		LoginPage lp = new LoginPage(driver, test);
		String expectedResult = "PASS";
		String actualResult = "";
		DashBoardPage dp = new DashBoardPage(driver, test);
		SchedulePage sp = new SchedulePage(driver, test);

		Object resultPage = dp.openSchedule();
		if (resultPage instanceof SchedulePage) {
			test.log(LogStatus.PASS, "Schedule Page Validated");
			actualResult = "PASS";
			System.out.println("SchedulePage opens");
		} else {
			actualResult = "FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Schedule Page not open");
		}
		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
			sAssert.fail("Got actual result as " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Open Schedule passed");
		}
		sp.clickAddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
		Thread.sleep(5000);
		actualResult = "";
		WebDriverWait wt = new WebDriverWait(driver, 60);
		wt.until(ExpectedConditions.invisibilityOfAllElements(sp.AddLesson));
		lp.takeScreenShot();

		int allSceduleSize = sp.allscheduleList.size();
		System.out.println(allSceduleSize);
		if (allSceduleSize != 0) {
			for (int j = 0; j < allSceduleSize; j++) {
				if (sp.allscheduleChapter.size() != 0) {
					WebElement slider = driver
							.findElement(By.xpath("//*[@class='schedule-right-card']//*[@class='mCSB_dragger']"));
					int i;
					for (i = 0; i < slider.getCssValue("top").length(); i++) {
						char c = slider.getCssValue("top").charAt(i);
						if ('0' <= c && c <= '9') {

						} else {
							break;
						}
					}
					String numberPart = slider.getCssValue("top").substring(0, i);

					int height = Integer.parseInt(numberPart);
					int start = 0;
					for (int counter = 1; counter <= 20; counter++) {

						if (!sp.allscheduleDate.get(j).isDisplayed()) {
							start = start + 10;
							Actions ac = new Actions(driver);
							ac.clickAndHold(slider).moveByOffset(height, start).release().build().perform();
							Thread.sleep(2000);
						}
					}
					String chap = sp.allscheduleChapter.get(j).getText().trim();
					String title = sp.allscheduleTitle.get(j).getText().trim();
					String date = sp.allscheduleDate.get(j).getText().trim();
					System.out.println(chap + title + date);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
					Calendar cal = Calendar.getInstance();

					Date d = simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
					if (chap.contains(data.get("Chapter")) && title.equalsIgnoreCase(data.get("Title"))) {
						actualResult = "PASS";
					} else {

					}
				}

			}
		}
		if (!expectedResult.equals(actualResult)) { // take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Add Schedule Fail " + actualResult);
			sAssert.fail("Got actual result as " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Add Schedule passed");
		}

		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 3, enabled = true, invocationCount = 1)
	public void studentMySchedule(Hashtable<String, String> data) throws IOException, InterruptedException, ParseException {
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		SoftAssert sAssert = new SoftAssert();
		WebDriverWait wt=new WebDriverWait(driver,60);
		LoginPage lp = new LoginPage(driver, test);
		String expectedResult = "PASS";
		String actualResult = "";
		DashBoardPage dp = new DashBoardPage(driver, test);
		SchedulePage sp = new SchedulePage(driver, test);

		Object resultPage = dp.openSchedule();
		if (resultPage instanceof SchedulePage) {
			test.log(LogStatus.PASS, "Schedule Page Validated");
			actualResult = "PASS";
			System.out.println("SchedulePage opens");
		} else {
			actualResult = "FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Schedule Page not open");
		}
		Object resultPage2 = dp.openSchedule();
		actualResult = "";
		if (sp.openMySchedule()) {
			actualResult = "PASS";
		} else {
			actualResult = "FAIL";
		}

		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "My Schedule Open Fail " + actualResult);
			sAssert.fail("Got actual result as " + actualResult);
		} else {
			test.log(LogStatus.PASS, "My Schedule Open Pass ");
		}

		actualResult = "";
//Edit Schedule
		Thread.sleep(5000);
		boolean editSchedule = sp.editSchedule(data.get("EditSubject"), data.get("EditChapter"));
		if (editSchedule == true) {
			actualResult = "PASS";
		} else {
			if (sp.EditIcon.size() == 0) {
				System.out.println("Not found any Schedule for edit");
				test.log(LogStatus.INFO, "Not found any Schedule for edit");
				actualResult = "PASS";
			}
		}
		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Edit Schedule Fail " + actualResult);
			sAssert.fail("Edit Schedule Fail " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Edit Schedule PASS");
		}

//All Filter
		actualResult = "";
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='all']/a")));
		// driver.findElement(By.xpath("//*[@id='all']/a")).click();
		Thread.sleep(5000);
		System.out.println("Applying All Filter");
		test.log(LogStatus.INFO, "Applying All Filter");
		List<HashMap<String, String>> scheduleListAll = new ArrayList<HashMap<String, String>>();
		int ScheduleSize = sp.scheduleList.size();
		try {
			scheduleListAll = sp.myScheduleFilter();
			for (int i = 0; i < ScheduleSize; i++) {
				test.log(LogStatus.INFO, "Scedule Title : " + scheduleListAll.get(i).get("ScheduleHeader"));
				test.log(LogStatus.INFO, "Scedule Subject : " + scheduleListAll.get(i).get("ScheduleSubjects"));
				test.log(LogStatus.INFO, "Scedule Date : " + scheduleListAll.get(i).get("ScheduleEndDate"));
				actualResult = "PASS";
			}
		} catch (Exception e) {
			actualResult = "FAIL";
		}

		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "All Schedule Filter Fail " + actualResult);
			sAssert.fail("All Schedule Filter Fail " + actualResult);
		} else {
			test.log(LogStatus.PASS, "All Schedule Filter PASS");
		}

		// Overdue Filter

		actualResult = "";
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='overdue']/a")));
		System.out.println("Applying Overdue Filter");
		test.log(LogStatus.INFO, "Applying Overdue Filter");
		sp.Overdue.click();
		Thread.sleep(5000);
		ScheduleSize = sp.scheduleList.size();
		List<HashMap<String, String>> scheduleListOverdue = new ArrayList<HashMap<String, String>>();
		try {
			scheduleListOverdue = sp.myScheduleFilter();
			for (int i = 0; i < ScheduleSize; i++) {
				test.log(LogStatus.INFO, "Scedule Title : " + scheduleListOverdue.get(i).get("ScheduleHeader"));
				test.log(LogStatus.INFO, "Scedule Subject : " + scheduleListOverdue.get(i).get("ScheduleSubjects"));
				test.log(LogStatus.INFO, "Scedule Date : " + scheduleListOverdue.get(i).get("ScheduleEndDate"));
				System.out.println("Scedule Title : " + scheduleListOverdue.get(i).get("ScheduleHeader"));
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yy hh:mm:ss");
				Calendar cal = Calendar.getInstance();
				Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
				Date scheduleEndDate = simpleDateFormat.parse(scheduleListOverdue.get(i).get("ScheduleEndDate"));
				if (scheduleEndDate.compareTo(currentDate) < 0) {
					actualResult = "PASS";
					System.out.println("End Date is ocuures before Todays Date");
					test.log(LogStatus.INFO, "End Date is ocuures before Todays Date");

				} else {
					System.out.println("End Date is ocuures after Todays Date");
					test.log(LogStatus.INFO, "End Date is ocuures after Todays Date");
					actualResult = "FAIL";
				}

			}
		} catch (Exception e) {
			actualResult = "FAIL";
		}

		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Overdue Schedule Filter Fail " + actualResult);
			sAssert.fail("Overdue Schedule Filter Fail " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Overdue Schedule Filter PASS");
		}
		List<WebElement> Completed = driver.findElements(By.xpath("//span[text()='completed']"));
		int CompletedSchedule = Completed.size();
		System.out.println("Total number of completed tests = " + CompletedSchedule);

		// Suggested Filter
		actualResult = "";
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='suggested']/a")));
		sp.Suggested.click();
		Thread.sleep(5000);
		System.out.println("Applying Suggested Filter");
		test.log(LogStatus.INFO, "Applying Suggested Filter");
		ScheduleSize = sp.scheduleList.size();
		List<HashMap<String, String>> scheduleListSuggested = new ArrayList<HashMap<String, String>>();
		try {
			scheduleListSuggested = sp.myScheduleFilter();
			for (int i = 0; i < ScheduleSize; i++) {
				test.log(LogStatus.INFO, "Scedule Title : " + scheduleListSuggested.get(i).get("ScheduleHeader"));
				test.log(LogStatus.INFO, "Scedule Subject : " + scheduleListSuggested.get(i).get("ScheduleSubjects"));
				test.log(LogStatus.INFO, "Scedule Date : " + scheduleListSuggested.get(i).get("ScheduleEndDate"));
				actualResult = "PASS";
			}
		} catch (Exception e) {
			actualResult = "FAIL";
		}

		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Suggested Schedule Filter Fail " + actualResult);
			sAssert.fail("Suggested Schedule Filter Fail " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Suggested Schedule Filter PASS");
		}

		// Recently Added Filter
		actualResult = "";
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='recently']/a")));
		sp.RecentlyAdded.click();
		Thread.sleep(5000);
		System.out.println("Applying Recently Added Filter");
		test.log(LogStatus.INFO, "Applying Recently Added Filter");
		ScheduleSize = sp.scheduleList.size();
		List<HashMap<String, String>> scheduleListRecentlyAdded = new ArrayList<HashMap<String, String>>();
		try {
			scheduleListRecentlyAdded = sp.myScheduleFilter();
			for (int i = 0; i < ScheduleSize; i++) {
				test.log(LogStatus.INFO, "Scedule Title : " + scheduleListRecentlyAdded.get(i).get("ScheduleHeader"));
				test.log(LogStatus.INFO,
						"Scedule Subject : " + scheduleListRecentlyAdded.get(i).get("ScheduleSubjects"));
				test.log(LogStatus.INFO, "Scedule Date : " + scheduleListRecentlyAdded.get(i).get("ScheduleEndDate"));
				actualResult = "PASS";
			}
		} catch (Exception e) {
			actualResult = "FAIL";
		}

		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Recently Added Schedule Filter Fail " + actualResult);
			sAssert.fail("Recently Added Schedule Filter Fail " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Recently Added Schedule Filter PASS");
		}
		sp.ExamSchedule();
		dp.Dashboard.get(0).click();
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 4, enabled = true, invocationCount = 1)
	public void studentDeleteSchedule(Hashtable<String, String> data)
			throws IOException, InterruptedException, ParseException {
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		SoftAssert sAssert = new SoftAssert();
		LoginPage lp = new LoginPage(driver, test);
		String expectedResult = "PASS";
		String actualResult = "";
		DashBoardPage dp = new DashBoardPage(driver, test);
		SchedulePage sp = new SchedulePage(driver, test);

		Object resultPage = dp.openSchedule();
		if (resultPage instanceof SchedulePage) {
			test.log(LogStatus.PASS, "Schedule Page Validated");
			actualResult = "PASS";
			System.out.println("SchedulePage opens");
		} else {
			actualResult = "FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Schedule Page not open");
		}
		int deleteSize = sp.deleteScheduleBtn.size();
		if (deleteSize != 0) {
			sp.deleteSchedule();
			Thread.sleep(5000);
		} else {

		}
		int deleteSize2 = sp.deleteScheduleBtn.size() + 1;

		if (deleteSize == deleteSize2) {
			actualResult = "PASS";
		} else {
			actualResult = "FAIL";
		}
		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Schedule Delete Fail " + actualResult);
			sAssert.fail("Schedule Delete Fail " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Schedule Delete PASS ");
			lp.takeScreenShot();
		}

		dp.Dashboard.get(0).click();
		sAssert.assertAll();
	}
}
