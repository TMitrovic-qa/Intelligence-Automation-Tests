package functions;

import objects.ObjCatalogFilter;
import support.Environment;
import support.SupportBase;
import support.TestContext;
import support.DualLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.util.Set;

public class fnCatalog {

    private final SupportBase supportBase;
    private static final DualLogger logger = DualLogger.getLogger(fnCatalog.class);
    private final Environment env;

    public fnCatalog(SupportBase supportBase) {
        this.supportBase = supportBase;
        this.env = Environment.getInstance();
    }

    @SneakyThrows
    @Step("Apply catalog filter for user: {username}")
    public void applyCatalogFilter(WebDriver driver, TestContext tc) {
        String username = env.getUsername();
        String password = env.getPassword();

        String parentWindow = driver.getWindowHandle();

        ObjCatalogFilter.signIn(driver).click();
        Thread.sleep(5000);

        takeScreenshot(driver, "After_SignIn_Click_Login_Modal");

        Set<String> allWindows = driver.getWindowHandles();

        int retries = 0;
        while (allWindows.size() == 1 && retries < 10) {
            Thread.sleep(500);
            allWindows = driver.getWindowHandles();
            retries++;
        }

        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        Thread.sleep(6000);

        ObjCatalogFilter.userName(driver).sendKeys(username);
        ObjCatalogFilter.password(driver).sendKeys(password);

        Thread.sleep(2000);
        ObjCatalogFilter.logInButton(driver).click();

        driver.switchTo().window(parentWindow);

        Thread.sleep(3000);

        takeScreenshot(driver, "After_Login_Navigate_To_Catalog");

        ObjCatalogFilter.catalogOption(driver).click();

        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("catalog?view=list")) {
            tc.setComment("URL contains 'catalog?view=list': " + currentUrl);
            logger.infoWithReport(tc.getComment());
        } else {
            tc.setComment("URL does not contain 'catalog?view=list'. Current URL: " + currentUrl);
            logger.errorWithReport(tc.getComment());
            supportBase.handleSoftFail(tc);
        }
    }

    private void takeScreenshot(WebDriver driver, String name) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        logger.infoWithReport("Screenshot taken: " + name);
    }
}
