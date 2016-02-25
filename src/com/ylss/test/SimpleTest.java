package com.ylss.test;

import io.appium.java_client.ios.IOSElement;

import java.util.List;

import org.testng.annotations.Test;

import com.ylss.utils.AppiumServer;
import com.ylss.utils.Common;

/**
 * this case just to test the variable that initialized in
 * the beforeSuite can be used in other cases
 */
public class SimpleTest extends AppiumServer{
	
	@Test
	public void test(){
		
		 List<IOSElement> element = Common.iosDriver.findElementsByName("音乐圈");
		 for(IOSElement ele:element){
			 if(ele.isDisplayed())
				 ele.click();
		 }
		 
	}
	
}
