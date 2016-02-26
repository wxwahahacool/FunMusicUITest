package com.ylss.utils;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.HideKeyboardStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseOperate { 
	@iOSFindBy(name = "我")
	@CacheLookup
	private WebElement UIAButtonMe;
	@iOSFindBy(name = "userDefaultImage")
	@CacheLookup
	private WebElement UIAImageMe;
	@iOSFindBy(name = "navigationbar sidebar")
	@CacheLookup
	private WebElement UIAButtonSideBar;
	@iOSFindBy(name = "未登录")
	@CacheLookup
	private WebElement UIALabel;
	
	
	
	
	 public IOSDriver<IOSElement> driver;
     private HashMap<String, HashMap<String, String>> yml; 
      

	public BaseOperate (IOSDriver<IOSElement> iosDriver,HashMap<String, HashMap<String, String>> yml) {
		PageFactory.initElements(new AppiumFieldDecorator(iosDriver), this);
		this.driver = iosDriver;
 		this.yml = yml;
	}
	
	public void switchToWebView(IOSDriver<IOSElement> iosDriver){
		Set<String> handles = iosDriver.getContextHandles();
		for(String handle:handles){
			if(handle.contains("WEBVIEW")||handle.contains("webview")){
				iosDriver.context(handle);
				System.out.println("switch to Web");
			}
		}
	}
	
	public void hideKeyboard(IOSDriver<IOSElement> iosDriver){
		iosDriver.hideKeyboard(); 
	}
	
	public void hide2Keyboard(IOSDriver<IOSElement> iosDriver){
		iosDriver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
	}
	
	
	public void lockScreen(IOSDriver<IOSElement> iosDriver,int seconds){
		iosDriver.lockScreen(seconds);
	}
	
	/**
	 * claseApp+launchApp=resetApp
	 * @param iosDriver
	 */
	public void closeApp(IOSDriver<IOSElement> iosDriver){
		iosDriver.closeApp();
	}
	
	public void launchApp(IOSDriver<IOSElement> iosDriver){
		iosDriver.launchApp();
	}
	
	public void resetApp(IOSDriver<IOSElement> iosDriver){
		iosDriver.resetApp();
	}

	/**
	 * the driver.runAppInBackground given by appium has a problem that if the app run in background after the 
	 * seconds you give it won't go back foreground,so use js to make your purpose realize
	 * @param iosDriver
	 * @param seconds
	 */
	public void runAppInBackground(IOSDriver<IOSElement> iosDriver,int seconds) {
		JavascriptExecutor jse = (JavascriptExecutor) iosDriver;
		jse.executeScript("var x = target.deactivateAppForDuration("+seconds+"); var MAX_RETRY=5, retry_count = 0; while (!x && retry_count < MAX_RETRY) { x = target.deactivateAppForDuration("+seconds+"); retry_count += 1}; x");
	}
	
	public void shake(IOSDriver<IOSElement> iosDriver){
		driver.shake();
	}
	
	public void swipeToUp(IOSDriver<IOSElement> driver, int during) {
        
		driver.context("NATIVE_APP");
		Dimension size = this.driver.manage().window().getSize(); 
		int startx = (int) size.width /2; 
		int starty = (int) (size.width * 0.95); 
		int endy = (int) (size.width * 0.05); ; 
		driver.swipe(startx, starty, startx, endy, during * 1000);
		
    }

    public void swipeToDown(IOSDriver<IOSElement> driver, int during) {
    	
    	driver.context("NATIVE_APP");
		Dimension size = this.driver.manage().window().getSize(); 
		int startx = (int) size.width /2; 
		int starty = (int) (size.width * 0.05); 
		int endy = (int) (size.width * 0.95); ; 
		driver.swipe(startx, starty, startx, endy, during * 1000);
       
    }

  
    public void swipeToLeft(IOSDriver<IOSElement> driver, int during) {
    	
		driver.context("NATIVE_APP");
		Dimension size = this.driver.manage().window().getSize(); 
		int startx = (int) (size.width * 0.95); 
		int endx = (int) (size.width * 0.05); 
		int starty = size.height / 2; 
		driver.swipe(startx, starty, endx, starty, during * 1000);
		
    }

 
    public void swipeToRight(IOSDriver<IOSElement> driver, int during) {
    	
    	driver.context("NATIVE_APP");
		Dimension size = this.driver.manage().window().getSize(); 
		int endx = (int) (size.width * 0.95); 
		int startx = (int) (size.width * 0.05); 
		int starty = size.height / 2; 
    	driver.swipe(startx, starty, endx, starty, during * 1000);
    	
    }
	
	public void buttonClick(){
		UIAButtonMe.click();
	}
	
	public void imageClick(){
		UIAImageMe.click();
	}
	
	public void sideBarClick(){
		UIAButtonSideBar.click();
	}
	
	public void labelClick(){
		UIALabel.click();
	}
	
	/**
	 * sendKeys is really not supposed to use because it is really slow,instead you can use quickInputText
	 * @param element
	 * @param value
	 */
	public void inputText(IOSElement element, String value){
		element.sendKeys(value);
	}
	
	/**
	 * recommended to use this method to sendkeys
	 * @param element
	 * @param value
	 */
	public void quickInputText(IOSElement element, String value){
		element.setValue(value);
	}
	
	
	
	public boolean isElementDisplayed(WebElement element){
		if(element.isDisplayed())
			return true;
		return false;	
	}
	
	public boolean isElementSelected(WebElement element){
		if(element.isSelected())
			return true;
		return false;	
	}
	
	public boolean isElementEnabled(WebElement element){
		if(element.isEnabled())
			return true;
		return false;	
	}
	
	public boolean isAlert(IOSDriver<WebElement> drivername){
		WebElement alert = drivername.findElementByClassName("UIAAlert");
		if(null != alert)
			return true;
		return false;	
	}
	
	public void confirmAlert(IOSDriver<WebElement> drivername){
		Alert alert = drivername.switchTo().alert();
        if(alert != null)
        	drivername.switchTo().alert().accept();
	}
	
    
    
    
    
     

       
        
        private By getBy(String type, String value) {
            By by = null;
            if (type.equals("id")) {
                by = By.id(value);
            }
            if (type.equals("name")) {
                by = By.name(value);
            }
            if (type.equals("xpath")) {
                by = By.xpath(value);
            }
            if (type.equals("class")) {
                by = By.className(value);
            }
            if (type.equals("linkText")) {
                by = By.linkText(value);
            }
            return by;
        }
        
       
        
        public IOSElement watiForElement(final By by) {
        	IOSElement element = null;
            int waitTime = 5;//Integer.parseInt(Config.getConfig("waitTime"));
            try {
                element = new WebDriverWait(driver, waitTime)
                        .until(new ExpectedCondition<IOSElement>() {
                        	public IOSElement apply(WebDriver d) {
                                return (IOSElement) d.findElement(by);
                            }
                        });
            } catch (Exception e) {
                System.out.println(by.toString() + " is not exist until " + waitTime);
            }
            
           if(!this.waitElementToBeDisplayed(element))
                element = null;
            
            return element;
        }
        
    	public boolean waitElementToBeNonDisplayed(final WebElement element) {
    		boolean wait = false;
    		if (element == null)
    			return wait;
    		try {
    			wait = new WebDriverWait(driver, 3)
    					.until(new ExpectedCondition<Boolean>() {
    						public Boolean apply(WebDriver d) {
    							return !element.isDisplayed();
    						}
    					});
    		}
    		catch (Exception e) {
    			System.out.println("Locator [" + element.toString()
    					+ "] is also displayed");
    		}
    		return wait;
    	}
    	
        private boolean waitElementToBeDisplayed(final WebElement element) {
            boolean wait = false;
            if (element == null)
                return wait;
            try {
                wait = new WebDriverWait(driver,5)
                        .until(new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver d) {
                                return element.isDisplayed();
                            }
                        });
            } catch (Exception e) {
                System.out.println(element.toString() + " is not displayed");
            }
            return wait;
        }
    	
