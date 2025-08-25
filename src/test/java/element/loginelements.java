package element;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;

public class loginelements {

    WebDriver driver;

    public loginelements(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);

    }

    @FindBy(name = "user-name")

	public

    WebElement userName;

    @FindBy(name = "password")

    WebElement password;

    @FindBy(name = "login-button")

    WebElement loginButton;

    public void enterUserName(String username) {

        userName.sendKeys(username);

    }

    public void enterPassword(String pwd) {

        password.sendKeys(pwd);

    }

    public void clickLoginButton() {

        loginButton.click();

    }

}

 