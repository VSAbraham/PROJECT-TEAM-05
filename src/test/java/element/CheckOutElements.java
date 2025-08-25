//package elements;
// 
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
// 
//public class CheckOutElements {
//    @FindBy(id = "first-name")
//    private WebElement firstname;
// 
//    @FindBy(id = "last-name")
//    private WebElement lastname;
// 
//    @FindBy(id = "postal-code")
//    private WebElement postalcode;
// 
//    @FindBy(id = "continue") // Update this to actual id
//    private WebElement continueButton;
// 
//    public void setFirstname(String fname) {
//        firstname.clear();
//        firstname.sendKeys(fname);
//    }
// 
//    public void setLastname(String lname) {
//        lastname.clear();
//        lastname.sendKeys(lname);
//    }
// 
//    public void setPostalcode(String zipcode) {
//        postalcode.clear();
//        postalcode.sendKeys(zipcode);
//    }
// 
//    public void clickContinue() {
//continueButton.click();
//    }
//}


package element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutElements {
    WebDriver driver;

    @FindBy(id = "first-name") WebElement firstName;
    @FindBy(id = "last-name") WebElement lastName;
    @FindBy(id = "postal-code") WebElement zipCode;
    @FindBy(id = "continue") WebElement continueBtn;
    @FindBy(css = ".error-message-container") WebElement errorMsg;

    public CheckOutElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

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
}
