package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
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
import com.ExtramarksWebsite_Pages.Parent_MyChildPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ParentDashboardTest extends BaseTest {
	
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
		Object[][] data = DataUtil.getData(xls, "ParentDashboardTest");
		return data;
	}
	@Test(dataProvider = "getData", priority = 2, enabled = true)
	public void mentorDashboardMenu(Hashtable<String, String> data)
			throws InterruptedException, IOException, ParseException, URISyntaxException {
		SoftAssert sAssert=new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		// --------Menu Icon -----------------
		DashBoardPage dp = new DashBoardPage(driver, test);
		int menuCount = dp.menuTab.size();
		test.log(LogStatus.INFO, "Total Menu : " + menuCount);
		System.out.println("Total Menu : " + menuCount);
		if (menuCount != 0) {
			int k = 0;
			for (int i = 0; i < menuCount; i++) {
				// System.out.println(dp.menuTab.get(i).getAttribute("class"));
				if (getAttribute(driver,dp.menuTab,i,"class","Menu Tab").trim().equalsIgnoreCase("active")) {
					test.log(LogStatus.INFO, "Current/default Menu : " + dp.menuTab.get(i).getText());
					System.out.println("Current/default Menu : " + dp.menuTab.get(i).getText());
					k = i;
				}
			}
			for (int i = 0; i < 1; i++) {
				//click(dp.menuTab,i,"Menu Tab");
				Thread.sleep(5000);
				String menu = dp.menuTab();
				String[] menuData = menu.split(">>");
				if (dp.menuTab.get(k).getText().trim().equalsIgnoreCase("Student")) {
					try {
						if (dp.menuLinks.size() != 0) {
                            for(String response:menuData[0].split(",")) {
                            	assertEquals(response, "OK",
    									"Verifying Menu Icon for Link : " + data.get("Student"),sAssert);
                            }
                      
                            	assertEquals(menuData[1], data.get("Student"), " Verifying Menu Link Name ",sAssert);
						}

					} catch (Exception e) {

					}
				}
				if (dp.menuTab.get(k).getText().trim().equalsIgnoreCase("Parent")) {
					try {
						if (dp.menuLinks.size() != 0) {
                            for(String response:menuData[0].split(",")) {
                            	assertEquals(response, "OK",
    									"Verifying Menu Icon for Link : " + data.get("Parent"),sAssert);
                            }
                      
                            	assertEquals(menuData[1], data.get("Parent"), " Verifying Menu Link Name ",sAssert);
						}

					} catch (Exception e) {

					}
				}
				if (dp.menuTab.get(k).getText().trim().equalsIgnoreCase("Mentor")) {
					try {
						if (dp.menuLinks.size() != 0) {
                            for(String response:menuData[0].split(",")) {
                            	assertEquals(response, "OK",
    									"Verifying Menu Icon for Link : " + data.get("Mentor"),sAssert);
                            }
                      
                            	assertEquals(menuData[1], data.get("Mentor"), " Verifying Menu Link Name ",sAssert);
						}

					} catch (Exception e) {

					}
				}

			}
			//dp.menuTab.get(k).click();
		}
		sAssert.assertAll();
	}
	@Test(dataProvider = "getData",priority=2)
	public void parentDashboardAddChild(Hashtable<String, String> data) throws InterruptedException, IOException {
		String expectedResult = "ParentDashboardTest_PASS";
		String actualResult = "";
		SoftAssert sAssert=new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		Thread.sleep(5000);
		DashBoardPage dp = new DashBoardPage(driver, test);
		if (dp.ParentTab() == 0) {
			actualResult = "Parent_Login_Fail";
		} else {
			actualResult = "ParentDashboardTest_PASS";

		}
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(dp.AddChild));
		click(dp.AddChild,"AddChild");
		Parent_MyChildPage pmc = new Parent_MyChildPage(driver, test);
		pmc.AddChild(data.get("ChildEmail"));
		pmc.AddChildNew(data.get("ChildName"), data.get("ChildUserId"), data.get("Password"), data.get("SchoolName"));
	      try {
				driver.switchTo().alert().accept();
				test.log(LogStatus.INFO, "New Child Added");
				actualResult = "ParentDashboardTest_PASS";
			} catch (Exception e2) {
				if (pmc.errorAddChild.size() != 0) {
					assertEquals(pmc.errorAddChild.get(0).getText().trim(), "Child username already exist",
							"Verifying Child Already Exsist",sAssert);
				} else {
					test.log(LogStatus.INFO, "No Message Alert Found for adding child");
					System.out.println("No Message Alert Found for adding child");
					actualResult = "Add_Child_Fail";
			}
		}

		Thread.sleep(5000);
		assertEquals(actualResult, expectedResult, "Verifying Dashboard Page Objects",sAssert);
		sAssert.assertAll();
	}
	@Test(dataProvider = "getData",priority=2,enabled=true)
	public void parentDashboardViewActivity(Hashtable<String, String> data) throws InterruptedException, IOException {
		String expectedResult = "DashboardTest_ViewActivity_PASS";
		String actualResult = "";
		SoftAssert sAssert=new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		Thread.sleep(5000);
		DashBoardPage dp = new DashBoardPage(driver, test);
		if (dp.ParentTab() == 0) {
			actualResult = "DashboardTest_ViewActivity_Fail";
		} else {
			actualResult = "DashboardTest_ViewActivity_PASS";
		}
		// View All my Activity
		Thread.sleep(3000);
		dp.ViewActivities();
	
		Thread.sleep(3000);
		assertEquals(actualResult, expectedResult, "Verifying Dashboard View Activity",sAssert);
		sAssert.assertAll();
	}

}
