package com.oned;

import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriver;

public class ExtractPageTitles {
	
	
	static {

		System.setProperty("webdriver.chrome.driver",
				"drivers\\chromedriver.exe");
	}


	public static ChromeDriver driver = new ChromeDriver();
	
	public static void main(String[] args) throws IOException {
	
		//step1: load excel
		//step2: get URLs
		//step3: iterate through URLs
		//step4: load each URL using web driver
		//step5: get title from page that is loaded
		//step6: Create page title model
		//step7: Export page title model to list
		
		
	
	}
	
	private class pageTitleModel{
		private String pageURL;
		private String pageTitle;
	}

}
