package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;

public class ClassPage extends BasePage {

	public ClassPage(WebDriver dr, ExtentTest t) {
		super(dr, t);
	}

	public int getTotalSub() {
		List<WebElement> SubjectLinks = driver
				.findElements(By.xpath("//a[contains(@href,'subject')]"));
		int links = SubjectLinks.size();
		// System.out.println("Total Subjects: "+links);
		return links;

	}

	public List<WebElement> getSubjectLinks() {
		List<WebElement> SubjectsLinks = driver
				.findElements(By.xpath("//a[contains(@href,'subject')]"));
		return SubjectsLinks;

	}
	public int getTotalSubSubj() {
		List<WebElement> SubSubjectLinks = driver
				.findElements(By.xpath("//*[@class='postlogin-card ng-scope']//a[contains(@href,'sub_subject')]"));
		int links = SubSubjectLinks.size();
		// System.out.println("Total Subjects: "+links);
		return links;

	}

	public List<WebElement> getSubSubjectLinks() {
		List<WebElement> SubSubjectsLinks = driver
				.findElements(By.xpath("//*[@class='postlogin-card ng-scope']//a[contains(@href,'sub_subject')]"));
		return SubSubjectsLinks;

	}

}
