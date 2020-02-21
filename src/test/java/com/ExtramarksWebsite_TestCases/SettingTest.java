package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
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
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.SubscriptionPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class SettingTest extends BaseTest {
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
		Object[][] data = DataUtil.getData(xls, "SettingTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1)
	public void editProfile(Hashtable<String, String> data) throws InterruptedException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Object resultPage = dp.openProfile();
		if (resultPage instanceof ProfilePage) {
			test.log(LogStatus.INFO, "Opening Profile");
			System.out.println("Opening Profile");

			ProfilePage pg = (ProfilePage) resultPage;
			pg.takeScreenShot();
			Object resultPage2 = pg.editProfile();
			if (resultPage2 instanceof ProfilePage) {
				test.log(LogStatus.PASS, "Edit Profile Pass");
				System.out.println("Edit Profile Pass");
				pg.takeScreenShot();
				pg.takeFullScreenshot();
			} else {
				test.log(LogStatus.FAIL, "Edit Profile Fail");
				System.out.println("Edit Profile Fail");
				sAssert.fail("Edit Profile Fail");
				dp.takeScreenShot();
				dp.takeFullScreenshot();
			}
		} else {
			test.log(LogStatus.FAIL, "Profile Page not Opened");
			System.out.println("Profile Page not Opened");
			dp.takeScreenShot();
		}
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 0)
	public void changePAssword(Hashtable<String, String> data) throws InterruptedException, IOException {
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		SoftAssert sAssert = new SoftAssert();
		Thread.sleep(5000);
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Object resultPage = dp.openProfile();
		if (resultPage instanceof ProfilePage) {
			test.log(LogStatus.INFO, "Opening Profile");
			System.out.println("Opening Profile");
			dp.takeScreenShot();
			ProfilePage pg = (ProfilePage) resultPage;
			pg.changePassword(data);
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOf(pg.savePasswordSuccess));
			if (pg.savePasswordSuccess.getText().trim().equalsIgnoreCase("Password changed successfully")) {
				test.log(LogStatus.PASS, "ChangePassword Pass");
				System.out.println("ChangePassword Pass");
				pg.takeScreenShot();
				Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
				String CurrentPassword = data.get("Password");
				Thread.sleep(100);
				DataUtil.setData(xls, "SettingTest", 1, "Password", data.get("ConfirmPassword"));
				Thread.sleep(100);
				DataUtil.setData(xls, "SettingTest", 1, "CurrentPassword", data.get("ConfirmPassword"));
				Thread.sleep(100);
				DataUtil.setData(xls, "SettingTest", 1, "NewPassword", CurrentPassword);
				Thread.sleep(100);
				DataUtil.setData(xls, "SettingTest", 1, "ConfirmPassword", CurrentPassword);
			} else {
				test.log(LogStatus.FAIL, "ChangePassword Fail");
				System.out.println("ChangePassword Fail");
				sAssert.fail("ChangePassword Fail");
				dp.takeScreenShot();
			}

		} else {
			test.log(LogStatus.INFO, "Profile Page not Opened");
			System.out.println("Profile Page not Opened");
			dp.takeScreenShot();
		}
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", priority = 4)
	public void redeemVoucher(Hashtable<String, String> data) throws InterruptedException, IOException {
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		SoftAssert sAssert = new SoftAssert();
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		dp.redeemVoucher(data);
		Thread.sleep(3000);

		try {

			Alert alert = driver.switchTo().alert();
			if (alert.getText().equalsIgnoreCase("The activation code or coupon code is invalid!")) {
				test.log(LogStatus.PASS, "Invalid Voucher entered");
				System.out.println("Invalid Voucher entered");
				alert.accept();
				dp.redeemVoucherClose.click();
				click(dp.redeemVoucherClose, "redeemVoucherClose");

			} else {
				test.log(LogStatus.FAIL, "Message not Rec for invalid Voucher entered");
				System.out.println("Message not Rec for invalid Voucher entered");
			}
			dp.takeScreenShot();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Getting Error on Voucher Page " + e.getMessage());
			System.out.println("Getting Error on Voucher Page " + e.getMessage());
			dp.takeScreenShot();
		}

	}

	@Test(dataProvider = "getData", priority = 3)
	public void settingSubscription(Hashtable<String, String> data) throws InterruptedException, IOException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);

		Object resultPage = dp.openSubscription();
		if (resultPage instanceof SubscriptionPage) {
			test.log(LogStatus.INFO, "Opening Subscription Page");
			System.out.println("Opening Subscription Page");
			SubscriptionPage sg = (SubscriptionPage) resultPage;
			String packgeName = sg.packageName.getText().trim();
			String packgeValidity = sg.packageValidity.getText().trim();
			test.log(LogStatus.PASS,
					"Subscription Package Name : " + packgeName + " and Validity is : " + packgeValidity);
			System.out.println("Subscription Package Name : " + packgeName + " and Validity is : " + packgeValidity);
			sg.takeScreenShot();
			driver.navigate().back();
			Thread.sleep(3000);
		} else {
			test.log(LogStatus.FAIL, "Subscription Page not opened");
			System.out.println("Subscription Page not opened");
			sAssert.fail("Subscription Page not opened");
			dp.takeScreenShot();
		}

		sAssert.assertAll();
	}
}
