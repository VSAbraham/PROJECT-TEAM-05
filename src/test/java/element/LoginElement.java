package element;
 
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
 
public class LoginElement {
    WebDriver driver;

    public LoginElement(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	@FindBy(name="user-name")
	WebElement userName;
	
	@FindBy(name="password")
	WebElement Password;
	
	@FindBy(name="login-button")
	WebElement loginButton;
	
	public void enterUserName(String username) {
		userName.isDisplayed();
		userName.clear();
		userName.sendKeys(username);
	}
	public void enterPassword(String password) {
		Password.isDisplayed();
		Password.clear();
		Password.sendKeys(password);
	}
	public void clickLoginButton() {
		loginButton.click();
	}
 
 
	
	
	
	
 
}
 
 