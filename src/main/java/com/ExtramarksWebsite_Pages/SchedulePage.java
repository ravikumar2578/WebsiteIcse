package com.ExtramarksWebsite_Pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SchedulePage extends BasePage {
	@FindBy(partialLinkText = "Add Schedule")
	public WebElement AddSchedule;

	@FindBy(id = "title")
	public WebElement AddTitle;

	@FindBy(xpath = "//div[@class='modal-body']//input[@id='radio1']/parent::div")
	List<WebElement> CategoryRadio;
	
	
	@FindBy(xpath = "//*[@id='service']")
	List<WebElement> CategoryDropdown;
	
	@FindBy(id = "classDropDown")
	public WebElement ClassDropDwn;

	@FindBy(xpath = "//select[@id='subjectDropDown']")
	WebElement SubjectDropDown;

	@FindBy(xpath = "//select[@id='subjectDropDown_edit']")
	WebElement EditSubject;

	@FindBy(id = "chapterDropDown")
	public WebElement ChapterDropDown;

	@FindBy(xpath = "//select[@id='chapterDropDown_edit']")
	public WebElement EditChapter;

	@FindBy(xpath = "//input[@id='schedule_date6']")
	WebElement StartDateTime;
	@FindBy(xpath = "//input[@id='schedule_date7']")
	WebElement EndDate;

	@FindBy(id = "addlessonSubmit")
	public List<WebElement> AddLesson;

	@FindBy(xpath = "//a[@class='btn change-subject-btn whitebg text-lightblue mr10']")
	WebElement MySchedule;

	@FindBy(xpath = "//i[@class='fa fa-pencil']")
	public List<WebElement> EditIcon;

	@FindBy(xpath = "//button[@id='addlessonSubmit_edit']")
	List<WebElement> EditSchedule;

	@FindBy(xpath = "//a[@title='Close Schedule']")
	List<WebElement> CloseSchedule;

	@FindBy(partialLinkText = "Overdue")
	public WebElement Overdue;

	@FindBy(partialLinkText = "Suggested")
	public WebElement Suggested;

	@FindBy(partialLinkText = "Recently Added")
	public WebElement RecentlyAdded;

	@FindBy(partialLinkText = "Exam Schedule")
	WebElement ExamSchedule;

	@FindBy(xpath = "//*[@id='schedule_title']")
	WebElement myScheduleHeader;

	@FindBy(xpath = "(//div[@class='datetimepicker-days'])[1]//table[@class]//td[@class='day active']")
	List<WebElement> Day_1;

	@FindBy(xpath = "(//div[@class='datetimepicker-hours'])[1]//table[@class]//*[@class='hour']")
	List<WebElement> Hours_1;

	@FindBy(xpath = "(//div[@class='datetimepicker-minutes'])[1]//table[@class=' table-condensed']//span[@class='minute']")
	List<WebElement> Minutes_1;

	@FindBy(xpath = "(//div[@class='datetimepicker-days'])[2]//table[@class]//td[@class='day' or @class='day new']")
	List<WebElement> Day_2;

	@FindBy(xpath = "(//div[@class='datetimepicker-hours'])[2]//table[@class]//*[@class='hour']")
	List<WebElement> Hours_2;

	@FindBy(xpath = "(//div[@class='datetimepicker-minutes'])[2]//table[@class=' table-condensed']//span[@class='minute']")
	List<WebElement> Minutes_2;

	@FindBy(xpath = "//*[@class='schedule-right-card']//ul/div/div/li")
	public List<WebElement> allscheduleList;

	@FindBy(xpath = "//*[@class='schedule-right-card']//li/p[1]/span/a")
	public List<WebElement> allscheduleChapter;

	@FindBy(xpath = "//*[@class='schedule-right-card']//li/p[1]/span/a/ancestor::p/following-sibling::div[1]")
	public List<WebElement> allscheduleTitle;

	@FindBy(xpath = "//*[@class='schedule-right-card']//li/p[1]/span/a/ancestor::p/following-sibling::p[2]")
	public List<WebElement> allscheduleDate;

	@FindBy(xpath = "//*[@id='activeSchedules']//div[@class='row']")
	public List<WebElement> scheduleList;

	@FindBy(xpath = "//*[@id='activeSchedules']//div[@class='row']//p[2]")
	List<WebElement> scheduleTitle;

	@FindBy(xpath = "//*[@id='activeSchedules']//div[@class='row']//p[1]")
	List<WebElement> scheduleSubject;

	@FindBy(xpath = "//*[@id='activeSchedules']//div[@class='row']//p[3]")
	List<WebElement> scheduleDate;
	
	@FindBy(xpath = "//a[@title='Delete Schedule']")
	public List<WebElement> deleteScheduleBtn;
	
	@FindBy(xpath = "//*[@id='deleteButton']")
	public List<WebElement> deleteScheduleConfBtn;
	
	

	public SchedulePage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void clickAddSchedule(String Title, String Class, String Subject, String Chapter)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		AddSchedule.click();
		Thread.sleep(2000);
		test.log(LogStatus.INFO, "Schedule Page");
		lp.takeScreenShot();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
		AddTitle.sendKeys(Title);

		// wait.until(ExpectedConditions.elementToBeClickable(By.id("radio1")));
		
		if(CategoryDropdown.size()!=0) {
			Select catdropdown=new Select( CategoryDropdown.get(0));
			catdropdown.selectByIndex(1);
		}
		if(CategoryRadio.size()!=0) {
			CategoryRadio.get(0).click();
		}
		
		

		// wait.until(ExpectedConditions.elementToBeSelected(By.id("classDropDown")));
		ClassDropDwn.click();
		Select sel = new Select(ClassDropDwn);
		sel.selectByVisibleText(Class);

		Thread.sleep(3000);
		SubjectDropDown.click();
		Select sel1 = new Select(SubjectDropDown);
		sel1.selectByVisibleText(Subject);

		ChapterDropDown.click();
		Select sel2 = new Select(ChapterDropDown);
		sel2.selectByVisibleText(Chapter);

		StartDateTime.click();
		Thread.sleep(1000);
		Day_1.get(0).click();
		Hours_1.get(0).click();
		Minutes_1.get(0).click();

		EndDate.click();
		Thread.sleep(1000);
		Day_2.get(0).click();
		Thread.sleep(1000);
		Hours_2.get(1).click();
		Minutes_2.get(1).click();
		AddLesson.get(0).click();
		test.log(LogStatus.INFO, "Schedule Added");
		lp.takeScreenShot();
	}

	public boolean openMySchedule() throws InterruptedException, ParseException {
		WebDriverWait wt = new WebDriverWait(driver, 20);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		wt.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("My Schedule")));
		jse.executeScript("arguments[0].click();", MySchedule);
		// MySchedule.click();
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "My Schedule");
		wt.until(ExpectedConditions.visibilityOf(myScheduleHeader));
		if (myScheduleHeader.getText().equalsIgnoreCase("My")) {
			return true;
		} else {
			return false;
		}

	}

	public boolean editSchedule(String editSubject, String editChapter) throws InterruptedException, ParseException {
		WebDriverWait wt = new WebDriverWait(driver, 20);
		int scheduleRow = 0;
		wt.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("My Schedule")));
		for (int i = 0; i < driver.findElements(By.xpath("(//div[contains(@class,'subject-list')])")).size(); i++) {
			scheduleRow = i + 1;

			List<WebElement> editIcon = driver.findElements(By
					.xpath("(//div[contains(@class,'subject-list')])[" + scheduleRow + "]//a[@title='Edit Schedule']"));
			((JavascriptExecutor) driver).executeScript("scroll(0,400)");
			if (editIcon.size() == 1) {
				editIcon.get(0).click();
				System.out.println("Opening Edit Schedule Page");
				test.log(LogStatus.INFO, "Opening Edit Schedule Page");
				Thread.sleep(2000);
				lp.takeScreenShot();
				break;
			}
		}
		Thread.sleep(3000);
		EditSubject.click();
		Select sel1 = new Select(EditSubject);
		sel1.selectByVisibleText(editSubject);
		Thread.sleep(2000);
		EditChapter.click();
		Select sel = new Select(EditChapter);
		sel.selectByVisibleText(editChapter);
		Thread.sleep(3000);
		EditSchedule.get(0).click();
		Thread.sleep(1000);
		wt.until(ExpectedConditions.invisibilityOfAllElements(EditSchedule));
		WebElement chap = driver.findElement(
				By.xpath("(//div[contains(@class,'subject-list')])[" + scheduleRow + "]//a[contains(@href,'chap')]"));
		if (chap.getText().trim().equalsIgnoreCase(editChapter)) {
			// wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Close
			// Schedule']")));
			CloseSchedule.get(0).click();
			Thread.sleep(3000);
			return true;
		} else {
			return false;
		}

	}

	public List<HashMap<String, String>> myScheduleFilter() throws InterruptedException, ParseException {

		int ScheduleSize = scheduleList.size();
		List<HashMap<String, String>> scheduleList = new ArrayList<HashMap<String, String>>();
		if (ScheduleSize != 0) {
			for (int i = 0; i < ScheduleSize; i++) {
				HashMap<String, String> scheduleData = new HashMap<String, String>();
				String scheduleHeader = scheduleTitle.get(i).getText().trim();
				String scheduleDates = scheduleDate.get(i).getText().trim();
				String scheduleSubjects = scheduleSubject.get(i).getText().trim();
				String[] scheduleEndDate = scheduleDates.split("-");
				scheduleData.put("ScheduleHeader", scheduleHeader);
				// scheduleData.put("ScheduleDates", scheduleDates);
				scheduleData.put("ScheduleSubjects", scheduleSubjects);
				scheduleData.put("ScheduleEndDate", scheduleEndDate[1].trim());
				scheduleList.add(scheduleData);
				// System.out.println("Schedule Title : " + scheduleHeader + "Subject : " +
				// scheduleSubjects
				// + " Schedule End Date : " + scheduleEndDate2);
				// test.log(LogStatus.INFO, "Schedule Title : " + scheduleHeader + "Subject : "
				// + scheduleSubjects
				// + " Schedule End Date : " + scheduleEndDate2);
				// lp.takeFullScreenshot();
			}

		}
		return scheduleList;
	}

	public void ExamSchedule() {
		ExamSchedule.click();
		test.log(LogStatus.INFO, "Exam Schedule");
		lp.takeScreenShot();
	}
	public void deleteSchedule() {
		
	
		deleteScheduleBtn.get(0).click();
		test.log(LogStatus.INFO, "Deleting Schedule");
		System.out.println("Deleting Schedule");
		WebDriverWait wt = new WebDriverWait(driver, 30);
		wt.until(ExpectedConditions.visibilityOf(deleteScheduleConfBtn.get(0)));
		deleteScheduleConfBtn.get(0).click();
		lp.takeScreenShot();
		}
	
}
