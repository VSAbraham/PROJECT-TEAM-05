package element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductElement {

    WebDriver driver;

    @FindBy(id = "react-burger-menu-btn")
    WebElement menu;

    @FindBy(className = "inventory_details_name")
    WebElement prodName;

    @FindBy(xpath = "//*[@id=\"inventory_item_container\"]/div/div/div[2]/div[2]")
    WebElement prodDesc;

    @FindBy(className = "inventory_details_price")
    WebElement prodCost;

    @FindBy(className = "inventory_details_img")
    WebElement prodImg;

    @FindBy(xpath = "//*[@id=\"header_container\"]/div[1]/div[2]/div")
    WebElement title;

    @FindBy(xpath = "//*[@id=\"shopping_cart_container\"]/a")
    WebElement addToCart;

    @FindBy(xpath = "//*[@id=\"page_wrapper\"]/footer")
    WebElement footer;

    // --- Constructor ---
    public ProductElement(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // --- Getters ---
    public String getProdName() { return prodName.getText().trim(); }
    public String getProdDesc() { return prodDesc.getText().trim(); }
    public String getProdPrice() { return prodCost.getText().trim(); }
    public String getProdImageSrc() { return prodImg.getAttribute("src"); }

    // --- UI checks if needed ---
    public boolean isMenuDisplayed() { return menu.isDisplayed(); }
    public boolean isTitleDisplayed() { return title.isDisplayed(); }
    public boolean isAddToCartDisplayed() { return addToCart.isDisplayed(); }
    public boolean isFooterDisplayed() { return footer.isDisplayed(); }
}
