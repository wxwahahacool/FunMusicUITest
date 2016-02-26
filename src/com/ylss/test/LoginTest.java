package com.ylss.test;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ylss.action.LoginAction;
import com.ylss.utils.AppiumServerSingleton;


public class LoginTest {

	LoginAction la;
	IOSDriver<IOSElement> iosDriver = null;
	AppiumDriverLocalService service = null;
	
	@BeforeSuite
	public void setUp() {
		System.out.println("this is before");
		iosDriver = AppiumServerSingleton.getInstance().getIosDriver();
		la = new LoginAction(iosDriver);
		
	}
	
	@Test
	public void loginIn() {
		
		la.loginTrigger();
		la.inputUserNameAndPassword();
		la.loginConfirm();

		Assert.assertEquals(la.isLoginSuccess(), true);

	}

	@Test(dependsOnMethods = "loginIn")
	public void loginOut() {
		
		la.loginOutTrigger();
		la.buttonMe();

		Assert.assertEquals(la.isLogoutSuccess(), true);
		
	}

	@AfterSuite
	public void tearDown() {
		System.out.println("this is after");
		AppiumServerSingleton.getInstance().stopDriver(iosDriver);
		AppiumServerSingleton.getInstance().stopAppium(service);

	}

	
}
