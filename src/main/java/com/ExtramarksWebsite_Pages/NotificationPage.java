package com.ExtramarksWebsite_Pages;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class NotificationPage extends BasePage {

	@FindBy(xpath = "//a[@class='subject-click']//img[@alt='Extramarks Logo']")
	public List<WebElement> Notification;

	@FindBy(xpath = "//a[contains(text(),'Back')]")
	public List<WebElement> back;

	@FindBy(xpath = "//*[@class='recent-activity-list']//p")
	public List<WebElement> nUserList;

	@FindBy(xpath = "//*[@class='recent-activity-list']//p/following-sibling::div[@class='pl-noti-btn']/a[1]")
	public List<WebElement> nAccept;

	@FindBy(xpath = "//*[@class='recent-activity-list']//p/following-sibling::div[@class='pl-noti-btn']/a[2]")
	public List<WebElement> nDecline;

	public List<WebElement> nAccept(int i) {
		List<WebElement> ele = driver.findElements(By.xpath("(//*[@class='recent-activity-list']//p)[" + i
				+ "]/following-sibling::div[@class='pl-noti-btn']/a[1]"));
		return ele;
	}

	public List<WebElement> nDecline(int i) {
		List<WebElement> ele = driver.findElements(By.xpath("(//*[@class='recent-activity-list']//p)[" + i
				+ "]/following-sibling::div[@class='pl-noti-btn']/a[2]"));
		return ele;
	}

	public NotificationPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	public void notificationAccept(Hashtable<String, String> data) throws Exception {
		try {
			NotificationPage np = new NotificationPage(driver, test);
			for (int i = 0; i < Notification.size(); i++) {
				// np.Notification.get(i).click();
				if (nUserList.get(i).getText().trim().contains(data.get("ProfileName"))) {
					if (nUserList.get(i).getText().trim().contains(data.get("Subject"))) {
						int k = i + 1;
						nAccept(k).get(0).click();
						Thread.sleep(3000);
						test.log(LogStatus.INFO, "Request Accept Pass");
						System.out.println("Request Accept Pass");
						break;
					}
				} else {
					// test.log(LogStatus.INFO, "Request Accept Fail");
					// System.out.println("Request Accept fail");
				}

			}
			int Back = back.size();
			if (Back == 0) {
				driver.navigate().back();
			} else {
				back.get(0).click();
			}
		} catch (Exception e) {
			System.out.println("Geting Error on Notification Accept page" + e.getMessage());
			test.log(LogStatus.INFO, "Geting Error on Notification Accept page" + e.getMessage());
			throw new Exception("Geting Error on Notification Accept page" + e.getMessage());
		}
	}

	public void notificationDecline(Hashtable<String, String> data) throws InterruptedException, IOException {

		NotificationPage np = new NotificationPage(driver, test);
		for (int i = 0; i < Notification.size(); i++) {
			// np.Notification.get(i).click();
			if (nUserList.get(i).getText().trim().contains(data.get("ProfileName"))) {
				if (nUserList.get(i).getText().trim().contains(data.get("Subject"))) {
					int k = i + 1;
					nDecline(k).get(0).click();
					Thread.sleep(3000);
					test.log(LogStatus.INFO, "Request DEcline Pass");
					System.out.println("Request Decline Pass");
					break;
				}
			} else {
				// test.log(LogStatus.INFO, "Request Accept Fail");
				// System.out.println("Request Accept fail");
			}

		}
		int Back = back.size();
		if (Back == 0) {
			driver.navigate().back();
		} else {
			back.get(0).click();
		}

	}

}
