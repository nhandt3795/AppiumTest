package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MobileMailSignIn extends Base {

	String ID_BTN_GOT_IT = "com.google.android.gm:id/welcome_tour_got_it";
	String ID_GO_TO_EMAIL = "com.google.android.gm:id/action_done";
	String ID_LIST_MAIL = "com.google.android.gm:id/account_address";
	String ID_BTN_ADD_EMAIL = "com.google.android.gm:id/setup_addresses_add_another";
	String XP_GOOGLE = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout";
	String XP_TXT_EMAIL = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[3]/android.view.View/android.view.View[1]/android.view.View[1]/android.widget.EditText";
	String XP_BTN_EMAIL_NEXT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[4]/android.view.View/android.widget.Button";
	String XP_TXT_PASSWORD = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[1]/android.view.View[3]/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.widget.EditText";
	String XP_BTN_PASSWORD_NEXT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[4]/android.view.View/android.widget.Button";
	String XP_BTN_SKIP = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[4]/android.view.View/android.widget.Button";
	String XP_BTN_ACCEPT_TERM = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.widget.Button";
	String ID_BTN_ACCEPT_SYNC = "com.google.android.gms:id/sud_navbar_next";

	public MobileMailSignIn(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}

	public void clickGotIt() {
		MobileElement gotItButton = driver.findElementById(ID_BTN_GOT_IT);
		gotItButton.click();
	}

	public MobileMail goToEmail() {
		// Wait for email addresses loaded
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(ID_LIST_MAIL)));
		MobileElement goToEmailButton = driver.findElementById(ID_GO_TO_EMAIL);
		goToEmailButton.click();
		return new MobileMail(driver);
	}

	public boolean isEmailInList(String email) {
		List<String> listMail = new ArrayList<String>();
		List<MobileElement> list = driver.findElementsById(ID_LIST_MAIL);
		if (list.size() == 0) {
			return false;
		} else {
			for (int i = 0; i < list.size(); i++) {
				listMail.add(list.get(i).getText());
			}
			if (listMail.contains(email)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public void signInEmail(String email, String password) {
		MobileElement addButton = driver.findElementById(ID_BTN_ADD_EMAIL);
		addButton.click();
		MobileElement selectGoogle = driver.findElementByXPath(XP_GOOGLE);
		selectGoogle.click();
		MobileElement fEmail = driver.findElementByXPath(XP_TXT_EMAIL);
		fEmail.sendKeys(email);
		MobileElement emailNextButton = driver.findElementByXPath(XP_BTN_EMAIL_NEXT);
		emailNextButton.click();
		MobileElement fPassword = driver.findElementByXPath(XP_TXT_PASSWORD);
		fPassword.sendKeys(password);
		MobileElement passwordNextButton = driver.findElementByXPath(XP_BTN_PASSWORD_NEXT);
		passwordNextButton.click();
		// Need to swipe down
		if (isElementPresent(By.xpath(XP_BTN_SKIP))) {
			MobileElement skipButton = driver.findElementByXPath(XP_BTN_SKIP);
			skipButton.click();
		}
		if (isElementPresent(By.xpath(XP_BTN_ACCEPT_TERM))) {
			MobileElement acceptTerm = driver.findElementById(XP_BTN_ACCEPT_TERM);
			acceptTerm.click();
		}
		if (isElementPresent(By.id(ID_BTN_ACCEPT_SYNC))) {
			MobileElement acceptSync = (MobileElement) driver.findElementById(ID_BTN_ACCEPT_SYNC);
			acceptSync.click();
		}
	}

	public MobileMail checkHostMailAndGoToEmail(String email, String password) {
		// Check if email is already in list
		if (isEmailInList(email)) {
			// Go to Email if true
			goToEmail();
			return new MobileMail(driver);
		} else {
			// Sign in then go to email if false
			signInEmail(email, password);
			goToEmail();
			return new MobileMail(driver);
		}
	}
}
