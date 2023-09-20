package com.oned;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PageList {
	static {
		
		System.setProperty("webdriver.chrome.driver", "/site-info-extraction/drivers/chromedriver.exe");
	}
	public static List<String> toVisitLinks = new ArrayList<String>();
	public static List<String> visitedLinks = new ArrayList<String>();
	public static List<String> externalLinks = new ArrayList<String>();
	public static String siteURL="https://www.apetito.co.uk/";
	public static ChromeDriver driver = new ChromeDriver();
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		toVisitLinks.add(siteURL);
		while(!toVisitLinks.isEmpty()) {
			
		}
	List<String> siteLinks =getLinksInPage(siteURL);
	System.out.println("Home Page links: "+siteLinks.size()+ " External Links: "+externalLinks.size());
	}
	
	public static List<String> getLinksInPage(String url) {
		List<String> linksInPage = new ArrayList<String>();
		driver.get(url);
		List<WebElement> anchorTagsList=driver.findElements(By.tagName("a"));
		System.out.println(anchorTagsList.size());
		for (WebElement anchorTag : anchorTagsList) {
			String href = anchorTag.getAttribute("href");
			System.out.println(href);
			if(href!=null && (href.startsWith(siteURL)|| href.startsWith("/"))) {
				if(linksInPage.contains(href)) {
					System.out.println("already exists");
					continue;
				}
				linksInPage.add(href);
			} else {
				externalLinks.add("href");
			}
		
		
	}
		return linksInPage;
	}

}
