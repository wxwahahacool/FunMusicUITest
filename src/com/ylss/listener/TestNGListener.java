package com.ylss.listener;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.ylss.utils.AppiumServerSingleton;


public class TestNGListener extends TestListenerAdapter {
	
	private static Logger log = Logger.getLogger(TestNGListener.class);

	@Override
	public void onTestSuccess(ITestResult tr) {
		
		log.info("Test Success");
		super.onTestSuccess(tr);
		
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		
		log.error("Test Failure");
		super.onTestFailure(tr);
		takeScreenShot(tr);
		
	}

	private void takeScreenShot(ITestResult tr) {
		
//		AppiumServer as = (AppiumServer) tr.getInstance();
		
//        IOSDriver<IOSElement> currentDirver = as.getDriver();
		IOSDriver<IOSElement> currentDirver = AppiumServerSingleton.getInstance().getIosDriver();;
		AppiumServerSingleton.getInstance().takeScreenshot(currentDirver,getMethodName(tr.getMethod().toString()));

    }
	
	private String getMethodName(String str){
		
		return str.split(":")[2].split("@")[0] + str.substring(str.indexOf("."),str.indexOf("("));
		
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		
//		log.error("Test Skipped");
		super.onTestSkipped(tr);
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		
//		log.info("Test Finish");
		super.onTestStart(result);
		
	}

	@Override
	public void onStart(ITestContext testContext) {
		
//		log.info("Test Start");
		super.onStart(testContext);
		
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);

		// List of test results which we will delete later
//		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		// collect all id's from passed test
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			log.info("PassedTests = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}
		Set<Integer> skipTestIds = new HashSet<Integer>();
		for (ITestResult skipTest : testContext.getSkippedTests().getAllResults()) {
			log.info("SkipTests = " + skipTest.getName());
			skipTestIds.add(getId(skipTest));
		}
		Set<Integer> failedTestIds = new HashSet<Integer>();

		for (ITestResult failedTest : testContext.getSkippedTests().getAllResults()) {
			log.info("FailedTests = " + failedTest.getName());
			failedTestIds.add(getId(failedTest));
		}

		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext();) {
			ITestResult testResult = iterator.next();
			if (passedTestIds.contains(getId(testResult)) || failedTestIds.contains(getId(testResult))) {
				log.info("Remove repeat Fail Test: " + testResult.getName());
				iterator.remove();
			}
		}
		
		/**
		 * @author xin.wang remove the repeated case also in pass or failed tests
		 */
		for (Iterator<ITestResult> iterator = testContext.getSkippedTests().getAllResults().iterator(); iterator.hasNext();) {
			ITestResult testResult = iterator.next();
			if (passedTestIds.contains(getId(testResult)) || failedTestIds.contains(getId(testResult)) || skipTestIds.contains(getId(testResult))) {
				log.info("Remove repeat Skip Test: " + testResult.getName());
				iterator.remove();
			}
		}

	}

	private int getId(ITestResult result) {
		
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
		
	}
}
