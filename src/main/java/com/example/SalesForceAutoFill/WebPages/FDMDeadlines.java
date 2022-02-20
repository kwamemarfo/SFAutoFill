package com.example.SalesForceAutoFill.WebPages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FDMDeadlines {
	
	public void getDeadline() {
		List<String> rawDates = this.deadlineDates();
		List<Date> formatedDates = this.createDates(rawDates);
		
		ZoneId z = ZoneId.of("Europe/London");
		Instant currentDate = ZonedDateTime.now(z).toInstant();
		Date deadlineDate = formatedDates.get(0);
		
		for (Date date : formatedDates) {
			if (currentDate.compareTo(date.toInstant()) <= 0) {
				deadlineDate = date;
				break;
			}
		}
		
		if (deadlineDate.compareTo(formatedDates.get(0)) == 0) {
			System.out.println("\n\nNo deadline established for submission!!! Default deadline below ");
		}
		
		System.out.println("\n\n" + "Next Deadline Date: " + deadlineDate);
	}
	
	public List<String> deadlineDates() {
		List<String> fdmDeadlineDates = new ArrayList<>();
		
		fdmDeadlineDates.add("09:00 Monday 31 January 2022");
		fdmDeadlineDates.add("09:00 Monday 28 February 2022");
		fdmDeadlineDates.add("09:00 Monday 4 April 2022");
		fdmDeadlineDates.add("09:00 Monday 2 May 2022");
		fdmDeadlineDates.add("09:00 Monday 30 May 2022");
		fdmDeadlineDates.add("09:00 Monday 4 July 2022 ");
		fdmDeadlineDates.add("09:00 Monday 1 August 2022");
		fdmDeadlineDates.add("09:00 Monday 5 September 2022");
		fdmDeadlineDates.add("09:00 Monday 3 October 2022");
		fdmDeadlineDates.add("09:00 Monday 31 October 2022");
		fdmDeadlineDates.add("09:00 Monday 28 November 2022");
		return fdmDeadlineDates;
	}
	
	private List<Date> createDates(List<String> rawDates) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:m E d MMM yyyy");
		List<Date> transformDeadlineDates = new ArrayList<>();
		for (String dates : rawDates) {
	        try {
	        	transformDeadlineDates.add(simpleDateFormat.parse(dates));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		}
		return transformDeadlineDates;
	}

}
