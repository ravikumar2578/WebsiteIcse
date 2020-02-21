package com.ExtramarksWebsite_Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Mentor_Assessment extends BasePage {
	@FindBy(id = "mentor_paper_name")
	public WebElement AssessmentTitle;

	@FindBy(xpath = "//a[@class='btn change-subject-btn orange mr10']")
	WebElement DraftAssessment;

	@FindBy(xpath = "//a[@class='text-lightblue font-semibold chapter-name pull-right']")
	List<WebElement> EvaluateAssessment;

	@FindBy(xpath = "//a[text()='View Assessment']")
	List<WebElement> ViewAssessment;

	@FindBy(xpath = "//select[@id='kelas']")
	public WebElement Classes;
	@FindBy(xpath = "//select[@id='subject-list']")
	WebElement Subject;
	@FindBy(xpath = "//select[@id='chapter-list']")
	WebElement Chapter;
	@FindBy(xpath = "//div[@class='row']//input[@onkeypress]")
	List<WebElement> WorkDuration;
	@FindBy(xpath = "//input[@name='mentor_paper_instruction[]']")
	WebElement AssessmentInstr;
	@FindBy(xpath = "//a[@id='first-lanjut']")
	WebElement Continue;
	@FindBy(xpath = "//a[@id='second-lanjut']")
	WebElement Continue2;
	@FindBy(xpath = "//a[@class='btn change-subject-btn lightblue']")
	public WebElement AddAssessment;
	@FindBy(xpath = "//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']")
	WebElement QuesArea;
	
	@FindBy(xpath = "//*[@class='postlogin-card my-profile-sec']//a[@id='essay-questions']")
	List<WebElement> QuesTypeSubjective;
	
	@FindBy(xpath = "//*[@class='postlogin-card my-profile-sec']//a[@id='repository-questions']")
	List<WebElement> QuesTypeLMS;

	@FindBy(xpath = "//*[@id='quesloder']")
	List<WebElement> QuesLoader;

	@FindBy(xpath = "//*[@id='mcqTableTbody']/tr//td[1]/div")
	List<WebElement> QuesLMScheckbox;

	@FindBy(xpath = "(//*[@id='lms']/div/ul/li//a)[9]")
	List<WebElement> QuesLMSPagesNext;

	@FindBy(xpath = "//*[@id='lmsquestionMoved']")
	List<WebElement> QuesLMSAdd;

	@FindBy(xpath = "//*[@id='lmsQuestionArea']/div[1]/div")
	List<WebElement> QuesLMSNoRecords;

	@FindBy(xpath = "//iframe[@title='Rich Text Editor, ques_editor_0']")
	WebElement iframe;

	@FindBy(xpath = "//select[@id='poin']")
	WebElement MarksDropDwn;

	@FindBy(xpath = "//a[@id='addtugasquestion']")
	WebElement AddQuestion;

	@FindBy(xpath = "//*[@class='add-more-tugas-ques']")
	WebElement AddMoreQuestion;

	@FindBy(xpath = "//a[@class='add-more-tugas-ques']")
	WebElement AddMoreQues;

	@FindBy(xpath = "//input[@id='add-all-murid']")
	WebElement AddAllStudents;

	@FindBy(xpath = "//*[@class='row']//*[@id='stdCount']")
	public WebElement step3StudentCount;

	@FindBy(xpath = "//button[@id='create-tugas']")
	WebElement SendAssessment;

	@FindBy(xpath = "//a[contains(@class,'btn font12 btn-border-orange ')]")
	List<WebElement> AssessmentList;

	@FindBy(xpath = "//p[@class='font16 font-bold text-lightblue mb0']")
	List<WebElement> SubjectName;

	@FindBy(xpath = "//p[@class='font12 text-black mb0']")
	List<WebElement> ChapterName;

	@FindBy(xpath = "//div[@class='col-sm-8 pdl0']")
	List<WebElement> Questions;

	@FindBy(xpath = "//select[@id='poin']")
	List<WebElement> Marks;

	@FindBy(xpath = "//a[@id='student-answer']")
	WebElement MarksCalculation;

	@FindBy(xpath = "//button[@id='lanjut-evaluate']")
	WebElement ContinueBtn;

	public Mentor_Assessment(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void DraftAssessment() throws InterruptedException {
		DraftAssessment.click();
		Thread.sleep(3000);
		lp.takeScreenShot();
		driver.navigate().back();
	}

	public Object AssessmentDetails(String Title, String Class, String subject, String chapter, String Hours_1,
			String Hours_2, String Minutes_1, String Minutes_2, String AssessmentInstruction) throws Exception {
		try {
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
			click(AddAssessment,"AddAssessment");
			test.log(LogStatus.INFO, "Adding Assessment Details");
			AssessmentTitle.sendKeys(Title);
			click(Classes,"Classes");
			Select sel = new Select(Classes);
			sel.selectByVisibleText(Class);
			Thread.sleep(3000);
			click(Subject,"Subject");
			Select sel1 = new Select(Subject);
			sel1.selectByVisibleText(subject);
			Thread.sleep(3000);
			click(Chapter,"Chapter");
			Select sel2 = new Select(Chapter);
			sel2.selectByVisibleText(chapter);

			WorkDuration.get(0).sendKeys(Hours_1);
			WorkDuration.get(1).sendKeys(Hours_2);
			WorkDuration.get(2).sendKeys(Minutes_1);
			WorkDuration.get(3).sendKeys(Minutes_2);

			AssessmentInstr.sendKeys(AssessmentInstruction);
			Thread.sleep(4000);
			lp.takeFullScreenshot();

			click(Continue,"Continue");
			test.log(LogStatus.INFO, "Assessment Details Added");
			Mentor_Assessment ma = new Mentor_Assessment(driver, test);
			return ma;
		} catch (Exception e) {
			test.log(LogStatus.INFO, "Getting error on assessment detail page" + driver.getCurrentUrl());
			System.out.println("Getting error on assessment detail page" + driver.getCurrentUrl());
			throw (e);
		}
	}

	public void writeQuestions(String Ques_Area) throws Exception {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Mentor_Assessment ma = new Mentor_Assessment(driver, test);
		Thread.sleep(3000);
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOfAllElements(QuesTypeSubjective));
			test.log(LogStatus.INFO, "Write the question Page opens");
			System.out.println("Write the question Page opens");
			lp.takeScreenShot();
			if (QuesTypeSubjective.size() != 0) {
				click(QuesTypeSubjective,0,"QuesTypeSubjective");
				writeSubjectiveQuestions(Ques_Area);
				Thread.sleep(3000);
				click(AddMoreQuestion,"AddMoreQuestion");
				Thread.sleep(3000);
				writeLMSQuestions(Ques_Area);
			}
			Continue2.click();

		} catch (Exception e) {
			System.out.println("Getting error on  write assesment page and Location is " + driver.getCurrentUrl());
			test.log(LogStatus.INFO,
					"Getting error on  write assesment page and Location is " + driver.getCurrentUrl());
			
		}

	}

	public void shareAssessment(String Ques_Area) throws Exception {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Mentor_Assessment ma = new Mentor_Assessment(driver, test);
		Thread.sleep(3000);
		try {
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOf(step3StudentCount));
			test.log(LogStatus.INFO, "Share Assessment Page opens");
			System.out.println("Share Assessment Page opens");
			lp.takeScreenShot();
			AddAllStudents.click();
			Thread.sleep(3000);
			lp.takeScreenShot();
			click(SendAssessment,"SendAssessment Button");
			test.log(LogStatus.INFO, "Assessment sent");

		} catch (Exception e) {
			System.out.println("Getting error on Share Assessment Page page and Location is " + driver.getCurrentUrl());
			test.log(LogStatus.INFO,
					"Getting error on Share Assessment Page page and Location is " + driver.getCurrentUrl());
			
		}
	}
	public void writeSubjectiveQuestions(String Ques_Area) throws Exception {
try {
		if (QuesTypeSubjective.size() != 0) {
			QuesTypeSubjective.get(0).click();
			driver.switchTo().frame(iframe);
			lp.takeFullScreenshot();
			test.log(LogStatus.INFO, "Creating Assessment");
			QuesArea.click();
			QuesArea.sendKeys(Ques_Area);
			driver.switchTo().defaultContent();
			Select sel = new Select(MarksDropDwn);
			sel.selectByIndex(5);
			AddQuestion.click();
			AddMoreQues.click();
			driver.switchTo().frame(iframe);
			QuesArea.sendKeys(Ques_Area);
			driver.switchTo().defaultContent();
			// Select sel1=new Select(MarksDropDwn);
			sel.selectByIndex(5);
			AddQuestion.click();
			Thread.sleep(4000);
			lp.takeFullScreenshot();
			test.log(LogStatus.INFO, "Subjective Questions is added");
			System.out.println("Subjective Questions is added");
		}
	} catch (Exception e) {
		System.out.println("Getting error on writeSubjectiveQuestions and Location is " + driver.getCurrentUrl());
		test.log(LogStatus.INFO,
				"Getting error on writeSubjectiveQuestions and Location is " + driver.getCurrentUrl());
		throw (e);
	}
}

	public void writeLMSQuestions(String Ques_Area) throws Exception {
		WebDriverWait wt = new WebDriverWait(driver, 30);
		try {
			Mentor_Assessment ma = new Mentor_Assessment(driver, test);
			if (QuesTypeLMS.size() != 0) {
				click(QuesTypeLMS,0,"QuesType");
               Thread.sleep(10000);
				if (QuesLMSNoRecords.get(0).getText().trim()
						.equalsIgnoreCase("No questions found in Extramarks repository.")) {
					writeSubjectiveQuestions(Ques_Area);
				} else {
					wt.until(ExpectedConditions.invisibilityOfAllElements(QuesLoader));
					if (QuesLMScheckbox.size() != 0) {
						do {
							for (int i = 0; i < 2; i++) {
								click(QuesLMScheckbox,i,"QuesLMScheckbox");
							}
							ma.takeScreenShot();
							Thread.sleep(3000);
							// QuesLMSPagesNext.get(0).click();
							Thread.sleep(5000);
							break;
						} while (QuesLMSPagesNext.get(0).getAttribute("onclick") != "return getnextpage('')");

					} else {
						System.out.println("No Question is Found on LMS Page");
						test.log(LogStatus.INFO, "No Question is Found on LMS Page");
					}
					QuesLMSAdd.get(0).click();

				}
			}
		} catch (Exception e) {
			System.out.println("Error Found on LMS Page" + driver.getCurrentUrl());
			test.log(LogStatus.INFO, "Error Found on LMS Page" + driver.getCurrentUrl());
			throw (e);
		}

	}

	public List<HashMap<String, String>> AssessmentList(String marks) throws Exception {
		List<HashMap<String, String>> assessmentList;
		try {
			int TotalAssessment = AssessmentList.size();
			System.out.println("Total Assessments : " + TotalAssessment);
			test.log(LogStatus.INFO, "Total Assessments : " + TotalAssessment);

			int view = ViewAssessment.size();

			System.out.println("Assessment yet to be evaluate = " + view);
			test.log(LogStatus.INFO, "Assessment yet to be evaluate = " + view);
			 assessmentList = new ArrayList<HashMap<String, String>>();
			for (int i = view-1; i >= 0; i--) {
				HashMap<String, String> assessmentData = new HashMap<String, String>();
				assessmentData.put("Subject", SubjectName.get(i).getText());
				assessmentData.put("Chapter", ChapterName.get(i).getText());
				test.log(LogStatus.INFO, "Assessment for " + SubjectName.get(i).getText() + " of the chapter "
						+ ChapterName.get(i).getText());
				System.out.println("Assessment for " + SubjectName.get(i).getText() + " of the chapter "
						+ ChapterName.get(i).getText());
				ViewAssessment.get(0).click();

				int ALreadyEvaluated = EvaluateAssessment.size();
				System.out.println("Evaluate Assessment present : " + ALreadyEvaluated);
				test.log(LogStatus.INFO, "Evaluate Assessment present : " + ALreadyEvaluated);

				if (ALreadyEvaluated == 0) {

					lp.takeFullScreenshot();
					driver.navigate().back();
				} else {
					EvaluateAssessment.get(0).click();
					int TotalQues = Questions.size();
					System.out.println("Total Questions: " + TotalQues);
					test.log(LogStatus.INFO, "Total Questions: " + TotalQues);

					for (int j = 0; j < TotalQues; j++) {
						Select sel = new Select(Marks.get(j));
						sel.selectByVisibleText(marks);
					}
					MarksCalculation.click();

					ContinueBtn.click();
				}
				assessmentList.add(assessmentData);
			}
			
		} catch (Exception e) {
			System.out.println("Error Found on LMS Page" + driver.getCurrentUrl());
			test.log(LogStatus.INFO, "Error Found on LMS Page" + driver.getCurrentUrl());
			throw (e);

		}
		return assessmentList;
	}

}
