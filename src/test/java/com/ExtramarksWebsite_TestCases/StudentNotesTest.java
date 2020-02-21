package com.ExtramarksWebsite_TestCases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
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
import com.ExtramarksWebsite_Pages.NotesPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentNotesTest extends BaseTest {
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
		Object[][] data = DataUtil.getData(xls, "StudentNotesTest");
		return data;
	}

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 1)
	public void studentAddNotes(Hashtable<String, String> data) throws IOException, InterruptedException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		String expectedResult = "AddNotes_PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		NotesPage np = new NotesPage(driver, test);
		Object resultPage = dp.openNotes();

		if (resultPage instanceof NotesPage) {
			test.log(LogStatus.INFO, "Notes Page Opened");
			actualResult = "AddNotes_PASS";
			System.out.println("NotesPage opens");
		} else {
			actualResult = "AddNotes_FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "Notes Page not open");
		}

		np.AddNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
				data.get("Description"));
		Thread.sleep(5000);
		if (np.notesList.size() != 0) {
			List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();

			notes = np.viewNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
					data.get("Description"));
			if (notes.size() != 0) {
				if (notes.get(0).get(data.get("Subject")).trim().contains(data.get("Subject"))
						&& notes.get(0).get(data.get("Title")).trim().contains(data.get("Title"))) {
					actualResult = "AddNotes_PASS";
				} else {
					actualResult = "AddNotes_Records_Not_Matched/Displayed_on_Grid_FAIL";
				}
			} else {
				actualResult = "AddNotes_Records_not_Displayed_on_Grid_FAIL";
			}
		} else {
			actualResult = "AddNotes_Records_Not_Display_on_Grid_FAIL";
		}
		assertEquals(actualResult, expectedResult, "Verifying add notes functionality", sAssert);
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 2)
	public void studentEditNotes(Hashtable<String, String> data) throws IOException, InterruptedException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		String expectedResult = "EditNotes_PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		NotesPage np = new NotesPage(driver, test);
		Object resultPage = dp.openNotes();
		np.EditNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
				data.get("Description"), data.get("EditSubject"), data.get("EditChapter"), 0);
		Thread.sleep(5000);

		if (np.notesList.size() != 0) {
			List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
			if (notes.size() != 0) {

				notes = np.viewNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
						data.get("Description"));
				if (notes.get(0).get(data.get("EditSubject")).trim().contains(data.get("EditSubject"))
						&& notes.get(0).get(data.get("Title")).trim().contains(data.get("Title"))) {
					actualResult = "EditNotes_PASS";
				} else {
					actualResult = "EditNotes_Records_not_MAtched/Displayed_on_Grid_FAIL";
				}
			} else {
				actualResult = "EditNotes_Records_not_Displayed_on_Grid_FAIL";
			}
		} else {
			actualResult = "EditNotes_Records_not_Displayed_on_Grid_FAIL";
		}

		assertEquals(actualResult, expectedResult, "Verifying edit notes functionality", sAssert);
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 3)
	public void studentDeleteNotes(Hashtable<String, String> data) throws IOException, InterruptedException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		String expectedResult = "DeleteNotes_PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		NotesPage np = new NotesPage(driver, test);
		Object resultPage = dp.openNotes();

		if (resultPage instanceof NotesPage) {
			test.log(LogStatus.INFO, "Notes Page Validated");
			actualResult = "DeleteNotes_PASS";
			System.out.println("NotesPage opens");
		} else {
			actualResult = "DeleteNotes_FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "Notes Page not open");
		}
		HashMap<String, String> DeleteNotes = np.DeleteNotes(0, data.get("Title"), data.get("Class"),
				data.get("Subject"), data.get("Chapter"), data.get("Description"));

		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
		if (DeleteNotes.size() != 0) {
			if (np.notesList.size() != 0) {
				notes = np.viewNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
						data.get("Description"));
				if (notes.get(0).get(data.get("Title")).trim().equalsIgnoreCase(DeleteNotes.get(data.get("Title")))
						&& notes.get(0).get(data.get("Subject"))
								.equalsIgnoreCase(DeleteNotes.get(data.get("Subject")))) {

					actualResult = "DeleteNotes_Records_not_deleted_FAIL";
				} else {
					actualResult = "DeleteNotes_PASS";
				}
			} else {
				actualResult = "DeleteNotes_PASS";
			}
		} else {
			
			test.log(LogStatus.INFO, "No Notes found to delete");
			System.out.println("No Notes found to delete");
			Reporter.log("No Notes found to delete");
			actualResult = "DeleteNotes_PASS";
			
		}

		assertEquals(actualResult, expectedResult, "Verifying delete notes functionality", sAssert);
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 4)
	public void studentShareNotes(Hashtable<String, String> data) throws IOException, InterruptedException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		String expectedResult = "shareNotes_PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		NotesPage np = new NotesPage(driver, test);
		Object resultPage = dp.openNotes();

		if (resultPage instanceof NotesPage) {
			test.log(LogStatus.INFO, "Notes Page Validated");
			actualResult = "shareNotes_PASS";
			System.out.println("NotesPage opens");
		} else {
			actualResult = "shareNotes_FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "Notes Page not open");
		}

		boolean shareNotesFlag = np.shareNotes(0, data.get("shareType"));
		if (shareNotesFlag == true) {
			actualResult = "shareNotes_PASS";
		} else {
			actualResult = "shareNotes_FAIL";
		}
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, dependsOnMethods = {
			"shareNotes" }, priority = 5)
	public void studentVerifysharedNotes(Hashtable<String, String> data) throws IOException, InterruptedException {
		SoftAssert sAssert = new SoftAssert();
		defaultLogin(data.get("Browser"), data.get("Username2"), data.get("Password2"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		String expectedResult = "VerifysharedNotes_PASS";
		String actualResult = "";
		LoginPage lp = new LoginPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		NotesPage np = new NotesPage(driver, test);
		Object resultPage = dp.openNotes();

		if (resultPage instanceof NotesPage) {
			test.log(LogStatus.INFO, "Notes Page Validated");
			actualResult = "VerifysharedNotes_PASS";
			System.out.println("NotesPage opens");
		} else {
			actualResult = "VerifysharedNotes_FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "Notes Page not open");
		}

		Thread.sleep(5000);

		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();

		notes = np.viewNotesFromOtherUser(data.get("Title"), data.get("Class"), data.get("Subject"),
				data.get("Chapter"), data.get("Description"));
		if (notes.size() != 0) {
			if (notes.get(0).get(data.get("Subject")).trim().contains(data.get("Subject"))
					&& notes.get(0).get(data.get("Title")).trim().contains(data.get("Title"))) {
				actualResult = "VerifysharedNotes_PASS";
			} else {
				actualResult = "VerifySharedNotes_Records_Not_Matched/Displayed_on_Grid_FAIL";
			}
		} else {
			actualResult = "VerifySharedNotes_Records_not_Displayed_on_Grid_FAIL";
		}

		assertEquals(actualResult, expectedResult, "Verifying shared notes functionality", sAssert);
		sAssert.assertAll();
	}

	@Test(dataProvider = "getData", enabled = true, invocationCount = 1, priority = 6)
	public void studentViewLesson(Hashtable<String, String> data)
			throws IOException, InterruptedException, URISyntaxException {

		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		SoftAssert sAssert = new SoftAssert();
		String expectedResult = "ViewLesson_PASS";
		String actualResult = "";
		DashBoardPage dp = new DashBoardPage(driver, test);

		Object resultPage = dp.openNotes();
		if (resultPage instanceof NotesPage) {
			test.log(LogStatus.INFO, "Notes Page Opened");
			actualResult = "ViewLesson_PASS";
			System.out.println("NotesPage opens");
		} else {
			actualResult = "ViewLesson_FAIL";
			dp.takeScreenShot();
			test.log(LogStatus.INFO, "Notes Page not open");
		}
		NotesPage np = (NotesPage) resultPage;
		HashMap<String, String> notesData = new HashMap<String, String>();

		Object resultPage2 = np.viewLesson(0, data.get("Title"), data.get("Class"), data.get("Subject"),
				data.get("Chapter"), data.get("Description"));

		// HttpClient httpClient=new HttpClient();
		// Verified by HTTP API

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.extramarks.com/weblpt/json/bredcrumbs/2269/0/chapter");
		// httpGet.addHeader("User-Agent", USER_AGENT);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		// System.out.println("GET Response Status:" +
		// httpResponse.getStatusLine().getStatusCode());
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		String response2 = httpResponse.getEntity().getContent().toString();
		// HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		String bredcrumbs = "";
		try {
			JSONObject jsonObject = new JSONObject(responseString);
			for (String key : jsonObject.keySet()) {
				// System.out.println(key + "=" + jsonObject.get(key)); // to get the value }

				JSONArray jsonArray = jsonObject.optJSONArray("bredcrumbs");
				if (jsonArray != null) {
					String rack_type = "";
					String rack_name = "";
					for (int i = 1; i < jsonArray.length(); i++) {
						// System.out.println(jsonArray.length());
						JSONObject jsonObjects = jsonArray.optJSONObject(i);
						rack_name = rack_name + "," + jsonObjects.optString("rack_name");
						rack_type = rack_type + "," + jsonObjects.optString("rack_type");
						// System.out.println("Rack Name" + rack_name + " Rack Type : " + rack_type);
					}
					bredcrumbs = rack_name;
					System.out.println("Bredcrumbs" + bredcrumbs);
				}
			}
		} catch (JSONException err) {
			System.out.println("error" + err.getMessage());
		}

		httpClient.close();
		if (resultPage2 instanceof HashMap<?, ?>) {
			assertContains(notesData.get("Subject"),bredcrumbs ,"Verifying View Lesson from notes functionality",
					sAssert);
		} else {
			
			assertContains("",bredcrumbs ,"Verifying View Lesson from notes functionality",
					sAssert);
		}
		sAssert.assertAll();
	}
}
