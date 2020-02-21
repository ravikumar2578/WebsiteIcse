package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

public class Mentor_Dashboard extends BasePage
{
	public Mentor_Dashboard(WebDriver driver, ExtentTest test) 
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//a[contains(text(),'Student')]")
	List<WebElement> Student;
	
	@FindBy(xpath="//div[@id='recent_more']//a[@class='btn btn-border-orange']")
	WebElement ViewActivity; 
	@FindBy(xpath="//div[@class='col-sm-5 text-right']//a")
	public	WebElement AddSChedule;
	@FindBy(xpath="//img[@alt='user-guide']")
	List<WebElement> AddStudent;
			
	@FindBy(xpath="//a[@id='jadwal']")
	WebElement Schedule;
	@FindBy(xpath="//a[@class='btn btn-border-orange ml5 hoverblue']")
	WebElement AddScheduleBtn;
	@FindBy(xpath="//input[@id='title']")
	WebElement Title;
	@FindBy(xpath="//input[@id='schedule_date6']")
	WebElement StartDate;
	@FindBy(xpath="//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-days']//table[@class=' table-condensed']//tr//td[@class='day']")
	List<WebElement> Date;
	@FindBy(xpath="//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-hours']//table[@class=' table-condensed']//td//span")
	List<WebElement> Hours; 	
	@FindBy(xpath="//div[@class='datetimepicker datetimepicker-dropdown-top-left dropdown-menu']//div[@class='datetimepicker-minutes']//table[@class=' table-condensed']//td//span")
	List<WebElement> Minutes;
	@FindBy(xpath="//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//div[@class='datetimepicker-hours']//table[@class=' table-condensed']//td//span")
	List<WebElement> Hours_End;
	@FindBy(xpath="//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//div[@class='datetimepicker-minutes']//table[@class=' table-condensed']//td//span")
	List<WebElement> Minutes_End;
	
	@FindBy(xpath="//input[@id='schedule_date7']")
	WebElement EndDt;
	@FindBy(xpath="//div[@class='datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu']//table[@class=' table-condensed']//tr//td[@class='day']")
	List<WebElement> EndDate;
	
	@FindBy(xpath="//select[@id='classDropDown']")
	WebElement ClassDropDwn;
	@FindBy(xpath="//select[@id='subjectDropDown']")
	WebElement SubjectDropDwn;
	@FindBy(xpath="//select[@id='chapterDropDown']")
	WebElement ChapterDropDwn; 
	@FindBy(xpath="//select[@id='service']")
	WebElement Service;
	@FindBy(xpath="//button[@id='addlessonSubmit']")
	WebElement CreateBtn;

	
	LoginPage lp= new LoginPage(driver, test);
	
	public void openStudent()
	{
		Student.get(1).click();
	}
	public void ViewActivities()
	{
		ViewActivity.click();
		lp.takeScreenShot();
		driver.navigate().back();
	}
	
	
		
		
	}
	
