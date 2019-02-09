package tests;

import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import utils.extentreports.ExtentTestManager;

public class Registeration extends BaseTest {

	@BeforeClass // The annotated method will be run before all tests in this suite have run
	public void setup() throws InterruptedException {
		driver.get("https://www.7tv.de/mein-7tv");
	}

	public static String getRandom(String[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	private void setEmailForRegistration() throws InterruptedException {
		Random random = new Random();
		driver.findElement(By.id("email"))
				.sendKeys(getRandom(names) + random.nextInt(900) + "@" + getRandom(domain) + ".com");
		// Adding sleep to wait for the response from checkEmail api
		Thread.sleep(1500);

	}

	private void selectDateOfBirth(String date) {
		String[] out = date.split("\\.");
		Select day = new Select(driver.findElement(By.name("birthdate.day")));
		Select month = new Select(driver.findElement(By.name("birthdate.month")));
		Select year = new Select(driver.findElement(By.name("birthdate.year")));

		day.selectByValue(out[0]);
		month.selectByVisibleText(out[1]);
		year.selectByVisibleText(out[2]);
	}

	public void retryEmail() throws InterruptedException {

		int i = 0;
		while (i < 10) {
			WebElement error = null;
			Random random = new Random();
			try {
				error = driver.findElement(By.cssSelector(".error.ng-binding.ng-scope"));
			} catch (NoSuchElementException ex) {
				break; // error is missing, exit the loop
			}
			if (error.isDisplayed() == true) {
				WebElement email = driver.findElement(By.id("email"));
				email.clear();
				Thread.sleep(2000);
				email.sendKeys(getRandom(names) + random.nextInt(9000) + "@gm");
				Thread.sleep(3000);
				WebElement suggestion = driver.findElement(By.cssSelector(".inline-guidline>a"));
				wait.until(ExpectedConditions.visibilityOf(suggestion));
				suggestion.click();
				i++;
				Thread.sleep(1000);
			}
		}

	}

	@Test(priority = 0, description = "Verify Modal Title")
	@Description("Test Description: Verify Modal Title")
	@Story("User can see login/registration modal")
	public void testModalTitle() throws InterruptedException {
		ExtentTestManager.getTest().setDescription("Test Description: Verify Modal Title");
		driver.findElement(By.cssSelector(".site-header_mytv")).click();
		WebElement modalTitle = driver.findElements(By.cssSelector(".modal-header-title")).get(1);
		wait.until(ExpectedConditions.visibilityOf(modalTitle));
		Assert.assertEquals(modalTitle.getText(), "LOGIN ODER KOSTENLOS REGISTRIEREN");
	}

	@Test(priority = 1, description = "Verify New window opens")
	@Description("Test Description: Verify New window opens")
	@Story("User can Open registration")
	public void testRegisterWindow() throws InterruptedException {
		ExtentTestManager.getTest().setDescription("Test Description: Verify Modal Title");
		driver.findElements(By.xpath("//a[text()=\"Kostenlos registrieren\"]")).get(1).click();
		Thread.sleep(3000);
		Set<String> winids = driver.getWindowHandles();
		Assert.assertEquals(winids.size(), 2, "New Window is not opened ");
	}

	@Test(priority = 2, description = "All fields are filled correctly, no hard errors are shown")
	@Description("Test Description: All fields are filled correctly, no hard errors are shown")
	@Story("User can fill registeration form")
	public void testRegistrationForm() throws InterruptedException {
		ExtentTestManager.getTest()
				.setDescription("Test Description: All fields are filled correctly, no hard errors are shown");

		// Move to new opened window
		String mainwindow = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}

		setEmailForRegistration();

		// Enter Password
		driver.findElement(By.id("pwd")).sendKeys("TestingIsFun123");

		// Click on Frau radio button
		driver.findElement(By.xpath("//input[@id=\"femaleRadio\"]/..")).click();

		selectDateOfBirth("12.Januar.1980");

		retryEmail();

		WebDriverWait waitforNoErrors = new WebDriverWait(driver, 10);
		waitforNoErrors.withMessage(
				"No unique email id could be generated , Alternative: Clear DB with testing registered emails and then run these tests");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".error.ng-binding.ng-scope")));

		// Click On Register
		driver.findElement(By.id("submitReg")).click();

		// Switch to Main Window
		driver.switchTo().window(mainwindow);
		WebDriverWait waitForToastMessage = new WebDriverWait(driver, 10);
		// Check if Toast message is visible
		waitForToastMessage.withMessage("Toast Message is not shown");
		waitForToastMessage
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".notification-content")));
		waitForToastMessage.withMessage("Toast Message is still shown");
		waitForToastMessage
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".notification-content")));

		// Assertions if logout is visible
		Assert.assertTrue(driver.findElement(By.className("button-logout")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.className("button-profile")).isDisplayed());
	}

	@Test(priority = 3, description = "Verify that logout was successful.")
	@Description("Test Description: Verify that logout was successful.")
	@Story("User can fill registeration form")
	public void testLogout() throws InterruptedException {
		driver.findElement(By.className("button-logout")).click();
		Assert.assertFalse(driver.findElement(By.className("button-logout")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.className("button-profile")).isDisplayed());

	}

}
