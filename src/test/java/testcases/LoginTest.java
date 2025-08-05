package testcases;

import commons.BrowserFactory;
import element.LoginElement;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
public class LoginTest {
	WebDriver driver;
	LoginElement loginpage;
 
 
    @BeforeClass
    public void setUp() {
        BrowserFactory bf = new BrowserFactory();
        bf.launchBrowser("edge");
        driver = bf.driver;
        driver.get("https://www.saucedemo.com/");
        loginpage = PageFactory.initElements(driver, LoginElement.class);
    }
    
    
    @Test
    public void runLoginTestsFromExcel() throws IOException {
        String excelFilePath = "C:\\Users\\vs.abraham\\Downloads\\login_test_data.xlsx";
        FileInputStream fis = new FileInputStream(excelFilePath);
 
        try (Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
 

		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++)
		 {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;
 
                String username = row.getCell(0).getStringCellValue();
                String password = row.getCell(1).getStringCellValue();
                String expectedResult = row.getCell(2).getStringCellValue();
 
                testLogin(username, password, expectedResult);
            }
        }
    }
 
    public void testLogin(String username, String password, String expectedResult) {
        driver.get("https://www.saucedemo.com/");
        loginpage.enterUserName(username);
        loginpage.enterPassword(password);
        loginpage.clickLoginButton();
 
        boolean loginSuccess = driver.getCurrentUrl().contains("inventory.html");
 
        if (expectedResult.equalsIgnoreCase("success")) {
            assert loginSuccess : "Expected success but login failed for user: " + username;
        } else {
            assert !loginSuccess : "Expected failure but login succeeded for user: " + username;
        }
 
    }
    
}
    
//    @Test
//    public void testLogin1() {
//    	driver.get("https://www.saucedemo.com/");
//        loginpage.enterUserName("locked_out_user");
//        loginpage.enterPassword("secret_sauce");
//        loginpage.clickLoginButton();
//    }
//    @Test
//    public void testLogin2() {
// 
//    	driver.get("https://www.saucedemo.com/");
//        loginpage.enterUserName("problem_user");
//        loginpage.enterPassword("secret_sauce");
//        loginpage.clickLoginButton();
//    }
//    @Test
//    public void testLogin3() {
//        driver.get("https://www.saucedemo.com/");
//        loginpage.enterUserName("performance_glitch_user");
//        loginpage.enterPassword("secret_sauce");
//        loginpage.clickLoginButton();
// 
//    }
//    @Test
//    public void testLogin() throws InterruptedException {
//    	driver.get("https://www.saucedemo.com/");
//    	loginpage.enterUserName("standard_user");
//    	loginpage.enterPassword("secret_sauce");
//    	loginpage.clickLoginButton();
////    	Thread.sleep(3000);
//    }
    
 