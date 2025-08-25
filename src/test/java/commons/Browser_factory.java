package Common; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver; 
public class Browser_factory {

    public WebDriver driver; 
    public WebDriver launchBrowser(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
            driver = new EdgeDriver();

        } else {
            System.out.println("Invalid browser name.");
            return null;
        }
 
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        System.out.println("Title: " + driver.getTitle());
        return driver;
    }
}

 