package commons;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected BrowserFactory browserFactory;
    protected WebDriver driver;
    protected String currentUser;

    private static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> testLog = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void setupReport() {
        extent = ExtentReport.getReportInstance();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"username", "password"})
    public void setUp(String username, String password) {
        browserFactory = new BrowserFactory();
        browserFactory.launchBrowser("edge");
        driver = browserFactory.driver;
        currentUser = username;

        driver.get("https://www.saucedemo.com/");

        // Create a new test log in the report
        ExtentTest test = extent.createTest(
                getClass().getSimpleName() + " :: " + username
        );
        testLog.set(test);
    }

    @AfterMethod(alwaysRun = true)
    public void logResult(ITestResult result) {
        ExtentTest test = testLog.get();

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "✅ " + result.getMethod().getMethodName() + " passed.");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "❌ " + result.getMethod().getMethodName() + " failed.");
            test.log(Status.FAIL, result.getThrowable());

            // Capture and attach screenshot
            String screenshotPath = captureScreenshot(
                    driver,
                    result.getMethod().getMethodName() + "_user_" + currentUser
            );

            if (screenshotPath != null) {
                try {
                    test.addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                    test.log(Status.WARNING, "⚠ Could not attach screenshot: " + e.getMessage());
                }
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "⏩ " + result.getMethod().getMethodName() + " skipped.");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Reuse Screenshot logic here
    private String captureScreenshot(WebDriver driver, String fileNamePrefix) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = fileNamePrefix + "_" + timestamp + ".png";

            File destFile = new File("screenshots/" + fileName);
            Files.createDirectories(destFile.getParentFile().toPath());
            Files.copy(srcFile.toPath(), destFile.toPath());

            return destFile.getAbsolutePath();
        } catch (Exception e) {
            System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}
