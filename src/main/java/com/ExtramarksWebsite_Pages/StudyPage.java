package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

public class StudyPage extends BasePage {

	public StudyPage(WebDriver dr, ExtentTest t) {
		super(dr, t);
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "board")
	List<WebElement>Board;

	@FindBy(name = "class")
	List<WebElement> Class;

}
