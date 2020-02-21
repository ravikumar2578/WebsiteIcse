package com.ExtramarksWebsite_Pages;

import static org.testng.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ExtramarksWebsite_Utils.*;
import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BasePage {
	static WebDriver driver;
	ExtentTest test;

	public BasePage(WebDriver dr, ExtentTest t) {
		driver = dr;// single place in framework where driver is init
		test = t; // screenshot
	}

	public boolean sendKeys(WebElement element, String name, String text) {
		try {
			Thread.sleep(1000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.sendKeys(text);
			return true;
		} catch (Exception e) {

			test.log(LogStatus.INFO, "Element " + name + " not found, unable to enter " + text);
			System.out.println("Element " + name + " not found, unable to enter " + text);
			Reporter.log("Element " + name + " not found, unable to enter " + text);
			return false;
		}

	}

	public boolean sendKeys(List<WebElement> element, int position, String name, String text) {
		try {
			Thread.sleep(1000);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.get(position).sendKeys(text);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO,
					"Element " + name + " not found, at position " + position + " unable to enter " + text);
			System.out.println("Element " + name + " not found, at position " + position + " unable to enter " + text);
			Reporter.log("Element " + name + " not found, at position " + position + " unable to enter " + text);
			return false;
		}

	}

	public boolean sendKeys(By locator, String name, String text) {
		try {
			Thread.sleep(1000);
			WebElement element = driver.findElement(locator);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOf(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.sendKeys(text);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Element " + name + " not found, unable to enter " + text);
			System.out.println("Element " + name + " not found, unable to enter " + text);
			Reporter.log("Element " + name + " not found, unable to enter " + text);
			return false;
		}

	}

	public boolean sendKeys(By locator, int position, String name, String text) {
		try {
			Thread.sleep(1000);
			List<WebElement> element = driver.findElements(locator);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(element));
			test.log(LogStatus.INFO, "Entering " + name + " ->" + text);
			System.out.println("Entering " + name + " ->" + text);
			Reporter.log("Entering " + name + " ->" + text);
			element.get(position).sendKeys(text);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.INFO,
					"Element " + name + " not found, at position " + position + " unable to enter " + text);
			System.out.println("Element " + name + " not found, at position " + position + " unable to enter " + text);
			Reporter.log("Element " + name + " not found, at position " + position + " unable to enter " + text);
			return false;
		}

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

	public void invisibilityOf(WebDriver driver, By locator, int timeout, String name) throws InterruptedException {

		try {
			WebDriverWait wt = new WebDriverWait(driver, timeout);
			wt.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			System.out.println("Element : " + name + " not found");
			Reporter.log("Element : " + name + " not found");
		} catch (TimeoutException e) {
			System.out.println("Element : " + name + " TimedOut");
			Reporter.log("Element : " + name + " TimedOut");
		} catch (StaleElementReferenceException e) {
			for (int i = 0; i <= 2; i++) {
				try {
					WebDriverWait wt = new WebDriverWait(driver, timeout);
					wt.until(ExpectedConditions.invisibilityOfElementLocated(locator));
					Thread.sleep(2000);
					break;
				} catch (Exception e2) {

				}
			}
			System.out.println("Element : " + name + " is not attached to DOM");
			Reporter.log("Element : " + name + " is not attached to DOM");
		}
	}

	public void takeScreenShot() {
		// decide name - time stamp
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Constants.SCREENSHOT_PATH;

		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File created " + file);
			file.mkdir();
		} else {
			// file.delete();
			// file.mkdir();
		}
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// FileUtils.copyFile(srcFile, new File(path));
			Files.copy(srcFile, new File(path + screenshotFile));
			// embed
			test.log(LogStatus.INFO, test.addScreenCapture("Screenshots/" + screenshotFile));
		} catch (IOException e) {
			System.out.println("An exception occured while taking screenshot " + e.getMessage());
		}

	}

	public void takeFullScreenshot() {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Constants.SCREENSHOT_PATH + screenshotFile;
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);
		try {
			ImageIO.write(fpScreenshot.getImage(), "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO, test.addScreenCapture("Screenshots/" + screenshotFile));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public void takeContentScreenshot(WebElement eleemnt) throws IOException {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Constants.SCREENSHOT_PATH;
		String contentPath = Constants.CONTENT_SCREENSHOT_PATH;
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File created " + file);
			file.mkdir();
		} else {
			// file.delete();
			// file.mkdir();
		}
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// FileUtils.copyFile(srcFile, new File(path));
			Files.copy(srcFile, new File(path + screenshotFile));
			// embed
		} catch (IOException e) {
			System.out.println("An exception occured while taking screenshot " + e.getMessage());
		}
		BufferedImage fullImg = null;
		try {
			fullImg = ImageIO.read(new File(path + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get the location of element on the page
		Point point = eleemnt.getLocation();

		// Get width and height of the element
		int eleWidth = eleemnt.getSize().getWidth();
		int eleHeight = eleemnt.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);

		try {
			ImageIO.write(eleScreenshot, "png", new File(contentPath + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Copy the element screenshot to disk
		test.log(LogStatus.INFO, test.addScreenCapture("Screenshots/Content/" + screenshotFile));

	}

	public void pause(int i) {
		try {
			Thread.sleep(1000 * i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
