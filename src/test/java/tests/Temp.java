package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;

public class Temp {
	AppiumDriver<MobileElement> driver;
	
	@Test
	public void TC() {
		MobileElement el1 =  driver.findElementById("com.google.android.gm:id/welcome_tour_got_it");
		el1.click();
		List<MobileElement> listMail = driver.findElementsById("com.google.android.gm:id/account_address");
		System.out.println(listMail.get(1).getText());
	}

	@BeforeMethod
	public void beforeMethod() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "EmulatorP3");
		caps.setCapability("udid", "330089a828d1a343");
		caps.setCapability("platformVersion", "8.1.0");
		caps.setCapability("platformName", "Android");
		caps.setCapability("appPackage", "com.google.android.gm");
		caps.setCapability("appActivity", ".ConversationListActivityGmail");
		
		try {
			URL url = new URL ("http://0.0.0.0:4723/wd/hub");
			driver = new AppiumDriver<MobileElement> (url, caps);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cause is: " + e.getCause());
		}
	}

	@AfterMethod
	public void afterMethoid() {
		
	}

}
