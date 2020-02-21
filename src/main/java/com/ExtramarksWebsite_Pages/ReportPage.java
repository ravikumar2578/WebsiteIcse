package com.ExtramarksWebsite_Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportPage extends BasePage {

	@FindBy(id = "subejctdropdown")
	WebElement SubjectDropDwn;

	@FindBy(id = "chapterDropDown")
	WebElement ChapterDropDwn;

	@FindBy(xpath = "//a[@id='view_comments']")
	List<WebElement> View;

	@FindBy(xpath = "//a[@class='practise-panel']")
	WebElement PractiseTab;

	@FindBy(xpath = "//select[@id='subejctdropdown']//option[@ng-repeat]")
	List<WebElement> Subjects;
	@FindBy(xpath = "//select[@id='chapterDropDown']//option[@ng-repeat]")
	List<WebElement> Chapters;

	@FindBy(xpath = "//a[@id='rapor']")
	public WebElement Report;

	@FindBy(xpath = "//*[@id='chapterDetailbox']//p[not(@style)]")
	public List<WebElement> chapterView;

	@FindBy(xpath = "//a[@class='learn-panel']")
	public List<WebElement> Learn;

	@FindBy(xpath = "//a[@class='practise-panel']")
	public List<WebElement> Practice;

	@FindBy(xpath = "//a[@class='test-panel']")
	public List<WebElement> Test;

	@FindBy(xpath = "//*[@class='tab-pane active']//p[contains(@ng-if,'chapterDetails')]")
	public List<WebElement> noChapter;

	public WebElement reportDataValue(String tab) {
		WebElement ele = driver.findElement(
				By.xpath("//*[@class='progress-report']/p[contains(text(),'" + tab + "')]/following-sibling::p"));
		return ele;

	}

	public WebElement reportDataValueTotal() {
		WebElement ele = driver.findElement(By.xpath("//*[@class='report-progress-bar-lg']"));
		return ele;

	}

	public List<WebElement> reportTableRow(String tab) {
		List<WebElement> ele = driver.findElements(By
				.xpath("//*[@ng-if='chapterDetails." + tab + ".length > 0']/following-sibling::tbody/tr[not(@style)]"));
		return ele;

	}

	public List<WebElement> reportTableHeader(String tab) {
		List<WebElement> ele = driver
				.findElements(By.xpath("//*[@ng-if='chapterDetails." + tab + ".length > 0']/tr/th"));

		return ele;

	}

	public List<WebElement> reportTableCol(String tab, int row) {
		List<WebElement> ele = driver.findElements(By.xpath("(//*[@ng-if='chapterDetails." + tab
				+ ".length > 0']/following-sibling::tbody/tr[not(@style)])[" + row + "]/td"));
		return ele;

	}

	public WebElement reportTableData(String tab, int row, int col) {
		WebElement ele = driver.findElement(By.xpath("(//*[@ng-if='chapterDetails." + tab
				+ ".length > 0']/following-sibling::tbody/tr[not(@style)])[" + row + "]/td[" + col + "]"));
		return ele;

	}

	public List<WebElement> reportTableFeedbackTable(String tab) {
		List<WebElement> ele = driver
				.findElements(By.xpath("//*[@class='subreport-block feedback-content']//table/tbody/tr/td"));
		return ele;

	}

	public ReportPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	WebDriverWait wt = new WebDriverWait(driver, 60);

	public HashMap<String, Double> viewReport(String sub, String chap) throws InterruptedException {
		Thread.sleep(3000);
		ReportPage rp = new ReportPage(driver, test);
		int TotalSubjects = Subjects.size();
		test.log(LogStatus.INFO, "Total Subjects : " + TotalSubjects);
		System.out.println("Total Subjects: " + TotalSubjects);
		// for(int i=0; i<=TotalSubjects;i++)
		// {
		int i = 0;
		Select sel = new Select(SubjectDropDwn);
		sel.selectByVisibleText(sub);

		int TotalChapters = Chapters.size();
		// for(int j=0; j<=TotalChapters;j++)
		// {
		int j = 0;
		Select sel1 = new Select(ChapterDropDwn);
		Thread.sleep(2000);
		sel1.selectByVisibleText(chap);
		Thread.sleep(2000);
		rp.takeFullScreenshot();
		// }
		HashMap<String, Double> reportData = new HashMap<String, Double>();
		double learnDataValue = Float.parseFloat(reportDataValue("Learn").getAttribute("data-value").trim());
		double practiceDataValue = Float.parseFloat(reportDataValue("Practice").getAttribute("data-value").trim());
		double testDataValue = Float.parseFloat(reportDataValue("Test").getAttribute("data-value").trim());
		double totalDataValue = Float.parseFloat(reportDataValueTotal().getAttribute("data-percent").trim());
		learnDataValue = Math.round(learnDataValue * 10) / 10.00;
		practiceDataValue = Math.round(practiceDataValue * 10) / 10.00;
		testDataValue = Math.round(testDataValue * 10) / 10.00;
		totalDataValue = Math.round(totalDataValue * 10) / 10.00;

		reportData.put("Learn", learnDataValue);
		reportData.put("Practice", practiceDataValue);
		reportData.put("Test", testDataValue);
		reportData.put("Total", totalDataValue);
		System.out.println("Learn " + learnDataValue + " Practice : " + practiceDataValue + " Test : " + testDataValue
				+ " Total " + totalDataValue);
		test.log(LogStatus.INFO, "Learn " + learnDataValue + " Practice : " + practiceDataValue + " Test : "
				+ testDataValue + " Total " + totalDataValue);
		Reporter.log("Learn " + learnDataValue + " Practice : " + practiceDataValue + " Test : " + testDataValue
				+ " Total " + totalDataValue);
		((JavascriptExecutor) driver).executeScript("scroll(0,600)");
		return reportData;
	}

	public List<HashMap<String, String>> Learn() {
		ReportPage rp = new ReportPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		wt.until(ExpectedConditions.visibilityOfAllElements(Learn));
		Learn.get(0).click();
		List<HashMap<String, String>> learndata = new ArrayList<HashMap<String, String>>();
		if (noChapter.size() != 0) {
			test.log(LogStatus.INFO, "Test - No Chapter Details Available ");
			System.out.println("Test - No Chapter Details Available ");
		} else if (reportTableRow("learn").size() != 0) {
			
				test.log(LogStatus.INFO, "Learn - Chapter Details Available ");
				System.out.println(" Learn - Chapter Details Available ");

				for (int i = 0; i < reportTableRow("learn").size(); i++) {
					int row = i + 1;
					HashMap<String, String> learnTable = new HashMap<String, String>();
					for (int j = 0; j < reportTableCol("learn", 1).size(); j++) {
						int col = j + 1;
						learnTable.put(reportTableHeader("learn").get(j).getText().trim(),
								reportTableData("learn", row, col).getText().trim());
						test.log(LogStatus.INFO,
								"Report data learn -  Row " + row + " "
										+ reportTableHeader("learn").get(j).getText().trim() + "  :"
										+ reportTableData("learn", row, col).getText().trim());
						System.out.println("Report data learn -  Row " + row + " "
								+ reportTableHeader("learn").get(j).getText().trim() + "  :"
								+ reportTableData("learn", row, col).getText().trim());
						Reporter.log("Report data learn -  Row " + row + " "
								+ reportTableHeader("learn").get(j).getText().trim() + "  :"
								+ reportTableData("learn", row, col).getText().trim());
					}
					learndata.add(learnTable);
				}

			}
		
		return learndata;
	}

	public List<HashMap<String, String>> Practice() {
		ReportPage rp = new ReportPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		wt.until(ExpectedConditions.visibilityOfAllElements(Practice));
		Practice.get(0).click();
		List<HashMap<String, String>> practiceData = new ArrayList<HashMap<String, String>>();
		if (noChapter.size() != 0) {
			test.log(LogStatus.INFO, "Practice - No Chapter Details Available ");
			System.out.println("Practice - No Chapter Details Available ");

		} else if (reportTableRow("practice").size() != 0) {
		
				test.log(LogStatus.INFO, " practice - Chapter Details Available ");
				System.out.println(" Practice - Chapter Details Available ");

				for (int i = 0; i < reportTableRow("practice").size(); i++) {
					int row = i + 1;
					HashMap<String, String> practiceTable = new HashMap<String, String>();
					for (int j = 0; j < reportTableCol("practice", 1).size(); j++) {
						int col = j + 1;
						practiceTable.put(reportTableHeader("practice").get(j).getText().trim(),
								reportTableData("practice", row, col).getText().trim());
						test.log(LogStatus.INFO,
								"Report data practice -  Row " + row + " "
										+ reportTableHeader("practice").get(j).getText().trim() + "  :"
										+ reportTableData("practice", row, col).getText().trim());
						System.out.println("Report data practice -  Row " + row + " "
								+ reportTableHeader("practice").get(j).getText().trim() + "  :"
								+ reportTableData("practice", row, col).getText().trim());
						Reporter.log("Report data practice -  Row " + row + " "
								+ reportTableHeader("practice").get(j).getText().trim() + "  :"
								+ reportTableData("practice", row, col).getText().trim());
					}
					practiceData.add(practiceTable);
				}

			
		}
		return practiceData;

	}

	public List<HashMap<String, String>> Test() {
		ReportPage rp = new ReportPage(driver, test);
		wt.until(ExpectedConditions.visibilityOfAllElements(Test));
		Test.get(0).click();
		List<HashMap<String, String>> testData = new ArrayList<HashMap<String, String>>();
		if (noChapter.size() != 0) {
			test.log(LogStatus.INFO, "Test - No Chapter Details Available ");
			System.out.println("Test - No Chapter Details Available ");
		} else if (reportTableRow("test").size() != 0) {
				test.log(LogStatus.INFO, " test - Chapter Details Available ");
				System.out.println(" test - Chapter Details Available ");

				for (int i = 0; i < reportTableRow("test").size(); i++) {
					int row = i + 1;
					HashMap<String, String> tableData = new HashMap<String, String>();
					for (int j = 0; j < reportTableCol("test", 1).size(); j++) {
						int col = j + 1;
						tableData.put(reportTableHeader("test").get(j).getText().trim(),
								reportTableData("test", row, col).getText().trim());
						test.log(LogStatus.INFO,
								"Report data test -  Row " + row + " "
										+ reportTableHeader("test").get(j).getText().trim() + "  :"
										+ reportTableData("test", row, col).getText().trim());
						System.out.println("Report data test -  Row " + row + " "
								+ reportTableHeader("test").get(j).getText().trim() + "  :"
								+ reportTableData("test", row, col).getText().trim());
						Reporter.log("Report data test -  Row " + row + " "
								+ reportTableHeader("test").get(j).getText().trim() + "  :"
								+ reportTableData("test", row, col).getText().trim());
						testData.add(tableData);
					}
				}
			
		}
		return testData;
	}
}
