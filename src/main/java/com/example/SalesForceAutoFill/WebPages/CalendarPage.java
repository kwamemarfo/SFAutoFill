package com.example.SalesForceAutoFill.WebPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class CalendarPage {
	
	private By calendarViewBy = By.xpath("//div[@id='calendar-view']");
	
	private By obscureHeaderBy = By.xpath("//tr[@class='period-header']");
	

	public OpenPage selectCalendarView(WebDriver driver) {
		driver.switchTo().parentFrame();
		
		WebDriverWait webWait = new WebDriverWait(driver, 30);
		
		webWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(1));
		WebElement selectCalenderView = webWait.until(ExpectedConditions
				.visibilityOfElementLocated(calendarViewBy));
		
		// Wait because page gets obsecured by loading banner before loading
		// Do NOT REMOVE --- find a better solution for banner obscuring clicking
		webWait.until(driver2 -> driver.findElement(obscureHeaderBy));
		selectCalenderView.click();
	
		return new OpenPage();
	}
}
