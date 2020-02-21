package com.ExtramarksWebsite_Pages;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Parent_MyChildPage extends BasePage {

	@FindBy(id = "childEmail")
	WebElement ChildEmail;

	@FindBy(id = "addChildButton")
	WebElement AddChildButton;

	@FindBy(xpath = "//a[@class='text-lightblue']")
	WebElement AddChildOption;

	@FindBy(xpath = "//input[@id='child_name']")
	WebElement ChildName;

	@FindBy(xpath = "//input[@id='child_uid']")
	WebElement ChildUserId;

	@FindBy(xpath = "//input[@id='child_password']")
	WebElement Password;
	@FindBy(id = "date")
	WebElement DobBtn;

	@FindBy(xpath = "//div[@class='datetimepicker-days']//th[@class='switch']")
	WebElement SwitchDays;

	@FindBy(xpath = "//div[@class='datetimepicker-months']//th[@class='switch']")
	WebElement SwitchYear;

	@FindBy(xpath = "//div[@class='datetimepicker-years']//span[contains(@class,'year active') or @class='year ']")
	List<WebElement> Years;

	@FindBy(xpath = "//div[@class='datetimepicker-months']//span[contains(@class,'month active') or @class='month']")
	List<WebElement> Months;

	@FindBy(xpath = "//div[@class='datetimepicker-days']//*[contains(@class,'day active') or @class='day']")
	List<WebElement> Days;

	@FindBy(id = "gender")
	WebElement Gender;

	@FindBy(xpath = "//input[@id='school_name']")
	WebElement SchoolName;

	@FindBy(xpath = "//select[@id='board']")
	WebElement Board;

	@FindBy(xpath = "//select[@id='classname']")
	WebElement Class;

	@FindBy(xpath = "//a[text()='Save']")
	WebElement SaveButton;

	@FindBy(xpath = "//*[@id='erroruserid']")
	public List<WebElement> errorAddChild;

	@FindBy(partialLinkText = "Child Subscription")
	public WebElement ChildSubs;

	@FindBy(xpath = "//img[@class='img-responsive img-center']")
	WebElement RegisterChild;

	@FindBy(xpath = "//img[@alt='user-guide']")
	public List<WebElement> childList;

	@FindBy(xpath = "//button[contains(@onclick, 'childProfile' )]")
	public List<WebElement> childViewProfileBtn;

	@FindBy(xpath = "//div[@class='profile-detail ng-binding']")
	public List<WebElement> childProfile;

	@FindBy(xpath = "//a[@id='jadwal']")
	public WebElement childSchedule;

	@FindBy(xpath = "//*[@id='emptyActiveSchedules']/span")
	public List<WebElement> noSchedule;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li[@id='hari']/a")
	public WebElement myChildTodayFilter;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li[@id='minggu']/a")
	public WebElement myChildWeekFilter;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li[@id='month']//select")
	public WebElement myChildSelectMonthFilter;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li//input[@id='active_jadwalsearch']")
	public WebElement myChildSearchFilter;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li[@id='overdue_hari']/a")
	public WebElement myChildOverdueTodayFilter;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li[@id='overdue_minggu']/a")
	public WebElement myChildOverdueWeekFilter;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li[@id='overdue_month']//select")
	public WebElement myChildOverdueSelectMonthFilter;

	@FindBy(xpath = "//ul[@class='recent-view-top-right-menu']//li//input[@id='overdue_jadwalsearch']")
	public WebElement myChildOverdueSearchFilter;

	public Parent_MyChildPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void AddChild(String childEmail) throws InterruptedException {

		RegisterChild.click();
		Thread.sleep(2000);
		lp.takeScreenShot();
		test.log(LogStatus.INFO, "Registering a child");
		ChildEmail.sendKeys(childEmail);
		lp.takeScreenShot();
		AddChildButton.click();
		Thread.sleep(3000);
		lp.takeScreenShot();
		test.log(LogStatus.INFO, "Child Added via email only");
	}

	public void AddChildNew(String child_Name, String userId, String pwd, String school_Name)
			throws InterruptedException {

		Thread.sleep(3000);

		AddChildOption.click();
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "Adding new child");
		ChildName.sendKeys(child_Name);
		ChildUserId.sendKeys(userId);
		Password.sendKeys(pwd);
		DobBtn.click();
		Thread.sleep(1000);
		SwitchDays.click();
		Thread.sleep(1000);
		SwitchYear.click();
		Thread.sleep(1000);
		Years.get(0).click();
		Thread.sleep(1000);
		Months.get(0).click();
		Thread.sleep(1000);
		Days.get(3).click();

		Select gender = new Select(Gender);
		gender.selectByVisibleText("Female");
		SchoolName.sendKeys(school_Name);

		Select board = new Select(Board);
		board.selectByVisibleText("ICSE");

		Select cls = new Select(Class);
		cls.selectByVisibleText("V");

		Thread.sleep(4000);
		lp.takeFullScreenshot();
		SaveButton.click();
		Thread.sleep(2000);

	}

	public HashMap<String, String> myChildProfile() throws InterruptedException {
		Thread.sleep(5000);
		HashMap<String, String> profileData = new HashMap<String, String>();
				String name = childProfile.get(0).getText().trim();
				String Class = childProfile.get(1).getText().trim();
				String userName = childProfile.get(3).getText().trim();
				String mobile = childProfile.get(4).getText().trim();
				profileData.put("Name", name);
				profileData.put("Class", Class);
				profileData.put("Username", userName);
				profileData.put("Mobile", mobile);
				test.log(LogStatus.INFO, "Name : " + name + " UserName : " + userName + " Mobile : " + mobile);
				System.out.println("Name : " + name + " UserName : " + userName + " Mobile : " + mobile);
				Reporter.log("Name : " + name + " UserName : " + userName + " Mobile : " + mobile);
				Thread.sleep(5000);

		return profileData;
	}
	public void myChildSchedule() throws InterruptedException {
		Thread.sleep(2000);
		WebDriverWait wt = new WebDriverWait(driver, 30);
		wt.until(ExpectedConditions.elementToBeClickable(childSchedule));
		childSchedule.click();
		Thread.sleep(3000);
		}
	
	
	public boolean myChildScheduleFilter(String month, String search) throws InterruptedException {
		Thread.sleep(2000);
		WebDriverWait wt = new WebDriverWait(driver, 30);
		wt.until(ExpectedConditions.visibilityOf(myChildTodayFilter));
		String noScheduleMsg = "";
		if (noSchedule.size() != 0) {
			noScheduleMsg = noSchedule.get(0).getText().trim();
		}
		if (noScheduleMsg.equalsIgnoreCase("No schedule found")) {
			test.log(LogStatus.INFO, "Schedule not Found");
			System.out.println("Schedule not Found");
			Reporter.log("Schedule not Found");

			// Applying Today Filter

			boolean todayFilterResult = todayFilter();
			// Applying Weekly Filter
			boolean weekFilterResult = weekFilter();

			// Applying Monthly Filter
			boolean monthFilterResult = monthFilter(month);

			// Applying Search Filter
			boolean searchFilterResult = searchFilter(search);
			
			driver.navigate().back();
		}

		return true;
	}
	

	public boolean todayFilter() throws InterruptedException {
		Thread.sleep(2000);
		// Applying Today Filter
		myChildTodayFilter.click();
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Today Filter Selected");
		System.out.println("Today Filter Selected");
		Reporter.log("Today Filter Selected");
		takeScreenShot();
		return true;
	}

	public boolean weekFilter() throws InterruptedException {
		Thread.sleep(2000);
		// Applying Weekly Filter
		myChildWeekFilter.click();
		Thread.sleep(1000);
		test.log(LogStatus.INFO, "Weekly Filter Selected");
		System.out.println("Weekly Filter Selected");
		Reporter.log("Weekly Filter Selected");
		takeScreenShot();
		return true;
	}

	public boolean monthFilter(String month) throws InterruptedException {
		Thread.sleep(2000);

		// Applying Monthly Filter

		myChildSelectMonthFilter.click();
		Thread.sleep(1000);
		Select monthSelect = new Select(myChildSelectMonthFilter);
		monthSelect.selectByVisibleText(month);
		Thread.sleep(1000);
		takeScreenShot();

		test.log(LogStatus.INFO, "Monthly Filter Selected");
		System.out.println("Monthly Filter Selected");
		Reporter.log("Monthly Filter Selected");
		takeScreenShot();
		return true;
	}

	public boolean searchFilter(String search) throws InterruptedException {
		Thread.sleep(2000);
		// Applying Search Filter
		myChildSearchFilter.click();
		Thread.sleep(1000);
		myChildSearchFilter.sendKeys(search);
		test.log(LogStatus.INFO, "Search Filter Selected");
		System.out.println("Search Filter Selected");
		Reporter.log("Search Filter Selected");
		takeScreenShot();

		return true;
	}
}
