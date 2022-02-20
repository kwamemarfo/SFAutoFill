package com.example.SalesForceAutoFill.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateServices {

	public LocalDate formatDate(String dateformat, String date) {
		if (dateformat.equals("firstDF")) {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-M-d");
			LocalDate formatedDate = LocalDate.parse(date, df);
			return formatedDate;
		} else if (dateformat.equals("secondDF")) {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");
			LocalDate formatedDate = LocalDate.parse(date, df);
			return formatedDate;
		}
		System.out.println("Problems with Service.formatDate -- use trace to log");
		return null;
	}

	public boolean checkDate(LocalDate timeSheetDate) {
		ZoneId z = ZoneId.of("Europe/London");
		LocalDate currentTime = LocalDate.now(z);
		if (timeSheetDate.isBefore(currentTime)) {
			return true;
		} else if (timeSheetDate.equals(currentTime)) {
			LocalDateTime currentTime2 = LocalDateTime.now(z);
			if (currentTime2.getHour() > 17) {
				return true;
			} else if (currentTime2.getHour() == 17 && currentTime2.getMinute() >= 30) {
				return true;
			}
		}
		return false;
	}
}
