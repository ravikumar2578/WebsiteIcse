package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ChildSubscriptionPage extends BasePage {
	@FindBy(xpath = "//button[contains(text(),'Program Subscription')]")
	WebElement ProgramSubscribtion;

	@FindBy(xpath = "//ul[@class='dropdown-menu pull-right subscription-select-child']/li")
	List<WebElement> SelectChild;

	@FindBy(xpath = "//div[@class='row clearfix']//div[@style='padding-left: 15px;']")
	WebElement ProgramSubMessage;

	public ChildSubscriptionPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	public void openProgSubs() throws InterruptedException {
		/*
		 * Buy Package not complete, needs to work on it
		 * 
		 */

		click(ProgramSubscribtion, "ProgramSubscribtion");
		if (this.SelectChild.size() != 0) {
			for (int i = 0; i <= this.SelectChild.size(); i++) {
				if (this.SelectChild.get(i).getAttribute("class").trim().equalsIgnoreCase("active")) {
					System.out.println("Child is already selected");
				} else {
					click(this.SelectChild, 0, "SelectChild");
					Thread.sleep(3000);
					String Message = ProgramSubMessage.getText();
					test.log(LogStatus.INFO, "This message " + Message + "is displaying");
					this.takeScreenShot();
				}

			}
		}else {
			test.log(LogStatus.INFO, "No Child Found ");
			System.out.println("No Child Found ");
			takeScreenShot();
		}
	}

}
