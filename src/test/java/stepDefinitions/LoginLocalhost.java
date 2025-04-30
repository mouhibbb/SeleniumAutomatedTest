package stepDefinitions;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class LoginLocalhost {


//	WebDriver driver = CommonClass.driver; // Utilisation du driver commun


	

//	@When("user enter login and password")
//	public void user_enter_login_and_password() {
//		driver.findElement(By.name("email")).sendKeys("mouhibbouazizi07@gmail.com");
//		driver.findElement(By.name("password")).sendKeys("1");
//
//		System.out.println("user enter login and password");
//	}
//
//	@And("clicks on login")
//	public void clicks_on_login() {
//		System.out.println("clicks on login");
//		driver.findElement(By.cssSelector("input[value='Se connecter']")).click();
//	}
//
//	@Then("user navigated to the home page")
//	public void user_navigated_to_the_home_page() {
//		try{Thread.sleep(2000);}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		}
//	
//
//
//	@Then("user clicks on {string}")
//	public void user_clicks_on(String string) {
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	    WebElement UsersButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Users')]")));
//	    UsersButton.click();
//	}
//
//	@Then("user is redirected to the users page")
//	public void user_is_redirected_to_the_users_page() {
//		try{Thread.sleep(2000);}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}	}
//
//	@Then("user activates all users in the table")
//	public void user_activates_all_users_in_the_table() {
//		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
//		
//		WebElement userTable=wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table")));
//		List<WebElement> rows = userTable.findElements(By.tagName("tr"));
//	    for (int i = 1; i < rows.size(); i++) { // Ignorer l'en-tête du tableau (si présent)
//	        WebElement row = rows.get(i);
//	        // Trouver le bouton "Activé" DANS cette ligne UNIQUEMENT
//	        try {
//	            WebElement desactivateButton = wait.until(ExpectedConditions.elementToBeClickable(
//	                row.findElement(By.xpath(".//button[contains(@class, 'mat-button-toggle-button') and .//span[text()='Desactivé']]"))
//	            ));
//
//	            if (desactivateButton.getDomAttribute("aria-checked").equals("true")) {
//	                // Trouver et cliquer sur le bouton "Activé" dans la même ligne
//	                WebElement activateButton = row.findElement(By.xpath(".//button[span[text()='Activé']]"));
//	                activateButton.click();
//	            }} 
//	            catch (NoSuchElementException e) {
//	            System.out.println("Utilisateur déjà activé ou bouton non trouvé.");
//	        } }
//	    }
//
//	@Then("user is redirected to the new users page")
//	public void user_is_redirected_to_the_new_users_page() {
//
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    	WebElement UsersButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'New Accounts')]")));
//    	UsersButton.click();
//    	try{Thread.sleep(2000);}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//    	
//	}
//	
//	@Then("user activates all new bank accounts in the table")
//	public void user_activates_all_new_bank_accounts_in_the_table() {
//		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
//
//		
//		WebElement userNewAccountTable=wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table")));
//		List<WebElement> rows = userNewAccountTable.findElements(By.tagName("tr"));
//	    for (int i = 1; i < rows.size(); i++) { // Ignorer l'en-tête du tableau (si présent)
//	        WebElement row = rows.get(i);
//	        // Trouver le bouton "Refuse"
//	        try {
//	            WebElement RefuséButton = wait.until(ExpectedConditions.elementToBeClickable(
//	                row.findElement(By.xpath(".//button[contains(@class, 'mat-button-toggle-button') and .//span[text()='Refusé']]"))));
//	                RefuséButton.click();
//		        WebElement ApprouvéButton = wait.until(ExpectedConditions.elementToBeClickable(
//			                row.findElement(By.xpath(".//button[contains(@class, 'mat-button-toggle-button') and .//span[text()='Approuvé']]"))));
//	            	ApprouvéButton.click();	        }
//	            
//	            catch (NoSuchElementException e) {
//	            System.out.println("Utilisateur déjà activé ou bouton non trouvé.");
//	        } }
//	    try{Thread.sleep(2000);}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//	@Then("user is redirected to the Credit users page")
//	public void user_is_redirected_to_the_credit_users_page() {
//
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    	WebElement UsersButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Credit')]")));
//    	UsersButton.click();
//    	try{Thread.sleep(2000);}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//		        
//	@Then("User activates all users in the table of Credit")
//	public void user_activates_all_users_in_the_table_of_credit() {
//		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
//		WebElement userCreditTable=wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table")));
//		List<WebElement> rows = userCreditTable.findElements(By.tagName("tr"));
//	    for (int i = 1; i < rows.size(); i++) { // Ignorer l'en-tête du tableau (si présent)
//	        WebElement row = rows.get(i);
//	        // Trouver le bouton "Refuse"
//	        try {
//	            WebElement RefuséButton = wait.until(ExpectedConditions.elementToBeClickable(
//	                row.findElement(By.xpath(".//button[contains(@class, 'mat-button-toggle-button') and .//span[text()='Refusé']]"))));
//	                RefuséButton.click();
//		        WebElement ApprouvéButton = wait.until(ExpectedConditions.elementToBeClickable(
//			                row.findElement(By.xpath(".//button[contains(@class, 'mat-button-toggle-button') and .//span[text()='Approuvé']]"))));
//	            	ApprouvéButton.click();	        }
//	            
//	            catch (NoSuchElementException e) {
//	            System.out.println("Utilisateur déjà activé ou bouton non trouvé.");
//	        } }
//	    try{Thread.sleep(2000);}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//	}    
//	@Then("user clicks on Log out")
//	public void user_clicks_on_log_out() {
//	    // Attendre que le bouton "Déconnexion" soit visible
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	    WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Déconnexion')]")));
//	    
//	    // Cliquer sur le bouton
//	    logoutButton.click();
//	}


	

}
