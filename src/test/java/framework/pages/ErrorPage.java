package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorPage implements Page {

    private final WebDriver driver;

    public ErrorPage(WebDriver driver){
        this.driver = driver;
    }

    public String getPageHeading () {
        return this.driver.findElement(By.className("heading-large")).getText();
    }

    public String getErrorContactInfoString() {
        return this.driver.findElement(By.xpath("//div/p/strong")).getText();
    }

}
