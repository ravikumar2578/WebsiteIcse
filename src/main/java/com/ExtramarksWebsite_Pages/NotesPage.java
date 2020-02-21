package com.ExtramarksWebsite_Pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.yaml.snakeyaml.scanner.Constant;

import com.ExtramarksWebsite_Utils.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class NotesPage extends BasePage {
	@FindBy(partialLinkText = "Add Notes")
	public WebElement AddNotes;
	@FindBy(id = "title")
	public WebElement AddTitle;
	@FindBy(id = "classDropDown")
	public WebElement ClassDropDwn;
	@FindBy(xpath = "//select[@id='subjectDropDown']")
	WebElement SubjectDropDown;
	@FindBy(id = "editSubjectDropDown")
	WebElement EditSubject;
	@FindBy(id = "editChapterDropDown")
	WebElement EditChapter;

	@FindBy(id = "subSubjectDiv")
	WebElement SubSubjectDropDwn;

	@FindBy(id = "subSubjectDiv")
	List<WebElement> SubSubject;

	@FindBy(id = "chapterDropDown")
	WebElement ChapterDropDown;

	@FindBy(id = "notesDescription")
	WebElement Description;

	@FindBy(id = "addlessonSubmit")
	WebElement Submit;

	@FindBy(xpath = "//*[@id='auploadfiles']")
	WebElement Attachment;

	@FindBy(xpath = "//*[@id='allnoteDiv']//div[@class='subject-list mb20']")
	public List<WebElement> notesList;
	
	
	@FindBy(xpath = "//*[@class='list-inline']//a[contains(@href,'chapter')]")
	public List<WebElement> viewLesson;
	
	public List<WebElement> NotesCol(int row, int col) {

		List<WebElement> element = driver.findElements(
				By.xpath("((//*[@id='allnoteDiv']//div[@class='subject-list mb20'])["+ row + "]//span)[" + col + "]"));
		return element;
		
		
	}

	@FindBy(xpath = "//a[@title='Edit Notes']")
	List<WebElement> EditNotes;

	@FindBy(id = "saveedit")
	WebElement SaveEdit;

	@FindBy(xpath = "//a[@title='Delete Notes']")
	List<WebElement> DeleteNote;

	@FindBy(xpath = "//button[@id='deleteButton']")
	WebElement DeleteButton;

	@FindBy(xpath = "//a[@title='Share Notes']")
	List<WebElement> shareNotes;

	@FindBy(xpath = "//*[@id='sharetype']")
	WebElement selectContact;

	@FindBy(xpath = "//*[@class='checkbox']/input")
	WebElement checkContact;

	@FindBy(xpath = "//*[@id='bagikan']")
	WebElement shareNotesBtn;

	@FindBy(xpath = "//*[@id='share-success']")
	public
	WebElement shareNotesSuccess;

	public NotesPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void AddNotes(String Title, String Class, String Subject, String Chapter, String description)
			throws IOException, InterruptedException {

		AddNotes.click();
		Thread.sleep(1000);
		AddTitle.sendKeys(Title);
		Select sel = new Select(ClassDropDwn);
		Thread.sleep(1000);
		sel.selectByVisibleText(Class);

		Thread.sleep(3000);
		SubjectDropDown.click();
		Select sel1 = new Select(SubjectDropDown);
		Thread.sleep(1000);
		sel1.selectByVisibleText(Subject);

		/*
		 * int SubSubjectPresent = SubSubject.size();
		 * System.out.println(SubSubjectPresent);
		 * 
		 * if (SubSubjectPresent != 0) { SubSubjectDropDwn.click(); Select sel2 = new
		 * Select(SubSubjectDropDwn); sel2.selectByIndex(3); }
		 */

		Thread.sleep(3000);
		ChapterDropDown.click();
		Select sel3 = new Select(ChapterDropDown);
		sel3.selectByVisibleText(Chapter);

		Description.sendKeys(description);
		/*
		 * Attachment.click(); new
		 * ProcessBuilder("D:\\AutoIT\\FileUpload1.exe").start();
		 * //Runtime.getRuntime().exec("D:\\FileUpload01.exe");
		 */
		//Attachment.click();
		String imagePath=Constants.Image_PATH +"test.png";
		File file = new File(imagePath);
        
		Attachment.sendKeys(file.getAbsolutePath());
		Thread.sleep(5000);
		lp.takeScreenShot();
		Submit.click();
		

	}

	public List<HashMap<String, String>> viewNotes(String Title, String Class, String Subject, String Chapter,
			String description) throws IOException, InterruptedException {
		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
		try {
		Thread.sleep(3000);
		
		WebDriverWait wt = new WebDriverWait(driver, 30);
		wt.until(ExpectedConditions.visibilityOfAllElements(notesList));
		for (int i = 1; i <= notesList.size(); i++) {
			((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			Thread.sleep(1000);
			HashMap<String, String> notesData = new HashMap<String, String>();
			notesData.put("Subject", NotesCol(i, 1).get(0).getText().trim());
			notesData.put("Title", NotesCol(i, 2).get(0).getText().trim());
			notesData.put("CreatedDate", NotesCol(i, 3).get(0).getText().trim());
			notes.add(notesData);
			System.out.println("Subject" + " : " + NotesCol(i, 1).get(0).getText().trim());
			test.log(LogStatus.INFO, "Subject" + " : " + NotesCol(i, 1).get(0).getText().trim());
		}
		}catch(Exception e) {
			test.log(LogStatus.INFO,"Getting error on View notes" +e.getMessage());
			System.out.println("Getting error on View notes" +e.getMessage());
			try {
				throw(e);
			} catch (Exception e1) {
				
			}
		}
		return notes;
	}

	int editIteration = 1;

	public void EditNotes(String Title, String Class, String Subject, String Chapter, String description,
			String editSubject, String editChapter, int i) throws InterruptedException, IOException {
		if (editIteration <= 2) {
			int TotalNotes = EditNotes.size();
			if (TotalNotes != 0) {
				// for (int i = 0; i < TotalNotes; i++) {
				Thread.sleep(4000);
				EditNotes.get(i).click();
				Thread.sleep(2000);
				test.log(LogStatus.INFO, "Edit notes clicked");
				lp.takeScreenShot();

				EditSubject.click();
				Select sel = new Select(EditSubject);
				sel.selectByVisibleText(editSubject);

				EditChapter.click();
				Select sel1 = new Select(EditChapter);
				sel1.selectByVisibleText(editChapter);

				lp.takeScreenShot();
				test.log(LogStatus.INFO, "Editing Notes");
				SaveEdit.click();
				Thread.sleep(2000);
				// }
			} else {
				AddNotes(Title, Class, Subject, Chapter, description);
				editIteration++;
				EditNotes(Title, Class, Subject, Chapter, description, editSubject, editChapter, i);
			}
		}

	}

	int deleteIteration = 1;

	public HashMap<String, String> DeleteNotes(int i, String Title, String Class, String Subject, String Chapter,
			String description) throws IOException, InterruptedException {
		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> notesData = new HashMap<String, String>();
		if (deleteIteration <= 2) {
			int deleteNotes = DeleteNote.size();

			if (deleteNotes != 0) {
				System.out.println(deleteNotes);
				WebDriverWait wt = new WebDriverWait(driver, 20);
				wt.until(ExpectedConditions.visibilityOfAllElements(DeleteNote));
				notes = viewNotes(Title, Class, Subject, Chapter, description);
				if(notes.size()!=0) {
					notesData = notes.get(i);
				}else {
					
				}
				
				Thread.sleep(3000);
				DeleteNote.get(i).click();
				DeleteButton.click();
				
			} else {
				AddNotes(Title, Class, Subject, Chapter, description);
				deleteIteration++;
				DeleteNotes(i, Title, Class, Subject, Chapter, description);

			}
		}
		return notesData;
	}

	public boolean shareNotes(int i, String shareType) throws IOException, InterruptedException {
		boolean shareNotesFlag=false;
		try {
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.visibilityOfAllElements(shareNotes));
			if (shareNotes.size() != 0) {
				shareNotes.get(i).click();
				Thread.sleep(3000);
				selectContact.click();
				Select mentor = new Select(selectContact);
				Thread.sleep(1000);
				mentor.selectByVisibleText(shareType);
				Thread.sleep(1000);
				if (shareNotesBtn.getText().trim().equalsIgnoreCase("Share (0)")) {
					checkContact.click();
				} else {

				}
				shareNotesBtn.click();
				Thread.sleep(1000);
				wt.until(ExpectedConditions.textToBePresentInElement(shareNotesSuccess, "Successfully share !"));
				shareNotesFlag= true;
			}
		} catch (Exception e) {
			
			try {
				throw (e);
			} catch (Exception e1) {

			}
		}
		return shareNotesFlag;
	}
	
	public List<HashMap<String, String>> viewNotesFromOtherUser(String Title, String Class, String Subject, String Chapter,
			String description) throws IOException, InterruptedException {
		Thread.sleep(3000);
		List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
		WebDriverWait wt = new WebDriverWait(driver, 30);
		wt.until(ExpectedConditions.visibilityOfAllElements(notesList));
		for (int i = 1; i < notesList.size(); i++) {
			((JavascriptExecutor) driver).executeScript("scroll(0,200)");
			Thread.sleep(1000);
			HashMap<String, String> notesData = new HashMap<String, String>();
			notesData.put(Subject, NotesCol(i, 1).get(0).getText().trim());
			notesData.put(Title, NotesCol(i, 2).get(0).getText().trim());
			notesData.put("CreatedDate", NotesCol(i, 3).get(0).getText().trim());
			notes.add(notesData);
			System.out.println(Subject + " : " + NotesCol(i, 1).get(0).getText().trim());
			test.log(LogStatus.INFO, Subject + " : " + NotesCol(i, 1).get(0).getText().trim());
		}
		return notes;
	}
	
	public Object viewLesson(int i,String Title, String Class, String Subject, String Chapter,
			String description) throws IOException, InterruptedException {
		HashMap<String, String> notesData=new HashMap<String,String>();
		try {
			Thread.sleep(3000);
			List<HashMap<String, String>> notes = new ArrayList<HashMap<String, String>>();
			WebDriverWait wt = new WebDriverWait(driver, 30);
			wt.until(ExpectedConditions.visibilityOfAllElements(viewLesson));
			notes=viewNotes(Title, Class, Subject,Chapter,description) ;
			notesData=notes.get(i);
			viewLesson.get(i).click();
			System.out.println(notesData);
		}catch(Exception e) {
			try {
				throw(e);
			} catch (Exception e1) {
			}
		}
		
		 if (notesData.get(i) == null) {
		        return "";
		    } else {
		        return notesData;
		    }
	}
}
