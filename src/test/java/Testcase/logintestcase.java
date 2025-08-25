package Testcase;
 
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.browser_Factory;
import elements.loginelements;
public class logintestcase {
	WebDriver driver;
    loginelements loginPage;
    
    @BeforeClass
    public void init() {
        browser_Factory browserFactory = new browser_Factory();
        browserFactory.setup();
        driver = browserFactory.driver;
        loginPage = PageFactory.initElements(driver, loginelements.class);
    }
    @Test
    public void testLogin() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        loginPage.setUsername("standard_user");
        loginPage.setPassword("secret_sauce");
        loginPage.setLoginButton();

driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        System.out.println("Login Test Completed");
       
        
        AddingItems ad = new AddingItems(driver);
        ad.additems();

driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        
    }

}