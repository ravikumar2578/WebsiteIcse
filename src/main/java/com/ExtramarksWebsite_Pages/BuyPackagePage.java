package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

public class BuyPackagePage extends BasePage {
	public BuyPackagePage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);

	}

	LoginPage lp = new LoginPage(driver, test);

	@FindBy(xpath = "//a[@class='btn add-schedule text-white pull-right mb-add-schedule']")
	WebElement Buy;

	@FindBy(xpath = "//select[@id='board']")
	WebElement SelectBoard;

	@FindBy(xpath = "//select[@id='classpack']")
	WebElement SelectClass;

	@FindBy(xpath = "//input[@id='boardclassnext']")
	WebElement Next;

	@FindBy(xpath = "(//div[@id='Online']//p[@class='btn btn-package-price col-md-12'])[1]")
	WebElement Pack;

	@FindBy(xpath = "//div[@id='Online']//a[@class='btn click-next']")
	WebElement Select;

	@FindBy(xpath = "//input[@id='addtocartbtn']")
	WebElement AddToCart;

	@FindBy(xpath = "//input[@value='Confirm']")
	WebElement Confirm;

	@FindBy(xpath = "//input[@id='postalcode']")
	WebElement PostalCode;

	@FindBy(xpath = "//input[@id='coupon']")
	WebElement ApplyCoupon;

	@FindBy(xpath = "//textarea[@id='address']")
	WebElement Address;

	@FindBy(xpath = "//li[@role='presentation']")
	List<WebElement> PackDetails;

	@FindBy(xpath = "//ul[@class='buy-package-subject-tab']//a[text()='Offline']")
	WebElement OfflineTab;

	@FindBy(xpath = "//div[@id='Offline']//div[contains(@class,'gallery-item')]")
	List<WebElement> OfflineDevices;

	public void BuyPackage(String coupon, String postal_code, String address) throws InterruptedException {
		Buy.click();
		Next.click();
		Pack.click();
		// Select.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		((JavascriptExecutor) driver).executeScript("scroll(0,500)");
		int details = PackDetails.size();
		System.out.println(details);
		for (int i = 0; i < details; i++) {
			//PackDetails.get(i).click();
			js.executeScript("arguments[0]. click();", PackDetails.get(i)); 
			js.executeScript("arguments[0].scrollIntoView(false);", PackDetails.get(i));
			lp.takeScreenShot();
			Thread.sleep(3000);
		}
		AddToCart.click();
		ApplyCoupon.sendKeys(coupon);
		PostalCode.sendKeys(postal_code);
		Address.sendKeys(address);
		Confirm.click();

	}

}
