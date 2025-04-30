package WebSocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static final String REPORT_PATH = "rapports/rapport.html";
    
    // Template pour le header HTML (début du fichier)
    private static final String HEADER =
            "<!DOCTYPE html>\n" +
            "<html lang=\"fr\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Rapport d'exécution des scénarios</title>\n" +
            "    <style>\n" +
            "        body { font-family: Arial, sans-serif; margin: 20px; }\n" +
            "        table { width: 100%; border-collapse: collapse; }\n" +
            "        th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }\n" +
            "        .success { background-color: #d4edda; }\n" +
            "        .failed { background-color: #f8d7da; }\n" +
            "        .warning { background-color: #fff3cd; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>Rapport d'exécution des scénarios</h1>\n" +
            "    <table>\n" +
            "        <thead>\n" +
            "            <tr>\n" +
            "                <th>Scénario</th>\n" +
            "                <th>Date & Heure</th>\n" +
            "                <th>Statut</th>\n" +
            "                <th>Message</th>\n" +
            "                <th>Durée</th>\n" +
            "                <th>Capture</th>\n" +
            "            </tr>\n" +
            "        </thead>\n" +
            "        <tbody>\n";

    // Template pour la fin du fichier HTML
    private static final String FOOTER =
            "        </tbody>\n" +
            "    </table>\n" +
            "</body>\n" +
            "</html>";

   
    public static synchronized void addReportEntry(String scenarioName,String status, String message,String duration) {
        try {
            File file = new File(REPORT_PATH);
            // Créer le dossier s'il n'existe pas
            file.getParentFile().mkdirs();
            
            // Si le fichier n'existe pas encore, le créer avec le header et le footer.
            if (!file.exists()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(HEADER + FOOTER);
                }
            }
            
            // Lire le contenu existant du fichier
            String content = readFile(file);
            
            // Chercher l'endroit où insérer la nouvelle ligne (avant le tag de fermeture </tbody>)
            int index = content.lastIndexOf("</tbody>");
            if(index == -1) {
                System.err.println("Le rapport HTML n'est pas structuré correctement.");
                return;
            }
            
            // Générer la date actuelle
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            
            // Déterminer la classe CSS en fonction du statut
            String cssClass = getCssClass(status);
            
            // Créer la nouvelle ligne (tr)
            String newRow = String.format(
                    "            <tr class=\"%s\">\n" +
                   "                <td>%s</td>\n" +
                  //  "                <td>%s</td>\n" +
                    "                <td>%s</td>\n" +
                    "                <td>%s</td>\n" +
                    "                <td>%s</td>\n" +
                    "                <td>%s</td>\n" +
                    "            </tr>\n",
                    cssClass,
                    scenarioName,
                    date,
                    status,
                    message
                    ,duration
                   // (capturePath != null && !capturePath.isEmpty()) ? "<a href=\"" + capturePath + "\">Voir</a>" : ""
            );
            
            // Insérer la nouvelle ligne avant la balise de fermeture </tbody>
            String newContent = content.substring(0, index) + newRow + content.substring(index);
            
            // Réécrire l'ensemble du fichier avec la nouvelle ligne ajoutée
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(newContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retourne une classe CSS en fonction du status.
     */
    private static String getCssClass(String status) {
        if (status == null) {
            return "";
        }
        String stat = status.toUpperCase();
        if (stat.equals("SUCCÈS") || stat.equals("SUCCESS")) {
            return "success";
        } else if (stat.equals("ÉCHEC") || stat.equals("FAIL") || stat.equals("FAILED")) {
            return "failed";
        } else if (stat.equals("AVERTISSEMENT") || stat.equals("WARNING")) {
            return "warning";
        }
        return "";
    }
    
    /**
     * Lit le contenu d'un fichier et le retourne sous forme de String.
     */
    private static String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();
        return builder.toString();
    }
}
