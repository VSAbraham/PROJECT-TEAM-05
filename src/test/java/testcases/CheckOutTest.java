package testcases;

import commons.ExcelUtils;
import commons.WaitUtils;
import element.CheckOutElements;
import org.openqa.selenium.*;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import commons.ExtentReport;
import commons.Screenshot;
import commons.BrowserFactory;

import java.util.List;
import java.util.Map;

public class CheckOutTest {
    WebDriver driver;
    WaitUtils waitUtils;
    List<Map<String, String>> loginData;
    List<Map<String, String>> checkoutData;

    ExtentReports report;
    ExtentTest test; // main test

    @BeforeClass
    public void setup() {
        report = ExtentReport.getReportInstance();
        test = report.createTest("CheckOut Test");

        BrowserFactory browserFactory = new BrowserFactory();
        browserFactory.launchBrowser("edge");
        driver = BrowserFactory.driver;

        driver.manage().window().maximize();
        waitUtils = new WaitUtils(driver, 5);

        loginData = ExcelUtils.readExcel("Excel/testcases (2).xlsx");
        checkoutData = ExcelUtils.readExcel("Excel/swaglabs_checkoutpage 1.xlsx");
    }

    @Test
    public void testLoginCartAndCheckout() throws Exception {
        for (Map<String, String> login : loginData) {
            String username = login.get("Username");
            String password = login.get("Password");

            // 🔹 create a subtest for this specific user
            ExtentTest userTest = test.createNode("Testing user: " + username);

            try {
                driver.get("https://www.saucedemo.com/");
                waitUtils.safeSendKeys(By.id("user-name"), username);
                waitUtils.safeSendKeys(By.id("password"), password);
                waitUtils.safeClick(By.id("login-button"));

                if (!waitUtils.waitForUrlContains("inventory")) {
                    String path = Screenshot.captureScreenshot(driver, "LoginError_" + username);
                    userTest.fail("Login failed for user: " + username + " | Password: " + password)
                            .addScreenCaptureFromPath(path);
                    continue;
                }

                userTest.pass("Login successful for user: " + username);

                CheckOutElements checkout = new CheckOutElements(driver);

                // ✅ Check cart using Page Object
                checkout.openCart();

                if (checkout.isCartEmpty()) {
                    driver.get("https://www.saucedemo.com/inventory.html");
                    checkout.addItemToCart();

                    driver.get("https://www.saucedemo.com/cart.html");
                    if (checkout.isCartEmpty()) {
                        String path = Screenshot.captureScreenshot(driver, "EmptyCart_" + username);
                        userTest.warning("Cart still empty after adding item for user: " + username)
                                .addScreenCaptureFromPath(path);
                        checkout.logout();
                        continue;
                    }
                }

                userTest.info("Cart has items (" + checkout.getCartItemCount() + ") for user: " + username);

                for (Map<String, String> checkoutForm : checkoutData) {
                    String firstName = checkoutForm.getOrDefault("First Name", "");
                    String lastName = checkoutForm.getOrDefault("Last Name", "");
                    String pincode = checkoutForm.getOrDefault("Pincode", "");

                    try {
                        waitUtils.safeClick(By.id("checkout"));

                        checkout.fillForm(firstName, lastName, pincode);
                        checkout.submitForm();

                        if (checkout.isErrorDisplayed()) {
                            String errorText = checkout.getErrorText();
                            String path = Screenshot.captureScreenshot(driver, "CheckoutError_" + username + "_" + firstName);
                            userTest.fail("Checkout error for user: " + username +
                                            " | FirstName: " + firstName +
                                            " | LastName: " + lastName +
                                            " | Pincode: " + pincode +
                                            " | Error: " + errorText)
                                    .addScreenCaptureFromPath(path);
                        } else {
                            userTest.pass("Checkout passed for user: " + username +
                                    " | FirstName: " + firstName +
                                    " | LastName: " + lastName +
                                    " | Pincode: " + pincode);
                        }

                        driver.get("https://www.saucedemo.com/cart.html");

                    } catch (Exception e) {
                        String path = Screenshot.captureScreenshot(driver, "CheckoutException_" + username + "_" + firstName);
                        userTest.fail("Checkout exception for user: " + username +
                                        " | FirstName: " + firstName +
                                        " | LastName: " + lastName +
                                        " | Pincode: " + pincode +
                                        " | Exception: " + e.getMessage())
                                .addScreenCaptureFromPath(path);
                    }
                }

                checkout.logout();
                userTest.info("Logged out for user: " + username);

            } catch (Exception e) {
                String path = Screenshot.captureScreenshot(driver, "LoginException_" + username);
                userTest.fail("Unexpected login failure for user: " + username +
                                " | Password: " + password +
                                " | Exception: " + e.getMessage())
                        .addScreenCaptureFromPath(path);
            }
        }
    }

    @AfterClass
    public void tearDown() {
        report.flush();
        if (driver != null) {
            driver.quit();
        }
    }
}
