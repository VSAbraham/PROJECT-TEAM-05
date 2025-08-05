package commons;
 
import org.openqa.selenium.WebDriver;
 
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

 
public class BrowserFactory {
		public WebDriver driver;
	   @Test
	   public void launchBrowser(String browser) {
	       if (browser.equalsIgnoreCase("chrome")) {
	           System.setProperty("webdriver.chrome.driver", "C:\\Users\\vs.abraham\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
	           driver = new ChromeDriver();
	       
	       } else if (browser.equalsIgnoreCase("edge")) {
	           System.setProperty("webdriver.edge.driver","C:\\Users\\vs.abraham\\Downloads\\edgedriver_win64\\msedgedriver.exe");
	           driver = new EdgeDriver();
	       } else {
	           System.out.println("Invalid browser name.");
//	           sc.close();
	           return;
	       }

	   }
}
 
 