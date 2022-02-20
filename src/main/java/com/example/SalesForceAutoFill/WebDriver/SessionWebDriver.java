package com.example.SalesForceAutoFill.WebDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

import io.github.bonigarcia.wdm.WebDriverManager;

@Component
public class SessionWebDriver {

	private WebDriver driver;
	
	public WebDriver getDriver() {
		try {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} catch (Exception e1) {
				e1.printStackTrace();
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
		}
		return driver;
	}
}
