package com.ExtramarksWebsite_Pages;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ServicesPage extends BasePage
{
	public ServicesPage(WebDriver dr, ExtentTest t) 
	{
		super(dr, t);
		PageFactory.initElements(driver, this);
	}
	LoginPage lp=new LoginPage(driver, test);
	
	@FindBy(xpath = "//div[@id='bs-example-navbar-collapse-2']//a[contains(text(),'Learning App')]")
	public WebElement LearningApp;

	@FindBy(xpath = "//div[@id='bs-example-navbar-collapse-2']//a[text()='Kids Learning']")
	WebElement KidsLearning;

	@FindBy(xpath = "//div[@id='bs-example-navbar-collapse-2']//a[contains(text(),'Test Prep')]")
	WebElement TestPrep;

	@FindBy(xpath = "//img[@alt='Apple App Store']")
	WebElement AppStore;

	@FindBy(xpath = "//img[@alt='Google App Store']")
	WebElement PlayStore;
	
	@FindBy(xpath="//i[@class='fa fa-download']")
	WebElement DownloadApp;
	
	@FindBy(xpath="//li[@class='dropdown open']//ul[@class='dropdown-menu default-dropdown-menu']//li")
	List<WebElement> TestPrepDropDwn;
	
	@FindBy(xpath="//a[@class='btn mt20 btn-inner-banner blue text-white']")
	WebElement Download;
	
	@FindBy(id="en_name")
	WebElement Name;
	
	@FindBy(id="en_mobile")
	WebElement Mobile;
	
	@FindBy(id="en_email")
	WebElement Email;
	
	@FindBy(xpath="//span[@id='sel1']//select[@id='en_course']")
	WebElement SelectCourse;
	
	@FindBy(xpath="//span[@id='sel2']//select[@id='emscc_loc']")
	WebElement SelectLocation;
	
	@FindBy(xpath="//input[@id='en_enrollSubmit' and @class='btn btn-default enrollbtn mt10']")
	WebElement Submit;
	
	
	
	
	public void LearningApp() throws InterruptedException 
	{
	
		WebDriverWait wt= new WebDriverWait(driver, 20);
	  wt.until(ExpectedConditions.elementToBeClickable(LearningApp));
	  
	  
	  LearningApp.click();
	  AppStore.click();

	  
	 /* ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
	  driver.switchTo().window(tabs.get(1));
	  System.out.println("Current url: "+driver.getCurrentUrl());*/
	  
	  Set <String> st= driver.getWindowHandles();
	  Iterator<String> it = st.iterator();
	  String parent =  it.next();
	  String child = it.next();

	  // switch to child
	  driver.switchTo().window(child);
	  Thread.sleep(2000);
	  System.out.println("currentURL : "+ driver.getCurrentUrl());
	  driver.close();
	  
	  driver.switchTo().window(parent);
	  System.out.println("Returned to parent");
	  
	  PlayStore.click();
	  Set <String> st1= driver.getWindowHandles();
	 Iterator<String> it1 = st1.iterator();
	  String parent1 =  it1.next();
	  String child1 = it1.next();

	  // switch to child
	  driver.switchTo().window(child1);
	  Thread.sleep(2000);
	  System.out.println("currentURL : "+ driver.getCurrentUrl());
	  driver.close();
	 
	  driver.switchTo().window(parent1);
	  System.out.println("Returned to parent");
	  driver.navigate().back();
	  
	}

	public void KidsLearning() throws InterruptedException
	{
		KidsLearning.click();
		DownloadApp.click();
		Set <String> st2= driver.getWindowHandles();
		Iterator<String> it2 = st2.iterator();
		String parent2 =  it2.next();
		String child2= it2.next();

		  // switch to child
		 driver.switchTo().window(child2);
		 Thread.sleep(2000);
		 System.out.println("currentURL : "+ driver.getCurrentUrl());
		 driver.close();
		 
		 driver.switchTo().window(parent2);
		 System.out.println("Returned to parent");
		 Thread.sleep(3000);
		 driver.navigate().back();
	}
	
	public void TestPrep()
	{
		TestPrep.click();
		int TestPrepOpt = TestPrepDropDwn.size();
		System.out.println("Total Testprep: "+ TestPrepOpt);
		
		for(int i=0; i<TestPrepOpt;i++)
		{
			TestPrepDropDwn.get(i).click();
			lp.takeFullScreenshot();
			
			
			Download.click();
			
			
			
		}
		
		
	}
	
	
	
	
}
