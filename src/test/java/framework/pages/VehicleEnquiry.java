package framework.pages;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VehicleEnquiry {

    private WebDriver driver;
    private static String WEBSITEURL = "https://www.gov.uk/get-vehicle-information-from-dvla";


    public VehicleEnquiry() {
        this.driver = new FirefoxDriver();
        this.driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    }

    public Page enterRegNo(String registrationNumber) throws Exception {
        WebDriverWait wait = doInitialActions(registrationNumber);

        WebElement newPageElement = this.driver.findElement(By.className("back"));
        wait.until(ExpectedConditions.visibilityOf(newPageElement));
        return new VehicleCheckPage(driver);
    }

    public Page enterInvalidFormatRegNo(String registrationNumber) throws Exception {
        WebDriverWait wait = doInitialActions(registrationNumber);

        WebElement errorElement = this.driver.findElement(By.id("Vrm-error"));
        wait.until(ExpectedConditions.visibilityOf(errorElement));
        return new RegEnterPage(driver);

    }

    public String[] checkDetails(String registrationNumber) throws Exception {
        WebDriverWait wait = doInitialActions(registrationNumber);

        WebElement newPageElement = this.driver.findElement(By.className("back"));
        wait.until(ExpectedConditions.visibilityOf(newPageElement));
        VehicleCheckPage vehicleCheckPage = new VehicleCheckPage(driver);
        return vehicleCheckPage.getVehicleInfo();
    }

    public ErrorPage incorrectRegNo(String registrationNumber) throws Exception {

        WebDriverWait wait = doInitialActions(registrationNumber);

        WebElement newPgElement = this.driver.findElement(By.xpath("//div/p/strong"));
        wait.until(ExpectedConditions.visibilityOf(newPgElement));
        WebElement newPageElement = this.driver.findElement(By.className("heading-large"));
        wait.until(ExpectedConditions.visibilityOf(newPageElement));
        ErrorPage errorPage = new ErrorPage(driver);
        return errorPage;
    }

    public void quit() {
        this.driver.quit();
    }

    private WebDriverWait doInitialActions(String registrationNumber) {
        this.driver.get(WEBSITEURL);
        this.driver.findElement(By.cssSelector("a.gem-c-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 200);
        WebElement regPageTitleElement = this.driver.findElement(By.className("heading-large"));
        wait.until(ExpectedConditions.visibilityOf(regPageTitleElement));
        this.driver.findElement(By.id("Vrm")).sendKeys(registrationNumber);
        this.driver.findElement(By.name("Continue")).submit();
        return wait;
    }

    public void takeScreenshot() {
        if (driver instanceof TakesScreenshot) {
            String path = "./target/screenshots/";
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
                FileUtils.copyFile(scrFile, new File(path + "file " + timestamp + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
