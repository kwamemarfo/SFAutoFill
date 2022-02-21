package com.example.SalesForceAutoFill.WebPages;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.SalesForceAutoFill.Service.DateServices;

@Component
public class TimeSheetPage {

	@Autowired
	private DateServices dateServices;
	
	@Autowired
	private PageManipulate pageManipulate;
	
	@Autowired
	private NextTimeSheetPage nextTSPage;
	
	@Autowired
	private FillInPage fillInPage;
	
	@Value("${Activity}")
	private String activity;
	
	@Value("${Time.Type}")
	private String sheetTimeType;
	
	@Value("${Entry.Hours}")
	private String entryHours;
	
	@Value("${Location}")
	private String locationTimeSheet;
	
	private By dateBy = By.cssSelector(".breadcrumb-item-link.breadcrumb-heading");
	
	private By tabHeaderBy = By.xpath("//div[@id='fixed-header-container']");
	
	private By tabElementsBy = By.xpath(".//div[contains("
			+ "@class,'time-entry-period day-container no-text-select BusDay')]");
	
	private By submitAllBy = By.xpath("//li[@id='submit-all']");
	
	private By tabDateBy = By.className("time-entry-period-details");
	
	private By tabHourBy = By.className("time-entry-period-summary");
	
	private By newEntryBy = By.xpath(".//div["
			+ "@class='time-entry-period-header-btn new-time-entry-btn hover-btn']");
	
	
	public Boolean fillInPage(WebDriver driver) {
		WebElement getDateFromWebsite = new WebDriverWait(driver, (15)).until(
				driver2 -> driver.findElement(dateBy));

		String startDateOfTimesheet = getDateFromWebsite.getText()
				.split(Pattern.quote("("))[1].split(" ")[0];
		LocalDate currentSheetDate = dateServices
				.formatDate("secondDF", startDateOfTimesheet);
		Boolean isCurrentWeekSheet = dateServices.checkDate(currentSheetDate);
		Boolean noProblemFillingTimeSheet = true;
		
		do {
			pageManipulate.switchFrame(driver, 1);
			String monthAndYear = startDateOfTimesheet.replaceAll("^\\d+", "");
			WebElement websiteTabHeader = new WebDriverWait(driver, 15).until(
					ExpectedConditions.visibilityOfElementLocated(tabHeaderBy));
			List<WebElement> tabElements = websiteTabHeader.findElements(tabElementsBy);

			String checkSubmited;
			try {
				checkSubmited = driver.findElement(submitAllBy).getAttribute("class");
			} catch (NoSuchElementException e2) {
				e2.printStackTrace();
				noProblemFillingTimeSheet = false;
				break;
			}

			for (WebElement tabElement : tabElements) {
				// check date from tab
				WebElement tabDate = tabElement.findElement(tabDateBy);
				String fulltabDate = tabDate.getText().split(" ")[1] + monthAndYear;
				LocalDate tabDateFormated = dateServices
						.formatDate("secondDF", fulltabDate);
				Boolean checktabDateFormated = dateServices.checkDate(tabDateFormated);

				// check if tab has any values for hours
				WebElement tabHourElement = tabElement.findElement(tabHourBy);
				String tabHours = tabHourElement.getText().split("\\Hours")[0];
				boolean checktabHours = this.checkHours(tabHours);

				if (checktabDateFormated && checktabHours && 
						checkSubmited.equals("disabled")) {			
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					WebElement newEntrybtn = tabElement.findElement(newEntryBy);
					
					Actions act = new Actions(driver);
					act.moveToElement(newEntrybtn).click().perform();

					//Fill in each new entry (time-sheet)
					fillInPage.fillInPage(driver);
				}
				System.out.println("<--  TimeSheet Completed -->: ");
			}
			
			String checkSubmited1 = driver.findElement(submitAllBy)
					.getAttribute("class");
			if (checkSubmited1.equals("disabled")) {
				//BREAK LOOP HERE and LOG OUT ---
				break;
			}

			while (!checkSubmited1.equals("disabled")) {
				try {
					Thread.sleep(10000);
					System.out.println("---- Waiting for user to submit Timesheet ---");
					checkSubmited1 = driver.findElement(submitAllBy)
							.getAttribute("class");
					if (checkSubmited1.equals("disabled")) {
						System.out.println("User submitted TimeSheets");
						nextTSPage.nextSheet(driver);
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (UnhandledAlertException e1) {
					e1.printStackTrace();
				}
			}
		} while (isCurrentWeekSheet);
		return noProblemFillingTimeSheet;
	}
	
	private boolean checkHours(String tabHours) {
		if (tabHours.equals("0.00")) {
			return true;
		}
		return false;
	}
}
