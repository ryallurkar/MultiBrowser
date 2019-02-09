package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.listeners.TestListener;

@Listeners({ TestListener.class })
public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;
	ArrayList<String> results = new ArrayList<>();
	static String domain[] = { "gmail", "outlook" };
	static String names[] = { "mark", "tom", "jerry", "tim", "Ron", "alex", "alberto", "beckie" };

	public WebDriver getDriver() {
		return driver;
	}

	@Parameters("browser") // this annotation is used to insert browser parameter from TestNG xml
	@BeforeClass // The annotated method will be run before all tests in this suite have run
	public void setup(@Optional("chrome") String browser) throws InterruptedException {

		System.setProperty("wdm.gitHubTokenName", "firefox_binary");
		System.setProperty("wdm.gitHubTokenSecret", "f2b0fed32144ea8feb5cd8cc7ed44f56d52c692f");
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();

		if (browser.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome"))

		{
			driver = new ChromeDriver();
		} else {
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		// Create a wait. All test classes use this.
		wait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
		driver.get("https://www.7tv.de");
		clickCookieButton();
	}

	public void clickCookieButton() throws InterruptedException {
		WebElement cookieButton = driver.findElement(By.xpath("//a[@id=\"btn_cookie\" and @class=\"button-modal\"]"));
		try {
			if (cookieButton.isDisplayed()) {
				cookieButton.click();

			}
		} catch (Exception e) {
			System.out.println("In Exception");
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", cookieButton);
		}

	}

	public void getAllTeasers() {
		List<WebElement> teasers = driver.findElements(By.cssSelector(".teaser-formatname"));
		for (WebElement element : teasers) {
			results.add(element.getText());
		}
	}

	public int getNumberOfTeasers() {
		return driver.findElements(By.cssSelector(".teaser-formatname")).size();
	}

	public String getTeaserFrom(ArrayList<String> results, int index) {
		return results.get(index);
	}

	public void searchTeaser(String teaserName) {
		driver.findElement(By.cssSelector(".site-search_input")).clear();
		driver.findElement(By.cssSelector(".site-search_input")).sendKeys(teaserName);
		driver.findElement(By.cssSelector(".site-search_submit.svg-lupe")).click();
	}

	@AfterClass(description = "Class Level Teardown!")
	public void teardown() {
		driver.quit();
	}

}
