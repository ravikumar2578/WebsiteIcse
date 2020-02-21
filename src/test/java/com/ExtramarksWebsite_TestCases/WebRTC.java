package com.ExtramarksWebsite_TestCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class WebRTC extends BaseTest {

	@Test(invocationCount = 1)
	public void open() throws InterruptedException {
		openBrowser("Chrome");

		driver.get("https://webrtc.extramarks.com/portal/webrtc/client");

		driver.findElement(By.id("joinBtn")).click();

	}

}
