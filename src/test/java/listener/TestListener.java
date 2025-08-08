package listener;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = getDriverFromResult(result);
        if (driver != null) {
            saveScreenshotPNG(driver);
            savePageUrl(driver.getCurrentUrl());
        }
    }

    private WebDriver getDriverFromResult(ITestResult result) {
        Object testClass = result.getInstance();
        try {
            return (WebDriver) testClass.getClass().getMethod("getDriver").invoke(testClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page URL", type = "text/plain")
    public static String savePageUrl(String url) {
        return url;
    }

    @Attachment(value = "Error message", type = "text/plain")
    public static String saveErrorMessage(String message) {
        return message;
    }
}
