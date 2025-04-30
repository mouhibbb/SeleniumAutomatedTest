package WebSocket;

import org.openqa.selenium.UnhandledAlertException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.websocket.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.messages.types.Duration;

@ClientEndpoint
public class WebSocketClient {
	private static WebDriver driver;

	public WebSocketClient() {
	}

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("✅ Connecté au WebSocket");
		driver = BrowserManager.getDriver();

	}

	@OnMessage
	public void onMessage(String message, Session session) {
		long startTime = System.currentTimeMillis();
		String status = "ÉCHEC";
		String logMessage = "";
		String scenarioName = "Scénario inconnu";

		try {

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> receivedMessage = objectMapper.readValue(message, Map.class);
			String type = (String) receivedMessage.get("type");
			System.out.println("📩 Message reçu : " + message);
			System.out.println("🔹 Type du message : " + type);
			try {

				JSONObject json = new JSONObject(message);
				String scenarioStr = json.optString("scenario", null);

				if (scenarioStr != null) {
					JSONObject scenario = new JSONObject(scenarioStr);

					if (scenario.has("name")) {
						String scenarioName2 = scenario.getString("name");
						System.out.println("Nom du scénario : " + scenarioName);
						scenarioName = scenarioName2; // ou autre traitement
					} else {
						System.out.println("⚠️ Le champ 'name' est manquant dans le scénario.");
					}
				} else {
					System.out.println("⚠️ Le champ 'scenario' est manquant ou nul.");
				}

			} catch (Exception e) {
				System.out.println("❌ Erreur lors du parsing JSON : " + e.getMessage());
			}

			if ("NAVIGATION".equalsIgnoreCase(type)) {
				NavigationHandler.handleNavigation(driver, receivedMessage, session);

//            	try {
//        		String url=(String) receivedMessage.get("url");
//            	System.out.println("navigation vers "+ url);
//            	driver.get(url);
//            	
//            	try {
//                  List<Map<String, Object>> inputs =InputExtractor.extractInputs(driver);
//                  if (!inputs.isEmpty()) {
//                      String jsonMessage = new ObjectMapper().writeValueAsString(inputs);
//                      session.getBasicRemote().sendText(jsonMessage);
//                      status = "SUCCÈS";
//                      logMessage = "Navigation réussie.";
//
//                  }
//              } catch (Exception e) {
//                  e.printStackTrace();
//              }}
//            	catch(UnhandledAlertException uae) {
//                    String alertText = "";
//                    try {
//                        Alert alert = driver.switchTo().alert();
//                        alertText = alert.getText();
//                        alert.accept();
//                    } catch (NoAlertPresentException ignored) {}
//                    status = "ÉCHEC";
//                    logMessage = "Alerte détectée pendant navigation : " + alertText;
//
//            	}
			} else if ("FILL_FORM".equalsIgnoreCase(type)) {
				try {
					// parsing du scénario
					Map<String, Object> scenario = objectMapper.readValue((String) receivedMessage.get("scenario"),
							Map.class);
					driver.get((String) scenario.get("url"));
					// on récupère la liste d'inputs
					List<Map<String, Object>> inputs = (List<Map<String, Object>>) scenario.get("inputs");

					// délègue tout le remplissage et la gestion d’alertes :
					logMessage = FormFIller.fillForm(driver, inputs);
					// Récupérer le chemin du fichier Excel
					String FilePath = null;
					for (Map<String, Object> input : inputs) {
						Object value=input.get("value"); 
						Object name=input.get("name");  

						if (("userFilePath".equals(input.get("name")) && value !=null) || ("compteFilePath".equals(input.get("name"))&& value !=null)) {
						    FilePath = (String) input.get("value");
						    System.out.println("FilePath"+ FilePath);
						    break;
						}

					}

					// Compter le nombre de lignes de données dans le fichier Excel
					if (FilePath != null) {
						int duplicates = 0;
						int total = 0;

						try (FileInputStream fis = new FileInputStream(FilePath);
								Workbook workbook = WorkbookFactory.create(fis)) {
							Sheet sheet = workbook.getSheetAt(0);
							// PhysicalNumberOfRows inclut l'en-tête, on soustrait 1 si la première ligne
							// est un header
							total = sheet.getPhysicalNumberOfRows() - 1;
							Pattern pattern = Pattern.compile("email déjà utilisé|Compte avec CIN \\d+ existe déjà", Pattern.CASE_INSENSITIVE);
							System.out.println("total " + total);
							System.out.println("pattern " + pattern);
							if (pattern != null) {
								Matcher matcher = pattern.matcher(logMessage);
								while (matcher.find()) {
									duplicates++;
								}
								;

								int successess = total - duplicates;
								int successPour = total > 0 ? (int) Math.round((successess * 100.0) / total) : 0;
								if (successPour == 0) {
									status = "ÉCHEC";
								} else {
									status = "SUCCÈS AVEC " + successPour + "%";
									System.out.println(status);
								}
							}
						} catch (Exception e) {
							System.err.println("Erreur lecture Excel : " + e.getMessage());
							total = 0;
						}
					} else {
						System.err.println("Chemin Excel introuvable dans inputs");
						status = logMessage.startsWith("ÉCHEC") ? "ÉCHEC" : "SUCCÈS";

					}
				} catch (IOException e) {
					logMessage = "ÉCHEC – parsing scénario : " + e.getMessage();
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		long durationMs = System.currentTimeMillis() - startTime;
		String durationFormatted = (durationMs / 1000.0) + "s";
		// Enregistrement dans le rapport
		ReportManager.addReportEntry(scenarioName, status, logMessage, durationFormatted);
	}

//                String scenarioJson = (String) receivedMessage.get("scenario"); // ⚠️ Ceci est une chaîne JSON
//
//            	Map<String, Object> scenario=objectMapper.readValue(scenarioJson, Map.class);
//            	String url=(String) scenario.get("url");
//            	List<Map<String, Object>> inputs=(List<Map<String,Object>>) scenario.get("inputs");
//                driver.get(url);
//              
//                // 👉 Remplir les champs du formulaire
//                WebElement submitButton = null;
//                List<WebElement> submitButtons = new ArrayList<>();
//
//                String submitName =null;
//                for (Map<String, Object> input : inputs) {
//                	pause(100);
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
//                    	try {
//                    	submitName=name;
//                    	if("Désactivé".equals(name)||"Activé".equals(name)) {
//
//                    	    String xpath = String.format(
//                    	            "//input[@value='%1$s'] | //button[normalize-space(text())='%1$s'] | //span[normalize-space(text())='%1$s']",
//                    	            name
//                    	        );
//                    	        submitButtons = driver.findElements(By.xpath(xpath));}
//
//                    	
//                    	else {
//                    	submitButton = driver.findElement(By.xpath("//input[@value='"+name+"']| //button[text()='"+name+"'] |  //span[text()='" + name + "']"));}}
//                    	 catch (Exception e) {
//                         	System.out.println("Erreur lors de l'exécution : " + e.getMessage());
//                             logMessage = "Erreur lors de l'exécution : " + e.getMessage();
//     					}
//                    	
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
//                	pause(100);
//                    System.out.println("🚀 Clic final sur le bouton submit : "+submitName);
//                    try {
//                    	
//                    submitButton.click();
//                    status = "SUCCÈS";
//                    logMessage = "Formulaire rempli et button cliquer";}
//                    catch (Exception e) {
//                    	System.out.println("Erreur lors de l'exécution : " + e.getMessage());
//                        logMessage = "Erreur lors de l'exécution : " + e.getMessage();
//					}}
//                else if (submitButtons!=null) {
//                	for(WebElement btn : submitButtons) {
//                		try {
//							System.out.println("🚀 Clic sur bouton : " + btn.getText());
//					        btn.click();
//					        pause(100);
//						} catch (Exception e) {
//							 System.err.println("⚠️ Impossible de cliquer sur un bouton '"+ btn + "' : " + e.getMessage());
//		                     logMessage = "Erreur lors de l'exécution : " + e.getMessage();
//						}
//                	
//                }
//                    status = "SUCCÈS";
//                    logMessage = "Formulaire rempli et button cliquer";
//
//                }

//                catch(UnhandledAlertException uae) {
//                    String alertText = "";
//                    try {
//                        alertText = driver.switchTo().alert().getText();
//                        driver.switchTo().alert().accept(); // ou dismiss()
//                    } catch (NoAlertPresentException ignored) {}
//                    status = "ÉCHEC";
//                    logMessage = "Alerte JavaScript détectée : " + alertText;
//
//                }
//                }
//            else {
//                System.out.println("⚠️ Type inconnu : " + type);
//            }
//            }

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		System.out.println("❌ WebSocket fermé : " + reason);
		if (driver != null) {
			driver.quit();
		}
	}

	public class WebSocketUtils {
		private final ObjectMapper objectMapper = new ObjectMapper();

		public void sendMessage(Session session, List<Map<String, String>> inputs) {
			try {
				String jsonMessage = objectMapper.writeValueAsString(inputs);
				session.getBasicRemote().sendText(jsonMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// public static void main(String[] args) {
		// try {
		// WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		// container.connectToServer(WebSocketClient.class, new
		// URI("ws://localhost:8082/ws/api"));

		// synchronized (WebSocketClient.class) {
		// WebSocketClient.class.wait();
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
	}

	public static void main(String[] args) {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(WebSocketClient.class, new URI("ws://localhost:8082/ws/api"));
			synchronized (WebSocketClient.class) {
				WebSocketClient.class.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void pause(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

//private List<Map<String, Object>> extractInputs() {
//    List<WebElement> inputs = driver.findElements(By.xpath("//input[@name and @type]"));
//    List<WebElement> selects=driver.findElements(By.xpath("//select[@name]"));
//    List<Map<String, Object>> inputList = new ArrayList<>();
//    List<WebElement> buttons = driver.findElements(By.xpath("//button | //input[@type='submit' or @type='button']"));
//
//    for (WebElement input : inputs) {
//        Map<String, Object> field = new HashMap<>();
//        field.put("name", input.getDomAttribute("name"));
//        field.put("type", input.getDomAttribute("type"));
//        field.put("placeholder", input.getDomAttribute("placeholder"));
//        inputList.add(field);
//    }
//   for(WebElement select:selects) {
//	   Map<String, Object> field=new HashMap<>();
//	   field.put("type", "select");
//	   field.put("name", select.getDomAttribute("name"));
//	   
//	   List<WebElement> options=select.findElements(By.tagName("option"));
//	   List<String>optionValues= new ArrayList<>();
//	   for(WebElement option:options) {
//		   optionValues.add(option.getText().trim());
//	   }
//	   field.put("options",optionValues);
//	   inputList.add(field);
//   }
//   // 🔹 Extraction des boutons
//   for (WebElement button : buttons) {
//       Map<String, Object> field = new HashMap<>();
//       field.put("type", button.getTagName().equals("button") ? "button" : button.getDomAttribute("type"));
//       field.put("name", button.getDomAttribute("name"));
//       String text = button.getTagName().equals("button") ? button.getText().trim() : button.getDomAttribute("value");
//
//       field.put("text", text); // Texte affiché sur le bouton
//       inputList.add(field);
//   }
//    System.out.println("inputList"+inputList);
//    return inputList;
//}
}
// 👉 Ouvrir l'URL

// 🔹 Envoyer les inputs via WebSocket
//if (!inputs.isEmpty()) {
//   String jsonMessage = objectMapper.writeValueAsString(inputs);
//   session.getBasicRemote().sendText(jsonMessage);
//   System.out.println("📤 Inputs envoyés au serveur : " + jsonMessage);
//} else {
//   System.out.println("⚠️ Aucun input détecté !");
//}

//catch (Exception e) {
//e.printStackTrace();
//System.out.println("❌ Erreur lors du traitement du message WebSocket");
//}
//driver = new ChromeDriver();
//
//System.out.println("📩 Message reçu : " + message);
//driver.get(message);
//try {
//List<Map<String, String>> inputs = extractInputs();
//if (!inputs.isEmpty()) {
//String jsonMessage = new ObjectMapper().writeValueAsString(inputs);
//session.getBasicRemote().sendText(jsonMessage);
//}
//} catch (Exception e) {
//e.printStackTrace();
//}
