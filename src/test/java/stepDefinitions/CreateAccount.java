//package stepDefinitions;
//
//
//import java.io.IOException;
//import java.util.Iterator;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import utils.ExcelUtils;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.When;
//public class CreateAccount {
//	WebDriver driver = CommonClass.driver; // Utilisation du driver commun
//	
//	@When("user clicks on Creer_un_compte And enter nom prenom email mots de passe et comfimer le mot de passe")
//	public void user_clicks_on_Creer_un_compte_And_enter_nom_prenom_email_mots_de_passe_et_comfimer_le_mot_de_passe() {
//		try{Thread.sleep(500);}
//		catch (InterruptedException e) {
//			e.printStackTrace();}
//		
//		try {
//		    ExcelUtils.setExcelFile("D:/etude/pfe/donnéeCreateUser.xlsx", "Sheet1");
//
//		    int rowCount = ExcelUtils.getRowCount(); // Récupérer le nombre de lignes
//		    	System.out.println("rowcount"+rowCount);
//		    for (int i = 1; i < rowCount; i++) {
//		        driver.findElement(By.cssSelector("input[value='Créer un compte']")).click();
//		        try{Thread.sleep(500);}	catch (InterruptedException e) {e.printStackTrace();}
//				
//		        String lastName = ExcelUtils.getCellData(i, 0);
//		        String firstName = ExcelUtils.getCellData(i, 1);
//		        String email = ExcelUtils.getCellData(i, 2);
//		        String password = ExcelUtils.getCellData(i, 3);
//		        String confirmPassword = ExcelUtils.getCellData(i, 4);
//
//		        driver.findElement(By.name("lastName")).sendKeys(lastName);
//		        driver.findElement(By.name("firstname")).sendKeys(firstName);
//		        driver.findElement(By.name("email")).sendKeys(email);
//		        driver.findElement(By.name("password")).sendKeys(password);
//		        driver.findElement(By.name("confirPassword")).sendKeys(confirmPassword);
//
//		        System.out.println("Compte créé pour : " + firstName + " " + lastName);
//		        driver.findElement(By.cssSelector("input[value=\"S'inscrire\"]")).click();
//
//		        Thread.sleep(500); // Pause pour éviter les erreurs de timing
//		    }
//
//		    ExcelUtils.closeExcelFile(); // Fermer le fichier Excel
//		} catch (IOException e) {
//		    System.out.println("Erreur lors de la lecture du fichier Excel : " + e.getMessage());
//		    e.printStackTrace();
//		} catch (InterruptedException e) {
//		    e.printStackTrace();
//		}
//
//
//			
//		}
//	
//	
//
//}
