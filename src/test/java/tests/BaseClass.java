package tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import io.appium.java_client.remote.MobileCapabilityType;
import pages.GoogleEmail;
import pages.MobileMail;
import pages.MobileMailSignIn;
import utility.GetDataProvider;
import utility.Utils;

public class BaseClass{
	AppiumDriver<MobileElement> driver;
	WebDriver webDriver;
	MobileMailSignIn home;
	MobileMail mail;
	GoogleEmail gmail;
	
	@BeforeMethod
	@Parameters({"deviceName","udid", "platformVersion","url"})
	public void beforeMethod(String deviceName, String udid, String platformVersion, String url) {
		// TODO Auto-generated constructor stub
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		caps.setCapability(MobileCapabilityType.UDID, udid);
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		caps.setCapability("appPackage", "com.google.android.gm");
		caps.setCapability("appActivity", ".ConversationListActivityGmail");
		Utils.logInfo("Starting Gmail");
		
		try {
			URL urlHub = new URL (url);
			driver = new AppiumDriver<MobileElement> (urlHub, caps);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cause is: " + e.getCause());
		}
		
		// Declare Webdriver
		System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(dataProvider = "email-with-content", dataProviderClass = GetDataProvider.class)
	public void sendEmailWithContent(String fromEmail, String toEmail, String password, String subject, String content) throws InterruptedException {
		String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		subject = subject + "_" + currentTime;
		home = new MobileMailSignIn(driver);
		home.clickGotIt();
		mail = home.goToEmail();
		mail.selectHostMail(fromEmail);
		mail.sendMail(toEmail, subject, content);
		webDriver.get("https://www.google.com/intl/en/gmail/about/#");
		gmail = new GoogleEmail(webDriver);
		gmail.clickSignIn();
		gmail.signIn(toEmail, password);
		assert(gmail.waitForNewEmailFrom(fromEmail));
	}
	
	@Test(dataProvider = "email-without-content", dataProviderClass = GetDataProvider.class)
	public void sendEmailWithoutContent(String fromEmail, String toEmail, String password, String subject) throws InterruptedException {
		String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		subject = subject + "_" + currentTime;
		home = new MobileMailSignIn(driver);
		home.clickGotIt();
		mail = home.goToEmail();
		mail.selectHostMail(fromEmail);
		mail.sendMailWithoutContent(toEmail, subject);
		webDriver.get("https://www.google.com/intl/en/gmail/about/#");
		gmail = new GoogleEmail(webDriver);
		gmail.clickSignIn();
		gmail.signIn(toEmail, password);
		assert(gmail.waitForNewEmailFrom(fromEmail));
	}
	
	@Test(dataProvider = "email-without-content", dataProviderClass = GetDataProvider.class)
	public void sendEmailAndCheck(String fromEmail, String toEmail, String password, String subject) throws InterruptedException {
		String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		subject = subject + "_" + currentTime;
		home = new MobileMailSignIn(driver);
		home.clickGotIt();
		mail = home.goToEmail();
		mail.selectHostMail(fromEmail);
		mail.sendMailWithoutContent(toEmail, subject);
		webDriver.get("https://www.google.com/intl/en/gmail/about/#");
		gmail = new GoogleEmail(webDriver);
		gmail.clickSignIn();
		gmail.signIn(toEmail, password);
		assert(gmail.isExistMailFrom(toEmail));
	}
	
	@AfterMethod
	public void afterMethod() {
		driver.quit();
		webDriver.quit();
	}
}
