package commons;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
 
public class ClickCart {
    WebDriver driver;
 
    public ClickCart(WebDriver driver) {
        this.driver = driver;
    }
 
    @Test
    public void click_cart() {
      driver.findElement(By.id("shopping_cart_container")).click(); // Click <a> directly
    }
}