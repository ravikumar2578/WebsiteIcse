package com.ExtramarksWebsite_Pages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DashBoardPage extends BasePage {

	public DashBoardPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[contains(@href,'dashboard')]")
	public List<WebElement> Dashboard;

	@FindBy(xpath = "//li/a[contains(@href,'/user/schedule')]")
	public WebElement Schedule;

	@FindBy(xpath = "//a[contains(@href,'notes')]")
	public WebElement Notes;
	@FindBy(xpath = "//*[@class='collapse navbar-collapse dashboard-leftnav']//a[contains(@id,'change-tugas-class') or contains(@href,'assessment')]")
	public WebElement Assessment;

	@FindBy(xpath = "//Select[@id='classname_switch']")
	public List<WebElement> AssessmentSwitchClass;

	@FindBy(xpath = "//Select[@id='boardclass_switch']")
	public List<WebElement> AssessmentSwitchBoard;

	@FindBy(xpath = "	//*[@id='addinvite']")
	public WebElement AssessmentSwitchBoardContinueBtn;

	@FindBy(xpath = "//a[contains(@href,'report')]")
	public WebElement Report;

	@FindBy(xpath = "//a[@class='btn btn-padding-tugas lightblue text-white font16']")
	public WebElement StartLearning;
	/*
	 * @FindBy(xpath="//div[@id='recent_more']//a[@class='btn btn-border-orange']")
	 * WebElement ViewActivity;
	 */
	@FindBy(xpath = "//*[@class='col-sm-8']//a")
	WebElement AddNotes;
	@FindBy(xpath = "//a[contains(text(),'Attempt Now')]")
	WebElement AssessmentAttempt;

	@FindBy(xpath = "//*[@class='col-sm-4 pdmb0']//a")
	public WebElement AddSchedule;

	@FindBy(xpath = "//*[contains(@class,'schedule-list') and not(@id)]/li")
	public List<WebElement> scheduleList;

	@FindBy(xpath = "(//*[contains(@class,'col-sm-7 student-dashboard-right')])[1]//h4")
	public List<WebElement> scheduleDate;

	@FindBy(xpath = "(//*[contains(@class,'col-sm-7 student-dashboard-right')])[1]//div[@class='col-sm-8']")
	public List<WebElement> scheduleInfo;

	@FindBy(xpath = "//a[contains(@href,'class')]")
	public WebElement Study;

	@FindAll(@FindBy(xpath = "//a[text()='LOGOUT']"))
	public List<WebElement> LogOut;

	public void openstudytab() throws InterruptedException {
		Thread.sleep(3000);
		click(Study, "Study Link on left menu");
		// JavascriptExecutor executor = (JavascriptExecutor) driver;
		// executor.executeScript("arguments[0].click();", Study);
	}

	public Object openStudent() {
		Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
		Student.get(0).click();
		return msp;
	}

	public Object openMentor() {
		Student_MentorPage smp = new Student_MentorPage(driver, test);
		mentor.get(0).click();
		return smp;
	}

	@FindBy(xpath = "//*[@class='nav navbar-nav']//a[contains(@href,'studentlisting') and contains(text(),'Student')]")
	List<WebElement> Student;

	@FindBy(xpath = "//a[contains(@href,'/user/guru')]")
	List<WebElement> mentor;

	@FindBy(xpath = "(//*[@class='profile-subscription'])[2]")
	public List<WebElement> LeftMenumentorTab;

	// ..................Mentor Dashboard..................

	@FindBy(xpath = "//div[@class='col-sm-5 text-right']//a")
	public WebElement Mentor_AddSchedule;

	@FindBy(xpath = "//a[@href='/user/recentactivity' or @href='/user/last-activity']")
	WebElement ViewActivity;

	public Object openSchedule() {
		SchedulePage sp = new SchedulePage(driver, test);
		Schedule.click();
		return sp;
	}

	public Object openNotes() throws InterruptedException {
		NotesPage np = new NotesPage(driver, test);
		click(Notes, "Notes link");
		return np;
	}

	public Object openAssessment() throws InterruptedException {
		AssessmentPage ap = new AssessmentPage(driver, test);
		click(Assessment, "Assessment link");
		return ap;
	}

	public Object openReport() throws InterruptedException {
		ReportPage rp = new ReportPage(driver, test);
		click(Report, "Report link");
		return rp;

	}

	public void MentorAddSchedule() throws InterruptedException {
		click(Mentor_AddSchedule, "Mentor_AddSchedule");
	}

	public void ViewActivities() throws InterruptedException, IOException {
		click(ViewActivity, "ViewActivity");
		Thread.sleep(5000);
		takeFullScreenshot();
		test.log(LogStatus.INFO, "All Activities");
		takeFullScreenshot();
		driver.navigate().back();
		driver.manage().timeouts().pageLoadTimeout(150, TimeUnit.SECONDS);
		Thread.sleep(5000);
	}

//............................................Parent Dashboard..............................................................	

	@FindBy(xpath = "//*[@class='postlogin-card']//a[@href='/user/my-child']")
	public WebElement AddChild;

	@FindBy(xpath = "//a[@href='/user/my-child']")
	public WebElement MyChild;

	@FindBy(xpath = "//a[contains(text(),'Subscription')]")
	public WebElement ChildSubscription;

	@FindBy(xpath = "//li[@class='active ']//a[contains(text(),'Parent')]")
	public List<WebElement> ParentTab;

	public Object openMyChild() {
		Parent_MyChildPage pmc = new Parent_MyChildPage(driver, test);
		MyChild.click();
		return pmc;
	}

	public Object openChildSubs() throws InterruptedException {
		ChildSubscriptionPage csp = new ChildSubscriptionPage(driver, test);
		click(ChildSubscription, "ChildSubscription");
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		invisibilityOf(driver, By.xpath("//*[@class='loader-bg']/img"), 200, "loader");
		if (driver.getCurrentUrl().trim().contains("childsubscription")) {
			return csp;
		} else {
			return new DashBoardPage(driver, test);
		}

	}

	public int ParentTab() {
		int ParentTabPresent = ParentTab.size();
		// System.out.println(ParentTabPresent);
		return ParentTabPresent;
	}

	// ...Progress Report Dashboard.....

	@FindBy(xpath = "//*[contains(text(),'Progress Report')]")
	public List<WebElement> progressReport;

	@FindBy(xpath = "//*[@class='start-learning']/a")
	public List<WebElement> pReportStartLearning;

	@FindBy(xpath = "//*[contains(@class,'highcharts-point')]")
	public List<WebElement> pReportSubPoint;

	@FindBy(xpath = "//*[contains(@class,'highcharts-tooltip')]")
	public List<WebElement> pReportScore;

	@FindBy(xpath = "//*[@class='highcharts-axis-labels highcharts-xaxis-labels ']//*[@y='211']")
	public List<WebElement> pReportSub;

	public void pReportSubPoint() throws InterruptedException {
		DashBoardPage dp = new DashBoardPage(driver, test);
		Actions ac = new Actions(driver);
		String Sub = "", scoreToolTip = "";
		try {
			System.out.println("Progress Report");
			test.log(LogStatus.INFO, "Progress Report");
			if (pReportStartLearning.size() != 0) {
				System.out.println("No Progress Report");
				test.log(LogStatus.INFO, "No Progress Report");
				dp.takeScreenShot();
				Thread.sleep(1000);
			} else {
				WebDriverWait wt = new WebDriverWait(driver, 60);
				wt.until(ExpectedConditions.visibilityOfAllElements(pReportSubPoint));
				int scoreSize = pReportSubPoint.size();
				int subSize = pReportSub.size();
				System.out.println("Total Subject :" + subSize);
				System.out.println("Total Subject score :" + scoreSize);
				if (pReportSubPoint.size() != 0 && pReportSub.size() != 0) {
					((JavascriptExecutor) driver).executeScript("scroll(0,400)");
					for (int i = 0; i < pReportSubPoint.size() && i < pReportSub.size(); i++) {
						ac.clickAndHold(pReportSubPoint.get(i)).build().perform();
						scoreToolTip = pReportScore.get(0).getText();
						Sub = pReportSub.get(i).getText();
						System.out.println("Subject : " + Sub + " and " + scoreToolTip);
						test.log(LogStatus.INFO, "Subject : " + Sub + " and " + scoreToolTip);
					}
					System.out.println("Progress Report Pass");
					test.log(LogStatus.PASS, "Progress Report Pass");
					dp.takeScreenShot();
					Thread.sleep(1000);
				} else {
					System.out.println("Progress Report Fail");
					test.log(LogStatus.FAIL, "Progress Report Fail");
					dp.takeScreenShot();
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			System.out.println("Progress Report, Getting Error" + e.getMessage());
			test.log(LogStatus.FAIL, "Progress Report, Getting Error" + e.getMessage());
			dp.takeScreenShot();
			Thread.sleep(1000);
		}

	}

	// ....Preparation Dashboard...

	@FindBy(xpath = "//*[contains(text(),'Preparation')]")
	public List<WebElement> preparation;

	@FindBy(xpath = "//*[@class='preparation-tab-item']/li")
	public List<WebElement> pProgram;

	@FindBy(xpath = "//*[@class='es-banner-content']/h2")
	public List<WebElement> pProgramName;

	public void preparation() throws InterruptedException {
		DashBoardPage dp = new DashBoardPage(driver, test);
		try {
			int PreparationProgramPresent = pProgram.size();
			if (PreparationProgramPresent != 0) {
				System.out.println("Total Preparation Program : " + PreparationProgramPresent);
				test.log(LogStatus.INFO, "Total Preparation Program : " + PreparationProgramPresent);
				WebDriverWait wt = new WebDriverWait(driver, 60);
				wt.until(ExpectedConditions.visibilityOfAllElements(pProgram));
				for (int i = 0; i < PreparationProgramPresent; i++) {
					try {
						pProgram.get(i).click();
						wt.until(ExpectedConditions.visibilityOfAllElements(pProgramName));
						if (pProgramName.size() != 0) {
							System.out.println("Preparation Program : " + pProgramName.get(0).getText());
							test.log(LogStatus.PASS, "Preparation Program : " + pProgramName.get(0).getText());
							dp.takeScreenShot();
						} else {
							System.out.println("Preparation Details Page is not displayed");
							test.log(LogStatus.FAIL, "Preparation Details Page is not displayed");
							dp.takeScreenShot();
						}
						driver.navigate().back();
						Thread.sleep(1000);
					} catch (Exception e) {
						System.out.println("Getting Error on click Preparion Page" + e.getMessage());
						test.log(LogStatus.PASS, "Getting Error on click on Preparion Page" + e.getMessage());
						dp.takeScreenShot();
					}
				}

			}
		} catch (Exception e) {
			System.out.println("Getting Error on Preparion Page" + e.getMessage());
			test.log(LogStatus.PASS, "Getting Error on Preparion Page" + e.getMessage());
			dp.takeScreenShot();
		}
	}

	// ----------Setting --------

	@FindAll(@FindBy(xpath = "(//*[contains(@class,'navbar-right')]//a[@data-toggle])[3]"))
	public List<WebElement> SettingsIcon;

	@FindBy(xpath = "//a[@href='/user/my-profile']")
	WebElement Profile;

	public Object openProfile() throws InterruptedException {
		SettingsIcon.get(0).click();
		Profile.click();
		WebDriverWait wt = new WebDriverWait(driver, 30);
		ProfilePage pg = new ProfilePage(driver, test);
		wt.until(ExpectedConditions.visibilityOfAllElements(pg.EditProfile));

		DashBoardPage dp = new DashBoardPage(driver, test);
		if (pg.EditProfile.size() != 0)
			return pg;
		else
			return dp;
	}

	// ----------Redeem Voucher --------

	@FindBy(xpath = "(//a[@href='/user/my-profile']/parent::li/following-sibling::li//a)[2]")
	WebElement redeemVoucher;

	@FindBy(id = "voucher_code")
	WebElement redeemVoucherCode;

	@FindBy(xpath = "//button[contains(text(),'APPLY')]")
	WebElement redeemVoucherApply;

	@FindBy(xpath = "//*[@id='voucher_field']/button")
	public WebElement redeemVoucherClose;

	public void redeemVoucher(Hashtable<String, String> data) throws InterruptedException {
		Thread.sleep(2000);
		SettingsIcon.get(0).click();
		redeemVoucher.click();
		Thread.sleep(2000);
		WebDriverWait wt = new WebDriverWait(driver, 30);
		ProfilePage pg = new ProfilePage(driver, test);
		wt.until(ExpectedConditions.visibilityOf(redeemVoucherCode));
		test.log(LogStatus.INFO, "Entering Voucher Code");
		redeemVoucherCode.sendKeys(data.get("VoucherCode"));
		redeemVoucherApply.click();
		Thread.sleep(2000);

	}
	// ----------My Subscription --------

	@FindBy(xpath = "(//a[@href='/user/my-profile']/parent::li/following-sibling::li//a)[1]")
	WebElement mySubscription;

	public Object openSubscription() throws InterruptedException {
		Thread.sleep(1000);
		WebDriverWait wt = new WebDriverWait(driver, 30);
		click(SettingsIcon.get(0), "SettingsIcon");
		Thread.sleep(1000);
		click(mySubscription, "mySubscription");
		SubscriptionPage sg = new SubscriptionPage(driver, test);
		wt.until(ExpectedConditions.visibilityOfAllElements(sg.packageDetail));
		DashBoardPage dp = new DashBoardPage(driver, test);
		if (sg.packageDetail.size() != 0)
			return sg;
		else
			return dp;
	}

	// ----------Notification --------

	@FindBy(xpath = "//li[@role='presentation' ]//a[@class='dropdown-toggle']")
	List<WebElement> NotificationIcon;

	@FindBy(xpath = "//a[@class='view-all-notification-btn']")
	WebElement ViewNotification;

	public Object openNotification() throws Exception {
		try {
			NotificationIcon.get(0).click();
			ViewNotification.click();
			NotificationPage np = new NotificationPage(driver, test);
			DashBoardPage dp = new DashBoardPage(driver, test);
			int AllNotification = np.Notification.size();
			if (AllNotification != 0) {
				return np;
			} else {
				return dp;
			}
		} catch (Exception e) {
			System.out.println("Geting Error on Notification page" + e.getMessage());
			test.log(LogStatus.INFO, "Geting Error on Notification page" + e.getMessage());
			throw new Exception("Geting Error on Notification page" + e.getMessage());
		}
	}

	// ----------Change Class --------

	@FindBy(xpath = "//a[@id='currentselected-board-class']/span")
	WebElement changeClassIcon;

	@FindBy(xpath = "//a[@class='add-class-btn']")
	WebElement changeClassButton;

	@FindBy(id = "changecustomboard")
	WebElement Board;

	@FindBy(id = "customboardclass")
	WebElement Class;

	public Object changeClass(Hashtable<String, String> data) throws InterruptedException {
		Thread.sleep(5000);
		click(changeClassIcon, "Change Class Icon");
		Thread.sleep(2000);
		click(changeClassButton, "Change Class Button");
		Select boardSelect = new Select(Board);
		boardSelect.selectByVisibleText(data.get("Board"));

		Select classSelect = new Select(Class);
		classSelect.selectByVisibleText(data.get("Class"));
		StudyPage sg = new StudyPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		try {
			wt.until(ExpectedConditions.visibilityOfAllElements(sg.Board));
		} catch (TimeoutException timeout) {

		}
		int boardSize = sg.Board.size();
		if (boardSize != 0) {
			takeScreenShot();
			test.log(LogStatus.PASS, "Selected Board : " + sg.Board.get(0).getText() + " and selected class : "
					+ sg.Class.get(0).getText());
			System.out.println("Selected Board : " + sg.Board.get(0).getText() + " and selected class : "
					+ sg.Class.get(0).getText());
			sg.takeScreenShot();
			click(Dashboard, 0, "Dashboard");
			return sg;
		} else {
			return this;
		}

	}

	// -----------Get Board class Name-------

	public String getClassName() throws Exception {
		String boardName = "", className = "";
		int Class = 0;
		try {

			String[] boardClassName = changeClassIcon.getText().split("-");
			boardName = boardClassName[0].trim();
			className = boardClassName[1].trim();
			// Class = DataUtil.romanToDecimal(className);
		} catch (Exception e) {
			try {
				throw new Exception("Geting Error for class Name on Dashboard page" + e.getMessage());
			} catch (Exception e2) {

			}

		}
		return className;
	}

	public Object logout() throws InterruptedException {

		Thread.sleep(3000);
		try {
			if (SettingsIcon.size() != 0) {
				SettingsIcon.get(0).click();
				if (LogOut.size() != 0) {
					LogOut.get(0).click();
				}
			}
		} catch (Exception e) {

		}
		List<WebElement> signin = driver.findElements(By.xpath("//*[@class='signin']"));
		if (driver != null) {
			driver.quit();
		}
		if (signin.size() != 0) {
			return new LoginPage(driver, test);
		} else {
			return new DashBoardPage(driver, test);
		}

	}

	// Menu Navigation

	@FindBy(xpath = "//a[@class='profile-subscription']/parent::li")
	public List<WebElement> menuTab;

	@FindBy(xpath = "//*[@class='pushmenu pushmenu-left pushmenu-open left-dashboard-menu']//*[@id='mCSB_4']//ul[@class='nav navbar-nav']/li/a")
	public List<WebElement> menuLinks;

	@FindBy(xpath = "//ul[@class='nav navbar-nav']/li/a/img")
	public List<WebElement> menuLinksImg;

	public String menuTab() throws InterruptedException, URISyntaxException, IOException {
		Thread.sleep(3000);
		String responses = "";
		String linkName = "";
		if (this.menuLinks.size() != 0) {

			for (int i = 0; i < this.menuLinks.size(); i++) {

				//this.menuLinks.get(8).sendKeys("");
				 Actions ac=new Actions(driver);
				 ac.moveToElement(this.menuLinks.get(i)).build().perform();
				// if (i >= 7) {
				// Actions ac=new Actions(driver);
				// ac.moveToElement(this.menuLinks.get(i));
				/*
				 * WebElement slider = driver.findElement(By.xpath(
				 * "//*[@class='collapse navbar-collapse dashboard-leftnav']//div[@class='mCSB_dragger']"
				 * )); int drag; for (drag = 0; drag < slider.getCssValue("top").length();
				 * drag++) { char c = slider.getCssValue("top").charAt(drag); if ('0' <= c && c
				 * <= '9') { } else { break; } } String numberPart =
				 * slider.getCssValue("top").substring(0, drag);
				 * //System.out.println("numberPart " + numberPart); int height =
				 * Integer.parseInt(numberPart); int start = 5; for (int counter = 1; counter <=
				 * 20; counter++) { if (!menuLinks.get(i).isDisplayed()) { start = start + 10;
				 * Actions ac = new Actions(driver); clickAndHoldMoveByOffset(driver, By.xpath(
				 * "//*[@class='collapse navbar-collapse dashboard-leftnav']//div[@class='mCSB_dragger']"
				 * ), height, start, "Menu Slider"); Thread.sleep(2000); } else { break; } }
				 */
				// }
				Thread.sleep(2000);
				String src = getAttribute(driver, this.menuLinksImg, i, "src", "MenuLinkImg");
				URI uri = new URI(src);
				URL url = uri.toURL();
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				try {
					connection.connect();
					if (i == 0) {
						responses = connection.getResponseMessage();
						linkName = menuLinks.get(i).getText().trim();
					} else {
						responses = responses + "," + connection.getResponseMessage();
						linkName = linkName + "," + this.menuLinks.get(i).getText().trim();
					}
					connection.disconnect();
				} catch (Exception exp) {
				}
				Thread.sleep(1000);
			}
		}
		return responses + ">>" + linkName;

	}
}
