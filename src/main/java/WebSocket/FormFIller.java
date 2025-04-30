package WebSocket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FormFIller {
	/**
     * Remplit le formulaire décrit par 'inputs' sur la page courante,
     * gère les alertes JS, et retourne un message de log.
     */
    public static String fillForm(WebDriver driver, List<Map<String, Object>> inputs) {
        String logMessage = "";
        WebElement submitButton = null;

        try {
            // Parcours des champs
            for (Map<String, Object> input : inputs) {
                if (input.get("value") == null) continue;
                String name        = (String) input.get("name");
                String value       = (String) input.get("value");
                String typeElement = (String) input.get("type");

                if ("date".equalsIgnoreCase(typeElement)) {
                    // conversion date → ISO
                    String isoDate = ConvertToIsoDate.convertToIsoDate(value);
                    WebElement el  = driver.findElement(By.name(name));
                    ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].value = arguments[1];", el, isoDate);
                    continue;
                }

                if ("submit".equalsIgnoreCase(typeElement) && "true".equalsIgnoreCase(value)) {
                    // on stocke le submit pour cliquer après
                    submitButton = driver.findElement(By.xpath(
                        "//input[@value='" + name + "']|//button[text()='" + name + "']|//button[.//span[text()='"+name+"']]"
                    ));
                    continue;
                }

                // champ standard ou select
                WebElement el = driver.findElement(By.name(name));
                if ("select".equalsIgnoreCase(el.getTagName())) {
                    new Select(el).selectByVisibleText(value);
                } else {
                    el.clear();
                    el.sendKeys(value);
                }
            }

            // clic final
            if (submitButton != null) {
                submitButton.click();
             // Délai court pour laisser le temps à l'alerte de s'afficher
                Thread.sleep(1000); // ou WebDriverWait si tu veux faire mieux

                try {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    alert.accept();
                    logMessage = alertText;
                    System.out.println("🛑 Alerte JS détectée : " + alertText);
                    return logMessage;
                } 
                catch (NoAlertPresentException ignored) {
                    // Aucune alerte n'est apparue → comportement normal
                }
            }

            logMessage = "Formulaire rempli et bouton cliqué";
        }
        catch (UnhandledAlertException uae) {
            // gestion de l’alerte
            String alertText = "";
            try {
            	System.out.println("mouhib");
                Alert alert = driver.switchTo().alert();
                alertText = alert.getText();
                alert.accept();
            	System.out.println(alertText);

            } catch (NoAlertPresentException ignored) {}
            logMessage = "ÉCHEC – Alerte JS détectée : " + alertText;
        }
        catch (Exception e) {
            logMessage = "ÉCHEC – Erreur remplissage : " + e.getMessage();
        }

        return logMessage;
    }

}
