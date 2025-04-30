package WebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputExtractor {
	  /**  
     * Parcourt la page courante et retourne la liste de tous les champs (input, select, button).  
     */
    public static List<Map<String, Object>> extractInputs(WebDriver driver) {
        List<Map<String, Object>> inputList = new ArrayList<>();

        // 1. Inputs standards
        for (WebElement input : driver.findElements(By.xpath("//input[@name and @type]"))) {
            Map<String, Object> field = new HashMap<>();
            field.put("name",        input.getDomAttribute("name"));
            field.put("type",        input.getDomAttribute("type"));
            field.put("placeholder", input.getDomAttribute("placeholder"));
            inputList.add(field);
        }

        // 2. Selects
        for (WebElement select : driver.findElements(By.xpath("//select[@name]"))) {
            Map<String, Object> field = new HashMap<>();
            field.put("type", "select");
            field.put("name", select.getDomAttribute("name"));

            List<String> options = new ArrayList<>();
            for (WebElement opt : select.findElements(By.tagName("option"))) {
                options.add(opt.getText().trim());
            }
            field.put("options", options);
            inputList.add(field);
        }

        // 3. Boutons
        for (WebElement button : driver.findElements(By.xpath("//button | //input[@type='submit' or @type='button']"))) {
            Map<String, Object> field = new HashMap<>();
            String tag = button.getTagName();
            field.put("type", tag.equals("button") ? "button" : button.getDomAttribute("type"));
            field.put("name", button.getDomAttribute("name"));
            String text = tag.equals("button") ? button.getText().trim() : button.getDomAttribute("value");
            field.put("text", text);
            inputList.add(field);
        }

        System.out.println("inputList = " + inputList);
        return inputList;
    }
}
