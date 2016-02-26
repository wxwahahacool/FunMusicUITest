package com.ylss.utils;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class AppiumServerSingleton {
	private IOSDriver<IOSElement> iosDriver;
	private  AppiumDriverLocalService service;
	private static AppiumServerSingleton single;
	
	public AppiumServerSingleton(){
		this.service = startAppium();
		this.iosDriver = startDriver(service);
	}
	
	public IOSDriver<IOSElement> getIosDriver() {
		return iosDriver;
	}

	public void setIosDriver(IOSDriver<IOSElement> iosDriver) {
		this.iosDriver = iosDriver;
	}

	public AppiumDriverLocalService getService() {
		return service;
	}

	public void setService(AppiumDriverLocalService service) {
		this.service = service;
	}


	public static synchronized AppiumServerSingleton getInstance() {  
        if (single == null) {    
            single = new AppiumServerSingleton();  
        }    
       return single;  
} 
	
private IOSDriver<IOSElement> startDriver(AppiumDriverLocalService service) {
		
		if (service == null || !service.isRunning())
            throw new RuntimeException("An appium server node is not started!");
		DesiredCapabilities capabilities = DesiredCapabilities.iphone();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "iOS");
		capabilities.setCapability("platformName", "Mac");
		capabilities.setCapability("deviceName", "iPhone 6");
		capabilities.setCapability("platformVersion", "9.2");
		capabilities.setCapability("nativeInstrumentsLib", "true");
		capabilities.setCapability("noReset", "true");
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "apps");
		File app = new File(appDir, "FunMusic.app");
		System.out.println("---->" + app.getAbsolutePath());
		capabilities.setCapability("app", app.getAbsolutePath());
		
		iosDriver = new IOSDriver<IOSElement>(service.getUrl(), capabilities);
		iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return iosDriver;
		
	}
	
	private AppiumDriverLocalService startAppium() {
		
		stopAppiumByShell();
		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
						.usingDriverExecutable(new File("/Applications/Appium.app/Contents/Resources/node/bin/node"))
						.withAppiumJS(
								new File(
										"/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js"))
						.withIPAddress("127.0.0.1").usingPort(4723).withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
		                .withArgument(GeneralServerFlag.COMMAND_TIMEOUT, "60"));
		

		service.start();
		return service;
	}
	
	public void stopAppium(AppiumDriverLocalService service) {
		
		if (service != null)
			service.stop();
		
	}
	
	public void stopDriver(IOSDriver<IOSElement> iosDriver) {
		
		if (iosDriver != null){
			iosDriver.quit();
		}
		
	}
	
private void stopAppiumByShell(){
		
		String[] cmds = {"/bin/sh","-c","ps -ef|grep appium|grep -v grep|awk {'print $2'}|xargs kill -9"};  
        Process pro;
		try {
			pro = Runtime.getRuntime().exec(cmds);
			pro.waitFor();  
		    InputStream in = pro.getInputStream();  
		    BufferedReader read = new BufferedReader(new InputStreamReader(in)); 
		    String line = null;  
	        while((line = read.readLine())!=null){  
	            System.out.println(line);  
	        }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}
	
	public void takeScreenshot(IOSDriver<IOSElement> drivername,String fileName) {
		
		String screenName = fileName + String.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())) + ".jpg";
		File dir = new File("test-output/snapshot");
        if (!dir.exists())
            dir.mkdirs();
        String screenPath = dir.getAbsolutePath() + "/" + screenName;
        try {
            File scrFile = ((TakesScreenshot) drivername)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenPath));
        } catch (IOException e) {
            System.out.println("Screen shot error: " + screenPath);
        }  
        
    }
	
}
