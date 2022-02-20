package com.example.SalesForceAutoFill.WebPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoginPage {

	@Value("${web.username}")
	private String username;
	
	@Value("${login.timeOut}")
	private long timeOut;
	
	private By usernameBy = By.name("username");
	
	private By alohaPageBy = By.className("oneAlohaPage");
	

	public OpenPage login(WebDriver driver) {
		WebDriverWait loginPage = new WebDriverWait(driver, 10);
		WebElement loginPage1 = loginPage.until(ExpectedConditions
				.visibilityOfElementLocated(usernameBy));
		loginPage1.sendKeys(username);
		
		System.out.println("<---   Waiting for User to Log-in   --->");
		this.waitForLogin(driver);
		
		return new OpenPage();
	}
	

	private void waitForLogin(WebDriver driver) {
		WebDriverWait waitForLogin = new WebDriverWait(driver, timeOut);
		waitForLogin.until(ExpectedConditions.visibilityOfElementLocated(alohaPageBy));
	}
}
