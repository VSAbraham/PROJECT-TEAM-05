package testcases;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import commons.BrowserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SocialSNS {

    WebDriver driver;
    String filePath = "C:\\Users\\ranjit.kumarbehera\\eclipse-workspace\\projecttt\\Excel\\testcases (2).xlsx";

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
        BrowserFactory browserFactory = new BrowserFactory();
        browserFactory.setup(browser);
        driver = browserFactory.driver;
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testAllCredentialsFromExcel() throws Exception {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            String username = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();

            System.out.println("Testing login with: " + username + " / " + password);

            driver.findElement(By.id("user-name")).clear();
            driver.findElement(By.id("user-name")).sendKeys(username);
            driver.findElement(By.id("password")).clear();
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("login-button")).click();

            Thread.sleep(2000);

            if (driver.getCurrentUrl().contains("inventory.html")) {
                System.out.println("Login successful for: " + username);
                testInventoryItemsSocialLinks();
                testCartPageSocialLinks();
                driver.findElement(By.id("react-burger-menu-btn")).click();
                Thread.sleep(1000);
                driver.findElement(By.id("logout_sidebar_link")).click();
                Thread.sleep(1000);
            } else {
                System.out.println("Login failed for: " + username);
            }
        }

        workbook.close();
        fis.close();
    }

    public void testInventoryItemsSocialLinks() throws InterruptedException {
        List<WebElement> items = driver.findElements(By.className("inventory_item_name"));

        for (int i = 0; i < items.size(); i++) {
            items = driver.findElements(By.className("inventory_item_name")); // Refresh list
            WebElement item = items.get(i);
            item.click();

            checkSocialLink("https://twitter.com/saucelabs");
            checkSocialLink("https://www.facebook.com/saucelabs");
            checkSocialLink("https://www.linkedin.com/company/sauce-labs/");

            driver.navigate().back();
            Thread.sleep(1000);
        }
    }

    public void testCartPageSocialLinks() throws InterruptedException {
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        checkSocialLink("https://twitter.com/saucelabs");
        checkSocialLink("https://www.facebook.com/saucelabs");
        checkSocialLink("https://www.linkedin.com/company/sauce-labs/");
    }

    public void checkSocialLink(String url) throws InterruptedException {
        WebElement link = driver.findElement(By.cssSelector("a[href='" + url + "']"));
        Assert.assertTrue(link.isDisplayed(), "Social media link should be visible: " + url);
        link.click();

        Thread.sleep(3000);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        Assert.assertEquals(tabs.size(), 2, "A new tab should open for: " + url);

        driver.switchTo().window(tabs.get(1));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(url.split("/")[2]), "Expected URL to contain: " + url);
        System.out.println("Opened URL: " + currentUrl);

        driver.close();
        driver.switchTo().window(tabs.get(0));
        Thread.sleep(1000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
