package tests;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.qameta.allure.Story;
import utils.extentreports.ExtentTestManager;

public class NewTeaser extends BaseTest {
	
	@Parameters({ "keyword", "result" })
	@Test(priority = 0, description = "Search teasers")
	@Story("User can search Teasers")
	public void testSearchTeaser2(String keyword, String result ) throws InterruptedException {
		ExtentTestManager.getTest().setDescription("Test Description: User search teaser and verify results");
		searchTeaser(keyword);
		getAllTeasers();
		Assert.assertEquals(getTeaserFrom(results, 0), result,"Result doesn't match");
	}

}
