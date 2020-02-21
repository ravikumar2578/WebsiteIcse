package com.ExtramarksWebsite_TestCases;

import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestRunner {

	public static void main(String[] args) {

		List<String> suites = new ArrayList<String>();
		TestRunner currentClass = new TestRunner();

		ClassLoader classLoader = currentClass.getClass().getClassLoader();
		URL resource = classLoader.getResource("testngSuite.xml");
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			suites.add(resource.getFile());
		}
		TestNG testNG = new TestNG();
		testNG.setTestSuites(suites);
		testNG.run();
		// testNG.setTestClasses(new Class[]{SignUpTest.class,LoginTest.class}); //
		// replace TestClass with your actual class where your test methods live
	}
}
