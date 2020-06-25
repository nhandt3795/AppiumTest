package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Base {
	public AppiumDriver<MobileElement> driver;
	public WebDriver webDriver;
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
	} catch (NoSuchElementException e) {
		return false;
	}
		}
}
