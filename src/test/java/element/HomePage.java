package elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    WebDriver driver;

    @FindBy(className = "product_sort_container")
    WebElement sortDropdown;

    @FindBy(className = "inventory_item_name")
    List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    List<WebElement> itemPrices;

    public void selectSortOption(String optionText) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(optionText);
    }

    public boolean isInventoryPageLoaded() {
        try {
            return sortDropdown.isDisplayed();
        }
       catch (Exception e) {
            return false;
        }
    }

    public boolean isSortedByPriceLowToHigh() {
        List<Double> actualPrices = new ArrayList<>();
        for (WebElement price : itemPrices) {
            actualPrices.add(Double.parseDouble(price.getText().replace("$", "")));
        }

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        return actualPrices.equals(expectedPrices);
    }

    public boolean isSortedByPriceHighToLow() {
        List<Double> actualPrices = new ArrayList<>();
        for (WebElement price : itemPrices) {
            actualPrices.add(Double.parseDouble(price.getText().replace("$", "")));
        }

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        expectedPrices.sort(Collections.reverseOrder());

        return actualPrices.equals(expectedPrices);
    }

    public boolean isSortedByNameAToZ() {
        List<String> actualNames = new ArrayList<>();
        for (WebElement name : itemNames) {
            actualNames.add(name.getText());
        }

        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);

        return actualNames.equals(expectedNames);
    }

    public boolean isSortedByNameZToA() {
        List<String> actualNames = new ArrayList<>();
        for (WebElement name : itemNames) {
            actualNames.add(name.getText());
        }

        List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(Collections.reverseOrder());

        return actualNames.equals(expectedNames);
    }
}
