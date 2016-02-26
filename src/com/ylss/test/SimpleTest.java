package com.ylss.test;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.util.List;

import org.testng.annotations.Test;

import com.ylss.utils.AppiumServerSingleton;

/**
 * this case just to test the variable that initialized in
 * the beforeSuite can be used in other cases
 */
public class SimpleTest {
	IOSDriver<IOSElement> iosDriver = null;
	@Test
	public void test(){
		
		 List<IOSElement> element = AppiumServerSingleton.getInstance().getIosDriver().findElementsByName("音乐圈");
		 for(IOSElement ele:element){
			 if(ele.isDisplayed())
				 ele.click();
		 }
		 
	}
	
}
