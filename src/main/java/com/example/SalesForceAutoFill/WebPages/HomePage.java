package com.example.SalesForceAutoFill.WebPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class HomePage {
	
	private By homeCalendarBy = By
			.xpath("//div[@class='tracking-period selected for-lightning']//div[@class='day-period BusDay']");

	public OpenPage calendarSelect(WebDriver driver) {
		driver.switchTo().frame(0);
		WebElement selectTimesheetToFill = new WebDriverWait(driver, (15))
				.until(ExpectedConditions.visibilityOfElementLocated(homeCalendarBy));
		
		selectTimesheetToFill.click();
		return new OpenPage();
	}

}
