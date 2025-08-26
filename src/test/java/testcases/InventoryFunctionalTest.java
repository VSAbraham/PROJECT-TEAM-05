package Testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import Common.Browser_factory;
import Common.report;
import Elements.inventory_page;
import Elements.login_page;
import java.time.Duration;
import java.util.List;

public class InventoryFunctionalTest {

    WebDriver driver;
    WebDriverWait wait;
    login_page loginPage;
    inventory_page inventoryPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        extent = report.getReportInstance();
        Browser_factory browserFactory = new Browser_factory();
        driver = browserFactory.launchBrowser("edge");

        // Set implicit wait for general element loading
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new login_page(driver);
        inventoryPage = new inventory_page(driver);

        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOf(loginPage.userName));
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        System.out.println("Login successful and inventory page loaded.");
    }

    @Test
    public void verifyProductNavigation() {
        test = extent.createTest("Verify Product Navigation");

        List<WebElement> productLinks = inventoryPage.getItemNames();

        if (productLinks.size() != 6) {
            test.fail("Expected 6 product links, but found: " + productLinks.size());
            return;
        }

        for (int i = 0; i < productLinks.size(); i++) {
            WebElement product = inventoryPage.getItemNames().get(i);
            String productName = product.getText();

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", product);
            wait.until(ExpectedConditions.visibilityOf(product));
            wait.until(ExpectedConditions.elementToBeClickable(product));

            // Add a short delay using a temporary wait
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOf(product));

            inventoryPage.clickItemByIndex(i);
            wait.until(ExpectedConditions.urlContains("inventory-item.html"));

            if (driver.getCurrentUrl().contains("inventory-item.html")) {
                test.pass("Clicked on product '" + productName + "' and navigated to detail page.");
                System.out.println("Test Passed: Product '" + productName + "' navigation successful.");
            } else {
                test.fail("Failed to navigate to detail page for product: " + productName);
            }

            driver.navigate().back();
            wait.until(ExpectedConditions.urlContains("inventory.html"));

            // Add a short delay before next iteration
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlContains("inventory.html"));
        }
    }

    


    @AfterClass
    public void tearDown() {
        extent.flush();
        if (driver != null) {
            driver.quit();
        }
        System.out.println("Test execution completed. Browser closed.");
    }
}
