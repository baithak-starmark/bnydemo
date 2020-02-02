package com.bny.qa.Wikipedia;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestWikiPedia {
	Properties prop;
	String homeUrl;
	WebDriver driver;
	FileInputStream inputStream, inputsearchPageElements;

	DesiredCapabilities capabilities;
	RemoteWebDriver driverRemote;

	@Before
	public void loadPage() {
		prop = new Properties();
		try {
			inputStream = new FileInputStream("src/test/resources/config/init.properties");
			inputsearchPageElements = new FileInputStream("src/test/resources/elements/searchPageElements.properties");

			prop.load(inputStream);
			prop.load(inputsearchPageElements);

			homeUrl = prop.getProperty("url");

			Capabilities caps = new DesiredCapabilities();
			((DesiredCapabilities) caps).setJavascriptEnabled(true);
			((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
			((DesiredCapabilities) caps).setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
					"E:\\learn\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
			
			driver = new PhantomJSDriver(caps);
			driver.navigate().to(homeUrl);
			driver.manage().window().maximize();

		} catch (Exception e) {

		}
	}

	@Test
	public void testPageLoads() {
		System.out.println("Runnning Headless  ..");
		System.out.println("Page title .."+driver.getTitle());
		Assert.assertEquals("Wikipedia".toLowerCase(), driver.getTitle().trim().toLowerCase());
	}

	@Test
	@Ignore
	public void testAllLinksWorking() {
		String urlbyId, searchByName, searchText, searchResultByClass, HeadingById;

		List<WebElement> pageLinks = driver.findElements(By.tagName("a"));
		urlbyId = prop.getProperty("englishUrlById");
		searchByName = prop.getProperty("searchBoxByName");
		searchText = prop.getProperty("searchText");
		searchResultByClass = prop.getProperty("searchResultByClass");
		HeadingById = prop.getProperty("HeadingById");

		driver.findElement(By.id(urlbyId)).click();
		WebElement search = driver.findElement(By.name(searchByName));
		search.sendKeys(searchText);
		search.submit();
		// driver.findElement(By.xpath(searchResultByClass)).click();
		// driver.findElement(By.linkText("Selenium (software)"));
		driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div[3]/ul/li[1]/div[1]/a/span")).click();
		Assert.assertTrue(driver.findElement(By.id(HeadingById)).getText().toLowerCase().startsWith("selenium"));

	}

	@Test
	@Ignore
	public void dockerimp() throws MalformedURLException {

		capabilities = DesiredCapabilities.chrome();
		// capabilities = DesiredCapabilities.firefox();

		driverRemote = new RemoteWebDriver(new URL("http://192.168.0.103:4444/wd/hub"), capabilities);
		driverRemote.get("https://www.wikipedia.org/");
		System.out.println("Got Page title: " + driverRemote.getTitle());

	}

	@After
	public void Shutdown() {
		// driver.close();
	}

}
