package com.oned;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RecursiveExtraction {
	static int count = 0;
	static {

		System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
	}

	private static List<String> toVisit = new ArrayList<String>();

	private static List<String> visited = new ArrayList<String>();

	private static List<String> externalLinks = new ArrayList<String>();

	private static final String SITE_URL = "https://holdings.macnica.co.jp/";

	private static final String SITE = "Macnica Holdings";

	public static ChromeDriver driver = new ChromeDriver();

	public static void main(String[] args) throws IOException {

		extractLinks(SITE_URL);
		exportListToExcel();
	}

	private static void extractLinks(String url) {
		System.out.println("Loading page: " + url);

		driver.get(url);

		// fetch all anchor tags present in page
		List<WebElement> anchorTagsList = driver.findElements(By.tagName("a"));
		// System.out.println("Page contains "+anchorTagsList.size()+ " Links");

		// remove current page url from toVisit List and add it to visited List
		toVisit.remove(url);
		visited.add(url);
		System.out.println("URLS size " + anchorTagsList.size());

		
		  toVisit.addAll(anchorTagsList.stream(). filter(Objects::nonNull) .map(tag ->
		  tag.getAttribute("href")) .filter(StringUtils::isNotBlank) 
			.filter(href->!href.contains("#"))
			.filter(href -> !toVisit.contains(href))
			.filter(href -> !visited.contains(href))
			.filter(href ->!href.contains("javascript:void")) 
			.filter(href ->(href.startsWith(SITE_URL)|| href.startsWith("/")))
			.distinct().collect(Collectors.toList()));
		  
  
		  
		 
		System.out.println("Completed:" + visited.size() + " Pending: " + toVisit.size());

		if (!toVisit.isEmpty()) {
			count++;
			if (visited.size() % 300 == 0) {
				try {
					exportListToExcel();
					System.out.println("Exported " + visited.size() + " urls");
				} catch (Exception e) {
					System.err.println("Error in exporting to excel: " + e);
				}

			}
			extractLinks(toVisit.get(0));

		}

	}

	private static void exportListToExcel() throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(SITE + "_internal");
		int rownum = 0;
		for (String href : visited) {
			try {
				Row row = spreadsheet.createRow(rownum++);
				Cell cell = row.createCell(1);
				cell.setCellValue(href);
			} catch (Exception e) {
				System.err.print("Unable to insert cell " + href);
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
		String timestamp = dateFormat.format(new Date());
		FileOutputStream out = new FileOutputStream(new File("target\\" + SITE + "_" + timestamp + ".xlsx"));

		workbook.write(out);
		out.close();
		workbook.close();

	}
}
