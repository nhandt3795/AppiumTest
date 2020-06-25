package pages;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MobileMail extends Base{
	String ID_BTN_COMPOSE = "com.google.android.gm:id/compose_button";
	String ID_TXT_TO = "com.google.android.gm:id/to";
	String ID_TXT_SUBJECT = "com.google.android.gm:id/subject";
	String XP_TXT_CONTENT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.widget.EditText";
	String ID_BTN_SEND = "com.google.android.gm:id/send";
	String XP_SELECTED_EMAIL = "//android.widget.FrameLayout[@resource-id=\"com.google.android.gm:id/selected_account_header\"]//android.widget.TextView[@resource-id=\"com.google.android.gm:id/account_name\"]";
	String ID_BTN_CLOSE = "com.google.android.gm:id/close_button";
	String XP_OTHER_MAIL = "//android.support.v7.widget.RecyclerView/android.view.ViewGroup";
	String XP_OTHER_MAIL_NAME = "//android.support.v7.widget.RecyclerView/android.view.ViewGroup/android.widget.TextView[@resource-id=\"com.google.android.gm:id/account_name\"]";
	
	public MobileMail(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}
	
	public void sendMail(String toEmail, String subject, String content) {
		MobileElement composeButton =  driver.findElementById(ID_BTN_COMPOSE);
		composeButton.click();
		System.out.println("Composing Email");
		MobileElement fTo =  driver.findElementById(ID_TXT_TO);
		fTo.sendKeys(toEmail);
		MobileElement fSubject =  driver.findElementById(ID_TXT_SUBJECT);
		fSubject.sendKeys(subject);
		MobileElement fContent =  driver.findElementByXPath(XP_TXT_CONTENT);
		fContent.sendKeys(content);
		MobileElement sendButton =  driver.findElementById(ID_BTN_SEND);
		sendButton.click();
		System.out.println("Sent Email");
	}
	
	public void selectHostMail(String fromEmail) {
		MobileElement myAccount = driver.findElementById("com.google.android.gm:id/og_apd_ring_view");
		myAccount.click();
		String selectedEmail = driver.findElementByXPath(XP_SELECTED_EMAIL).getText();
		if (selectedEmail.equalsIgnoreCase(fromEmail)) {
			driver.findElementById(ID_BTN_CLOSE).click();
		} else {
			List<MobileElement> listOtherMail = driver.findElementsByXPath(XP_OTHER_MAIL);
			List<MobileElement> listOtherAccount = driver.findElementsByXPath(XP_OTHER_MAIL_NAME);
			for (int i = 0; i < listOtherAccount.size(); i++) {
				String accountName = listOtherAccount.get(i).getText();
				if(accountName.equalsIgnoreCase(fromEmail)) {
					listOtherMail.get(i).click();
					break;
				}
			}
		}
	}
	
}