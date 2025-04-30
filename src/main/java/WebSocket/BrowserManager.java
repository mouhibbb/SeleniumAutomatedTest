package WebSocket;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserManager {
    private static WebDriver driver;
    private static long lastMessageTime = 0;
    private static final long THRESHOLD_MS = 60000; // Exemple : 60 secondes

    public static synchronized WebDriver getDriver() {
        // Si aucun driver n'existe ou si le délai d'inactivité est dépassé, on en crée un nouveau
        if (driver == null || (System.currentTimeMillis() - lastMessageTime) > THRESHOLD_MS) {
        	System.out.println("currentTimeMillis "+System.currentTimeMillis());
        	System.out.println("lastMessageTime "+lastMessageTime);
        	System.out.println(THRESHOLD_MS);
            resetDriver();
        }
        // Actualiser le timestamp à chaque accès
        lastMessageTime = System.currentTimeMillis();
        return driver;
    }

    private static void resetDriver() {
        // Fermer l'ancien driver si existant
        if (driver != null) {
            driver.quit();
        }
        // Créer une nouvelle instance de ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/BOUAZIZI/Desktop/Cucumber22/src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        lastMessageTime = System.currentTimeMillis();
    }
}
