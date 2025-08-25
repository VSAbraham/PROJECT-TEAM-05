package commons;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class browser_Factory {
	public WebDriver driver;
@Parameters("browser")
  @BeforeClass
  public void setup(String browser) {
	  
	  if(browser.equalsIgnoreCase("chrome")) {
		  System.setProperty("webdriver.chrome.driver", "Driver\\chromedriver.exe");
		  driver=new ChromeDriver();
	  }
	  else if(browser.equalsIgnoreCase("edge")) {
		  System.setProperty("webdriver.edge.driver", "Driver\\msedgedriver.exe");
		  driver=new EdgeDriver();
		
		  }
	  else {
	  throw new IllegalArgumentException("Browser not supported"); 
		  }
	  driver.manage().window().maximize();
	  driver.get("https://www.saucedemo.com/v1/");
	 
		  }
     
	   @AfterClass
	   public void tearDown() {
	   driver.quit();
		  }	 
  }

