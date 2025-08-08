package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverProvider {
    private static DriverProvider instance;
    private WebDriver driver;

    private DriverProvider() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public static DriverProvider getInstance() {
        if (instance == null) {
            instance = new DriverProvider();
        }
        return instance;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
