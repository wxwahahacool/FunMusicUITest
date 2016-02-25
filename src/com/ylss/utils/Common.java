package com.ylss.utils;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public interface Common {
	public static final AppiumDriverLocalService service = new AppiumServer().startAppium();
	public static final IOSDriver<IOSElement> iosDriver = new AppiumServer().startDriver(service);
}
