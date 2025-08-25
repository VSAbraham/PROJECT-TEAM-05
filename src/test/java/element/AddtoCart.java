package element;
 
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
 
public class AddtoCart {

	@FindBy(id="add-to-cart-sauce-labs-backpack")

	WebElement backpack;

	public void backpackButton() {

		backpack.click();

	}

	@FindBy(className="product_sort_container")

	WebElement sortcontainer;

	public void sorting() {

		sortcontainer.click();

	}
 
	@FindBy(xpath="//option[@value = \"hilo\"]")

	WebElement highlow;

	public void highlowbutton() {

		highlow.click();

	}

	@FindBy(id="add-to-cart-sauce-labs-bolt-t-shirt")

	WebElement boltTShirt;

	public void bolttShirtButton() {

		boltTShirt.click();

	}

	@FindBy(id="item_3_title_link")

	WebElement orangeshirt;

	public void orangeShirtText() {

		orangeshirt.click();

	}
 
	@FindBy(id="add-to-cart")

	WebElement orangeaddtocart;

	public void orangeAddtocartButton() {

		orangeaddtocart.click();

	}
 
	@FindBy(id="back-to-products")

	WebElement obacktoproduct;

	public void obacktoProduct() {

		obacktoproduct.click();

	}
 
	@FindBy(className="shopping_cart_link")

	WebElement shoppingCart;

	public void shoppingCartButton() {

		shoppingCart.click();

	}

	@FindBy(name="remove-test.allthethings()-t-shirt-(red)")

	WebElement removeitem;

	public void RemoveOneProduct() {

		removeitem.click();

	}

	//id="continue-shopping"

	@FindBy(id="continue-shopping")

	WebElement shopping;

	public void continueshopping() {

		shopping.click();

	}
 
 
}

 