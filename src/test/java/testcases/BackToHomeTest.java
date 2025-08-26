package testcases;

import commons.BrowserFactory;
import commons.ExtentReport;
import commons.Screenshot;
import commons.ExcelUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import element.BackToHomePage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class BackToHomeTest {
    WebDriver driver;
    BackToHomePage homePage;
    ExtentReports extent;
    ExtentTest test;
    BrowserFactory browserFactory;

    @BeforeClass
    public void setUp() {
        browserFactory = new BrowserFactory();  // ✅ create object
        browserFactory.launchBrowser("edge");
        driver = BrowserFactory.driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        homePage = new BackToHomePage(driver);

        extent = ExtentReport.getReportInstance();
        test = extent.createTest("BackToHome Flow Test");
    }

    @Test
    public void testLoginForAllUsersFromExcel() {
        String filePath = "Excel/AcceptedUsers.xlsx";  // 🔧 adjust your path
        List<Map<String, String>> users = ExcelUtils.readExcel(filePath);

        for (Map<String, String> user : users) {
            String username = user.get("Username");
            String password = user.get("Password");

            ExtentTest node = test.createNode("User: " + username + " / " + password);

            try {
                // login
                homePage.login(username, password);
                node.log(Status.INFO, "Attempted login with: " + username);

                // verify login success
                boolean isLoggedIn = homePage.verifyLogin();
                if (isLoggedIn) {
                    node.log(Status.PASS, "Login successful for user: " + username);
                    homePage.logout();
                    node.log(Status.INFO, "Logged out successfully");
                } else {
                    node.log(Status.FAIL, "Login failed for user: " + username);
                }

            } catch (Exception e) {
                node.log(Status.FAIL, "Exception during login for user " + username + ": " + e.getMessage());
            }

            // after each user, go back to login page for next iteration
            driver.get("https://www.saucedemo.com/");
        }
    }

    @AfterMethod
    public void captureResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotName = Screenshot.captureScreenshot(driver, result.getName());
            try {
                test.addScreenCaptureFromPath("screenshots/" + result.getName() + "_" + screenshotName + ".png");
            } catch (Exception ignored) {}
            test.log(Status.FAIL, "Failure Reason: " + result.getThrowable());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
