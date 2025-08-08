package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ObjRFQ {

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

    public static WebElement salesMenuItem(WebDriver driver) {
        return waitForElement(driver, By.xpath("//p[normalize-space()='Sales']"));
    }
    public static WebElement RFQ(WebDriver driver) {
        return waitForElement(driver, By.xpath("//div[contains(@class, 'MuiButtonBase-root') and .//p[text()='RFQ']]"));
    }

    public static WebElement newRFQ(WebDriver driver) {
        return waitForElement(driver, By.xpath("//button[contains(@class, 'MuiButton-containedPrimary') and .//span[text()='New']]"));
    }

    public static WebElement RFQName(WebDriver driver) {
        return waitForElement(driver, By.xpath("//input[@placeholder='Enter RFQ Name...']"));
    }
    public static WebElement RFQDescription(WebDriver driver) {
        return waitForElement(driver, By.xpath("//textarea[@placeholder='Enter Description...']"));
    }
    public static WebElement createButton(WebDriver driver) {
        return waitForElement(driver, By.xpath("//button[.//span[normalize-space(text())='Create']]"));
    }
    public static WebElement RFQItems(WebDriver driver) {
        return waitForElement(driver, By.xpath("//p[starts-with(normalize-space(text()), 'RFQ')]"));
    }
    public static WebElement suggestedItems(WebDriver driver) {
        return waitForElement(driver, By.xpath("//p[starts-with(normalize-space(text()), 'Suggested Items')]"));
    }
    public static WebElement firstProductFromTheList(WebDriver driver) {
        return waitForElement(driver, By.xpath("(//div[contains(@class, 'MuiGrid-root') and contains(@class, 'MuiGrid-item')]//div[contains(@class, 'MuiBox-root')])[1]"));
    }
    public static WebElement searchField(WebDriver driver) {
        return waitForElement(driver, By.xpath("//input[@placeholder='Search...' and @type='text']"));
    }

}
