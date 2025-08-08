package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ObjCatalogFilter {

    public static WebElement waitForElement(WebDriver driver, By locator) {
        int resultCount = 0;
        int loop = 0;

        while (resultCount == 0 && loop <= 10) {
            resultCount = driver.findElements(locator).size();
            if (resultCount > 0) {
                return driver.findElement(locator);
            }
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            loop++;
        }

        throw new RuntimeException("Element not found after waiting: " + locator.toString());
    }

    public static WebElement signIn(WebDriver driver) {
        return waitForElement(driver, By.xpath("//button[.//span[normalize-space()='Sign In']]"));
    }

    public static WebElement userName(WebDriver driver) {
        return waitForElement(driver, By.xpath("//input[@id='1-email']"));
    }

    public static WebElement password(WebDriver driver) {
        return waitForElement(driver, By.xpath("//input[@name='password']"));
    }

    public static WebElement logInButton(WebDriver driver) {
        return waitForElement(driver, By.xpath("//button[@type='submit' and contains(., 'Log In')]"));
    }

    public static WebElement catalogOption(WebDriver driver) {
        return waitForElement(driver, By.xpath("//p[normalize-space()='Catalog']"));
    }
}
