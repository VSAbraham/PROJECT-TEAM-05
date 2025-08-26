package element;

import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class SocialSNSElements {

    WebDriver driver;

    // constructor
    public SocialSNSElements(WebDriver driver) {
        this.driver = driver;
    }

    // ✅ Login method
    public void login(String username, String password) throws InterruptedException {
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);
    }

    // ✅ Logout method
    public void logout() throws InterruptedException {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);
    }

    // ✅ Inventory page social link test
    public void testInventoryItemsSocialLinks() throws InterruptedException {
        List<WebElement> items = driver.findElements(By.className("inventory_item_name"));

        for (int i = 0; i < items.size(); i++) {
            items = driver.findElements(By.className("inventory_item_name")); // refresh list
            WebElement item = items.get(i);
            item.click();

            checkSocialLink("https://twitter.com/saucelabs");
            checkSocialLink("https://www.facebook.com/saucelabs");
            checkSocialLink("https://www.linkedin.com/company/sauce-labs/");

            driver.navigate().back();
            Thread.sleep(1000);
        }
    }

    // ✅ Cart page social link test
    public void testCartPageSocialLinks() throws InterruptedException {
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        checkSocialLink("https://twitter.com/saucelabs");
        checkSocialLink("https://www.facebook.com/saucelabs");
        checkSocialLink("https://www.linkedin.com/company/sauce-labs/");

        driver.navigate().back(); // return to inventory
    }

    // ✅ Reusable social link checker
    public void checkSocialLink(String expectedUrl) throws InterruptedException {
        WebElement link = driver.findElement(By.cssSelector("a[href='" + expectedUrl + "']"));
        Assert.assertTrue(link.isDisplayed(), "Social media link should be visible: " + expectedUrl);

        link.click();
        Thread.sleep(3000);

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        Assert.assertEquals(tabs.size(), 2, "A new tab should open for: " + expectedUrl);

        driver.switchTo().window(tabs.get(1));
        String currentUrl = driver.getCurrentUrl();

        // ✅ normalize domain check (twitter.com → x.com migration support)
        String expectedDomain = expectedUrl.split("/")[2];
        Assert.assertTrue(currentUrl.contains(expectedDomain) || currentUrl.contains("x.com"),
                "Expected URL to contain domain: " + expectedDomain + " but got: " + currentUrl);

        System.out.println("Opened URL: " + currentUrl);

        driver.close();
        driver.switchTo().window(tabs.get(0));
        Thread.sleep(1000);
    }
}
