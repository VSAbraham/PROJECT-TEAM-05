package Testcase;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import commons.BrowserFactory;
import commons.Screenshot;
import commons.ExtentReport;
import element.LoginElement;

public class LoginTest {

    WebDriver driver;
    LoginElement loginPage;
    String url;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void init() {
        extent = ExtentReport.getReportInstance();

        // Launch browser using BrowserFactory
        BrowserFactory browserFactory = new BrowserFactory();
        browserFactory.launchBrowser("edge");

        // ✅ Assign the driver from BrowserFactory
        driver = BrowserFactory.driver;

        // ✅ Now initialize your page object with the driver
        loginPage = new LoginElement(driver);

        // Navigate to the app before getting URL (driver.getCurrentUrl() is invalid before navigation)
        driver.get("https://www.saucedemo.com/");
        url = driver.getCurrentUrl();
    }


    @Test

    public void testLogin() throws IOException, InterruptedException {
        String excelFilePath = "Excel/testcases (2).xlsx";
        FileInputStream fis = new FileInputStream(excelFilePath);
        try (Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;
                String username = row.getCell(0).getStringCellValue();
                String password = row.getCell(1).getStringCellValue();
                test = extent.createTest("Login Test - " + username);
                loginPage.enterUserName(username);
                loginPage.enterPassword(password);
                loginPage.clickLoginButton();
                Thread.sleep(2000); // Wait for page to respond
                // Check for invalid login using error message container

                boolean isErrorVisible = driver.findElements(By.className("error-message-container")).size() > 0;
                if (isErrorVisible) {
                    Screenshot.captureScreenshot(driver, "InvalidLogin_" + username);
                    test.fail("Login failed for user: " + username);
                }
                else {
                    test.pass("Login successful for user: " + username);
                }

                driver.navigate().to(url); // Reset for next test
            }
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
        if (driver != null) {
            driver.quit();
        }
    }
}

 