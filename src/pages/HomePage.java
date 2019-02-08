package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    //*********Constructor*********
    public HomePage (WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //*********Page Variables*********
    String baseURL = "https://smart-sme:DekWTRxlNF20LXgWiJpCKo4Dyf8yZc83@api-test.allianz.com/sme-fe-staging/?userType=agent&applicationId=sme-dev";
//    String baseURL = "http://localhost:4200/?userType=broker#/agentBrokerPage";

    //*********Page Methods*********

    //Go to Homepage
    @Step("Open Home Page ...")
    public void goToHomePage () throws InterruptedException{
        driver.get(baseURL);
        Thread.sleep(8000);
        driver.findElement(By.id("username")).sendKeys("M7787");
        driver.findElement(By.id("password")).sendKeys("12121212");
        driver.findElement(By.id("kc-login")).click();
        Thread.sleep(3000);
    }

}
