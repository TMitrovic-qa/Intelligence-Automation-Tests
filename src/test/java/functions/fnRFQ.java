package functions;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import objects.ObjRFQ;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.DualLogger;
import support.Environment;
import support.SupportBase;
import support.TestContext;
import support.JsonRead;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class fnRFQ {

    private final SupportBase supportBase;
    private static final DualLogger logger = DualLogger.getLogger(fnRFQ.class);
    private final Environment env;
    public static String rfqName = "Test" + new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());
    public static String rfqDescription = "Test" + new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());

    public fnRFQ(SupportBase supportBase) {
        this.supportBase = supportBase;
        this.env = Environment.getInstance();
    }

    @SneakyThrows
    @Step("Upload Document for RFQ")
    public void uploadDocumentForRFQ(WebDriver driver, TestContext tc) {
        ObjRFQ.salesMenuItem(driver).click();
        ObjRFQ.RFQ(driver).click();
        logger.infoWithReport("Navigated to RFQ page");

        //Create new RFQ
        ObjRFQ.newRFQ(driver).click();
        ObjRFQ.RFQName(driver).sendKeys(rfqName);
        ObjRFQ.RFQDescription(driver).sendKeys(rfqDescription);
        takeScreenshot(driver, "Create New RFQ");
        ObjRFQ.createButton(driver).click();

        String[] filePaths = {
                System.getProperty("user.dir") + "/src/test/resources/1.23116309-07-2025.pdf",
                System.getProperty("user.dir") + "/src/test/resources/Annual Disposable for Ed Don (1).xlsx",
                System.getProperty("user.dir") + "/src/test/resources/processed-0BE6D0CF-AFC0-45C0-9260-5278F239E402 (1).png"
        };

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'MuiBox-root')]//input[@type='file']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'block';", fileInput);
        fnSendMultipleFilesUpload(filePaths, fileInput);

        Thread.sleep(10000);
        takeScreenshot(driver, "Confirm uploaded documents");

        if (ObjRFQ.RFQItems(driver).isDisplayed()) {
            logger.infoWithReport("RFQ Items are visible.");
        } else {
            logger.errorWithReport("RFQ Items are NOT visible.");
        }

        if (ObjRFQ.suggestedItems(driver).isDisplayed()) {
            logger.infoWithReport("Suggested Items are visible.");
        } else {
            logger.errorWithReport("Suggested Items are NOT visible.");
        }
    }

    public void fnSendMultipleFilesUpload(String[] filePaths, WebElement ele) {
        if (filePaths == null || ele == null) {
            throw new IllegalArgumentException("File paths or element cannot be null.");
        }

        String driverType = JsonRead.returnDriver();
        StringBuilder combinedPaths = new StringBuilder();

        for (String path : filePaths) {
            File file = new File(path);
            if (!file.exists()) throw new RuntimeException("File not found: " + path);
            combinedPaths.append(file.getAbsolutePath()).append("\n");
        }

        if ("REMOTE".equalsIgnoreCase(driverType)) {
            LocalFileDetector detector = new LocalFileDetector();
            ((RemoteWebElement) ele).setFileDetector(detector);
        }

        ele.sendKeys(combinedPaths.toString().trim());
    }

    @Step("Search added products and get trimmed name")
    public String searchAddedProducts(WebDriver driver, TestContext tc) {
        WebElement element = ObjRFQ.firstProductFromTheList(driver);
        String fullText = element.getText().trim();
        int index = fullText.indexOf('(');

        String finalText;
        if (index != -1) {
            finalText = fullText.substring(0, index).trim();
        } else {
            finalText = fullText;
        }

        //Populate search field with previously trimmed value
        WebElement searchField = ObjRFQ.searchField(driver);
        searchField.clear();
        searchField.sendKeys(finalText);

        //Confirm the final result
        String searchFieldValue = searchField.getAttribute("value").trim();

        takeScreenshot(driver, "Search Results");

        if (fullText.equals(searchFieldValue)) {
            System.out.println("Confirmation passed: product text equals search field value.");
        } else {
            System.out.println("Confirmation failed: product text DOES NOT equal search field value.");
        }

        return finalText;

    }
    private void takeScreenshot(WebDriver driver, String name) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        logger.infoWithReport("Screenshot taken: " + name);
    }
}