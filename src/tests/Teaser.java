package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import utils.extentreports.ExtentTestManager;

public class Teaser extends BaseTest {

	@Test(priority = 0, description = "Verify Page is in DE")
	@Description("Test Description: Verify Page is in DE")
	@Story("User can search Teasers")
	public void testPageLanguage() throws InterruptedException {
		ExtentTestManager.getTest().setDescription("Test Description: Verify Page is in DE");
		// Webpage is displayed in German
		WebElement rootElement = driver.findElement(By.cssSelector("html"));
		Assert.assertEquals(rootElement.getAttribute("lang"), "de","Language is not DE");
	}

	@Test(priority = 0, description = "Search teasers Scenario 1")
	@Description("Test Description: User search teaser and verify results")
	@Story("User can search Teasers")
	public void testSearchTeaser1() throws InterruptedException {
		ExtentTestManager.getTest().setDescription("Test Description: User search teaser and verify results");

		searchTeaser("Sweet & Easy");

		// Search results page is shown
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(.,\"Suchergebnis\")]")).isDisplayed(),"Search results page not opened");

		getAllTeasers();
		// Headline is shown as stated
		Assert.assertEquals(getTeaserFrom(results, 0), "SWEET & EASY - ENIE BACKT","Result doesn't match");
		// Three search results are shown
		Assert.assertEquals(getNumberOfTeasers(), 3,"Deviation from the expected result , 1 > Results > 1 are shown");
	}

	@Test(priority = 0, description = "Search teasers Scenario 2")
	@Description("Test Description: User search teaser and verify result")
	@Story("User can search Teasers")
	public void testSearchTeaser2() throws InterruptedException {
		ExtentTestManager.getTest().setDescription("Test Description: User search teaser and verify results");
		searchTeaser("Sweet & Easy pikant");
		// One search result is shown
		Assert.assertEquals(getNumberOfTeasers(), 1,"Deviation from the expected result , 1 > Results > 1 are shown");
	}

}
