package pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;

public class GoogleEmail {
	WebDriver driver;
	public static long registerTime;
	String LATESTMAILFROMPARAM = "(//*[@class=\"yW\"]//span[@email=\"{param}\"]/../../../..//td[@class=\"xW xY \"]/span/span)[1]";

	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void quit() {
		// TODO Auto-generated method stub

	}

	public Navigation navigate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Options manage() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPageSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCurrentUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public void get(String url) {
		// TODO Auto-generated method stub

	}

	public <T extends WebElement> List<T> findElements(By by) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends WebElement> T findElement(By by) {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		// TODO Auto-generated method stub

	};

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
//			click(this.signIn, 2000);
//			driver.findElement();
		driver.findElement(SIGN_IN).click();
	}

	public void setEmail(String email) {
//			type(email, this.email);
		driver.findElement(EMAIL).sendKeys(email);
		;
	}

	public void setPassword(String password) {
//			type(password, this.password);
		driver.findElement(PASSWORD).sendKeys(password);
		;
	}

	public void clickEmailNext() {
//			click(this.next, 500);
		driver.findElement(EMAIL_NEXT).click();
	}

	public void clickPasswordNext() {
//			click(this.passwordNext, 500);
		driver.findElement(PASSWORD_NEXT).click();
	}

	public boolean waitForNewEmailFrom(String email) {
		// Refresh until new email found
		for (int i = 0; i < 5; i++) {
			if (!isExistMailFrom(email)) {
				driver.navigate().refresh();
			} else {
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
		String receivedTime = this.driver.findElement(By.xpath(LATESTMAILFROMPARAM.replace("{param}", hostEmail))).getText();
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
		}
		catch (StaleElementReferenceException e) {
			String receivedTime = this.driver.findElement(By.xpath(LATESTMAILFROMPARAM.replace("{param}", hostEmail))).getText();
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
		}
	}
}
