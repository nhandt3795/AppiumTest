package tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pages.GoogleEmail;
import pages.MobileMail;
import pages.MobileMailSignIn;
import utility.GetDataProvider;

public class BaseClass{
	AppiumDriver<MobileElement> driver;
	WebDriver webDriver;
	
	@BeforeMethod
	@Parameters({"deviceName","udid", "platformVersion","url"})
	public void beforeMethod(String deviceName, String udid, String platformVersion, String url) {
		// TODO Auto-generated constructor stub
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", deviceName);
		caps.setCapability("udid", udid);
		caps.setCapability("platformVersion", platformVersion);
		caps.setCapability("platformName", "Android");
		caps.setCapability("appPackage", "com.google.android.gm");
		caps.setCapability("appActivity", ".ConversationListActivityGmail");
		
		try {
			URL urlHub = new URL (url);
			driver = new AppiumDriver<MobileElement> (urlHub, caps);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cause is: " + e.getCause());
		}
		
		// Declare Webdriver
		System.setProperty("webdriver.chrome.driver", "./src/test/java/webDriver/chromedriver.exe");
		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(dataProvider = "email-to-send", dataProviderClass = GetDataProvider.class)
	public void sendEmail(String fromEmail, String toEmail, String password, String subject, String content) throws InterruptedException {
		System.out.println("Go to Email");
		MobileMailSignIn home = new MobileMailSignIn(driver);
		home.clickGotIt();
		MobileMail mail = home.goToEmail();
		mail.selectHostMail(fromEmail);
		mail.sendMail(toEmail, subject, content);
		System.out.println("Checking new email");
		webDriver.get("https://www.google.com/intl/en/gmail/about/#");
		GoogleEmail gmail = new GoogleEmail(webDriver);
		gmail.clickSignIn();
		gmail.signIn(toEmail, password);
		assert(gmail.waitForNewEmailFrom(fromEmail));
	}
	
	@AfterMethod
	public void afterMethod() {
		driver.quit();
		webDriver.quit();
	}
}
