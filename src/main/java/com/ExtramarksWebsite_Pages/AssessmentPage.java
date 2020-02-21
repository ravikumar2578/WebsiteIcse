package com.ExtramarksWebsite_Pages;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class AssessmentPage extends BasePage {
	@FindBy(partialLinkText = "Attempt Assessment")
	List<WebElement> AttemptAssessment;
	@FindBy(xpath = "//h4[contains(text(),'Question')]")
	List<WebElement> Questions;
	@FindBy(xpath = "//iframe[@class='cke_wysiwyg_frame cke_reset']")
	List<WebElement> AnswerFrame;
	@FindBy(xpath = "//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']")
	List<WebElement> Answers;
	@FindBy(xpath = "//a[@class='btn practise-btn orange ml10 font12']")
	WebElement Finish;
	@FindBy(xpath = "//button[@id='kumpulkan-tugas']")
	WebElement FinishAccept;
	@FindBy(partialLinkText = "Submitted for")
	WebElement SubmittedforEvaluation;

	@FindBy(xpath = "//p[@class='pull-right']")
	WebElement SubmittedEvaluation;
	@FindBy(xpath = "//a[@class='btn font12 tugas-submit-btn orange']")
	List<WebElement> ViewAnswer;
	@FindBy(partialLinkText = "View Assessment")
	List<WebElement> ViewAssessment;
	@FindBy(partialLinkText = "Evaluated")
	WebElement Evaluated;
	@FindBy(xpath = "//a[@class='btn font12 tugas-submit-btn orange']")
	WebElement ViewEvaluated;
	@FindBy(partialLinkText = "Not Attempted")
	WebElement NotAttempted;

	public AssessmentPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void AttemptAssessment(String answer) throws InterruptedException {
		int TotalAssessment = AttemptAssessment.size();
		lp.takeScreenShot();
		test.log(LogStatus.INFO, "Total Assessments to be attempted= " + TotalAssessment);
		System.out.println("Total Assessments to be attempted = " + TotalAssessment);

		if (TotalAssessment == 0) {

		} else {
			for (int k = TotalAssessment; k > 0; k--) {

				AttemptAssessment.get(0).click();
				// AttemptAssessment.get(0).click();
				lp.takeScreenShot();
				int TotalQuestions = Questions.size();
				lp.takeScreenShot();
				test.log(LogStatus.INFO, "Total Questions in Assessment= " + TotalQuestions);
				System.out.println("Total Questions in Assessment= " + TotalQuestions);
				int size = AnswerFrame.size();
				for (int i = 0; i < size; i++) {
					int j = 0;
					driver.switchTo().frame(i);
					Answers.get(j).sendKeys(answer);
					Thread.sleep(2000);
					lp.takeScreenShot();
					test.log(LogStatus.INFO, "Attempting question no. " + (i + 1));
					driver.switchTo().defaultContent();
					j++;
				}
				lp.takeFullScreenshot();
				test.log(LogStatus.INFO, "Finish Assessment");
				Finish.click();
				FinishAccept.click();
				lp.takeScreenShot();
			}
		}
	}

	public void SubmittedForEvaluation() {
		SubmittedforEvaluation.click();
		if(ViewAnswer.size()!=0) {
			ViewAnswer.get(0).click();
			driver.navigate().back();
		}else {
			System.out.println("No Answer Found");
			test.log(LogStatus.INFO, "No Answer Found");
		}
	}

	public void Evaluated() throws IOException, InterruptedException {
		Evaluated.click();
		if(ViewAssessment.size()!=0) {
			click(ViewAssessment,0,"ViewAssessment");
			click(ViewEvaluated,"ViewAssessment");
		}else {
			
		}
		
		/*
		 * Screenshot screenshot = new
		 * AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).
		 * takeScreenshot(driver); ImageIO.write(screenshot.getImage(), "PNG", new
		 * File("D:\\Website_Report\\Screenshot_1\\"));
		 */
		lp.takeFullScreenshot();

	}
}
