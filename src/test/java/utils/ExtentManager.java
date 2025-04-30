//package utils;
//
//import java.io.IOException;
//import java.net.URI;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.websocket.CloseReason;
//import javax.websocket.ContainerProvider;
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.WebSocketContainer;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.Select;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//import WebSocket.WebSocketClient;
//import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
//
//public class ExtentManager {
//
//	private static ExtentReports extent;
//    public static ExtentReports getInstance() {
//    if(extent==null) {
//    	ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
//        extent = new ExtentReports();
//        extent.attachReporter(spark);
//    }
//    return extent;
//    }
//    
//    private static WebDriver driver;
//    static {
//        System.setProperty("webdriver.chrome.driver", "C:/Users/BOUAZIZI/Desktop/Cucumber/src/test/resources/drivers/chromedriver.exe"); // Mets le bon chemin
//    }
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("✅ Connecté au WebSocket");
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) {
//    	try {
//    		driver = new ChromeDriver();
//
//    		ObjectMapper objectMapper=new ObjectMapper();
//    		Map<String, Object> receivedMessage=objectMapper.readValue(message, Map.class);
//    		String type=(String) receivedMessage.get("type");
//            System.out.println("📩 Message reçu : " + message);
//            System.out.println("🔹 Type du message : " + type);
//                    
//            if("NAVIGATION".equalsIgnoreCase(type)) {
//        		String url=(String) receivedMessage.get("url");
//            	System.out.println("navigation vers "+ url);
//            	driver.get(url);
//            	try {
//                  List<Map<String, Object>> inputs = extractInputs();
//                  if (!inputs.isEmpty()) {
//                      String jsonMessage = new ObjectMapper().writeValueAsString(inputs);
//                      session.getBasicRemote().sendText(jsonMessage);
//                  }
//              } catch (Exception e) {
//                  e.printStackTrace();
//              }
//            	}
//            else if("FILL_FORM".equalsIgnoreCase(type)){
//                String scenarioJson = (String) receivedMessage.get("scenario"); // ⚠️ Ceci est une chaîne JSON
//
//            	Map<String, Object> scenario=objectMapper.readValue(scenarioJson, Map.class);
//            	String url=(String) scenario.get("url");
//            	List<Map<String, Object>> inputs=(List<Map<String,Object>>) scenario.get("inputs");
//                driver.get(url);
//                // 👉 Remplir les champs du formulaire
//                WebElement submitButton = null;
//                String submitName =null;
//                for (Map<String, Object> input : inputs) {
//                	try {
//                		Thread.sleep(100);
//                	}
//                	catch(InterruptedException e){
//                		e.printStackTrace();
//                	}
//                	if (input.get("value")==null) {
//                		System.out.println("null");
//						continue;
//					}
//                    String name = (String) input.get("name");
//                    String value = (String) input.get("value");
//                    String typeElement = (String) input.get("type");
//                    System.out.println("🔹 name = " + name);
//                    System.out.println("🔹 value = " + value);
//                    System.out.println("🔹 typeElement = " + typeElement);
//
//                    if ("date".equalsIgnoreCase(typeElement)) {
//                        System.out.println("⏳ Avant conversion : " + name + " = " + value);
//
//                        // Convertir la date au format ISO (yyyy-MM-dd)
//                        String isoDate = convertToIsoDate(value);
//                        System.out.println("✅ Après conversion : " + name + " = " + isoDate);
//
//                        // Trouver l'élément et utiliser JavaScript pour définir sa valeur
//                        WebElement element = driver.findElement(By.name(name));
//                        JavascriptExecutor js = (JavascriptExecutor) driver;
//                        js.executeScript("arguments[0].value = arguments[1];", element, isoDate);
//
//                        System.out.println("✅ Remplissage du champ date : " + name + " = " + isoDate);
//                        continue; // 🔥 Passer directement à l'élément suivant
//                    }
//                    
//                    if("submit".equalsIgnoreCase(typeElement) && "true".equalsIgnoreCase(value)) {
//                    	submitName=name;
//                    	submitButton = driver.findElement(By.xpath("//input[@value='"+name+"']| //button[text()='"+name+"']"));
//                        System.out.println("🔹 Bouton submit détecté : " + name);
//                        continue; // Ne pas cliquer immédiatement
//
//                    }
//
//                 WebElement element = driver.findElement(By.name(name));
//                    if(element.getTagName().equals("select")) {
//                    	Select dropdown=new Select(element);
//                    	 try {
//                             dropdown.selectByVisibleText(value); // Sélection par texte visible
//                             System.out.println("✅ Sélectionné : " + name + " = " + value);
//                         } catch (Exception e) {
//                             System.out.println("⚠️ Erreur : Option '" + value + "' introuvable dans " + name);
//                         }}
//                   else {
//                    	        element.clear(); // Efface le champ avant d'écrire
//                    	        element.sendKeys(value);
//                    	        System.out.println("✅ Rempli : " + name + " = " + value);
//                    	    }
//                   
//                 // 🔥 Cliquer sur le bouton submit à la fin
//                    
//                    	
//                    }
//                if (submitButton != null) {
//                	try {
//                		Thread.sleep(100);
//                	}
//                	catch(InterruptedException e){
//                		e.printStackTrace();
//                	}
//                    System.out.println("🚀 Clic final sur le bouton submit : "+submitName);
//                    submitButton.click();
//                }
//                }
//            else {
//                System.out.println("⚠️ Type inconnu : " + type);
//            }
//            }
//            catch(IOException e){
//            	e.printStackTrace();
//            	System.out.println("Erreur lors du traitement du message WebSocket");
//            }
//    	}
//             
//    
//
//
//
//        @OnClose
//        public void onClose(Session session, CloseReason reason) {
//            System.out.println("❌ WebSocket fermé : " + reason);
//            if (driver != null) {
//                driver.quit();
//            }
//        }
//    private List<Map<String, Object>> extractInputs() {
//        List<WebElement> inputs = driver.findElements(By.xpath("//input[@name and @type]"));
//        List<WebElement> selects=driver.findElements(By.xpath("//select[@name]"));
//        List<Map<String, Object>> inputList = new ArrayList<>();
//        List<WebElement> buttons = driver.findElements(By.xpath("//button | //input[@type='submit' or @type='button']"));
//
//        for (WebElement input : inputs) {
//            Map<String, Object> field = new HashMap<>();
//            field.put("name", input.getDomAttribute("name"));
//            field.put("type", input.getDomAttribute("type"));
//            field.put("placeholder", input.getDomAttribute("placeholder"));
//            inputList.add(field);
//        }
//       for(WebElement select:selects) {
//    	   Map<String, Object> field=new HashMap<>();
//    	   field.put("type", "select");
//    	   field.put("name", select.getDomAttribute("name"));
//    	   
//    	   List<WebElement> options=select.findElements(By.tagName("option"));
//    	   List<String>optionValues= new ArrayList<>();
//    	   for(WebElement option:options) {
//    		   optionValues.add(option.getText().trim());
//    	   }
//    	   field.put("options",optionValues);
//    	   inputList.add(field);
//       }
//       // 🔹 Extraction des boutons
//       for (WebElement button : buttons) {
//           Map<String, Object> field = new HashMap<>();
//           field.put("type", button.getTagName().equals("button") ? "button" : button.getDomAttribute("type"));
//           field.put("name", button.getDomAttribute("name"));
//           String text = button.getTagName().equals("button") ? button.getText().trim() : button.getDomAttribute("value");
//
//           field.put("text", text); // Texte affiché sur le bouton
//           inputList.add(field);
//       }
//        System.out.println("inputList"+inputList);
//        return inputList;
//    }
//    private String convertToIsoDate(String date) {
//    	 try {
//    	        // 📌 Vérifie si la date est déjà au format correct "YYYY-MM-DD"
//    	        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
//    	            return date; // ✅ Ne pas convertir si c'est déjà bon
//    	        }
//
//    	        // 🔄 Sinon, convertir "JJ/MM/AAAA" → "AAAA-MM-JJ"
//    	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//    	        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    	        LocalDate localDate = LocalDate.parse(date, inputFormatter);
//    	        return outputFormatter.format(localDate); // Retourne la date ISO
//    	    } catch (DateTimeParseException e) {
//    	        System.out.println("⚠️ Erreur : Format de date invalide - " + date);
//    	        return date; // Retourne la date d'origine en cas d'erreur
//    	    }
//    }
//public class WebSocketUtils {
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    public static void sendMessage(Session session, List<Map<String, String>> inputs) {
//        try {
//            String jsonMessage = objectMapper.writeValueAsString(inputs);
//            session.getBasicRemote().sendText(jsonMessage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void main(String[] args) {
//        try {
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//            container.connectToServer(WebSocketClient.class, new URI("ws://localhost:8082/ws/api"));
//
//            synchronized (WebSocketClient.class) {
//                WebSocketClient.class.wait();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//public static void main(String[] args) {
//    try {
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        container.connectToServer(WebSocketClient.class, new URI("ws://localhost:8082/ws/api"));
//        synchronized (WebSocketClient.class) {
//            WebSocketClient.class.wait();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
//}
