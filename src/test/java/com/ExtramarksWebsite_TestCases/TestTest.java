package com.ExtramarksWebsite_TestCases;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

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

public class TestTest extends BaseTest {
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

	@Test(dataProvider = "getData", priority = 1, enabled = true)
	public void verifyTest(Hashtable<String, String> data) throws Exception {
		SoftAssert sAssert = new SoftAssert();
		ServiceTest service = new ServiceTest();
		String expectedResult = "Test_PASS";
		String actualResult = "Test_PASS";
		defaultLogin(data.get("Browser"), data.get("Username"), data.get("Password"));
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		LoginPage lp = new LoginPage(driver, test);
		SubjectPage sp = new SubjectPage(driver, test);
		ChapterPage chPg = new ChapterPage(driver, test);
		ClassPage cp = new ClassPage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		dp.openstudytab();
		Thread.sleep(5000);
		int subsize = cp.getTotalSub();
//Subject 
		
		for (int subj = 0; subj < subsize; subj++) {
			int subNo = subj + 1;
			WebDriverWait wt = new WebDriverWait(driver, 120);
			String subject="";
			try {
			wt.until(ExpectedConditions.visibilityOfAllElements(cp.getSubjectLinks()));
			String subjects = cp.getSubjectLinks().get(subj).getText();
			String[] subjects2 = subjects.split("\\n");
			
			subject = subjects2[0].trim();
			}catch(Exception e) {
				System.out.println("Subject not found "  + subject);
				test.log(LogStatus.INFO, "Subjects " + subNo + " :" + subject);
				dp.openstudytab();
				Thread.sleep(5000);
				continue;
			}
			
			System.out.println("Subjects " + subNo + " :" + subject);
			test.log(LogStatus.INFO, "Subjects " + subNo + " :" + subject);
			boolean clickSubResult = click(cp.getSubjectLinks(), subj, "SubjectLinks");
			if (clickSubResult) {
				Thread.sleep(2000);
				lp.takeScreenShot();
				Thread.sleep(3000);
//Sub Subject
				int subSubjsize = cp.getTotalSubSubj();
				if (subSubjsize != 0) {
					for (int subSubj = 0; subSubj < subSubjsize; subSubj++) {
						int subSubjNo = subSubj + 1;
						String subSubject="";
						try {
							String subSubjects = cp.getSubSubjectLinks().get(subSubj).getText();
							String[] subSubject2 = subSubjects.split("\\n");
							subSubject = subSubject2[0].trim();
							System.out.println("Sub Subjects:" + subSubject);
						}catch(Exception e) {
							System.out.println("Subject not found " + subSubjNo + " :" + subSubject);
							test.log(LogStatus.INFO, "Subject not found " + subSubjNo + " :" + subSubject);
							dp.openstudytab();
							Thread.sleep(5000);
							click(cp.getSubjectLinks(), subj, "SubjectLinks");
							Thread.sleep(5000);
							continue;
						}
					
						test.log(LogStatus.INFO, "Sub Subjects:" + subSubject);
						boolean clickSubSubjectResult = click(cp.getSubSubjectLinks(), subSubj,
								"cp.getSubSubjectLinks");
						if (clickSubSubjectResult) {
							Thread.sleep(2000);
							lp.takeScreenShot();
// Chapter
							int chapSize = sp.getMainChapter().size();
							System.out.println("No. of chapters in this subject = " + chapSize);
							test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
							Thread.sleep(1000);
							fluentWaitIsDisplay(sp.getMainChapter(), 0, 60, "sp.getMainChapter");
							if (chapSize != 0) {
								for (int ch = 0; ch < chapSize; ch++) {
									int chapNo = ch + 1;
									wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
									Thread.sleep(5000);
									String chapter = sp.getMainChapter().get(ch).getText();
									System.out.println("Main Chapter :  " + chapNo + "  " + chapter);
									test.log(LogStatus.INFO, "Main Chapter :  " + chapNo + "  " + chapter);
									boolean clickresult = click(sp.getMainChapter(), ch, "chapter link");
									if (clickresult) {
										lp.takeScreenShot();
										Thread.sleep(5000);
// Sub Chapter
										int subChapSize = sp.getSubChapter().size();
										System.out.println("No.Sub Chapters = " + subChapSize);
										test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);

										if (subChapSize != 0)
										// ..............................................
										{
											for (int su = 0; su < subChapSize; ++su) {
												System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
												test.log(LogStatus.INFO,
														"Chapter : " + sp.getSubChapter().get(su).getText());
												Thread.sleep(1000);
												sp.getSubChapter().get(su).click();
												Thread.sleep(2000);
												lp.takeScreenShot();
// Post Sub Chapter
												int postSubChap = sp.getPostSubChap().size();
												test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
												System.out.println("No. of Sub-sub Chapters = " + postSubChap);
												if (postSubChap != 0) {
													for (int ps = 0; ps < postSubChap; ps++) {
														System.out.println("Subchapter : "
																+ sp.getPostSubChap().get(ps).getText());
														Thread.sleep(1000);
														sp.getPostSubChap().get(ps).click();
														test.log(LogStatus.INFO, "Open sub chapters");
														Thread.sleep(2000);
														lp.takeScreenShot();
// Service Tab Section on post chapter
														try {
															boolean testServiceResult = service.verifyTestService(data,sAssert);
															if (testServiceResult) {
																test.log(LogStatus.PASS, "Test Service Pass for  "
																		+ subject + ">>" + subSubject + ">>" + chapter);
																System.out.println("Test Service Pass for  " + subject
																		+ ">>" + subSubject + ">>" + chapter);
															} else {
																test.log(LogStatus.FAIL, "Test Service Fail for  "
																		+ subject + ">>" + subSubject + ">>" + chapter);
																System.out.println("Test Service Fail for  " + subject
																		+ ">>" + subSubject + ">>" + chapter);
																sAssert.fail("Test Service Fail for  " + subject + ">>"
																		+ subSubject + ">>" + chapter);
																actualResult = "Test_Service_Fail";
															}
														} catch (Exception e) {
															test.log(LogStatus.INFO, "Test Test Fail for  " + subject
																	+ ">>" + subSubject + ">>" + chapter);
															System.out.println("Test Test Fail for  " + subject + ">>"
																	+ subSubject + ">>" + chapter);
															sAssert.fail("Test Test Test for  " + subject + ">>"
																	+ subSubject + ">>" + chapter);
															lp.takeScreenShot();
														}
														if (chPg.BackToChapter.size() != 0) {
															JavascriptExecutor jslpt = (JavascriptExecutor) driver;
															jslpt.executeScript("arguments[0].click();",
																	chPg.BackToChapter.get(0));
															boolean subSubjectDisplay = fluentWaitIsDisplay(
																	cp.getSubSubjectLinks(), subSubj, 120,
																	"getTotalSubSubj");
															if (subSubjectDisplay) {
																int subSubjsize2 = cp.getTotalSubSubj();
																if (subSubjsize2 != 0) {
																	wt.until(ExpectedConditions.elementToBeClickable(
																			cp.getSubSubjectLinks().get(subSubj)));
																	boolean clickSubSubjectResult2 = click(
																			cp.getSubSubjectLinks(), subSubj,
																			"cp.getSubSubjectLinks");
																	if (clickSubSubjectResult2) {
																		System.out.println(
																				"User navigated on Sub Subjects from Test Tab "
																						+ subject + ">>" + subSubject);

																		test.log(LogStatus.INFO,
																				"User navigated on Sub Subjects from Test Tab "
																						+ subject + ">>" + subSubject);
																		lp.takeScreenShot();
																	} else {
																		System.out.println(
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject + ">>" + subSubject);

																		test.log(LogStatus.INFO,
																				"User not able to navigated on chapter from Sub Subjects "
																						+ subject + ">>" + subSubject);
																		lp.takeScreenShot();
																	}
																}
															} else {
																System.out.println(
																		"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																				+ subject + ">>" + subSubject);
																test.log(LogStatus.INFO,
																		"Back Button is not working, User not able to navigated on Sub Subjects from Test Tab  "
																				+ subject + ">>" + subSubject);
																sAssert.fail(
																		"Back Button is not working, User not able to navigated on Sub Subjects from Test Tab  "
																				+ subject + ">>" + subSubject);
																lp.takeScreenShot();
																dp.openstudytab();
																Thread.sleep(5000);
																click(cp.getSubjectLinks(), subj, "SubjectLinks");
																Thread.sleep(5000);
																click(cp.getSubSubjectLinks(), subSubj, "SubSubjectLinks");
																Thread.sleep(5000);
															}
														} else {
															driver.navigate().back();
														}

														Thread.sleep(5000);
														sp.getMainChapter().get(ch).click();
														Thread.sleep(5000);
														sp.getSubChapter().get(su).click();
														Thread.sleep(5000);
													}

												} else {
// Service Tab Section on sub chapter												
													try {
														boolean testServiceResult = service.verifyTestService(data,sAssert);
														if (testServiceResult) {
															test.log(LogStatus.PASS, "Test Service Pass for  "
																	+ subject + ">>" + subSubject + ">>" + chapter);
															System.out.println("Test Service Pass for  " + subject
																	+ ">>" + subSubject + ">>" + chapter);
														} else {
															test.log(LogStatus.FAIL, "Test Service Fail for  "
																	+ subject + ">>" + subSubject + ">>" + chapter);
															System.out.println("Test Service Fail for  " + subject
																	+ ">>" + subSubject + ">>" + chapter);
															sAssert.fail("Test Service Fail for  " + subject + ">>"
																	+ subSubject + ">>" + chapter);
															actualResult = "Test_Service_Fail";
														}
													} catch (Exception e) {
														test.log(LogStatus.INFO, "Test Test Fail for  " + subject
																+ ">>" + subSubject + ">>" + chapter);
														System.out.println("Test Test Fail for  " + subject + ">>"
																+ subSubject + ">>" + chapter);
														sAssert.fail("Test Test Test for  " + subject + ">>"
																+ subSubject + ">>" + chapter);
														lp.takeScreenShot();
													}
													if (chPg.BackToChapter.size() != 0) {
														JavascriptExecutor jslpt = (JavascriptExecutor) driver;
														jslpt.executeScript("arguments[0].click();",
																chPg.BackToChapter.get(0));
														boolean subSubjectDisplay = fluentWaitIsDisplay(
																cp.getSubSubjectLinks(), subSubj, 120,
																"getTotalSubSubj");
														if (subSubjectDisplay) {
															int subSubjsize2 = cp.getTotalSubSubj();
															if (subSubjsize2 != 0) {
																wt.until(ExpectedConditions.elementToBeClickable(
																		cp.getSubSubjectLinks().get(subSubj)));
																boolean clickSubSubjectResult2 = click(
																		cp.getSubSubjectLinks(), subSubj,
																		"cp.getSubSubjectLinks");
																if (clickSubSubjectResult2) {
																	System.out.println(
																			"User navigated on Sub Subjects from Test Tab "
																					+ subject + ">>" + subSubject);

																	test.log(LogStatus.INFO,
																			"User navigated on Sub Subjects from Test Tab "
																					+ subject + ">>" + subSubject);
																	lp.takeScreenShot();
																} else {
																	System.out.println(
																			"User not able to navigated on chapter from Sub Subjects "
																					+ subject + ">>" + subSubject);

																	test.log(LogStatus.INFO,
																			"User not able to navigated on chapter from Sub Subjects "
																					+ subject + ">>" + subSubject);
																	lp.takeScreenShot();
																}
															}
														} else {
															System.out.println(
																	"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																			+ subject + ">>" + subSubject);
															test.log(LogStatus.INFO,
																	"Back Button is not working, User not able to navigated on Sub Subjects from Test Tab  "
																			+ subject + ">>" + subSubject);
															sAssert.fail(
																	"Back Button is not working, User not able to navigated on Sub Subjects from Test Tab  "
																			+ subject + ">>" + subSubject);
															lp.takeScreenShot();
															dp.openstudytab();
															Thread.sleep(5000);
															click(cp.getSubjectLinks(), subj, "SubjectLinks");
															Thread.sleep(5000);
															click(cp.getSubSubjectLinks(), subSubj, "SubSubjectLinks");
															Thread.sleep(5000);
														}
													} else {
														driver.navigate().back();
													}

													Thread.sleep(5000);
													sp.getMainChapter().get(ch).click();
													Thread.sleep(5000);
												}

											}

										}
// Sub Chap Else Start ...............................................................................................................................................

										else {
// Service Tab Section on chapter												
											try {
												boolean testServiceResult = service.verifyTestService(data,sAssert);
												if (testServiceResult) {
													test.log(LogStatus.PASS, "Test Service Pass for  " + subject + ">>"
															+ subSubject + ">>" + chapter);
													System.out.println("Test Service Pass for  " + subject + ">>"
															+ subSubject + ">>" + chapter);
												} else {
													test.log(LogStatus.FAIL, "Test Service Fail for  " + subject + ">>"
															+ subSubject + ">>" + chapter);
													System.out.println("Test Service Fail for  " + subject + ">>"
															+ subSubject + ">>" + chapter);
													sAssert.fail("Test Service Fail for  " + subject + ">>"
															+ subSubject + ">>" + chapter);
													actualResult = "Test_Service_Fail";
												}
											} catch (Exception e) {
												test.log(LogStatus.INFO, "Test Test Fail for  " + subject + ">>"
														+ subSubject + ">>" + chapter);
												System.out.println("Test Test Fail for  " + subject + ">>" + subSubject
														+ ">>" + chapter);
												sAssert.fail("Test Test Test for  " + subject + ">>" + subSubject
														+ ">>" + chapter);
												lp.takeScreenShot();
											}
											if (chPg.BackToChapter.size() != 0) {
												JavascriptExecutor jslpt = (JavascriptExecutor) driver;
												jslpt.executeScript("arguments[0].click();", chPg.BackToChapter.get(0));
												boolean subSubjectDisplay = fluentWaitIsDisplay(cp.getSubSubjectLinks(),
														subSubj, 120, "getTotalSubSubj");
												if (subSubjectDisplay) {
													int subSubjsize2 = cp.getTotalSubSubj();
													if (subSubjsize2 != 0) {
														wt.until(ExpectedConditions.elementToBeClickable(
																cp.getSubSubjectLinks().get(subSubj)));
														boolean clickSubSubjectResult2 = click(cp.getSubSubjectLinks(),
																subSubj, "cp.getSubSubjectLinks");
														if (clickSubSubjectResult2) {
															System.out.println(
																	"User navigated on Sub Subjects from Test Tab "
																			+ subject + ">>" + subSubject);

															test.log(LogStatus.INFO,
																	"User navigated on Sub Subjects from Test Tab "
																			+ subject + ">>" + subSubject);
															lp.takeScreenShot();
														} else {
															System.out.println(
																	"User not able to navigated on chapter from Sub Subjects "
																			+ subject + ">>" + subSubject);

															test.log(LogStatus.INFO,
																	"User not able to navigated on chapter from Sub Subjects "
																			+ subject + ">>" + subSubject);
															lp.takeScreenShot();
														}
													}
												} else {
													System.out.println(
															"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																	+ subject + ">>" + subSubject);
													test.log(LogStatus.INFO,
															"Back Button is not working, User not able to navigated on Sub Subjects from Test Tab  "
																	+ subject + ">>" + subSubject);
													sAssert.fail(
															"Back Button is not working, User not able to navigated on Sub Subjects from Test Tab  "
																	+ subject + ">>" + subSubject);
													lp.takeScreenShot();
													dp.openstudytab();
													Thread.sleep(5000);
													click(cp.getSubjectLinks(), subj, "SubjectLinks");
													Thread.sleep(5000);
													click(cp.getSubSubjectLinks(), subSubj, "SubSubjectLinks");
													Thread.sleep(5000);
												}
											} else {
												driver.navigate().back();
											}
										}
									} else {
										actualResult = "Test_Not_able_to_click_on_chapter_Fail " + subject + ">>"
												+ subSubject + ">>" + chapter;
										System.out.println("Test_Not_able_to_click_on_Chapter, Location is --> "
												+ subject + ">>" + subSubject + ">>" + chapter);
										test.log(LogStatus.FAIL, "Test_Not_able_to_click_on_Chapter, Location is --> "
												+ subject + ">>" + subSubject + ">>" + chapter);
										sAssert.fail("Test_Not_able_to_click_on_Chapter, Location is --> " + subject
												+ ">>" + subSubject + ">>" + chapter);
										Thread.sleep(5000);
									}
								}

							} else {
								actualResult = "Test_chapter_page_not_display_FAIL";
								System.out.println("Chapter page is not Displayed, Location is --> " + subject + " -- >"
										+ subSubject);
								test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject
										+ " -- >" + subSubject);
								sAssert.fail("Chapter page is not Displayed, Location is --> " + subject + " -- >"
										+ subSubject);
								lp.takeScreenShot();

							}
							if (chPg.BackToChapter.size() != 0) {
								JavascriptExecutor jslpt = (JavascriptExecutor) driver;
								jslpt.executeScript("arguments[0].click();", chPg.BackToChapter.get(0));
							} else {
								driver.navigate().back();
							}
							Thread.sleep(5000);

// if Click on Sub subject Fail								
						} else {
							actualResult = "Test_Not_able_to_click_on_Sub_subject_Fail " + subject + ">>"
									+ subSubject;
							System.out.println("Test_Not_able_to_click_on_subject, Location is --> " + subject + ">>"
									+ subSubject);
							test.log(LogStatus.FAIL, "Test_Not_able_to_click_on_subject, Location is --> " + subject
									+ ">>" + subSubject);
							sAssert.fail("Test_Not_able_to_click_on_subject, Location is --> " + subject + ">>"
									+ subSubject);
							Thread.sleep(5000);
						}
					}
//Sub Subject end
//chapter start --in case sub subject not present on page
				} else {
					int chapSize = sp.getMainChapter().size();
					System.out.println("No. of chapters in this subject = " + chapSize);
					test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
					Thread.sleep(1000);
					if (chapSize != 0) {
						for (int ch = 0; ch < chapSize; ch++) {
							int chapNo = ch + 1;
							wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
							Thread.sleep(5000);
							String chapter = sp.getMainChapter().get(ch).getText();
							System.out.println("Main Chapter :  " + chapNo + "  " + chapter);
							test.log(LogStatus.INFO, "Main Chapter :  " + chapNo + "  " + chapter);
// clicking on chapter
							boolean clickResult = click(sp.getMainChapter(), ch, "chapter link");
							if (clickResult) {

								lp.takeScreenShot();
								Thread.sleep(5000);
// sub Chapter
								int subChapSize = sp.getSubChapter().size();
								System.out.println("No.Sub Chapters = " + subChapSize);
								test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);
								if (subChapSize != 0)
								// ..............................................
								{
									for (int su = 0; su < subChapSize; ++su) {
										System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
										test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
										Thread.sleep(1000);
										sp.getSubChapter().get(su).click();
										Thread.sleep(5000);
										lp.takeScreenShot();
// Post sub Chapter
										int postSubChap = sp.getPostSubChap().size();
										test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
										System.out.println("No. of Sub-sub Chapters = " + postSubChap);
										if (postSubChap != 0) {
											for (int ps = 0; ps < postSubChap; ps++) {
												System.out.println(
														"Subchapter : " + sp.getPostSubChap().get(ps).getText());
												Thread.sleep(1000);
												sp.getPostSubChap().get(ps).click();
												test.log(LogStatus.INFO, "Open sub chapters");

												Thread.sleep(5000);
												lp.takeScreenShot();
// Service Tab Section on post sub chapter											
												try {
													boolean testServiceResult = service.verifyTestService(data,sAssert);
													if (testServiceResult) {
														test.log(LogStatus.PASS,
																"Test Service Pass for  " + subject + ">>" + chapter);
														System.out.println(
																"Test Service Pass for  " + subject + ">>" + chapter);
													} else {
														test.log(LogStatus.FAIL, "Test Service Fail for  " + subject
																+ ">>" + subject + ">>" + chapter);
														System.out.println("Test Service Fail for  " + subject + ">>"
																+ subject + ">>" + chapter);
														sAssert.fail(
																"Test Service Fail for  " + subject + ">>" + chapter);
														actualResult = "Test_Service_Fail";
													}
												} catch (Exception e) {
													test.log(LogStatus.INFO,
															"Test Test Fail for  " + subject + ">>" + chapter);
													System.out.println(
															"Test Test Fail for  " + subject + ">>" + chapter);
													sAssert.fail("Test Test Test for  " + subject + ">>" + chapter);
													lp.takeScreenShot();
												}
												if (chPg.BackToChapter.size() != 0) {
													JavascriptExecutor jslpt = (JavascriptExecutor) driver;
													jslpt.executeScript("arguments[0].click();",
															chPg.BackToChapter.get(0));
													boolean subSubjectDisplay = fluentWaitIsDisplay(
															cp.getSubSubjectLinks(), subj, 120, "getTotalSubSubj");
													if (subSubjectDisplay) {
														int subjsize2 = cp.getTotalSubSubj();
														if (subjsize2 != 0) {
															wt.until(ExpectedConditions.elementToBeClickable(
																	cp.getSubSubjectLinks().get(subj)));
															boolean clickSubSubjectResult2 = click(
																	cp.getSubSubjectLinks(), subj,
																	"cp.getSubSubjectLinks");
															if (clickSubSubjectResult2) {
																System.out.println(
																		"User navigated on Subjects from Test Tab "
																				+ subject);

																test.log(LogStatus.INFO,
																		"User navigated on Subjects from Test Tab "
																				+ subject);
																lp.takeScreenShot();
															} else {
																System.out.println(
																		"User navigated on Subjects from Test Tab "
																				+ subject);

																test.log(LogStatus.INFO,
																		"User navigated on Subjects from Test Tab "
																				+ subject);
																lp.takeScreenShot();
															}
														}
													} else {
														System.out.println(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																		+ subject);
														test.log(LogStatus.INFO,
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																		+ subject);
														sAssert.fail(
																"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																		+ subject);
														lp.takeScreenShot();
														dp.openstudytab();
														Thread.sleep(5000);
														click(cp.getSubjectLinks(), subj, "SubjectLinks");

														Thread.sleep(5000);
													}
												} else {
													driver.navigate().back();
												}
												Thread.sleep(5000);
												sp.getMainChapter().get(ch).click();

												Thread.sleep(5000);
												sp.getSubChapter().get(su).click();
												Thread.sleep(5000);
											}

										} else {
// Service Tab Section on sub chapter												
											try {
												boolean testServiceResult = service.verifyTestService(data,sAssert);
												if (testServiceResult) {
													test.log(LogStatus.PASS,
															"Test Service Pass for  " + subject + ">>" + chapter);
													System.out.println(
															"Test Service Pass for  " + subject + ">>" + chapter);
												} else {
													test.log(LogStatus.FAIL, "Test Service Fail for  " + subject + ">>"
															+ subject + ">>" + chapter);
													System.out.println("Test Service Fail for  " + subject + ">>"
															+ subject + ">>" + chapter);
													sAssert.fail("Test Service Fail for  " + subject + ">>" + chapter);
													actualResult = "Test_Service_Fail";
												}
											} catch (Exception e) {
												test.log(LogStatus.INFO,
														"Test Test Fail for  " + subject + ">>" + chapter);
												System.out.println("Test Test Fail for  " + subject + ">>" + chapter);
												sAssert.fail("Test Test Test for  " + subject + ">>" + chapter);
												lp.takeScreenShot();
											}
											if (chPg.BackToChapter.size() != 0) {
												JavascriptExecutor jslpt = (JavascriptExecutor) driver;
												jslpt.executeScript("arguments[0].click();", chPg.BackToChapter.get(0));
												boolean subSubjectDisplay = fluentWaitIsDisplay(cp.getSubSubjectLinks(),
														subj, 120, "getTotalSubSubj");
												if (subSubjectDisplay) {
													int subjsize2 = cp.getTotalSubSubj();
													if (subjsize2 != 0) {
														wt.until(ExpectedConditions.elementToBeClickable(
																cp.getSubSubjectLinks().get(subj)));
														boolean clickSubSubjectResult2 = click(cp.getSubSubjectLinks(),
																subj, "cp.getSubSubjectLinks");
														if (clickSubSubjectResult2) {
															System.out.println(
																	"User navigated on Subjects from Test Tab "
																			+ subject);

															test.log(LogStatus.INFO,
																	"User navigated on Subjects from Test Tab "
																			+ subject);
															lp.takeScreenShot();
														} else {
															System.out.println(
																	"User navigated on Subjects from Test Tab "
																			+ subject);

															test.log(LogStatus.INFO,
																	"User navigated on Subjects from Test Tab "
																			+ subject);
															lp.takeScreenShot();
														}
													}
												} else {
													System.out.println(
															"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																	+ subject);
													test.log(LogStatus.INFO,
															"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																	+ subject);
													sAssert.fail(
															"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
																	+ subject);
													lp.takeScreenShot();
													dp.openstudytab();
													Thread.sleep(5000);
													click(cp.getSubjectLinks(), subj, "SubjectLinks");

													Thread.sleep(5000);
												}
											} else {
												driver.navigate().back();
											}
											Thread.sleep(5000);
											sp.getMainChapter().get(ch).click();
										}

									}

								}
//........Sub Chapter End

								else {
// Service Tab Section on chapter										
									try {
										boolean testServiceResult = service.verifyTestService(data,sAssert);
										if (testServiceResult) {
											test.log(LogStatus.PASS,
													"Test Service Pass for  " + subject + ">>" + chapter);
											System.out.println("Test Service Pass for  " + subject + ">>" + chapter);
										} else {
											test.log(LogStatus.FAIL, "Test Service Fail for  " + subject + ">>"
													+ subject + ">>" + chapter);
											System.out.println("Test Service Fail for  " + subject + ">>" + subject
													+ ">>" + chapter);
											sAssert.fail("Test Service Fail for  " + subject + ">>" + chapter);
											actualResult = "Test_Service_Fail";
										}
									} catch (Exception e) {
										test.log(LogStatus.INFO, "Test Test Fail for  " + subject + ">>" + chapter);
										System.out.println("Test Test Fail for  " + subject + ">>" + chapter);
										sAssert.fail("Test Test Test for  " + subject + ">>" + chapter);
										lp.takeScreenShot();
									}
									if (chPg.BackToChapter.size() != 0) {
										JavascriptExecutor jslpt = (JavascriptExecutor) driver;
										jslpt.executeScript("arguments[0].click();", chPg.BackToChapter.get(0));
										boolean subSubjectDisplay = fluentWaitIsDisplay(cp.getSubSubjectLinks(), subj,
												120, "getTotalSubSubj");
										if (subSubjectDisplay) {
											int subjsize2 = cp.getTotalSubSubj();
											if (subjsize2 != 0) {
												wt.until(ExpectedConditions
														.elementToBeClickable(cp.getSubSubjectLinks().get(subj)));
												boolean clickSubSubjectResult2 = click(cp.getSubSubjectLinks(), subj,
														"cp.getSubSubjectLinks");
												if (clickSubSubjectResult2) {
													System.out.println(
															"User navigated on Subjects from Test Tab " + subject);

													test.log(LogStatus.INFO,
															"User navigated on Subjects from Test Tab " + subject);
													lp.takeScreenShot();
												} else {
													System.out.println(
															"User navigated on Subjects from Test Tab " + subject);

													test.log(LogStatus.INFO,
															"User navigated on Subjects from Test Tab " + subject);
													lp.takeScreenShot();
												}
											}
										} else {
											System.out.println(
													"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
															+ subject);
											test.log(LogStatus.INFO,
													"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
															+ subject);
											sAssert.fail(
													"Back Button is not working or timeLimit Exceeds, User not able to navigated on Sub Subjects from Test Tab  "
															+ subject);
											lp.takeScreenShot();
											dp.openstudytab();
											Thread.sleep(5000);
											click(cp.getSubjectLinks(), subj, "SubjectLinks");

											Thread.sleep(5000);
										}
									} else {
										driver.navigate().back();
									}
								}
// if Click on chapter Fail	
							} else {

								actualResult = "Test_Not_able_to_click_on_chapter_Fail " + subject + ">>" + chapter;
								System.out.println("Test_Not_able_to_click_on_Chapter, Location is --> " + subject
										+ ">>" + chapter);
								test.log(LogStatus.FAIL, "Test_Not_able_to_click_on_Chapter, Location is --> "
										+ subject + ">>" + chapter);
								sAssert.fail("Test_Not_able_to_click_on_Chapter, Location is --> " + subject + ">>"
										+ chapter);
								Thread.sleep(3000);
							}

						}

					} else {
						actualResult = "Test_Chapter page is not Displayed_FAIL";
						System.out.println("Chapter page is not Displayed, Location is --> " + subject);
						test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject);
						sAssert.fail("Chapter page is not Displayed, Location is --> " + subject);
						Thread.sleep(5000);
					}

				}
				if (chPg.BackToChapter.size() != 0) {
					JavascriptExecutor jslpt = (JavascriptExecutor) driver;
					jslpt.executeScript("arguments[0].click();", chPg.BackToChapter.get(0));
				} else {
					driver.navigate().back();
				}
				Thread.sleep(5000);

// if Click on Subject Fail			
			} else {
				actualResult = "Test_Not_able_to_click_on_subject_Fail " + subject;
				System.out.println("Test_Not_able_to_click_on_subject, Location is --> " + subject);
				test.log(LogStatus.FAIL, "Test_Not_able_to_click_on_subject, Location is --> " + subject);
				sAssert.fail("Test_Not_able_to_click_on_subject --> " + subject);
				Thread.sleep(5000);
			}

		}

		lp.takeScreenShot();
		if (chPg.BackToChapter.size() != 0) {
			JavascriptExecutor jslpt = (JavascriptExecutor) driver;
			jslpt.executeScript("arguments[0].click();", chPg.BackToChapter.get(0));
		} else {
			driver.navigate().back();
		}
		lp.takeScreenShot();
		if (actualResult.equalsIgnoreCase(expectedResult)) {
			test.log(LogStatus.PASS, "Test Pass");
		} else {
			test.log(LogStatus.FAIL, "Test FAIL");
		}
		sAssert.assertAll();
	}
}
