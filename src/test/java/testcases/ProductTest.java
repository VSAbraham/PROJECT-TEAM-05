package testcases;

import commons.BaseTest;
import commons.BrowserFactory;
import commons.Screenshot;
import element.InventoryElements;
import element.LoginElement;
import element.ProductElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

public class ProductTest extends BaseTest {

    InventoryElements Inv;
    ProductElement home;
    LoginElement login;
    BrowserFactory browserFactory;

    // ✅ Store current username for screenshot naming
    private String currentUser = "";

    @BeforeMethod
    @Parameters({"username", "password"})
    public void setup(String username, String password) {
        this.currentUser = username;
        browserFactory = new BrowserFactory();
        browserFactory.launchBrowser("edge");

        login = PageFactory.initElements(browserFactory.driver, LoginElement.class);
        home = PageFactory.initElements(browserFactory.driver, ProductElement.class);
        Inv = PageFactory.initElements(browserFactory.driver, InventoryElements.class);

        browserFactory.driver.get("https://www.saucedemo.com/");
        login.enterUserName(username);
        login.enterPassword(password);
        login.clickLoginButton();

        // ✅ Check if login was successful
        if (!Inv.isInventoryPageLoaded()) {
            browserFactory.driver.quit();
            throw new SkipException("❌ Skipping tests: Login failed for user -> " + username);
        }
    }

    @Test(priority = 1)
    public void tc_00_verify() throws InterruptedException {
        if (Inv.chk_itm_0_display() && Inv.chk_itm_0_enabled()) {
            String expectedTitle = Inv.item_0_title();
            String expectedDesc  = Inv.item_0_desc();
            String expectedPrice = Inv.item_0_price();
            String expectedImg   = Inv.item_0_img();

            Inv.clk_itm_0();
//            Thread.sleep(3000);
            Assert.assertEquals(home.getProdName(), expectedTitle, "❌ Title Mismatch");
            Assert.assertEquals(home.getProdDesc(), expectedDesc, "❌ Description Mismatch");
            Assert.assertEquals(home.getProdPrice(), expectedPrice, "❌ Price Mismatch");
            Assert.assertEquals(home.getProdImageSrc(), expectedImg, "❌ Image Mismatch");

            System.out.println("✅ Product 0 verified successfully.");
        }
    }

    @Test(priority = 2)
    public void tc_01_verify() throws InterruptedException {
        if (Inv.chk_itm_1_display() && Inv.chk_itm_1_enabled()) {
            String expectedTitle = Inv.item_1_title();
            String expectedDesc  = Inv.item_1_desc();
            String expectedPrice = Inv.item_1_price();
            String expectedImg   = Inv.item_1_img();

            Inv.clk_itm_1();
//            Thread.sleep(3000);
            Assert.assertEquals(home.getProdName(), expectedTitle, "❌ Title Mismatch");
            Assert.assertEquals(home.getProdDesc(), expectedDesc, "❌ Description Mismatch");
            Assert.assertEquals(home.getProdPrice(), expectedPrice, "❌ Price Mismatch");
            Assert.assertEquals(home.getProdImageSrc(), expectedImg, "❌ Image Mismatch");

            System.out.println("✅ Product 1 verified successfully.");
        }
    }

    @Test(priority = 3)
    public void tc_02_verify() throws InterruptedException {
        if (Inv.chk_itm_2_display() && Inv.chk_itm_2_enabled()) {
            String expectedTitle = Inv.item_2_title();
            String expectedDesc  = Inv.item_2_desc();
            String expectedPrice = Inv.item_2_price();
            String expectedImg   = Inv.item_2_img();

            Inv.clk_itm_2();
//            Thread.sleep(3000);
            Assert.assertEquals(home.getProdName(), expectedTitle, "❌ Title Mismatch");
            Assert.assertEquals(home.getProdDesc(), expectedDesc, "❌ Description Mismatch");
            Assert.assertEquals(home.getProdPrice(), expectedPrice, "❌ Price Mismatch");
            Assert.assertEquals(home.getProdImageSrc(), expectedImg, "❌ Image Mismatch");

            System.out.println("✅ Product 2 verified successfully.");
        }
    }

    @Test(priority = 4)
    public void tc_03_verify() throws InterruptedException {
        if (Inv.chk_itm_3_display() && Inv.chk_itm_3_enabled()) {
            String expectedTitle = Inv.item_3_title();
            String expectedDesc  = Inv.item_3_desc();
            String expectedPrice = Inv.item_3_price();
            String expectedImg   = Inv.item_3_img();

            Inv.clk_itm_3();
//            Thread.sleep(3000);
            Assert.assertEquals(home.getProdName(), expectedTitle, "❌ Title Mismatch");
            Assert.assertEquals(home.getProdDesc(), expectedDesc, "❌ Description Mismatch");
            Assert.assertEquals(home.getProdPrice(), expectedPrice, "❌ Price Mismatch");
            Assert.assertEquals(home.getProdImageSrc(), expectedImg, "❌ Image Mismatch");

            System.out.println("✅ Product 3 verified successfully.");
        }
    }

    @Test(priority = 5)
    public void tc_04_verify() throws InterruptedException {
        if (Inv.chk_itm_4_display() && Inv.chk_itm_4_enabled()) {
            String expectedTitle = Inv.item_4_title();
            String expectedDesc  = Inv.item_4_desc();
            String expectedPrice = Inv.item_4_price();
            String expectedImg   = Inv.item_4_img();

            Inv.clk_itm_4();
//            Thread.sleep(3000);
            Assert.assertEquals(home.getProdName(), expectedTitle, "❌ Title Mismatch");
            Assert.assertEquals(home.getProdDesc(), expectedDesc, "❌ Description Mismatch");
            Assert.assertEquals(home.getProdPrice(), expectedPrice, "❌ Price Mismatch");
            Assert.assertEquals(home.getProdImageSrc(), expectedImg, "❌ Image Mismatch");

            System.out.println("✅ Product 4 verified successfully.");
        }
    }

    @Test(priority = 6)
    public void tc_05_verify() throws InterruptedException {
        if (Inv.chk_itm_5_display() && Inv.chk_itm_5_enabled()) {
            String expectedTitle = Inv.item_5_title();
            String expectedDesc  = Inv.item_5_desc();
            String expectedPrice = Inv.item_5_price();
            String expectedImg   = Inv.item_5_img();

            Inv.clk_itm_5();
//            Thread.sleep(3000);
            Assert.assertEquals(home.getProdName(), expectedTitle, "❌ Title Mismatch");
            Assert.assertEquals(home.getProdDesc(), expectedDesc, "❌ Description Mismatch");
            Assert.assertEquals(home.getProdPrice(), expectedPrice, "❌ Price Mismatch");
            Assert.assertEquals(home.getProdImageSrc(), expectedImg, "❌ Image Mismatch");

            System.out.println("✅ Product 5 verified successfully.");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                Screenshot.captureScreenshot(
                        browserFactory.driver,
                        result.getMethod().getMethodName() + "_user_" + currentUser
                );
            }
        } finally {
            if (browserFactory != null && browserFactory.driver != null) {
                browserFactory.driver.quit();
            }
        }

        // ✅ Log result to report (from BaseTest)
        super.logResult(result);
    }
}
