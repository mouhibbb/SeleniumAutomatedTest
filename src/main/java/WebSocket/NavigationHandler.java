package WebSocket;

import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NavigationHandler {
    public static void handleNavigation(WebDriver driver, Map<String, Object> receivedMessage, Session session) {
        String status;
        String logMessage;

        try {
            String url = (String) receivedMessage.get("url");
            System.out.println("navigation vers " + url);
            driver.get(url);

            try {
                List<Map<String, Object>> inputs = InputExtractor.extractInputs(driver);
                if (!inputs.isEmpty()) {
                    String jsonMessage = new ObjectMapper().writeValueAsString(inputs);
                    session.getBasicRemote().sendText(jsonMessage);
                    status = "SUCCÈS";
                    logMessage = "Navigation réussie.";
                    System.out.println(logMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (UnhandledAlertException uae) {
            String alertText = "";
            try {
                Alert alert = driver.switchTo().alert();
                alertText = alert.getText();
                alert.accept();
            } catch (NoAlertPresentException ignored) {}

            status = "ÉCHEC";
            logMessage = "Alerte détectée pendant navigation : " + alertText;
            System.out.println(logMessage);
        }
    }

}
