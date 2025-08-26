package testcases;

import commons.BrowserFactory;
import commons.ExtentReport;
import commons.Screenshot;
import element.SocialSNSElements;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;

public class SocialSNSTest {

    WebDriver driver;
    SocialSNSElements sns;
    String filePath = "Excel/testcases (2).xlsx";

    // ✅ Extent Report
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        BrowserFactory browserFactory = new BrowserFactory();
        browserFactory.launchBrowser("edge"); // or "chrome"
        driver = BrowserFactory.driver;
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        sns = new SocialSNSElements(driver);

        // initialize report
        extent = ExtentReport.getReportInstance();
    }

    @Test
    public void testAllCredentialsFromExcel() throws Exception {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String username = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();

            // ✅ Create report entry per user
            test = extent.createTest("Login Test: " + username);

            try {
                System.out.println("Testing login with: " + username + " / " + password);
                sns.login(username, password);

                if (driver.getCurrentUrl().contains("inventory.html")) {
                    System.out.println("Login successful for: " + username);
                    sns.testInventoryItemsSocialLinks();
                    sns.testCartPageSocialLinks();
                    sns.logout();

                    test.log(Status.PASS, "Login successful and social links verified for user: " + username);
                } else {
                    System.out.println("Login failed for: " + username);
                    test.log(Status.FAIL, "Login failed for user: " + username);

                    // capture screenshot for failed login
                    String screenshotName = Screenshot.captureScreenshot(driver, "login_failed_" + username);
                    test.addScreenCaptureFromPath("screenshots/login_failed_" + username + "_" + screenshotName + ".png");
                }

            } catch (Exception e) {
                test.log(Status.FAIL, "Test failed for user: " + username + " - " + e.getMessage());

                // capture screenshot on exception
                String screenshotName = Screenshot.captureScreenshot(driver, "exception_" + username);
                test.addScreenCaptureFromPath("screenshots/exception_" + username + "_" + screenshotName + ".png");
            }
        }

        workbook.close();
        fis.close();
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotName = Screenshot.captureScreenshot(driver, result.getName());
            try {
                test.addScreenCaptureFromPath("screenshots/" + result.getName() + "_" + screenshotName + ".png");
            } catch (Exception ignored) {}
            test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test passed");
        } else {
            test.log(Status.SKIP, "Test skipped");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // ✅ flush report
        extent.flush();
    }
}
