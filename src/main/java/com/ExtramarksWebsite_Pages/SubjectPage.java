package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

public class SubjectPage extends BasePage
{
	
	
	@FindBy(xpath="//a[contains(@href,'/chapter') and not(contains(@href,'lpt'))]")
	public	List<WebElement> MainChap;
	
	public SubjectPage(WebDriver dr, ExtentTest t)
	{
		super(dr, t);
		PageFactory.initElements(driver, this);
	}
	
	public List<WebElement> getMainChapter()
	{
		List<WebElement> MainChap = driver.findElements(By.xpath("//a[contains(@href,'/chapter') and not(contains(@href,'lpt'))]"));
		return MainChap;
	}
	
	public List<WebElement> getSubChapter()
	{
		List<WebElement> SubChap= driver.findElements(By.xpath("//div[@class='panel-collapse sub-subject collapse ng-scope in']//a[@ng-if]"));
		return SubChap;
	}
	
	public List<WebElement> getPostSubChap()
	{
		List<WebElement> postSub=driver.findElements(By.xpath("//div[contains(@class, 'topic collapse in')]"));
		return postSub;
	}
	
}
