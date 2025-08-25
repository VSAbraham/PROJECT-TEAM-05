package testcases;

import commons.ExcelUtils;
import commons.WaitUtils;
import element.CheckOutElements;
import org.openqa.selenium.*;
import org.testng.annotations.*;
import testcases.LoginAndAddToCart;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import commons.ExtentReport;
import commons.ScreenshotUntil;

import java.util.List;
import java.util.Map;

public class CheckOutTest {
    WebDriver driver;
    WaitUtils waitUtils;
    List<Map<String, String>> loginData;
    List<Map<String, String>> checkoutData;
    LoginAndAddToCart loginAndCart;
    
    ExtentReports report;
    ExtentTest test;
    
    

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
    	report = ExtentReport.getReportInstance();
    	test = report.createTest("CheckOut Test");
    	
        loginAndCart = new LoginAndAddToCart();
        loginAndCart.setup(browser);
        driver = loginAndCart.driver;
        waitUtils = new WaitUtils(driver, 1);

        loginData = ExcelUtils.readExcel("Driver/testcases (2).xlsx");
        checkoutData = ExcelUtils.readExcel("Driver/swaglabs_checkoutpage 1.xlsx");
    }

    @Test
    public void testLoginCartAndCheckout() throws Exception {
        for (Map<String, String> login : loginData) {
            String username = login.get("Username");
            String password = login.get("Password");

            try {
                driver.get("https://www.saucedemo.com/");
                waitUtils.safeSendKeys(By.id("user-name"), username);
                waitUtils.safeSendKeys(By.id("password"), password);
                waitUtils.safeClick(By.id("login-button"));

                if (!waitUtils.waitForUrlContains("inventory")) {
                    ScreenshotUntil.takeScreenshot(driver, "LoginError_" + username);
                    System.out.println("[INFO] Login failed for user: " + username);
                    continue;
                }

                System.out.println("[INFO] Login successful for user: " + username);

                // Check cart status
                waitUtils.safeClick(By.className("shopping_cart_link"));
                List<WebElement> cartItems = driver.findElements(By.className("cart_item"));

                if (cartItems.isEmpty()) {
                    System.out.println("[INFO] Cart is empty. Trying to add item for user: " + username);
                    driver.get("https://www.saucedemo.com/inventory.html");
                    loginAndCart.addItemToCart();

                    // Re-check cart
                    driver.get("https://www.saucedemo.com/cart.html");
                    cartItems = driver.findElements(By.className("cart_item"));
                    if (cartItems.isEmpty()) {
                        ScreenshotUntil.takeScreenshot(driver, "EmptyCart_" + username);
                        System.out.println("[WARNING] Cart still empty after adding item. Skipping checkout for user: " + username);
                        loginAndCart.logout();
                        continue;
                    }
                }

                System.out.println("[INFO] Cart has items for user: " + username + ". Proceeding to checkout.");

                for (Map<String, String> checkoutForm : checkoutData) {
                    try {
                       waitUtils.safeClick(By.id("checkout"));

                        String firstName = checkoutForm.getOrDefault("First Name", "");
                        String lastName = checkoutForm.getOrDefault("Last Name", "");
                        String pincode = checkoutForm.getOrDefault("Pincode", "");

                        CheckOutElements checkout = new CheckOutElements(driver);
                        checkout.fillForm(firstName, lastName, pincode);
                        checkout.submitForm();

                        if (checkout.isErrorDisplayed()) {
                            String errorText = checkout.getErrorText();
                            ScreenshotUntil.takeScreenshot(driver, "CheckoutError_" + username + "_" + firstName);
                            System.out.println("[INFO] Expected error displayed: " + errorText);
                        } else {
                            System.out.println("[INFO] Checkout passed for: " + firstName + " " + lastName);
                        }

                        driver.get("https://www.saucedemo.com/cart.html");

                    } catch (Exception e) {
                        ScreenshotUntil.takeScreenshot(driver, "CheckoutException_" + username + "_" + checkoutForm.getOrDefault("First Name", "Unknown"));
                        System.out.println("[EXCEPTION] Checkout failed: " + e.getMessage());
                    }
                }

                loginAndCart.logout();

            } catch (Exception e) {
                ScreenshotUntil.takeScreenshot(driver, "LoginException_" + username);
                System.out.println("[EXCEPTION] Login failed: " + e.getMessage());
            }
        }
    }

    @AfterClass
    public void tearDown() {
    	report.flush();
        loginAndCart.tearDown();
    }
}
