package com.example.SalesForceAutoFill.WebPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class LogoutPage {
	
	private By userProfBy = By.xpath("//span[@data-aura-class='oneUserProfileCardTrigger']");
	
	private By userLogOutBy = By.xpath("//div[@class='oneUserProfileCard']");
	
	private By logOutBy = By.xpath("//a[@data-aura-class='uiOutputURL']");
	
	private By loginFormBy = By.xpath("//div[@id='theloginform']");
	

	public OpenPage logout(WebDriver driver, Boolean wasTimeSheetFilledIn) {
		long waitForLogOut;
		String message;
		if (wasTimeSheetFilledIn) {
			message = "<-- No issues Encountered, gracefully looged out -->";
			waitForLogOut = 0;
		} else {
			message = "<-- Problems with TimeSheet Consider manual adjustment !!! -->";
			waitForLogOut = 100000;
		}
		try {
			System.out.println(message);
			Thread.sleep(waitForLogOut);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.clickLogOut(driver);
		return new OpenPage();
	}

	private void clickLogOut(WebDriver driver) {
		driver.switchTo().defaultContent();
		WebElement userProfile = new WebDriverWait(driver, 15).until(ExpectedConditions
				.elementToBeClickable(userProfBy));
		userProfile.click();
		
		WebElement selLogOut = new WebDriverWait(driver, 15).until(ExpectedConditions
				.visibilityOfElementLocated(userLogOutBy));
		WebElement selectLogOut = selLogOut.findElement(logOutBy);

		selectLogOut.click();
		
		new WebDriverWait(driver, 15).until(ExpectedConditions
				.visibilityOfElementLocated(loginFormBy));
	}
}
