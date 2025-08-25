package Elements;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class inventory_page {
    WebDriver driver;

    public inventory_page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_item")
    private List<WebElement> allItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    // Getter for item count
    public int getItemCount() {
        return allItems.size();
    }

    // Getter for item names
    public List<WebElement> getItemNames() {
        return itemNames;
    }

    // Getter for item prices
    public List<WebElement> getItemPrices() {
        return itemPrices;
    }

    // Click item by name
    public boolean clickItemByName(String name) {
        for (WebElement item : itemNames) {
            if (item.getText().equalsIgnoreCase(name)) {
                item.click();
                return true;
            }
        }
        return false;
    }

    // Click item by index
    public boolean clickItemByIndex(int index) {
        if (index >= 0 && index < itemNames.size()) {
            itemNames.get(index).click();
            return true;
        }
        return false;
    }
}
