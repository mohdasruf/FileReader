package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegEnterPage implements Page {

    private final WebDriver driver;

    public RegEnterPage(WebDriver driver){
        this.driver = driver;
    }

    public String getPageHeading () {
        return this.driver.findElement(By.className("heading-large")).getText();
    }

    public String getErrorStringHeading () {
        return this.driver.findElement(By.cssSelector(".heading-medium")).getText();
    }

    public String registrationNumber() {
        System.out.println("registration number: " + this.driver.findElement(By.id("Vrm")).getText());
        return this.driver.findElement(By.id("Vrm")).getText();
    }

    public String getErrorString() {
        return this.driver.findElement(By.id("Vrm-error")).getText();
    }
}

