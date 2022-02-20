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
	

	public boolean checkHours(String tabHours) {
		if (tabHours.equals("0.00")) {
			return true;
		}
		return false;
	}

	public Boolean fillInPage(WebDriver driver) {
		WebElement getDateFromWebsite = new WebDriverWait(driver, (15)).until(
				driver2 -> driver.findElement(By.cssSelector(".breadcrumb-item-link.breadcrumb-heading")));

		String startDateOfTimesheet = getDateFromWebsite.getText().split(Pattern.quote("("))[1].split(" ")[0];
		LocalDate currentSheetDate = dateServices.formatDate("secondDF", startDateOfTimesheet);
		Boolean isCurrentWeekSheet = dateServices.checkDate(currentSheetDate);
		Boolean noProblemFillingTimeSheet = true;
		
		do {
			pageManipulate.switchFrame(driver, 1);
			String monthAndYear = startDateOfTimesheet.replaceAll("^\\d+", "");
			WebElement websiteTabHeader = new WebDriverWait(driver, 15).until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fixed-header-container']")));

			List<WebElement> tabElements = websiteTabHeader.findElements(
					By.xpath(".//div[contains(@class,'time-entry-period day-container no-text-select BusDay')]"));

			String checkSubmited;
			try {
				checkSubmited = driver.findElement(By.xpath("//li[@id='submit-all']")).getAttribute("class");
			} catch (NoSuchElementException e2) {
				e2.printStackTrace();
				noProblemFillingTimeSheet = false;
				break;
			}

			for (WebElement tabElement : tabElements) {
				// check date from tab
				WebElement tabDate = tabElement.findElement(By.className("time-entry-period-details"));
				String fulltabDate = tabDate.getText().split(" ")[1] + monthAndYear;
				LocalDate tabDateFormated = dateServices.formatDate("secondDF", fulltabDate);
				Boolean checktabDateFormated = dateServices.checkDate(tabDateFormated);

				// check if tab has any values for hours
				WebElement tabHourElement = tabElement.findElement(By.className("time-entry-period-summary"));
				String tabHours = tabHourElement.getText().split("\\Hours")[0];
				boolean checktabHours = this.checkHours(tabHours);

				if (checktabDateFormated && checktabHours && checkSubmited.equals("disabled")) {

					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					WebElement element = tabElement.findElement(
							By.xpath(".//div[@class='time-entry-period-header-btn new-time-entry-btn hover-btn']"));

					Actions act = new Actions(driver);
					act.moveToElement(element).click().perform();

					fillInPage.fillInPage(driver);
				}
				System.out.println("<--  TimeSheet Completed -->: ");
			}

			String checkSubmited1 = driver.findElement(By.xpath("//li[@id='submit-all']")).getAttribute("class");

			if (checkSubmited1.equals("disabled")) {
				//BREAK LOOP HERE and LOG OUT, BREAKING LOOP ---
				break;
			}

			// Page is reloading, checkSubmited to see what is happening
			while (!checkSubmited1.equals("disabled")) {
				try {
					Thread.sleep(10000);
					System.out.println("---- Waiting for user to submit Timesheet ----------");
					checkSubmited1 = driver.findElement(By.xpath("//li[@id='submit-all']")).getAttribute("class");
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
}
