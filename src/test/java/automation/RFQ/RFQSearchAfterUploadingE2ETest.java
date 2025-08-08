package automation.RFQ;

import functions.fnCatalog;
import functions.fnRFQ;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import support.DriverProvider;
import support.Environment;
import support.SupportBase;
import support.TestContext;
import listener.TestListener;

@Feature("RGQ Search Already Uploaded Documents")
@Listeners(TestListener.class)
public class RFQSearchAfterUploadingE2ETest {

    private WebDriver driver;
    private fnCatalog catalogFunctions;
    private fnRFQ rfqFunctions;
    private Environment env;
    private TestContext tc;
    private SupportBase supportBase;

    @BeforeClass
    public void setup() {
        env = Environment.getInstance();
        env.setupEnvironment("DEV");

        driver = DriverProvider.getInstance().getDriver();
        driver.manage().window().maximize();

        tc = new TestContext();
        supportBase = new SupportBase();
        catalogFunctions = new fnCatalog(supportBase);
        rfqFunctions = new fnRFQ(supportBase);

        driver.get(env.getBaseUrl());
    }

    @Test(description = "RGQ Search Already Uploaded Documents")
    @Description("RGQ Search Already Uploaded Documents")
    @Step("RGQ Search Already Uploaded Documents")
    public void testRFQSearchAfterUploading() {
        catalogFunctions.applyCatalogFilter(driver, tc);
        rfqFunctions.uploadDocumentForRFQ(driver, tc);
        rfqFunctions.searchAddedProducts(driver, tc);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @SuppressWarnings("unused")
    public WebDriver getDriver() {
        return driver;
    }
}
