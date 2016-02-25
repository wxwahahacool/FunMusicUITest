package com.ylss.action;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.util.HashMap;

import com.ylss.utils.BaseOperate;
import com.ylss.utils.YamlFileDirector;
import com.ylss.utils.YamlReader;

public class LoginAction {
	
	private HashMap<String, HashMap<String, String>> ymlLogin = new YamlReader().getYamlFile(YamlFileDirector.loginPage);
	BaseOperate bo;
	
	public LoginAction(IOSDriver<IOSElement> iosDriver){
		
		bo = new BaseOperate(iosDriver, ymlLogin);
		
	}
	
	public void runAppBackground(IOSDriver<IOSElement> iosDriver, int seconds){
		
		bo.runAppInBackground(iosDriver, seconds);
		
	}
	
	public void swipeLeft(IOSDriver<IOSElement> iosDriver,int during){
		
		bo.swipeToLeft(iosDriver, during);
		
	}
	
	public void swipeRight(IOSDriver<IOSElement> iosDriver,int during){
		
		bo.swipeToRight(iosDriver, during);
		
	}
	
	public void swipeUp(IOSDriver<IOSElement> iosDriver,int during){
		
		bo.swipeToUp(iosDriver, during);
		
	}
	
	public void swipeDown(IOSDriver<IOSElement> iosDriver,int during){
		
		bo.swipeToDown(iosDriver, during);
		
	}
	
	public void buttonMe(){
		
		bo.getElement("meButton").click();
		
	}
	
	public void loginTrigger(){
		
		bo.getElement("meButton").click();
		bo.getElements("labelIsNotLogin").get(1).click();
		
	}
	
	public void loginOutTrigger(){
		
		bo.getElement("sideBar").click();
		bo.getElements("logout").get(1).click();
		
	}
	
	public void inputUserNameAndPassword(){
		
//		bo.inputText(bo.getElement("userName"), "admin");
//		bo.inputText(bo.getElement("passWord"), "admin");
		
		bo.quickInputText(bo.getElement("userName"), "admin");
		bo.quickInputText(bo.getElement("passWord"), "admin");
		
	}
	
	public void loginConfirm(){
		
		bo.getElement("loginSubmit").click();
		
	}
	
	public boolean isLoginSuccess(){
		
		IOSElement element = bo.getElement("labelIsLogin");
		if(null !=  element && "王博".equals(element.getText()))
			return true;
		return false;
		
	}
	
	public boolean isLogoutSuccess(){
		
		IOSElement element = bo.getElements("labelIsNotLogin").get(1);
		if(null !=  element && "未登录123".equals(element.getText()))
			return true;
		return false;
		
	}

	
}
	
