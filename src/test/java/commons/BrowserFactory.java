package commons;
 
import org.openqa.selenium.WebDriver;
 
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

 
public class BrowserFactory {
		public static WebDriver driver;
	   @Test
	   public void launchBrowser(String browser) {
	       if (browser.equalsIgnoreCase("chrome")) {
	           System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
	           driver = new ChromeDriver();
	       
	       } else if (browser.equalsIgnoreCase("edge")) {
	           System.setProperty("webdriver.edge.driver","drivers/msedgedriver.exe");
	           driver = new EdgeDriver();
	       } else {
	           System.out.println("Invalid browser name.");
	           return;
	       }

	   }
}
 
 