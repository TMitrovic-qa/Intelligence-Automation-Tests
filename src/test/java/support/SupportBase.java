package support;

import listener.TestListener;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class SupportBase {
    protected final DualLogger logger = DualLogger.getLogger(getClass());

    @Getter
    private final WebDriver driver = DriverProvider.getInstance().getDriver();

    @Getter
    private final Environment env = Environment.getInstance();

    @Getter
    private final SoftAssert softAssert = new SoftAssert();

    protected final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    public void handleSoftFail(TestContext testContext) {
        logger.errorWithReport(testContext.getErrorMessage());
        TestListener.saveScreenshotPNG(getDriver());
        TestListener.saveErrorMessage(testContext.getErrorMessage());
        getSoftAssert().assertEquals(testContext.getErrorMessage(), "OK");
    }

    public void tearDown(String testClass) throws Exception {
        getDriver().quit();
        logger.info("Driver closed in @AfterSuite of " + testClass);
    }
}
