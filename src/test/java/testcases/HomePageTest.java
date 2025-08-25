package testcases;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import elements.HomePage;
import elements.LoginElements;
import common.BrowserFactory;
import common.ExcelUtils;

public class HomePageTest {

    WebDriver driver;
    LoginElements loginPage;
    HomePage homePage;

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
        BrowserFactory browserFactory = new BrowserFactory();
        browserFactory.setup(browser);
        driver = browserFactory.driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");

        loginPage = PageFactory.initElements(driver, LoginElements.class);
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @DataProvider(name = "loginData")
    public Iterator<Object[]> getLoginData() {
        List<Map<String, String>> data = ExcelUtils.readExcel("C:\\Users\\janani.rs\\eclipse-workspace\\projectnew\\Excel\\testcases (2).xlsx");
        return data.stream()
                   .map(entry -> new Object[]{entry.get("Username"), entry.get("Password")})
                   .iterator();
    }

    @Test(dataProvider = "loginData")
    public void testLoginAndSort(String username, String password) {
        driver.get("https://www.saucedemo.com"); // Reset to login page
        loginPage.login(username, password);

        if (homePage.isInventoryPageLoaded()) {
            System.out.println("✅ Login successful for: " + username);

            homePage.selectSortOption("Price (low to high)");
            if (homePage.isSortedByPriceLowToHigh()) {
                System.out.println("✅ Sorted by Price (Low to High)");
            } else {
                System.out.println("❌ Price Low to High sorting failed");
            }

            homePage.selectSortOption("Price (high to low)");
            if (homePage.isSortedByPriceHighToLow()) {
                System.out.println("✅ Sorted by Price (High to Low)");
            } else {
                System.out.println("❌ Price High to Low sorting failed");
            }

            homePage.selectSortOption("Name (A to Z)");
            if (homePage.isSortedByNameAToZ()) {
                System.out.println("✅ Sorted by Name (A to Z)");
            } else {
                System.out.println("❌ Name A to Z sorting failed");
            }

            homePage.selectSortOption("Name (Z to A)");
            if (homePage.isSortedByNameZToA()) {
                System.out.println("✅ Sorted by Name (Z to A)");
            } else {
                System.out.println("❌ Name Z to A sorting failed");
            }

        } else {
            System.out.println("❌ Login failed for: " + username);
        }
    }

    }

