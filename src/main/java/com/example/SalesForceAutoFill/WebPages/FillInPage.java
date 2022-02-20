package com.example.SalesForceAutoFill.WebPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FillInPage {
	
	@Value("${Activity}")
	private String activity;
	
	@Value("${Time.Type}")
	private String sheetTimeType;
	
	@Value("${Entry.Hours}")
	private String entryHours;
	
	@Value("${Location}")
	private String locationTimeSheet;
	
	private By acitvityNameBy = By.className("ResourceActivityName");
	
	private By timeEntryRatesBy = By.className("NewTimeEntryRates");
	
	private By timeEntryHoursBy = By.cssSelector(".EntryUnitsText.entry-units");
	
	private By locationBy = By
			.xpath("//td[@class='data2Col last']//select[@class='persist-time-field']");
	
	private By saveEntryBtnBy = By.id("saveNewEntryBtn");
	
	
	public OpenPage fillInPage(WebDriver driver) {
		
		WebDriverWait webWait = new WebDriverWait(driver, 40);
		WebElement activityDropd = webWait.until(ExpectedConditions
				.visibilityOfElementLocated(acitvityNameBy));
		Select activityDropdown = new Select(activityDropd);
		activityDropdown.selectByVisibleText(activity);

		WebElement timeTyp = webWait.until(ExpectedConditions
				.visibilityOfElementLocated(timeEntryRatesBy));
		Select timeType = new Select(timeTyp);
		timeType.selectByVisibleText(sheetTimeType);

		// id could be dynamic (consider different approach)!!
		webWait.until(ExpectedConditions.elementToBeClickable(timeEntryHoursBy))
		.sendKeys(entryHours);
		
		Select location = new Select(driver.findElement(locationBy));
		location.selectByVisibleText(locationTimeSheet);

		webWait.until(ExpectedConditions.elementToBeClickable(saveEntryBtnBy)).click();

		WebElement timeEntryBox = driver.findElement(By.id("AddEntryPopup"));
		webWait.until(ExpectedConditions.invisibilityOf(timeEntryBox));
	
		return new OpenPage();
	}
}
