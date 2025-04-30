//package stepDefinitions;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Random;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import utils.ExcelUtils;
//
//public class CreerCompteBancaire {
//	
//	WebDriver driver = CommonClass.driver; // Utilisation du driver commun
//	
//	@When("l  utilisateur clique sur creer_un_compte")
//	public void l_utilisateur_clique_sur_creer_un_compte() {
//		driver.findElement(By.name("email")).sendKeys("syry@gmail.com");
//		driver.findElement(By.name("password")).sendKeys("11111111");
//		driver.findElement(By.cssSelector("input[value='Se connecter']")).click();
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	    WebElement UsersButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(),'Creer Compte')]")));
//	    UsersButton.click();
//	}	
//	
//
//	@Then("il remplit le formulaire de création de compte")
//	public void il_remplit_le_formulaire_de_création_de_compte() {
//		LocalDate startDate=LocalDate.of(1950, 01, 01);
//		LocalDate endDate= LocalDate.of(2000, 01, 01);
//		long randomDays= new Random().nextInt((int) (endDate.toEpochDay()-startDate.toEpochDay()));
//		LocalDate randomDate = startDate.plusDays(randomDays);
//		String formattedDate = randomDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//		try {
//		    ExcelUtils.setExcelFile("D:/etude/pfe/donnéeCreateUser.xlsx", "Sheet2");
//
//		    for (int i = 1; i < 2; i++) {
//		        try{Thread.sleep(500);}	catch (InterruptedException e) {e.printStackTrace();}
//				
//		        String nom = ExcelUtils.getCellData(i, 0);
//		        String prenom = ExcelUtils.getCellData(i, 1);
//		        String identite = ExcelUtils.getCellData(i, 3);
//		        String nationalite = ExcelUtils.getCellData(i, 4);
//		        String email = ExcelUtils.getCellData(i, 6);
//		        String telephone = ExcelUtils.getCellData(i, 7);
//		        String rue = ExcelUtils.getCellData(i, 8);
//		        String profession = ExcelUtils.getCellData(i, 9);
//		        String ville = ExcelUtils.getCellData(i, 10);
//		        String pays = ExcelUtils.getCellData(i, 11);
//		        String code_postal = ExcelUtils.getCellData(i, 12);
//		        String salaire = ExcelUtils.getCellData(i, 13);
//		        
//		        driver.findElement(By.name("nom")).sendKeys(nom);
//		        driver.findElement(By.name("prenom")).sendKeys(prenom);
//		        driver.findElement(By.name("date_naissance")).sendKeys(formattedDate);
//		        driver.findElement(By.name("identite")).sendKeys(identite);
//		        driver.findElement(By.name("nationalite")).sendKeys(nationalite);
//		        WebElement select_sexe = driver.findElement(By.name("sexe"));
//		        Select select2 = new Select(select_sexe);
//		        select2.selectByValue("homme"); 
//
//		        driver.findElement(By.name("email")).sendKeys(email);
//		        driver.findElement(By.name("telephone")).sendKeys(telephone);
//		        driver.findElement(By.name("rue")).sendKeys(rue);
//		        driver.findElement(By.name("profession")).sendKeys(profession);
//		        driver.findElement(By.name("ville")).sendKeys(ville);
//		        driver.findElement(By.name("pays")).sendKeys(pays);
//		        driver.findElement(By.name("code_postal")).sendKeys(code_postal);
//		        driver.findElement(By.name("salaire")).sendKeys(salaire);
//		        WebElement select_statut_emploi = driver.findElement(By.name("statut_emploi"));
//		        Select select = new Select(select_statut_emploi);
//		        select.selectByValue("independant"); 
//
//		        WebElement select_type_compte = driver.findElement(By.name("type_compte"));
//		        Select select1 = new Select(select_type_compte);
//		        select1.selectByValue("compte_courant"); 
//
//
//		        driver.findElement(By.cssSelector("input[value=\"Soumettre\"]")).click();
//			    WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
//		        WebElement DeconnexionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Déconnexion')]")));
//		        DeconnexionButton.click();
//
//		    }
//
//		    ExcelUtils.closeExcelFile(); // Fermer le fichier Excel
//		} catch (IOException e) {
//		    System.out.println("Erreur lors de la lecture du fichier Excel : " + e.getMessage());
//		    e.printStackTrace();
//		}	}
//
//
//
//}
