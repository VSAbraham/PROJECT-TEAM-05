package element;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckOutElements {
    WebDriver driver;

    // Checkout form
    @FindBy(id = "first-name") WebElement firstName;
    @FindBy(id = "last-name") WebElement lastName;
    @FindBy(id = "postal-code") WebElement zipCode;
    @FindBy(id = "continue") WebElement continueBtn;
    @FindBy(css = ".error-message-container") WebElement errorMsg;

    // Cart + Inventory
    @FindBy(className = "shopping_cart_link") WebElement cartIcon;
    @FindBy(css = ".cart_item") List<WebElement> cartItems;
    @FindBy(css = ".inventory_item button") WebElement addToCartBtn;

    // Logout
    @FindBy(id = "react-burger-menu-btn") WebElement menuBtn;
    @FindBy(id = "logout_sidebar_link") WebElement logoutLink;

    public CheckOutElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // --- Cart Actions ---
    public void openCart() {
        cartIcon.click();
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    public void addItemToCart() {
        try {
            addToCartBtn.click();
            System.out.println("[INFO] Item added to cart.");
        } catch (Exception e) {
            System.out.println("[ERROR] No item found to add to cart: " + e.getMessage());
        }
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    // --- Checkout Actions ---
    public void fillForm(String fName, String lName, String zip) {
        firstName.clear(); firstName.sendKeys(fName);
        lastName.clear(); lastName.sendKeys(lName);
        zipCode.clear(); zipCode.sendKeys(zip);
    }

    public void submitForm() {
        continueBtn.click();
    }

    public boolean isErrorDisplayed() {
        try {
            return errorMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorText() {
        return errorMsg.getText();
    }

    // --- Logout ---
    public void logout() {
        try {
            menuBtn.click();
            logoutLink.click();
            System.out.println("[INFO] Logged out successfully.");
        } catch (Exception e) {
            System.out.println("[ERROR] Logout failed: " + e.getMessage());
        }
    }
}
