package com.ExtramarksWebsite_Utils;

public class Constants {
	// report path
	public static final String REPORT_PATH = "./AutomationReport/";
	// Website URL
	//http://testautomation-www.extramarks.com"
	public static final String URL = "http://www.extramarks.com/";
	public static final String SCREENSHOT_PATH = "./AutomationReport/Screenshots/";
	public static final String CONTENT_SCREENSHOT_PATH = "./AutomationReport/Screenshots/Content/";
	public static final String Image_PATH = "./src/test/resources/Images/";
	//public static final String XLS_FILE_PATH = "./src/test/resources/website_data.xlsx";
	public static final String XLS_FILE_PATH = "./src/test/resources/XLS/website_data.xlsx";
	public static final String USERNAME = "1021021021";
	public static final String PASSWORD = "123456";

	// driver path
	public static final String CHROME_PATH = "./src/test/resources/Drivers/chromedriver.exe";
	public static final String IEDRIVER_PATH = "./src/test/resources/Drivers/IEDriverServer.exe";

	// Locators
	// Login(ID)
	public static final String LOGIN_USERNAME = "usernameLogin";
	public static final String LOGIN_PASSWORD = "passwdLogin";
	public static final String LOGIN_BUTTON = "login-account";

	// Dashboard(xpath)
	public static final String SHOW_HIDE_MENU_LINK = "//*[@id='showHideMenu']";
	public static final String MENU_SLIDERBAR = "//*[@id='mCSB_1_dragger_vertical']/div";
	public static final String BATCH_MASTER_LINK = "//*[@id='menuIdFerp']/li[15]/a";
	public static final String CREATE_BATCH_LINK = "//*[@id='menuIdFerp']/li[15]/ul/li[1]/a";
	public static final String BATCH_LISTING_LINK = "//*[@id='menuIdFerp']/li[15]/ul/li[2]/a";
	// BatchlistingPage
	public static final String CENTERNAME = "//*[@id='center_id']";
	public static final String COURSENAME = "//*[@id='course']";
	public static final String COURSETYPE = "//*[@id='course_type']";
	public static final String BATCHNAME = "//*[@id='batch_name']";
	public static final String SEARCHBATCH = "//*[@id='Search']";

	public static final String BATCHMATCH = "/html/body/div[1]/div/div[3]/div/div/div/div/table/tbody/tr[2]/td[5]";
	
	// DataBase Live
	
	public static final String DATABASE_URL = "jdbc:mysql://10.0.20.107/website_ver2";
	public static final String DATABASE_USERNAME = "ReadDaemons";
	public static final String DATABASE_PASSWORD = "KU8wHAyyacdnYpp9FJqCbECbBhfFQxReC5DLUZ";
	
	//DataBase Test
	//public static final String DATABASE_URL = "jdbc:mysql://10.1.17.6/website_automation";
	//public static final String DATABASE_USERNAME = "auto_test";
	//public static final String DATABASE_PASSWORD = "Abc!@#456";
	
	
	
	
}
