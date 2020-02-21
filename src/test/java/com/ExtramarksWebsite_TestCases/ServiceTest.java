package com.ExtramarksWebsite_TestCases;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.ChapterPage;
import com.ExtramarksWebsite_Pages.ClassPage;
import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.SubjectPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ServiceTest extends BaseTest {
	public static int testDataRow = 1;
	String UserType;

	@BeforeMethod(alwaysRun = true)
	public void init(Method method) {
		rep = ExtentManager.getInstance();
		String testMethodName = method.getName();
		test = rep.startTest(testMethodName);
	}

	@AfterMethod(alwaysRun = true)
	public void logOut(ITestResult itr) throws InterruptedException {

		try {
			logStatus(itr);
			DashBoardPage dp = new DashBoardPage(driver, test);
			Thread.sleep(1000);
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("arguments[0].click();", dp.SettingsIcon.get(0));
			click(dp.SettingsIcon, 0, "Settingicon");
			click(dp.LogOut, 0, "Logout");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rep.endTest(test);
			rep.flush();
			if (driver != null) {
				driver.quit();
				driver = null;
			}
		}
	}

	@AfterTest(alwaysRun = true)
	public void quit() throws InterruptedException {
     System.out.println("Test Execution finished");
     test.log(LogStatus.INFO, "Test Execution finished");
     Reporter.log("Test Execution finished");
	}
	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "chPgTServicesTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1)
	public boolean verifyLearnService(Hashtable<String, String> data, SoftAssert sAssert) throws Exception {
		boolean learnServiceResult = false;
		// SoftAssert sAssert = new SoftAssert();
		ChapterPage chPg = new ChapterPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		int LearnPresent = chPg.getLearnTB().size();
		if (LearnPresent != 0) {
			Thread.sleep(3000);
			boolean displayLearnTabResult = fluentWaitIsDisplay(chPg.getLearnTB(), 0, 60,
					"Verify Learn Tab is Display");
			if (displayLearnTabResult) {
				boolean clickLeanTabResult = click(chPg.getLearnTB(), 0, "Click on LearnTB");
				if (clickLeanTabResult) {
					/*
					 * wt.until(ExpectedConditions. visibilityOfAllElements( chPg. getLearnTB() ));
					 * int LessonPresent = chPg.Lesson.size(); if (LessonPresent != 0) {
					 * chPg.Lesson(); }
					 */
					// Concept Learning
					wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
					int ConLAnimationPresent = chPg.getConceptLearningAnimation().size();
					if (ConLAnimationPresent != 0) {
						boolean displayResult = fluentWaitIsDisplay(chPg.getConceptLearningAnimation(), 0, 60,
								"getConceptLearningAnimation Screen");
						boolean animationResult = chPg.conceptLearningAnimation();
						if (animationResult) {
							test.log(LogStatus.PASS, "ConceptLearning Page Pass");
							chPg.takeScreenShot();
							Reporter.log("ConceptLearning Page Pass");
							System.out.println("ConceptLearning Page Pass");
							learnServiceResult = true;
						} else {
							test.log(LogStatus.FAIL,
									"ConceptLearning Page Fail, Location is " + driver.getCurrentUrl());
							chPg.takeScreenShot();
							sAssert.fail("ConceptLearning Page Fail, Location is " + driver.getCurrentUrl());
							System.out.println("ConceptLearning Page Fail, Location is " + driver.getCurrentUrl());
							learnServiceResult = false;
						}
						Thread.sleep(3000);
					}
					// Detail Learning
					wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
					int DetailedLearningPresent = chPg.getDetailedLearning().size();
					test.log(LogStatus.INFO, "No.of DetailedLearningPresent = " + DetailedLearningPresent);
					chPg.takeScreenShot();
					if (DetailedLearningPresent != 0) {
						boolean displayResult2 = fluentWaitIsDisplay(chPg.getDetailedLearning(), 0, 60,
								"DetailedLearning Screen");
						boolean clickresult2 = chPg.clickDetailedLearning();
						if (clickresult2) {
							fluentWaitIsDisplay(driver.findElements(By.xpath("//iframe[@id='fulscr']")), 0, 60,
									"Detail Learning Screen");
							Thread.sleep(2000);
							boolean detailLeaningResult = chPg.DetailedLearning();
							if (detailLeaningResult) {
								test.log(LogStatus.PASS, "DetailedLearning Test Pass");
								System.out.println("DetailedLearning Test Pass");
								learnServiceResult = true;

							} else {
								test.log(LogStatus.FAIL,
										"DetailedLearning Test Fail, Location is " + driver.getCurrentUrl());
								System.out.println("DetailedLearning Test Fail, Location is " + driver.getCurrentUrl());
								sAssert.fail("DetailedLearning Test Fail, Location is " + driver.getCurrentUrl());
								chPg.takeScreenShot();
								learnServiceResult = false;
								Thread.sleep(3000);
							}
						} else {
							System.out.println("Unable to navigate on Detail Leaning page " + driver.getCurrentUrl());
							test.log(LogStatus.FAIL,
									"unable to navigate on Detail Leaning page " + driver.getCurrentUrl());
							sAssert.fail("Unable to navigate on Detail Leaning page " + driver.getCurrentUrl());
							learnServiceResult = false;
						}

					}
					// Quick Learning
					wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
					int QuickLearningPresent = chPg.getQuickLearning().size();
					if (QuickLearningPresent != 0) {
						boolean displayResult3 = fluentWaitIsDisplay(chPg.getQuickLearning(), 0, 120,
								"Quick Learning Page");
						boolean clickQuicklearn = chPg.clickQuickLearning();
						{
							if (clickQuicklearn) {
								boolean displayResult4 = fluentWaitIsDisplay(chPg.AddNotes, 60, "Quick Learning Page");
								Thread.sleep(2000);
								if (displayResult4) {
									Thread.sleep(3000);
									/*
									 * try { String result = chPg.addNotes( data.get("Title"),
									 * data.get("Description")); assertContains( chPg.addSuccess.getText() .trim(),
									 * "Added Successfully", "Verifying Add Notes Functionality", sAssert); boolean
									 * displayResult5 = fluentWaitIsDisplay( chPg.addSchedule, 0, 60,
									 * "Quick Learning Screen"); if (displayResult3) { chPg.addSchedule(
									 * data.get("Title")); } } catch (Exception e) { test.log(LogStatus.FAIL,
									 * "Getting Error on adding Notes/Schedule " + e.getMessage());
									 * System.out.println( "Getting Error on adding Notes/Schedule " +
									 * e.getMessage()); sAssert.fail( "Getting Error on adding Notes/Schedule " +
									 * e.getMessage()); lp.takeScreenShot(); }
									 */
								}
							}
							boolean qLearning = chPg.QuickLearning();
							if (qLearning) {
								test.log(LogStatus.PASS, "QuickLearning Test Pass ");
								System.out.println("QuickLearning Test Pass ");
								learnServiceResult = true;
							} else {
								test.log(LogStatus.FAIL, "QuickLearning Test Fail ",
										"Location is " + driver.getCurrentUrl());
								System.out
										.println("QuickLearning Test Fail " + "Location is " + driver.getCurrentUrl());
								sAssert.fail("QuickLearning Test Fail " + "Location is " + driver.getCurrentUrl());
								chPg.takeScreenShot();
								learnServiceResult = false;
							}
							Thread.sleep(3000);
						}
					}

					// Miscellaneous
					wt.until(ExpectedConditions.visibilityOfAllElements(chPg.getLearnTB()));
					int MiscellaneousPresent = chPg.getMiscellaneous().size();
					if (MiscellaneousPresent != 0) {
						boolean displayResult6 = fluentWaitIsDisplay(chPg.getMiscellaneous(), 0, 60,
								"getMiscellaneous display");
						Thread.sleep(3000);
						chPg.Miscellaneous();
						Thread.sleep(5000);
						boolean clickMiscResult = chPg.clickMiscellaneous();
						if (clickMiscResult) {
							test.log(LogStatus.PASS, "Miscellaneous Test Pass ");
							System.out.println("Miscellaneous Test Pass ");
							learnServiceResult = true;
							Thread.sleep(3000);
						} else {
							test.log(LogStatus.FAIL, "Miscellaneous Test Fail ");
							System.out.println("Miscellaneous Test Fail");
							sAssert.fail("Miscellaneous Test Fail " + " Location is " + driver.getCurrentUrl());
							learnServiceResult = false;
						}
					}

					/*
					 * wt.until(ExpectedConditions. visibilityOfAllElements( chPg. getLearnTB() ));
					 * int QuickStudyPresent = chPg.getQuickStudy().size(); if (QuickStudyPresent !=
					 * 0) { chPg.QuickStudy(); }
					 */

				}
			}

		}
		return learnServiceResult;
	}

	@Test(dataProvider = "getData", priority = 1)
	public boolean verifyPracticeService(Hashtable<String, String> data, SoftAssert sAssert) throws Exception {
		boolean practiceServiceResult = false;

		// SoftAssert sAssert = new SoftAssert();

		ChapterPage chPg = new ChapterPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		int PractisePresent = chPg.getPracticeTb().size();
		if (PractisePresent != 0) {
			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			click(chPg.getPracticeTb(), 0, "Practice Tab");
			int MotionGalleryPresent = chPg.getMotionGallery().size();
			test.log(LogStatus.INFO, "MotionGalleryPresent" + MotionGalleryPresent);
			chPg.takeScreenShot();
			if (MotionGalleryPresent != 0) {
				System.out.println("Motion Gallery");
				chPg.MotionGallery();
			}

			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			chPg.getPracticeTb().get(0).click();
			int NCERTPresent = chPg.getNCERTSol().size();
			test.log(LogStatus.INFO, "NCERTPresent" + NCERTPresent);
			chPg.takeScreenShot();
			if (NCERTPresent != 0) {
				boolean clickResult5 = chPg.clickNCERTSolution();
				if (clickResult5) {
					boolean ncertResult6 = chPg.NCERTSolution();
					if (ncertResult6) {
						test.log(LogStatus.PASS, "NCERT Test is Pass");
						System.out.println("NCERT Test is Pass");
						sAssert.fail("NCERT Test is Pass");
					} else {
						test.log(LogStatus.FAIL, "NCERT is Fail,Location is : " + driver.getCurrentUrl());
						System.out.println("NCERT is Fail,Location is : " + driver.getCurrentUrl());
						sAssert.fail("NCERT is Fail,Location is : " + driver.getCurrentUrl());
					}
					/*
					 * String result = chPg.addNotes(data.get("Title"), data.get("Description"));
					 * assertContains(chPg.addSuccess.getText().trim(), "Added Successfully",
					 * "Verifying Add Notes Functionality", sAssert);
					 * chPg.addSchedule(data.get("Title"));
					 */
				} else {
					test.log(LogStatus.FAIL,
							"Not able to click on NCERTPresent,Location is : " + driver.getCurrentUrl());
					System.out.println("Not able to click on NCERTPresent,Location is : " + driver.getCurrentUrl());
					sAssert.fail("Not able to click on NCERTPresent,Location is : " + driver.getCurrentUrl());
				}

				if (chPg.BackToChapter.size() != 0) {
					chPg.BackToChapter.get(0).click();
				} else {
					driver.navigate().back();
				}
				Thread.sleep(5000);
			}

			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			click(chPg.getPracticeTb(), 0, "getPracticeTb");
			int QAPresent = chPg.getQA().size();
			test.log(LogStatus.INFO, "QAPresent" + QAPresent);
			chPg.takeScreenShot();
			if (QAPresent != 0) {
				System.out.println("QA");
				boolean clickQA = click(chPg.getQA(), 0, "getQA");
				if (clickQA) {
					boolean QAResult = chPg.QA();
					if (QAResult) {
						test.log(LogStatus.PASS, "Q&A Test is Pass ");
						System.out.println("Q&A Test is Pass ");
						sAssert.fail("Q&A Test is Pass ");
					} else {
						test.log(LogStatus.FAIL, "Q&A Test is Fail ,Location is : " + driver.getCurrentUrl());
						System.out.println("Q&A Test is Fail ,Location is : " + driver.getCurrentUrl());
						sAssert.fail("Q&A Test is Fail ,Location is : " + driver.getCurrentUrl());
					}
				} else {
					test.log(LogStatus.FAIL, "Not able to click on Q&A,Location is : " + driver.getCurrentUrl());
					System.out.println("Not able to click on Q&A,Location is : " + driver.getCurrentUrl());
					sAssert.fail("Not able to click on Q&A,Location is : " + driver.getCurrentUrl());
				}

			}

			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			chPg.getPracticeTb().get(0).click();
			int HOTSPresent = chPg.getNCERTSol().size();
			test.log(LogStatus.INFO, "HOTSPresent" + HOTSPresent);
			chPg.takeScreenShot();
			if (HOTSPresent != 0) {
				boolean hotsResult = chPg.HOTS();
				if (hotsResult) {
					test.log(LogStatus.FAIL, "HOTS Test is Pass");
					System.out.println("HOTS Test is Pass");
					sAssert.fail("HOTS Test is Pass");
				} else {
					test.log(LogStatus.FAIL, "HOTS Test is Fail ,Location is : " + driver.getCurrentUrl());
					System.out.println("HOTS Test is Fail ,Location is : " + driver.getCurrentUrl());
					sAssert.fail("HOTS Test is Fail ,Location is : " + driver.getCurrentUrl());
				}
			}

			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			chPg.getPracticeTb().get(0).click();
			int TopicWiseQAPresent = chPg.getTopicWiseQA().size();
			test.log(LogStatus.INFO, "TopicWiseQAPresent" + TopicWiseQAPresent);
			chPg.takeScreenShot();
			if (TopicWiseQAPresent != 0) {
				System.out.println("TopicWiseQA");
				chPg.TopicwiseQA();
			}

			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			chPg.getPracticeTb().get(0).click();
			int CaseStudyPresent = chPg.getCaseStudy().size();
			test.log(LogStatus.INFO, "CaseStudyPresent" + CaseStudyPresent);
			chPg.takeScreenShot();
			if (CaseStudyPresent != 0) {
				System.out.println("Case Study");
				chPg.CaseStudy();
			}

			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			chPg.getPracticeTb().get(0).click();
			int AssignmentPresent = chPg.getAssignment().size();
			test.log(LogStatus.INFO, "AssignmentPresent" + AssignmentPresent);
			chPg.takeScreenShot();
			System.out.println(AssignmentPresent);
			if (AssignmentPresent != 0) {
				System.out.println("Assignment is present");
				chPg.Assignment();
			}

			wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='practise-panel']")));
			chPg.getPracticeTb().get(0).click();
			int ConceptCraftPresent = chPg.getConceptCraft().size();
			test.log(LogStatus.INFO, "ConceptCraftPresent" + ConceptCraftPresent);
			chPg.takeScreenShot();
			if (ConceptCraftPresent != 0) {
				System.out.println("Concept Craft");
				chPg.ConceptCraft();
			}
		}
		return practiceServiceResult;
	}

	@Test(dataProvider = "getData", priority = 1)
	public boolean verifyTestService(Hashtable<String, String> data, SoftAssert sAssert) throws Exception {
		boolean testServiceResult = false;
		// SoftAssert sAssert = new SoftAssert();
		ChapterPage chPg = new ChapterPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
		chPg.getTestTb().get(0).click();
		test.log(LogStatus.INFO, "Open Test");
		chPg.takeScreenShot();
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
		chPg.getTestTb().get(0).click();
		int MCQPresent = chPg.getMCQ().size();
		if (MCQPresent != 0) {
			boolean clickMCQResult = chPg.openMCQTest();
			if (clickMCQResult) {
				/*
				 * try { String result = chPg.addNotes(data.get("Title"),
				 * data.get("Description")); assertContains(chPg.addSuccess.getText().trim(),
				 * "Added Successfully", "Verifying Add Notes Functionality", sAssert);
				 * chPg.addSchedule(data.get("Title")); } catch (Exception e) {
				 * 
				 * }
				 */

				boolean mcqResult = chPg.MCQ();
				if (mcqResult) {
					boolean testTabDisplay = fluentWaitIsDisplay((By.xpath("//a[@id='test-panel']")), 120, "Test Tab");
					if (testTabDisplay) {
						test.log(LogStatus.PASS, "MCQ Test is Pass");
						System.out.println("MCQ Test is Pass");
						testServiceResult = true;
					}
					else {
						test.log(LogStatus.FAIL, "MCQ Test is Fail, Location is " + driver.getCurrentUrl());
						System.out.println("MCQ Test is Fail, Location is " + driver.getCurrentUrl());
						sAssert.fail("MCQ Test is Fail, Location is " + driver.getCurrentUrl());
						testServiceResult = false;
					}
				}
			} else {
				test.log(LogStatus.FAIL, "Not able to click on MCQ");
				System.out.println("Not able to click on MCQ");
				sAssert.fail("Not able to click on MCQ");
				testServiceResult = false;
			}

		}
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
		chPg.getTestTb().get(0).click();
		int AdaptiveTestPresent = chPg.getAdaptiveTest().size();
		if (AdaptiveTestPresent != 0) {
			boolean clickadaptiveTest = click(chPg.getAdaptiveTest(), 0, "getAdaptiveTest");
			if (clickadaptiveTest) {
				boolean adaptiveTestResult = chPg.AdaptiveTest();
				if (adaptiveTestResult) {
					boolean testTabDisplay = fluentWaitIsDisplay((By.xpath("//a[@id='test-panel']")), 120, "Test Tab");
					if (testTabDisplay) {
						test.log(LogStatus.PASS, "Adaptive Test Pass");
						System.out.println("Adaptive Test Pass");
						Reporter.log("Adaptive Test Pass");
						testServiceResult = false;
					} else {
						test.log(LogStatus.FAIL, "Adaptive Test Fail");
						System.out.println("Adaptive Test Fail");
						sAssert.fail("Adaptive Test Fail");
						testServiceResult = false;
					}
				}
			} else {
				test.log(LogStatus.FAIL, "Not able to click on Adaptive");
				System.out.println("Not able to click on Adaptive");
				sAssert.fail("Not able to click on Adaptive");
				testServiceResult = false;
			}

		}

		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
		chPg.getTestTb().get(0).click();
		int PeriodicTestPresent = chPg.getPeriodicTest().size();
		if (PeriodicTestPresent != 0) {
			chPg.PeriodicTest();
			testServiceResult = true;
		}

		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
		chPg.getTestTb().get(0).click();
		int UniformTestPresent = chPg.getUniformTest().size();
		if (UniformTestPresent != 0) {
			chPg.UniformTest();
			testServiceResult = true;
		}

		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='test-panel']")));
		chPg.getTestTb().get(0).click();
		int SolvedPapersPresent = chPg.getSolvedPapers().size();
		if (SolvedPapersPresent != 0) {
			chPg.SolvedPapers();
			testServiceResult = true;
		}
		return testServiceResult;
	}

}
