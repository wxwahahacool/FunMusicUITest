package com.ylss.test;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ylss.action.LoginAction;
import com.ylss.utils.AppiumServer;
import com.ylss.utils.Common;


public class LoginTest extends AppiumServer {

	LoginAction la;

	
	@BeforeSuite
	public void setUp() {
		
		System.out.println("this is beforesuit");
		la = new LoginAction(Common.iosDriver);
		
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
		
		System.out.println("this is aftersuit");
		new AppiumServer().stopDriver(Common.iosDriver);
		new AppiumServer().stopAppium(Common.service);

	}

	
}
