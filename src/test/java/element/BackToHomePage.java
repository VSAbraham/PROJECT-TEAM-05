package element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BackToHomePage {
    WebDriver driver;

    // Constructor
    public BackToHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");
    By addToCartBackpack = By.id("add-to-cart-sauce-labs-backpack");
    By cartIcon = By.className("shopping_cart_link");
    By checkoutButton = By.id("checkout");
    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By postalCode = By.id("postal-code");
    By continueButton = By.id("continue");
    By finishButton = By.id("finish");
    By backHomeButton = By.id("back-to-products");
    By menuButton = By.id("react-burger-menu-btn");
    By logoutLink = By.id("logout_sidebar_link");

    // Actions
    public void login(String user, String pass) throws InterruptedException {
        driver.get("https://www.saucedemo.com");
        driver.findElement(usernameField).sendKeys(user);
        driver.findElement(passwordField).sendKeys(pass);
        driver.findElement(loginButton).click();
        Thread.sleep(1000);
    }

    public void addToCartAndCheckout(String fName, String lName, String zip) throws InterruptedException {
        driver.findElement(addToCartBackpack).click();
        Thread.sleep(500);
        driver.findElement(cartIcon).click();
        Thread.sleep(500);
        driver.findElement(checkoutButton).click();
        Thread.sleep(500);
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(postalCode).sendKeys(zip);
        driver.findElement(continueButton).click();
        Thread.sleep(500);
    }

    public void finishAndBackToHome() throws InterruptedException {
        driver.findElement(finishButton).click();
        Thread.sleep(500);
        driver.findElement(backHomeButton).click();
        Thread.sleep(500);
    }

    // Verify if login is successful or failed
    public boolean verifyLogin() {
        try {
            // Success case: URL should contain inventory.html
            if (driver.getCurrentUrl().contains("inventory.html")) {
                return true;
            }

            // Failure case: check for error message
            WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
            if (errorMsg.isDisplayed()) {
                System.out.println("Login failed with error: " + errorMsg.getText());
            }
            return false;
        } catch (Exception e) {
            // If no element found or other issue, assume login failed
            return false;
        }
    }


    public boolean verifyHomeNavigation() {
        return driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html");
    }

    public void logout() throws InterruptedException {
        driver.findElement(menuButton).click();
        Thread.sleep(500);
        driver.findElement(logoutLink).click();
        Thread.sleep(500);
    }
}
