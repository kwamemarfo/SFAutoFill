package com.example.SalesForceAutoFill.WebPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class NextTimeSheetPage {
	
	private By pageBodyBy = By.className("no-menu");
	
	private By tabElemBy = By.xpath(".//div[contains(normalize-space(@class),"
			+ "'time-entry-period day-container BusDay first-period')]");

	private By calendarBy = By
			.xpath("//div[@id='fixed-header-container']"
					+ "//a[@class='period-summary-btn']");
	
//	 Doesn't like this??
//	private By dateToFillBy = By.xpath("//div[@class='tracking-period selected for-lightning')]");
	
	private By sibblingBy = By.xpath("following-sibling::*");
	
	private By selectNextDateBy = By
			.xpath(".//div[normalize-space(@class)='day-period BusDay']");
	
	private By nextCalPageBy = By.xpath("//div[normalize-space(@class)="
			+ "'forecasting-period-header-btn next-forecasting-period']");
	
	private By dayFromCalBy = By
			.xpath("//div[normalize-space(@class)='tracking-period  for-lightning']");
	
	
	
	public void nextSheet(WebDriver driver) {
		while (true) {
			WebElement timeSheetPageBody = new WebDriverWait(driver, 15)
					.until(ExpectedConditions.visibilityOfElementLocated(pageBodyBy));

			List<WebElement> tabElements = timeSheetPageBody.findElements(tabElemBy);

			Boolean isSubmitted = false;
			for (WebElement webElement : tabElements) {
				try {
					WebElement getSubmision = webElement.findElement(By.className("entry-card-status"));
					String submissionInfo = getSubmision.getText();
					// Change this later with equals submitted or approved (Get the correct phrase)
					if (submissionInfo.equals("DRAFTS")) {
						isSubmitted = false;
						break;
					} else {
						isSubmitted = true;
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();
					break;
				}
			}
			if (!isSubmitted) {
				break;
			}
			this.selectNextTimeSheet(driver);
			break;
		}
	}
	
	private void selectNextTimeSheet(WebDriver driver) {
		WebElement calendarSelect = driver.findElement(calendarBy);
		calendarSelect.click();

		WebElement selectTimesheetToFill = new WebDriverWait(driver, (15)).until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='tracking-periods']//div[@class='tracking-period selected for-lightning']")));

		try {
			WebElement selectNextWeek = selectTimesheetToFill.findElement(sibblingBy);

			WebElement selectedNextWeek = selectNextWeek.findElement(selectNextDateBy);
			selectedNextWeek.click();
			this.checkAlert(driver);
		} catch (NoSuchElementException e1) {
			// If no sibling element, click next page calendar
			e1.printStackTrace();
			this.nextCalendarPage(driver);

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}


	private void nextCalendarPage(WebDriver driver) {
		WebElement selectNextPageCalendar = driver.findElement(nextCalPageBy);
		selectNextPageCalendar.click();

		WebElement selectDayFromCalendar = new WebDriverWait(driver, 15).until(ExpectedConditions
				.visibilityOfElementLocated(dayFromCalBy));
		selectDayFromCalendar.click();
		this.checkAlert(driver);
	}
	
	public void checkAlert(WebDriver driver) {
		try {
			driver.switchTo().window(driver.getWindowHandle());
			driver.switchTo().frame(1);
			WebElement test = new WebDriverWait(driver, 15)
					.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("//td[@class='messageCell']")));
			test.findElement(By.linkText("here")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
