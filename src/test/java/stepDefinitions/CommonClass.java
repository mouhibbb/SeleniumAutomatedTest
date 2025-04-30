//package stepDefinitions;
//
//import java.time.Duration;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.en.Given;
//
//public class CommonClass {
//	public static WebDriver driver;
//	@Before
//	@Given("browser is open")
//	public void browser_is_open() {
//		System.out.println("browser page");
//		System.setProperty("webdriver.chrome.driver","C:/Users/BOUAZIZI/Desktop/Cucumber"
//				+ "/src/test/resources/drivers/chromedriver.exe");
//		driver=new ChromeDriver();	
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
//		driver.manage().window().maximize()
//		;		
//		driver.navigate().to("http://localhost:4200");
//}
//	//@After
//	//public void closeDriver() {
//	//	if (driver!=null) {
//	//		System.out.println("fermeture du navigateur");
//	//		driver.quit();
//	//		driver=null;
//	//	}
//	//}
//}
