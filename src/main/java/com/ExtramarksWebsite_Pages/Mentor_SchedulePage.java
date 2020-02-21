package com.ExtramarksWebsite_Pages;

import java.io.IOException;
import java.util.List;

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

public class Mentor_SchedulePage extends BasePage {
	@FindBy(xpath = "//a[@id='lampiran']")
	WebElement UploadBtn;
	@FindBy(xpath = "//select[@id='uploadsubject']")
	WebElement UploadSubject;
	@FindBy(xpath = "//select[@id='uploadchapters']")
	WebElement UploadChapter;
	@FindBy(id = "fileNameUpload")
	WebElement FileUpload;

	@FindBy(xpath = "//input[@value='Upload File']")
	WebElement UploadFileButton;

	@FindBy(xpath = "(//*[@class='postlogin-card']//*[@class='recent-view'])[1]")
	public
	List<WebElement> studentExist;
	
	@FindBy(xpath = "//img[@alt='user-guide']")
	List<WebElement> AddStudent;
	
	@FindBy(xpath = "//a[@id='jadwal']")
	WebElement Schedule;
	@FindBy(xpath = "//*[@class='postlogin-card my-profile-sec ng-scope']//a[contains(text(),'Add Schedule')]")
	WebElement AddScheduleBtn;

	@FindBy(xpath = "//input[@id='title']")
	WebElement Title;
	@FindBy(xpath = "//input[@id='schedule_date6']")
	WebElement StartDate;

	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-days']//table[@class=' table-condensed']//tr//td[@class='day']")
	List<WebElement> Date;

	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-hours']//table[@class=' table-condensed']//td//span")
	List<WebElement> Hours;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-minutes']//table[@class=' table-condensed']//td//span")
	List<WebElement> Minutes;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//div[@class='datetimepicker-hours']//table[@class=' table-condensed']//td//span")
	List<WebElement> Hours_End;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//div[@class='datetimepicker-minutes']//table[@class=' table-condensed']//td//span")
	List<WebElement> Minutes_End;

	@FindBy(xpath = "//input[@id='schedule_date7']")
	WebElement EndDt;
	@FindBy(xpath = "//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//table[@class=' table-condensed']//tr//td[@class='day']")
	List<WebElement> EndDate;
	@FindBy(xpath = "//select[@id='classDropDown']")
	WebElement ClassDropDwn;
	@FindBy(xpath = "//select[@id='subjectDropDown']")
	WebElement SubjectDropDwn;
	@FindBy(xpath = "//select[@id='chapterDropDown']")
	WebElement ChapterDropDwn;

	@FindBy(xpath = "//select[@id='service']")
	WebElement Service;

	@FindBy(xpath = "//button[@id='addlessonSubmit']")
	WebElement CreateBtn;

	@FindBy(xpath = "//*[@class='postlogin-card']//a[@title]")
	public List<WebElement> studentList;

	public Mentor_SchedulePage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void AddSchedule(String title, String Class, String Subject, String Chapter) throws InterruptedException {
		Thread.sleep(3000);
		click(Schedule, "Schedule");
		Thread.sleep(3000);
		Thread.sleep(3000);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		click(AddScheduleBtn, "AddScheduleBtn");
		WebDriverWait wt = new WebDriverWait(driver, 20);
		wt.until(ExpectedConditions.visibilityOf(Title));
		Title.sendKeys(title);
		click(StartDate, "StartDate");
		click(Date, 0, "StartDate");
		click(Hours, 2, "Hours");
		click( Minutes, 2, "Minutes");

		click(EndDt, "EndDt");
		click(EndDate, 1, "EndDate");
		click(Hours_End, 5, "EndDate");
		click(Minutes_End, 2, "EndDate");
		Select classdrp = new Select(ClassDropDwn);
		classdrp.selectByVisibleText(Class);
		Thread.sleep(3000);
		click(SubjectDropDwn, "SubjectDropDwn");
		Select subjectdrp = new Select(SubjectDropDwn);
		subjectdrp.selectByVisibleText(Subject);

		Thread.sleep(3000);
		click(ChapterDropDwn, "ChapterDropDwn");
		Select chapter = new Select(ChapterDropDwn);
		chapter.selectByVisibleText(Chapter);

		Thread.sleep(3000);
		click(Service, "Service");
		Select service = new Select(Service);
		service.selectByIndex(1);
		click(CreateBtn, "CreateBtn");
		driver.navigate().back();
		driver.navigate().back();

	}

	public void UploadFiles() throws IOException, InterruptedException {
		WebDriverWait wt = new WebDriverWait(driver, 15);
		wt.until(ExpectedConditions.elementToBeClickable(UploadBtn));
		UploadBtn.click();
		Thread.sleep(3000);
		Select subject = new Select(UploadSubject);
		subject.selectByIndex(1);
		Thread.sleep(3000);
		Select chap = new Select(UploadChapter);
		chap.selectByIndex(3);
		FileUpload.click();
		new ProcessBuilder("D:\\AutoIT\\FileUpload1.exe").start();
		UploadFileButton.click();
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "Uploading File");

		Thread.sleep(5000);
	}

}
