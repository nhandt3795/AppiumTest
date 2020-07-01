package pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import utility.Utils;

public class GoogleEmail {
	WebDriver driver;
	String LATESTMAILFROMPARAM = "(//*[@class=\"yW\"]//span[@email=\"{param}\"]/../../../..//td[@class=\"xW xY \"]/span/span)[1]";

	By SIGN_IN = By.xpath("//div[@class='h-c-header__cta']//li[2]//a[contains(text(),'Sign in')]");
	By EMAIL = By.xpath("//*[@name=\"identifier\"]");
	By EMAIL_NEXT = By.id("identifierNext");
	By PASSWORD = By.xpath("//*[@id=\"password\"]//input");
	By PASSWORD_NEXT = By.id("passwordNext");
	By WELCOME_MAIL = By.xpath("(//span[contains(text(),'automationpractice')])[2]");
	By BTN_CONFIRM = By.xpath("//*[@class=\"yKBrKe\"]/div[@role=\"button\"]/span/span");

	public GoogleEmail(WebDriver driver) {
		this.driver = driver;
	}

	public void signIn(String emailStr, String passwordStr) throws InterruptedException {
		Utils.logInfo("Signing in G-mail on Google Chrome");
		ArrayList<String> tabs = new ArrayList<String>(this.driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		this.setEmail(emailStr);
		this.clickEmailNext();
		this.setPassword(passwordStr);
		this.clickPasswordNext();
		try {
			driver.findElement(BTN_CONFIRM).click();
		} catch (NoSuchElementException e) {
		}
	}

	public void clickSignIn() {
		driver.findElement(SIGN_IN).click();
	}

	public void setEmail(String email) {
		driver.findElement(EMAIL).sendKeys(email);
		;
	}

	public void setPassword(String password) {
		driver.findElement(PASSWORD).sendKeys(password);
		;
	}

	public void clickEmailNext() {
		driver.findElement(EMAIL_NEXT).click();
	}

	public void clickPasswordNext() {
		driver.findElement(PASSWORD_NEXT).click();
	}

	public boolean waitForNewEmailFrom(String email) {
		Utils.logInfo("Checking new email");
		// Refresh until new email found
		for (int i = 0; i < 5; i++) {
			if (!isExistMailFrom(email)) {
				Utils.logInfo("Mail not found. Refreshing...");
				driver.navigate().refresh();
			} else {
				Utils.logInfo("DONE");
				return true;
			}
		}
		return false;
	}

	public boolean isExistMailFrom(String hostEmail) {
		// Get current time
		LocalTime currentTime = LocalTime.now();
		// Get list of email from "Host Email"
		try {
			String receivedTime = this.driver.findElement(By.xpath(LATESTMAILFROMPARAM.replace("{param}", hostEmail)))
					.getText();
			if (receivedTime.contains("M")) {
				try {
					// Get email received time and check if it's no more than 2 minutes
					DateFormat df = new SimpleDateFormat("hh:mm aa");
					DateFormat outputFormat = new SimpleDateFormat("HH:mm");
					Date time12 = df.parse(receivedTime);
					String output = outputFormat.format(time12);
					LocalTime mailtime = LocalTime.parse(output);
					if (Duration.between(mailtime, currentTime).toMinutes() < 2) {
						return true;
					} else {
						return false;
					}
				} catch (ParseException pe) {
					return false;
				}
			} else {
				try {
					// Get email received time and check if it's no more than 2 minutes
					LocalTime mailTime = LocalTime.parse(receivedTime);
					if (Duration.between(mailTime, currentTime).toMinutes() < 2) {
						return true;
					} else {
						return false;
					}
				} catch (DateTimeParseException pe) {
					return false;
				}
			}
		} catch (NoSuchElementException ne) {
			return false;
		} catch (StaleElementReferenceException e) {
			return false;
		}
	}
}
