package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
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
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.Mentor_StudentPage;
import com.ExtramarksWebsite_Pages.NotificationPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.Student_MentorPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentMentorTest extends BaseTest {
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
		Object[][] data = DataUtil.getData(xls, "StudentMentorTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1, enabled = false)
	public void studentMentorAdd(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		String expectedResult = "PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Student_MentorPage msp = new Student_MentorPage(driver, test);
	    dp.LeftMenumentorTab.get(0).click();
		Thread.sleep(3000);
		Object resultPage = dp.openMentor();

		if (resultPage instanceof Student_MentorPage) {
			test.log(LogStatus.INFO, "Mentor Page opens");
			System.out.println("Mentor Page opens");
			Reporter.log("Mentor Page opens");
			actualResult = "PASS";

			Student_MentorPage smp = (Student_MentorPage) resultPage;
			String ProfileNames = smp.profileName.getText();
			boolean Writedata = xls.setCellData("Data", "StudentMentorTest", "ProfileName", 1, ProfileNames);
			// System.out.println(Writedata+" data is "+data);
			boolean addMentorResult = msp.addMentor(data.get("Email"), data.get("Board"), data.get("Class"),
					data.get("Subject"));

			if (addMentorResult) {
				actualResult = "PASS";
			} else {
				actualResult = "FAIL";
			}
		} else {
			actualResult = "FAIL";
			test.log(LogStatus.INFO, "Mentor Page not open");
			System.out.println("Mentor Page not opens");
			Reporter.log("Mentor Page not opens");

		}

		if (!expectedResult.equals(actualResult)) {
			System.out.println("Add Mentor for Acceptance is Fail");
			test.log(LogStatus.FAIL, "Add Mentor for Acceptance is Fail");
			Throwable throwError = new Throwable();
			sAssert.fail("Add Mentor for Acceptance is Fail", throwError);
		} else {
			System.out.println("Mentor is added, Pending for Acceptance for " + data.get("Subject"));
			test.log(LogStatus.PASS, "Mentor is added, Pending for Acceptance for " + data.get("Subject"));
			Reporter.log("Mentor is added, Pending for Acceptance for " + data.get("Subject"));
		}
		lp.takeScreenShot();
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 2, enabled = false)
	public void studentMentorAcceptNotificationByMentor(Hashtable<String, String> data) throws Exception {
		// Accepting Notification
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username2"), data.get("Password2"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Object resultPage2 = dp.openNotification();
		if (resultPage2 instanceof NotificationPage) {
			test.log(LogStatus.INFO, "Opening Notification Page");
			System.out.println("Opening Notification Page");
			Reporter.log("Opening Notification Page");
			NotificationPage np = new NotificationPage(driver, test);
			np.notificationAccept(data);
		}
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 3, enabled = false)
	public void studentMentorMentorListVerification(Hashtable<String, String> data) throws InterruptedException, IOException {
		// Verifying Accepted Mentor List By Student
		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "PASS";
		String actualResult = "";
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Object resultPage = dp.openMentor();
		if (resultPage instanceof Student_MentorPage) {
			Student_MentorPage smp = new Student_MentorPage(driver, test);
			int mentorSize = smp.acceptedMentorRow.size();
			if (mentorSize != 0) {
				// List<HashMap<String, String>> mentorList = new ArrayList<HashMap<String,
				// String>>();
				Object resultPage2 = smp.getAcceptedmentorList();
				if (resultPage2 instanceof List) {
					List<HashMap<String, String>> mentorList = (List) resultPage2;
					for (int i = 0; i < mentorList.size(); i++) {
						if (data.get("Email").equalsIgnoreCase(mentorList.get(i).get("MentorEmail"))) {
							if (data.get("Subject").equalsIgnoreCase(mentorList.get(i).get("MentorSubject"))) {
								actualResult = "PASS";
								System.out.println("Mentor Name" + mentorList.get(0).get("MentorName") + " Email"
										+ mentorList.get(0).get("MentorEmail") + " Board : "
										+ mentorList.get(0).get("MentorBoard") + "Subject : "
										+ mentorList.get(0).get("MentorSubject") + "");
								test.log(LogStatus.INFO,
										"Mentor Name" + mentorList.get(0).get("MentorName") + " Email"
												+ mentorList.get(0).get("MentorEmail") + " Board : "
												+ mentorList.get(0).get("MentorBoard") + "Subject : "
												+ mentorList.get(0).get("MentorSubject") + "");
								Reporter.log("Mentor Name" + mentorList.get(0).get("MentorName") + " Email"
										+ mentorList.get(0).get("MentorEmail") + " Board : "
										+ mentorList.get(0).get("MentorBoard") + "Subject : "
										+ mentorList.get(0).get("MentorSubject") + "");
							} else {
								actualResult = "Fail";
							}

						} else {
							actualResult = "Fail";
						}

					}
				}
			}
		} else {
			actualResult = "Fail";
		}
		if (!expectedResult.equals(actualResult)) {
			// take screenshot
			dp.takeScreenShot();
			System.out.println("Add Mentor after Acceptance is Fail");
			test.log(LogStatus.FAIL, "Add Mentor after Acceptance is Fail");
			Throwable throwError = new Throwable();
			sAssert.fail("Add Mentor after Acceptance is Fail", throwError);
		} else {
			System.out.println("Mentor is added after acceptance for Subject : " + data.get("Subject"));
			test.log(LogStatus.PASS, "Mentor is added after acceptance for Subject : " + data.get("Subject"));
			Reporter.log("Mentor is added after acceptance for Subject : " + data.get("Subject"));
		}
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 1, enabled = true)
	public void studentMentorDelete(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		String expectedResult = "PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Student_MentorPage msp = new Student_MentorPage(driver, test);
		Object resultPage = dp.openMentor();

		if (resultPage instanceof Student_MentorPage) {
			test.log(LogStatus.INFO, "Mentor Page opens");
			System.out.println("Mentor Page opens");
			Reporter.log("Mentor Page opens");
			actualResult = "PASS";

			Student_MentorPage smp = (Student_MentorPage) resultPage;
			boolean deleteMentor = smp.deletePendingMentor(data);

			if (deleteMentor) {

				actualResult = "PASS";
			} else {
				actualResult = "FAIL";
			}
		} else {
			actualResult = "FAIL";
			test.log(LogStatus.INFO, "Mentor Page not open");
			System.out.println("Mentor Page not opens");
			Reporter.log("Mentor Page not opens");
		}
		if (!expectedResult.equals(actualResult)) {
			System.out.println("Mentor deletion is Fail");
			test.log(LogStatus.FAIL, "Mentor deletion is Fail");
			Throwable throwError = new Throwable();
			sAssert.fail("Mentor deletion is Fail", throwError);
		} else {
			System.out.println("Mentor deletion is Pass");
			test.log(LogStatus.PASS, "Mentor deletion is Pass");
			Reporter.log("Mentor deletion is Pass");
		}
		lp.takeScreenShot();
		sAssert.assertAll();
	}

}
