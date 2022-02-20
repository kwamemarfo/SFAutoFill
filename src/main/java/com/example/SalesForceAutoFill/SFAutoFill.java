package com.example.SalesForceAutoFill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.SalesForceAutoFill.Service.Service;

@Component
public class SFAutoFill {
	
	@Autowired
	private Service service;
	
	public void sfAutoFill() {

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
