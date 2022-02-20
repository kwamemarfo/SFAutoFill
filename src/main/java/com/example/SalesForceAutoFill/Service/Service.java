package com.example.SalesForceAutoFill.Service;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.example.SalesForceAutoFill.WebDriver.SessionWebDriver;
import com.example.SalesForceAutoFill.WebPages.CalendarPage;
import com.example.SalesForceAutoFill.WebPages.FDMDeadlines;
import com.example.SalesForceAutoFill.WebPages.HomePage;
import com.example.SalesForceAutoFill.WebPages.LoginPage;
import com.example.SalesForceAutoFill.WebPages.LogoutPage;
import com.example.SalesForceAutoFill.WebPages.OpenPage;
import com.example.SalesForceAutoFill.WebPages.PageManipulate;
import com.example.SalesForceAutoFill.WebPages.TimeSheetPage;

@Configuration
@PropertySource("classpath:user-settings.properties")
@Component
public class Service{

	@Autowired
	private LoginPage loginpage;

	@Autowired
	private SessionWebDriver webDriver;

	@Autowired
	private HomePage homepage;

	@Autowired
	private CalendarPage calendarViewSelect;

	@Autowired
	private TimeSheetPage timeSheetPage;

	@Autowired
	private PageManipulate pageManipulate;
	
	@Autowired
	private LogoutPage logoutPage;
	
	@Autowired
	private FDMDeadlines fdmDeadlines;

	private WebDriver driver;

	public WebDriver getPage() {
		driver = webDriver.getPage();
		return driver;
	}

	public OpenPage login() {
		return loginpage.login(driver);
	}

	public OpenPage homePageCalenderSelect() {
		return homepage.calendarSelect(driver);
	}

	public OpenPage selectCalendarView() {
		return calendarViewSelect.selectCalendarView(driver);
	}

	public Boolean fillInTimeSheetPage() {
		return timeSheetPage.fillInPage(driver);	
	}

	public void switchFrame(int frameId) {
		pageManipulate.switchFrame(driver, frameId);
	}

	public OpenPage logout(Boolean wasTimeSheetFilledIn) {
		return logoutPage.logout(driver, wasTimeSheetFilledIn);	
	}

	public void closeAll() {
		driver.quit();	
	}

	public void FDMDeadlines() {
		fdmDeadlines.getDeadline();	
	}
}
