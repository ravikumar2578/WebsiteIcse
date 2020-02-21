package com.ExtramarksWebsite_Utils;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.DataProvider;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.TestResult;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class Listener<TestParameters> extends TestListenerAdapter {

	 
	private void setTestNameInXml(ITestResult tr)
	    {
	        try
	        {
	        	String testParameter="testcaseName";
	        	TestParameters testParams = null;
	        	 testParams = (TestParameters)testParameter;
	            java.lang.reflect.Field method = TestResult.class.getDeclaredField("m_method");
	            method.setAccessible(true);
	            method.set(tr, tr.getMethod().clone());
	            java.lang.reflect.Field methodName = BaseTestMethod.class.getDeclaredField("m_methodName");
	            methodName.setAccessible(true);
	            methodName.set(tr.getMethod(),tr.getParameters()[0]);
	        }
	        catch (IllegalAccessException e)
	        {
	            e.printStackTrace();
	        }
	        catch (NoSuchFieldException e)
	        {
	            e.printStackTrace();
	        }
	    }
	 
	 @Override
	    public void onTestSuccess(ITestResult tr)
	    {
	        setTestNameInXml(tr);
	        System.out.println(tr.getName()+" test case started");	
	        super.onTestSuccess(tr);
	    }
}
