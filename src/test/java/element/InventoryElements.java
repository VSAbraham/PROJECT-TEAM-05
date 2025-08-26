package element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryElements {
    WebDriver driver;

    // ================== BACKPACK ==================
    @FindBy(id = "item_4_title_link")
    WebElement backpack;
    @FindBy(css = "#item_4_img_link img")
    WebElement backpackImg;
    @FindBy(xpath = "(//div[@class='inventory_item_desc'])[1]")
    WebElement backpackDesc;
    @FindBy(xpath = "(//div[@class='inventory_item_price'])[1]")
    WebElement backpackPrice;

    // ================== BIKE LIGHT ==================
    @FindBy(id = "item_0_title_link")
    WebElement bikelight;
    @FindBy(css = "#item_0_img_link img")
    WebElement bikelightImg;
    @FindBy(xpath = "(//div[@class='inventory_item_desc'])[2]")
    WebElement bikelightDesc;
    @FindBy(xpath = "(//div[@class='inventory_item_price'])[2]")
    WebElement bikelightPrice;

    // ================== BOLT T-SHIRT ==================
    @FindBy(id = "item_1_title_link")
    WebElement boltshirt;
    @FindBy(css = "#item_1_img_link img")
    WebElement boltshirtImg;
    @FindBy(xpath = "(//div[@class='inventory_item_desc'])[3]")
    WebElement boltshirtDesc;
    @FindBy(xpath = "(//div[@class='inventory_item_price'])[3]")
    WebElement boltshirtPrice;

    // ================== FLEECE JACKET ==================
    @FindBy(id = "item_5_title_link")
    WebElement jacket;
    @FindBy(css = "#item_5_img_link img")
    WebElement jacketImg;
    @FindBy(xpath = "(//div[@class='inventory_item_desc'])[4]")
    WebElement jacketDesc;
    @FindBy(xpath = "(//div[@class='inventory_item_price'])[4]")
    WebElement jacketPrice;

    // ================== ONESIE ==================
    @FindBy(id = "item_2_title_link")
    WebElement onesie;
    @FindBy(css = "#item_2_img_link img")
    WebElement onesieImg;
    @FindBy(xpath = "(//div[@class='inventory_item_desc'])[5]")
    WebElement onesieDesc;
    @FindBy(xpath = "(//div[@class='inventory_item_price'])[5]")
    WebElement onesiePrice;

    // ================== TEST.ALLTHETHINGS T-SHIRT ==================
    @FindBy(id = "item_3_title_link")
    WebElement tshirt;
    @FindBy(css = "#item_3_img_link img")
    WebElement tshirtImg;
    @FindBy(xpath = "(//div[@class='inventory_item_desc'])[6]")
    WebElement tshirtDesc;
    @FindBy(xpath = "(//div[@class='inventory_item_price'])[6]")
    WebElement tshirtPrice;


    // Page indicators
    @FindBy(className = "title")
    WebElement inventoryTitle;

    @FindBy(id = "react-burger-menu-btn")
    WebElement menuButton;

    // --- Constructor ---
    public InventoryElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ Check inventory page loaded
    public boolean isInventoryPageLoaded() {
        try {
            return inventoryTitle.isDisplayed() || menuButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ================== ITEM 0 ==================
    public boolean chk_itm_0_display() { return bikelight.isDisplayed(); }
    public boolean chk_itm_0_enabled() { return bikelight.isEnabled(); }
    public void clk_itm_0() { bikelight.click(); }
    public String item_0_title() { return bikelight.getText().trim(); }
    public String item_0_img() { return bikelightImg.getAttribute("src"); }
    public String item_0_desc() { return bikelightDesc.getText().trim(); }
    public String item_0_price() { return bikelightPrice.getText().trim(); }

    // ================== ITEM 1 ==================
    public boolean chk_itm_1_display() { return boltshirt.isDisplayed(); }
    public boolean chk_itm_1_enabled() { return boltshirt.isEnabled(); }
    public void clk_itm_1() { boltshirt.click(); }
    public String item_1_title() { return boltshirt.getText().trim(); }
    public String item_1_img() { return boltshirtImg.getAttribute("src"); }
    public String item_1_desc() { return boltshirtDesc.getText().trim(); }
    public String item_1_price() { return boltshirtPrice.getText().trim(); }

    // ================== ITEM 2 ==================
    public boolean chk_itm_2_display() { return onesie.isDisplayed(); }
    public boolean chk_itm_2_enabled() { return onesie.isEnabled(); }
    public void clk_itm_2() { onesie.click(); }
    public String item_2_title() { return onesie.getText().trim(); }
    public String item_2_img() { return onesieImg.getAttribute("src"); }
    public String item_2_desc() { return onesieDesc.getText().trim(); }
    public String item_2_price() { return onesiePrice.getText().trim(); }

    // ================== ITEM 3 ==================
    public boolean chk_itm_3_display() { return tshirt.isDisplayed(); }
    public boolean chk_itm_3_enabled() { return tshirt.isEnabled(); }
    public void clk_itm_3() { tshirt.click(); }
    public String item_3_title() { return tshirt.getText().trim(); }
    public String item_3_img() { return tshirtImg.getAttribute("src"); }
    public String item_3_desc() { return tshirtDesc.getText().trim(); }
    public String item_3_price() { return tshirtPrice.getText().trim(); }

    // ================== ITEM 4 ==================
    public boolean chk_itm_4_display() { return backpack.isDisplayed(); }
    public boolean chk_itm_4_enabled() { return backpack.isEnabled(); }
    public void clk_itm_4() { backpack.click(); }
    public String item_4_title() { return backpack.getText().trim(); }
    public String item_4_img() { return backpackImg.getAttribute("src"); }
    public String item_4_desc() { return backpackDesc.getText().trim(); }
    public String item_4_price() { return backpackPrice.getText().trim(); }

    // ================== ITEM 5 ==================
    public boolean chk_itm_5_display() { return jacket.isDisplayed(); }
    public boolean chk_itm_5_enabled() { return jacket.isEnabled(); }
    public void clk_itm_5() { jacket.click(); }
    public String item_5_title() { return jacket.getText().trim(); }
    public String item_5_img() { return jacketImg.getAttribute("src"); }
    public String item_5_desc() { return jacketDesc.getText().trim(); }
    public String item_5_price() { return jacketPrice.getText().trim(); }
}
