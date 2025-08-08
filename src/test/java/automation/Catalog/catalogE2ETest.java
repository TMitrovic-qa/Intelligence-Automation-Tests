package automation.Catalog;

import functions.fnCatalog;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import support.DriverProvider;
import support.Environment;
import support.TestContext;
import support.SupportBase;
import listener.TestListener;

@Feature("Catalog Filter Feature")
@Listeners(TestListener.class)
public class catalogE2ETest {

    private WebDriver driver;
    private fnCatalog catalogFunctions;
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

        driver.get(env.getBaseUrl());
    }

    @Test(description = "Filter for Catalog")
    @Description("Testing Filter implementation for Catalog")
    @Step("Running catalog filter test")
    public void testApplyCatalogFilter() {
        catalogFunctions.applyCatalogFilter(driver, tc);
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
