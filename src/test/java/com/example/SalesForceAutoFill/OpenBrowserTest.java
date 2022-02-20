package com.example.SalesForceAutoFill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.SalesForceAutoFill.Service.Service;


@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class OpenBrowserTest {
	
	@Autowired
	private Service service;
	
	
	@Test
	public void test_ThatCompletionOfTimeSheetsFromSalesForceCanBeAutomated() {
		//Later, Define methods for OpenPage to conduct tests
		
		service.getPage();

		service.login();
		
		service.homePageCalenderSelect();
		
		service.selectCalendarView();
		
		service.switchFrame(1);
		
		Boolean wasTimeSheetFilledIn = service.fillInTimeSheetPage();
		
		service.logout(wasTimeSheetFilledIn);
		
		service.closeAll();
		
		service.FDMDeadlines();
	}
}
