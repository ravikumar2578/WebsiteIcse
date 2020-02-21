package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.Mentor_Assessment;
import com.ExtramarksWebsite_Pages.NotificationPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.Student_MentorPage;
import com.ExtramarksWebsite_Pages.StudyPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class MentorAssessmentTest extends BaseTest {
	
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
		Object[][] data = DataUtil.getData(xls, "MentorAssessmentTest");
		return data;
	}
	@Test(dataProvider = "getData")
	public void MentorAssessment(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		 DashBoardPage dp = new DashBoardPage(driver, test);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		Object changeClassresultPage =null;
		try {
		changeClassresultPage = dp.changeClass(data);
		}catch(Exception e) {
			
		}
		if (changeClassresultPage  instanceof StudyPage) {
			StudyPage sg = (StudyPage) changeClassresultPage ;
			System.out.println("Board/Class is selected successfully");
			test.log(LogStatus.INFO, "Board/Class is selected successfully");
		} else {
			System.out.println("Board/Class is not selected");
			test.log(LogStatus.INFO, "Board/Class not is selected");
		}
		String expectedResult = "PASS";
		String actualResult = "";
		Thread.sleep(3000);
		//JavascriptExecutor js=(JavascriptExecutor)driver;
		//js.executeScript("arguments[0].click();", dp.LeftMenumentorTab.get(0));
		Thread.sleep(3000);
		click(dp.Assessment,"Assessment");
		Mentor_Assessment ma = new Mentor_Assessment(driver, test);
		Thread.sleep(1000);
		if (dp.AssessmentSwitchClass.size() != 0) {
			if (dp.AssessmentSwitchClass.get(0).isDisplayed()) {
				click(dp.AssessmentSwitchClass,0,"AssessmentSwitchClass");
				Select boardSel = new Select(dp.AssessmentSwitchBoard.get(0));
				boardSel.selectByVisibleText(data.get("Board"));
				Select classSel = new Select(dp.AssessmentSwitchClass.get(0));
				classSel.selectByVisibleText(data.get("Class"));
				click(dp.AssessmentSwitchBoardContinueBtn,"dp.AssessmentSwitchBoardContinueBtn");
				Thread.sleep(3000);
			}
		}
		test.log(LogStatus.INFO, "Opening Assessment Page");
		System.out.println("Opening Assessment Page");
		ma.DraftAssessment();
		Thread.sleep(3000);
			ma.AssessmentDetails(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
					data.get("Hours_1"), data.get("Hours_2"), data.get("Minutes_1"), data.get("Minutes_2"),
					data.get("AssessmentInstruction"));
			ma.writeQuestions(data.get("Ques_Area"));
		String students = ma.step3StudentCount.getText();
		if (students.contains("0")) {
				System.out.println("No any Student is Added");
				test.log(LogStatus.INFO, "No any Student is Added");
				actualResult = "PASS";
		} else {
			ma.shareAssessment(data.get("Ques_Area"));
			List<HashMap<String, String>> viewAssessmentList = ma.AssessmentList(data.get("Marks"));
			for (int i = 0; i < viewAssessmentList.size(); i++) {
				if (viewAssessmentList.get(i).get("Subject").equalsIgnoreCase(data.get("Subject"))) {
					if (viewAssessmentList.get(i).get("Chapter").equalsIgnoreCase(data.get("Chapter"))) {
						actualResult = "PASS";
					} else {
						{
							actualResult = "Fail";
						}
					}
				}
			}

		}
	
		if (!expectedResult.equals(actualResult)) {
			dp.takeScreenShot();
			test.log(LogStatus.FAIL, "AssessmentTest Failed");
			System.out.println("AssessmentTest Failed");
			sAssert.fail("AssessmentTest Failed");
		} else {
			test.log(LogStatus.PASS, "AssessmentTest passed");
			System.out.println("AssessmentTest Pass");
		}

		sAssert.assertAll();
	}

}
