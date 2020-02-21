package com.ExtramarksWebsite_Pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.function.Function;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Student_MentorPage extends BasePage {

	@FindBy(xpath = "//a[@data-target='#add-guru']")
	WebElement addMentor;

	@FindBy(xpath = "//input[@id='mentor_email']")
	WebElement emailMentor;

	@FindBy(xpath = "//select[@id='mentor_board']")
	WebElement mentorBoard;

	@FindBy(xpath = "//select[@id='mentor_class']")
	WebElement mentorClass;

	@FindBy(xpath = "//select[@id='mentor_subject']")
	WebElement mentorSubject;
	
	
	@FindBy(xpath = "//button[@id='popup_addguru']")
	WebElement AddMentorButton;

	@FindBy(xpath = "//*[@class='modal-body']//*[contains(text(),'Your request has been sent successfully')]")
	List<WebElement> AddMentorSuccessMsg;

	@FindBy(xpath = "//*[@id='add-guru']//button[@class='close']")
	List<WebElement> AddMentorClosePopupButton;

	@FindBy(xpath = "//*[@id='view-subjects']//button[contains(@class,'close')]")
	List<WebElement> CloseButton;

	@FindBy(xpath = "//div[@ng-repeat='pguru in pendingguru']")
	public List<WebElement> pendingMentorList;

	@FindBy(xpath = "//*[@ng-repeat='pguru in pendingguru']//i[@title='see all the mapped subjects']")
	public List<WebElement> pendingMentorListSubButton;

	@FindBy(xpath = "//*[@ng-repeat='pguru in pendingguru']//a[@title='decline']")
	public List<WebElement> pendingMentorDeleteButton;

	@FindBy(xpath = "//*[@id='deleteButton']")
	public List<WebElement> pendingMentorDeleteOK;

	@FindBy(xpath = "//*[@id='subjectslisting']//ul/li/div/div")
	public List<WebElement> pendingMentorListSubjects;

	@FindBy(xpath = "//*[@class='profile-name']")
	public WebElement profileName;

	@FindBy(xpath = "//*[@ng-repeat='aguru in acceptedguru']")
	public List<WebElement> acceptedMentorRow;

	@FindBy(xpath = "//*[@ng-repeat='aguru in acceptedguru']//div[@class='caption']/./*")
	public List<WebElement> acceptedMentorCol;

	public Student_MentorPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public boolean addMentor(String email, String Board, String Class, String Subject) throws Exception {
		boolean addMentorFlag = false;
		try {
			/*
			 * Select sel = new Select(mentorBoard); sel.selectByVisibleText(Board);
			 * Thread.sleep(2000); mentorClass.click(); Select sel1= new
			 * Select(mentorClass); sel1.selectByVisibleText(Class);
			 */
			String[] subjects = Subject.split(",");

			for (int i = 0; i < subjects.length; i++) {
				addMentor.click();
				Thread.sleep(3000);
				test.log(LogStatus.INFO, "Adding Mentor");
				emailMentor.clear();
				Thread.sleep(1000);
				emailMentor.sendKeys(email);
				mentorSubject.click();
				try {
				Select sel2 = new Select(mentorSubject);
				sel2.selectByVisibleText(subjects[i]);
				}catch(Exception e){
					if(AddMentorClosePopupButton.size()!=0) {
						AddMentorClosePopupButton.get(0).click();
					}
					System.out.println("Subject is not present/select in dropdown"+e.getMessage());
					test.log(LogStatus.INFO, "Subject is not present/select in dropdown"+e.getMessage());
					throw (e);
					
				}
				AddMentorButton.click();
				WebDriverWait wt = new WebDriverWait(driver, 30);
				// wt.until(ExpectedConditions.visibilityOfAllElements((AddMentorSuccessMsg)));
				if (AddMentorSuccessMsg.size() != 0) {
					// if (AddMentorSuccessMsg.get(0).getText().equalsIgnoreCase("Your request has
					// been sent successfully")) {
					wt.until(ExpectedConditions.visibilityOfAllElements(pendingMentorList));
					wt.until(ExpectedConditions.elementToBeClickable(pendingMentorList.get(0)));
					wt.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver wdriver) {
							return ((JavascriptExecutor) driver).executeScript("return document.readyState")
									.equals("complete");
						}
					});
					Thread.sleep(5000);
					pendingMentorListSubButton.get(0).click();
					if (pendingMentorListSubjects.size() != 0) {
						Thread.sleep(5000);
						for (int j = 0; j < pendingMentorListSubjects.size(); j++) {
							if (pendingMentorListSubjects.get(j).getText().trim().equalsIgnoreCase(subjects[i])) {

								System.out.println("Subject is found on added mentor for subject: " + subjects[i]
										+ " found Subjects " + pendingMentorListSubjects.get(j).getText());
								test.log(LogStatus.INFO, "Subject is found on added mentor for subject: " + subjects[i]
										+ " found Subjects " + pendingMentorListSubjects.get(j).getText());

								Actions ac = new Actions(driver);
								ac.moveToElement(CloseButton.get(0)).build().perform();
								// JavascriptExecutor js=( JavascriptExecutor)driver;
								// js.executeScript("arguments.[0].click();",CloseButton);
								wt.until(ExpectedConditions.elementToBeClickable(CloseButton.get(0)));
								wt.until(new ExpectedCondition<Boolean>() {
									public Boolean apply(WebDriver wdriver) {
										return ((JavascriptExecutor) driver).executeScript("return document.readyState")
												.equals("complete");
									}
								});
								if (CloseButton.size() > 0)
									CloseButton.get(0).sendKeys("");
								CloseButton.get(0).click();
								System.out.println("Close button is clicked");
								addMentorFlag = true;
								break;
							} else {
								System.out.println("Subject is  not found on added mentor for subject: " + subjects[i]
										+ "  not found Subjects" + pendingMentorListSubjects.get(j).getText());
								test.log(LogStatus.INFO,
										"Subject is not found on added mentor for subject: " + subjects[i]
												+ " not found Subjects" + pendingMentorListSubjects.get(j).getText());
							}
						}
						break;
					}
					lp.takeScreenShot();
				} else if (AddMentorClosePopupButton.get(0).isDisplayed()) {
					System.out.println(
							"Mentor is already added/or Popup is not automatically closed for subject: " + subjects[i]);
					test.log(LogStatus.INFO,
							"Mentor is already added/or Popup is not automatically closed for subject: " + subjects[i]);
					// Reporter.log("Mentor is already added/or Popup is not automatically closed
					// for subject: " + subjects[i]);
					Thread.sleep(1000);

					AddMentorClosePopupButton.get(0).click();
					Thread.sleep(4000);
				}
			}
		} catch (Exception e) {
			System.out.println("Geting Error on add mentor page" + e.getMessage());
			test.log(LogStatus.INFO, "Geting Error on add mentor page" + e.getMessage());
			throw (e);
		
		}
		 finally{
			 
		 }
		return addMentorFlag;

	}

	public Object getAcceptedmentorList() throws InterruptedException, IOException {
		int mentorSize = acceptedMentorRow.size();
		List<HashMap<String, String>> mentorList = new ArrayList<HashMap<String, String>>();
		if (mentorSize != 0) {
			for (int i = 0; i < mentorSize; i++) {
				HashMap<String, String> mentorData = new HashMap<String, String>();
				mentorData.put("MentorName", acceptedMentorCol.get(0).getText());
				mentorData.put("MentorEmail", acceptedMentorCol.get(1).getText());
				mentorData.put("MentorSubject", acceptedMentorCol.get(2).getAttribute("title"));
				mentorData.put("MentorBoard", acceptedMentorCol.get(3).getText());
				mentorList.add(mentorData);
			}
			return mentorList;
		} else {
			System.out.println("Mentor not Found");
			test.log(LogStatus.INFO, "Mentor not Found");
			// Reporter.log("Mentor not Found");
			return null;
		}
	}

	int counter = 1;
	boolean deleted = false;

	public boolean deletePendingMentor(Hashtable<String, String> data) throws Exception {
		int deletementorSize = pendingMentorList.size();
		try {
				if (deletementorSize != 0) {
					pendingMentorDeleteButton.get(0).click();
					pendingMentorDeleteOK.get(0).click();
					try {
						Alert alert = driver.switchTo().alert();
						alert.accept();
					} catch (NoAlertPresentException Ex) {

					}
					lp.takeScreenShot();
					test.log(LogStatus.PASS, "Mentor Deleted");
					// Reporter.log("Mentor Deleted");
					deleted = true;
				} else {
					lp.takeScreenShot();
					test.log(LogStatus.PASS, "Mentor not Present");
					deleted = true;
				}
			
		} catch (Exception e) {
			System.out.println("Geting Error on delete mentor page" + e.getMessage());
			test.log(LogStatus.INFO, "Geting Error on delete mentor page" + e.getMessage());
			throw (e);
		}
		return deleted;
	}
}
