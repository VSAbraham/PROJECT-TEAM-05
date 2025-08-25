package testcases;

import commons.browser_Factory;
import commons.ExtentReport;
import commons.ScreenshotUntil;
import element.AddtoCart;
import element.loginelements;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginAndAddToCart extends ExtentReport {
    public WebDriver driver;
    loginelements loginPage;
    AddtoCart additems;
    String url;
    ExtentReports report;
    ExtentTest test;

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
    	report = ExtentReport.getReportInstance();
    	test = report.createTest("addtocart Test");
    	
        browser_Factory browserFactory = new browser_Factory();
        browserFactory.setup(browser);
        driver = browserFactory.driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        url = "https://www.saucedemo.com";
        driver.get(url);

        loginPage = PageFactory.initElements(driver, loginelements.class);
        additems = PageFactory.initElements(driver, AddtoCart.class);
    }

    @Test
    public void testLoginAndCartActions() throws Exception {
        String excelFilePath = "Driver/testcases (2).xlsx";
        FileInputStream fis = new FileInputStream(excelFilePath);

        try (Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                String username = row.getCell(0).getStringCellValue();
                String password = row.getCell(1).getStringCellValue();

                System.out.println("[INFO] Testing login for user: " + username);

                driver.get(url);
                loginPage.enterUserName(username);
                loginPage.enterPassword(password);
                loginPage.clickLoginButton();
                Thread.sleep(2000);

                boolean isErrorVisible = driver.findElements(By.className("error-message-container")).size() > 0;
                if (isErrorVisible) {
                	ScreenshotUntil.takeScreenshot(driver, "InvalidLogin_" + username);
                    System.out.println("[ERROR] Login failed for user: " + username);
                    continue;
                }

                System.out.println("[INFO] Login successful for user: " + username);
                performCartActions(username);
                logout();
            }
        }
    }

    public void performCartActions(String username) {
        try {
            List<WebElement> inventoryItems = driver.findElements(By.className("inventory_item"));
            List<String> itemNames = new ArrayList<>();

            for (WebElement item : inventoryItems) {
                try {
                    WebElement nameElement = item.findElement(By.className("inventory_item_name"));
                    WebElement addButton = item.findElement(By.xpath(".//button[text()='Add to cart']"));

                    String itemName = nameElement.getText();
                    addButton.click();
                    Thread.sleep(1000); // Wait for button to update

                    WebElement updatedButton = item.findElement(By.xpath(".//button"));
                    String buttonText = updatedButton.getText();

                    if (buttonText.equalsIgnoreCase("Remove")) {
                        itemNames.add(itemName);
                        System.out.println("[INFO] Added to cart: " + itemName);
                    } else {
                        System.out.println("[WARNING] Item not added for user: " + username + " - " + itemName);
                    }

                } catch (Exception e) {
                	ScreenshotUntil.takeScreenshot(driver, "AddItemError_" + username);
                    System.out.println("[ERROR] Failed to add item for user: " + username);
                }
            }

            for (String itemName : itemNames) {
                try {
                    WebElement itemLink = driver.findElement(By.linkText(itemName));
                    itemLink.click();

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement removeButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Remove')]")));

                    if (removeButton.isDisplayed()) {
                        System.out.println("[INFO] Verified in detail page: " + itemName + " is added to cart.");
                    }

                    driver.findElement(By.id("back-to-products")).click();
                } catch (Exception e) {
                	ScreenshotUntil.takeScreenshot(driver, "VerifyItemError_" + username + "_" + itemName.replace(" ", "_"));
                    System.out.println("[ERROR] Verification failed for " + itemName + ": " + e.getMessage());
                }
            }

            driver.findElement(By.className("shopping_cart_link")).click();

            List<WebElement> removeButtons = driver.findElements(By.cssSelector(".cart_button"));
            for (WebElement remove : removeButtons) {
                try {
                    remove.click();
                    System.out.println("[INFO] Removed item from cart.");
                } catch (Exception e) {
                	ScreenshotUntil.takeScreenshot(driver, "RemoveItemError_" + username);
                    System.out.println("[ERROR] Failed to remove item for user: " + username);
                }
            }

            driver.findElement(By.id("continue-shopping")).click();

        } catch (Exception e) {
        	ScreenshotUntil.takeScreenshot(driver, "CartActionError_" + username);
            System.out.println("[ERROR] Exception during cart actions for user: " + username + " - " + e.getMessage());
        }
    }

    public void addItemToCart() {
        try {
            if (!driver.getCurrentUrl().contains("inventory")) {
                driver.get("https://www.saucedemo.com/inventory.html");
            }

            WebElement firstAddToCartButton = driver.findElement(By.cssSelector(".inventory_item button"));
            firstAddToCartButton.click();
            System.out.println("[INFO] One item added to cart.");
        } catch (Exception e) {
        	ScreenshotUntil.takeScreenshot(driver, "AddSingleItemError");
            System.out.println("[ERROR] Failed to add single item to cart: " + e.getMessage());
        }
    }

    public void logout() {
        try {
            driver.findElement(By.id("react-burger-menu-btn")).click();
            driver.findElement(By.id("logout_sidebar_link")).click();
            driver.findElement(By.id("login-button"));
        } catch (Exception e) {
            System.out.println("[WARNING] Logout failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
        	report.flush();
            driver.quit();
        }
    }
}
