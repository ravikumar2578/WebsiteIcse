package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;
import org.testng.collections.Lists;

import com.ExtramarksWebsite_Pages.BasePage;
import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest extends Assertion {

	/*
	 * @BeforeMethod public void init() { rep = ExtentManager.getInstance(); test =
	 * rep.startTest("StudentTest"); }
	 * 
	 * @AfterMethod public void quit() { rep.endTest(test); rep.flush(); }
	 */

	public ExtentReports rep;
	public static ExtentTest test;
	public static WebDriver driver;
	public static Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);

	public static WebDriver openBrowser(String browser) {
		// normal machine
		if (browser.equals("Mozilla")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
			// System.setProperty("webdriver.chrome.driver",
			// "D:\\Drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-web-security");
			options.addArguments("use-fake-ui-for-media-stream");
			options.addArguments("allow-file-access-from-files");
			options.addArguments("use-fake-device-for-media-stream");
			// Disable extensions and hide infobars
			options.addArguments("--disable-extensions");
			options.addArguments("disable-infobars");

			Map<String, Object> prefs = new HashMap<>();

			// Enable Flash
			prefs.put("profile.default_content_setting_values.plugins", 1);
			prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
			prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);
			prefs.put("profile.default_content_setting_values.notifications", 1);
			// Hide save credentials prompt
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			/*
			 * String urlname =
			 * "{\"hardware.audio_capture_allowed_urls\" : [\"https://webrtc.extramarks.com/portal/webrtc/client\"]}"
			 * ; // Object obj=(Object)urlname; JsonPrimitive str = new
			 * JsonPrimitive("https://webrtc.extramarks.com/portal/webrtc/client");
			 * JsonArray arr = new JsonArray(); arr.add(str); JsonObject obj = new
			 * JsonObject(); obj.add("hardware.audio_capture_allowed_urls", arr);
			 * options.setExperimentalOption("prefs", obj);
			 */
			// options.addArguments("headless");
			options.addArguments("window-size=1366x768");
			// options.addArguments("incognito");
			// capabilities.setCapability("download.prompt_for_download", true);

			driver = new ChromeDriver(options);
			System.out.println("Opening Browser");
			Reporter.log("Opening Browser");
			//test.log(LogStatus.INFO, "Opening Browser");
			String browser_version = null;
			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = cap.getBrowserName();
			String browserVersion = cap.getVersion();
			System.out.println("Browser name : " + browserName + " and version: " + browserVersion);
			Reporter.log("Browser name : " + browserName + " and version: " + browserVersion);
			//test.log(LogStatus.INFO, "Browser name : " + browserName + " and version: " + browserVersion);

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

		} else if (browser.equals("ie")) {
			// System.setProperty("webdriver.ie.driver", Constants.IEDRIVER_PATH);
			driver = new InternetExplorerDriver();

		}
		// driver.manage().window().maximize(); -------This is not working when
		// executing with Jenkins

		// Dimension d = new Dimension(1382, 744);// --------------working with jenkins
		// Resize the current window to the given dimension
		// driver.manage().window().setSize(d);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// test.log(LogStatus.INFO, "Opened the browser");
		return driver;
	}

	public boolean defaultLogin(String browser, String userName, String password) throws InterruptedException {
		// String browser = data.get("Browser");
	
		//if (driver == null) {
		 openBrowser(browser);
	//	} 
		LaunchPage launch = new LaunchPage(driver, test);
		LoginPage lp = launch.goToHomePage();
		WebElement signin = driver.findElement(By.xpath("//ul[@id='navigation-top']//a[@class='signin']"));
		click(signin,"signin");
		Thread.sleep(1000);
		Object resultPage = lp.doLogin(userName, password);
		if(resultPage instanceof DashBoardPage ) {
			return true;
		}else {
			return false;
		}
		
	}

	public void openWindow() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.open()");
	}

	public void sendKeys(WebElement element, String text) {
		element.sendKeys(text);

	}

	public boolean click(WebElement element, String name) throws InterruptedException {
		boolean isClick = false;
		int time = 60;
		try {
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", element);
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			isClick = true;
		} catch (TimeoutException e) {
			try {
				System.out.println("Element : " + name + " not clickable, TimedOut after waiting for " + time);
				test.log(LogStatus.INFO, "Element : " + name + " not clickable, TimedOut after waiting for " + time);
				element.click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						element.click();
						isClick = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			}

		}
		return isClick;
	}

	public boolean click(By locator, String name) throws InterruptedException {
		WebElement element = driver.findElement(locator);
		boolean isClick = false;
		int time = 60;
		try {
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", element);
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			isClick = true;
		} catch (TimeoutException e) {
			try {
				System.out.println("Element : " + name + " not clickable, TimedOut after waiting for " + time);
				test.log(LogStatus.INFO, "Element : " + name + " not clickable, TimedOut after waiting for " + time);
				element.click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						element.click();
						isClick = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			}

		}
		return isClick;
	}

	public boolean click(By locator, int position, String name) throws InterruptedException {

		List<WebElement> element = driver.findElements(locator);
		boolean isClick = false;
		int time = 60;
		try {
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", element);
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element.get(position)));
			element.get(position).click();
			isClick = true;
		} catch (TimeoutException e) {
			try {
				System.out.println("Element : " + name + " not clickable, TimedOut after waiting for " + time);
				test.log(LogStatus.INFO, "Element : " + name + " not clickable, TimedOut after waiting for " + time);
				element.get(position).click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						element.get(position).click();
						isClick = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			}

		}
		return isClick;
	}

	public boolean click(List<WebElement> element, int position, String name) throws InterruptedException {
		boolean isClick = false;
		int time = 60;
		try {
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", element);
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.elementToBeClickable(element.get(position)));
			element.get(position).click();
			isClick = true;
		} catch (TimeoutException e) {
			try {
				System.out.println("Element : " + name + " not clickable, TimedOut after waiting for " + time);
				test.log(LogStatus.INFO, "Element : " + name + " not clickable, TimedOut after waiting for " + time);
				element.get(position).click();
			} catch (StaleElementReferenceException e2) {
				for (int i = 0; i <= 2; i++) {
					try {
						element.get(position).click();
						isClick = true;
						break;
					} catch (Exception e3) {
						if (i == 2) {
							System.out.println("Element : " + name + " is not attached to DOM");
							test.log(LogStatus.INFO, "Element : " + name + " is not attached to DOM");
						}
					}
				}
			} catch (NoSuchElementException e4) {
				System.out.println("Element : " + name + " not found");
				test.log(LogStatus.INFO, "Element : " + name + " not found");
			}

		}
		return isClick;
	}

	public boolean fluentWaitIsDisplay(final WebElement element, int timout, String name) throws Exception {
		boolean isdisplay = false;
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					WebElement element2 = element;
					return element2.isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.INFO, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);
		}
		return isdisplay;
	}

	public static boolean fluentWaitIsDisplay(final List<WebElement> element, final int position, int timout,
			String name) throws Exception {
		boolean isdisplay = false;
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					WebElement element2 = element.get(position);
					return element2.isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.FAIL, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);

		}
		return isdisplay;
	}

	public static boolean fluentWaitIsDisplay(final By locator, int timout, String name) throws Exception {
		boolean isdisplay = false;
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {

					return driver.findElement(locator).isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.INFO, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);

		}
		return isdisplay;
	}

	public static boolean fluentWaitIsDisplay(final By locator, final int position, int timout, String name)
			throws Exception {
		boolean isdisplay = false;
		try {
			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
			fluentWait.withTimeout(timout, TimeUnit.SECONDS);
			fluentWait.pollingEvery(2, TimeUnit.SECONDS);
			fluentWait.ignoring(NoSuchElementException.class);
			fluentWait.ignoring(TimeoutException.class);
			fluentWait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {

					return driver.findElements(locator).get(position).isDisplayed();
				}
			});
			isdisplay = true;
		} catch (Exception e) {
			System.out.println("Element : " + name + " is not found after waiting for time - " + timout);
			test.log(LogStatus.INFO, "Element : " + name + " is not found after waiting for time - " + timout);
			Reporter.log("Element : " + name + " is not found after waiting for time - " + timout);
		}
		return isdisplay;
	}

	public String getAttribute(WebDriver driver, List<WebElement> element, int position, String attributeName,
			String name) throws InterruptedException {
		String values = "";

		try {
			Thread.sleep(3000);
			values = element.get(position).getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					values = element.get(position).getAttribute(attributeName);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public String getAttribute(WebDriver driver, By locator, int position, String attributeName, String name)
			throws InterruptedException {
		String values = "";

		try {
			Thread.sleep(3000);
			List<WebElement> element = driver.findElements(locator);
			values = element.get(position).getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					List<WebElement> element = driver.findElements(locator);
					values = element.get(position).getAttribute(attributeName);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public String clickAndHoldMoveByOffset(WebDriver driver, By locator, int position, int height, int width,
			String name) throws InterruptedException {
		String values = "";

		try {
			List<WebElement> element = driver.findElements(locator);
			Actions ac = new Actions(driver);
			ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					List<WebElement> element = driver.findElements(locator);
					Actions ac = new Actions(driver);
					ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public String clickAndHoldMoveByOffset(WebDriver driver, By locator, int height, int width, String name)
			throws InterruptedException {
		String values = "";

		try {
			WebElement element = driver.findElement(locator);
			Actions ac = new Actions(driver);
			ac.clickAndHold(element).moveByOffset(height, width).release().build().perform();
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					WebElement element = driver.findElement(locator);
					Actions ac = new Actions(driver);
					ac.clickAndHold(element).moveByOffset(height, width).release().build().perform();
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public String clickAndHoldMoveByOffset(WebDriver driver, List<WebElement> element, int position, int height,
			int width, String name) throws InterruptedException {
		String values = "";

		try {
			Actions ac = new Actions(driver);
			ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					Actions ac = new Actions(driver);
					ac.clickAndHold(element.get(position)).moveByOffset(height, width).release().build().perform();
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
		}
		return values;
	}

	public void defaultLogin(String userName, String password) throws InterruptedException, IOException {
		LaunchPage launch = new LaunchPage(driver, test);
		LoginPage lp = launch.goToHomePage();
		WebElement signin = driver.findElement(By.xpath("//ul[@id='navigation-top']//a[@class='signin']"));
		signin.click();
		Object resultPage = lp.doLogin(userName, password);
		Thread.sleep(5000);
		lp.takeScreenShot();
		test.log(LogStatus.PASS, "LOGIN PASS");
	}

	public static void assertEquals(String actual, String expected, String message, SoftAssert sAssert) {
		BasePage bp = new BasePage(driver, test);
		if (actual.equals(expected)) {
			sAssert.assertEquals(actual, expected,
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			Reporter.log(message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.PASS, message + " Actual Result : " + actual + " Expected Result : " + expected);
		} else {
			sAssert.assertEquals(actual, expected, message);
			test.log(LogStatus.FAIL, message + " Actual Result : " + actual + " Expected Result : " + expected);
			// Reporter.log(message + " Actual Result : " + actual + " Expected Result : " +
			// expected);
		}
		bp.takeScreenShot();
	}

	public static void assertEquals(Double actual, Double expected, String message, SoftAssert sAssert) {
		BasePage bp = new BasePage(driver, test);
		if (actual.equals(expected)) {
			sAssert.assertEquals(actual, expected,
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			Reporter.log(message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.PASS, message + " Actual Result : " + actual + " Expected Result : " + expected);
		} else {
			sAssert.assertEquals(actual, expected, message);
			test.log(LogStatus.FAIL, message + " Actual Result : " + actual + " Expected Result : " + expected);

			// Reporter.log(message+"Actual Result : "+actual+"Expected Result :
			// "+expected);
		}
		bp.takeScreenShot();
	}

	public static void assertContains(String actual, String expected, String message, SoftAssert sAssert) {
		BasePage bp = new BasePage(driver, test);

		if (actual.contains(expected)) {
			sAssert.assertTrue((actual.contains(expected)),
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			Reporter.log(message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.PASS, message + " Actual Result : " + actual + " Expected Result : " + expected);
		} else {
			sAssert.assertTrue((actual.contains(expected)),
					message + " Actual Result : " + actual + " Expected Result : " + expected);
			test.log(LogStatus.FAIL, message + " Actual Result : " + actual + " Expected Result : " + expected);

			// Reporter.log(message+"Actual Result : "+actual+"Expected Result :
			// "+expected);
		}
		bp.takeScreenShot();

	}

	public static void logStatus(ITestResult result) throws Exception {
		BasePage bp = new BasePage(driver, test);
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				test.log(LogStatus.FAIL, "Test Case : " + result.getName() + "  is Fail, getting error : "
						+ result.getThrowable().getMessage().substring(0, 210));
				Reporter.log("Test Case : " + result.getName() + " is Fail, getting error : "
						+ result.getThrowable().getMessage().substring(0, 210));
				bp.takeScreenShot();
				result.setStatus(1);
			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "Test Case : " + result.getName() + "  is Skip");
				Reporter.log("Test Case : " + result.getName() + "  is Skip");
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(LogStatus.PASS, "Test Case : " + result.getName() + "  is Pass");
				Reporter.log("Test Case : " + result.getName() + "  is Pass");
			}
	
		} catch (Exception e) {
			bp.takeScreenShot();
		}
	}

}
