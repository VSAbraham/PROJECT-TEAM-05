package demoonce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class BackToHome {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\amaravathi.srividya\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        System.out.println("🔧 Browser launched and maximized.");
    }

    @Test
    public void testBackToHomeFlow() throws InterruptedException {
        login();
        addToCartAndCheckout();
        finishAndBackToHome();
        verifyHomeNavigation();
        logout();
    }

    public void login() throws InterruptedException {
        driver.get("https://www.saucedemo.com");
        Thread.sleep(1000);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);
        System.out.println("🔐 Logged in successfully.");
    }

    public void addToCartAndCheckout() throws InterruptedException {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("first-name")).sendKeys("Amaravathi");
        driver.findElement(By.id("last-name")).sendKeys("SriVidya");
        driver.findElement(By.id("postal-code")).sendKeys("600001");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);
        System.out.println("🛒 Item added and checkout details filled.");
    }

    public void finishAndBackToHome() throws InterruptedException {
        driver.findElement(By.id("finish")).click();
        Thread.sleep(1000);
        WebElement backHomeButton = driver.findElement(By.id("back-to-products"));
        backHomeButton.click();
        Thread.sleep(1000);
        System.out.println("🏠 Clicked Back to Home.");
    }

    public void verifyHomeNavigation() {
        if (driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html")) {
            System.out.println("✅ Test Passed: Navigated to Home page.");
        } else {
            System.out.println("❌ Test Failed: Navigation did not go to Home page.");
        }
    }

    public void logout() throws InterruptedException {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);
        System.out.println("🔁 Logged out and returned to login screen.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🧹 Browser closed.");
        }
    }
}
