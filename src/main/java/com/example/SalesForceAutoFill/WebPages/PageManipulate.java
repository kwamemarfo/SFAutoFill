package com.example.SalesForceAutoFill.WebPages;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class PageManipulate {

	public void switchFrame(WebDriver driver, int frameId) {
		driver.switchTo().defaultContent();
		new WebDriverWait(driver, frameId).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
	}

	public void waitForLogin(WebDriver driver) {
		long timeInSeconds = 300;
		new WebDriverWait(driver, timeInSeconds).until(driver2 -> driver.findElement(By.className("oneAlohaPage")));
	}

	public String timeSheetHeaderDate(WebDriver driver) {
		WebElement getDateFromWebsite = new WebDriverWait(driver, (15)).until(
				driver2 -> driver.findElement(By.xpath("//div[@class='breadcrumb-item-link breadcrumb-heading']")));
		return getDateFromWebsite.getText().split(Pattern.quote("("))[1].split(" ")[0];
	}

}
