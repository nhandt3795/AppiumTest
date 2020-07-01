package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MobileMailSignIn extends Base {

	String ID_BTN_GOT_IT = "com.google.android.gm:id/welcome_tour_got_it";
	String ID_GO_TO_EMAIL = "com.google.android.gm:id/action_done";
	String ID_LIST_MAIL = "com.google.android.gm:id/account_address";

	public MobileMailSignIn(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}

	public void clickGotIt() {
		System.out.println("[INFO] Going to Email");
		MobileElement gotItButton = driver.findElementById(ID_BTN_GOT_IT);
		gotItButton.click();
	}

	public MobileMail goToEmail() {
		// Wait for email addresses loaded
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(ID_LIST_MAIL)));
		MobileElement goToEmailButton = driver.findElementById(ID_GO_TO_EMAIL);
		goToEmailButton.click();
		return new MobileMail(driver);
	}
}