//    	public WebElement getElement(String elementKey){
//    		String type = this.yml.get(elementKey).get("type");
//            String value = this.yml.get(elementKey).get("value");
//    		return this.watiForElement(this.getBy(type, value));
//    	}
        
//        public WebElement getElementOld(String elementKey){
//    		String type = this.yml.get(elementKey).get("type");
//            String value = this.yml.get(elementKey).get("value");
//    		WebElement element = this.watiForElement(this.getBy(type, value));
//    		if(!this.waitElementToBeDisplayed(element)){
//    			 element = null;
//    		}
//    		return element;
//    	}
    //    
//        public WebElement getElementNoWait(String elementKey) {
//    		WebElement element = null;
//    		String type = this.yml.get(elementKey).get("type");
//    		String value = this.yml.get(elementKey).get("value");
//    		try {
//    			element = driver.findElement(this.getBy(type, value));
//    		}
//    		catch (Exception e) {
//    			element = null;
//    		}
//    		return element;
//    	}
        
        /*
         * deal with elements
         */
        public List<IOSElement> getElements(String key) {     
            return this.getLocators(key, true);
        }
        
        private List<IOSElement> getLocators(String key, boolean wait) {
        	List<IOSElement> element = null;
            if (this.yml.containsKey(key)) {
                HashMap<String, String> m = this.yml.get(key);
                String type = m.get("type");
                String value = m.get("value");         
                By by = this.getBy(type, value);
                    try {
                        element = (List<IOSElement>) driver.findElements(by);
                    } catch (Exception e) {
                        element = null;
                    }
            } else
                System.out.println("Locator " + key + " is not exist in " + ".yaml");
            return element;
        }
        
        public IOSElement getElement(String key) {     
            return this.getLocator(key, true);
        }
        
        public IOSElement getIOSElement(String key) {     
            return this.getLocator(key, true);
        }
         
        public IOSElement getElementNoWait(String key) {
            return this.getLocator(key, false);
        }
        
        private IOSElement getLocator(String key, boolean wait) {
        	IOSElement element = null;
            if (this.yml.containsKey(key)) {
                HashMap<String, String> m = this.yml.get(key);
                String type = m.get("type");
                String value = m.get("value");
                //增加一个xpath的特殊处理 用于简化yaml中xpath的书写
//                if(!"".equals(value) && value.equals("xpath")){
//                	String xpathTemplate = "//%1$s[contains(@text,'%2$s')]";
//                	value = String.format(xpathTemplate, "","");
//                }
                By by = this.getBy(type, value);
                if (wait) {
                    element = this.watiForElement(by);
                    boolean flag = this.waitElementToBeDisplayed(element);
                    if (!flag)
                        element = null;
                } else {
                    try {
                        element = driver.findElement(by);
                    } catch (Exception e) {
                        element = null;
                    }
                }
            } else
                System.out.println("Locator " + key + " is not exist in " + ".yaml");
            return element;
        }
        
    	public WebElement getElementObject(String elementKey){
    		String type = this.yml.get(elementKey).get("type");
            String value = this.yml.get(elementKey).get("value");
            WebElement result = null;
            if (type.equals("id")) {
            	result = driver.findElement(By.id(value));
            }
            if (type.equals("name")) {
            	result = driver.findElement(By.name(value));
            }
            if (type.equals("xpath")) {
            	result = driver.findElement(By.xpath(value));
            }
            if (type.equals("class")) {
            	result = driver.findElement(By.className(value));
            }
            if (type.equals("linkText")) {
            	result = driver.findElement(By.linkText(value));
            }else{
            	
            }
            return result;
    	}
    	
    	
}
