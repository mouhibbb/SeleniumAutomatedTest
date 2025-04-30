package WebSocket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConvertToIsoDate {

    public static String convertToIsoDate(String date) {
        try {
            // 📌 Vérifie si la date est déjà au format "YYYY-MM-DD"
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return date;
            }

            // 🔄 Conversion "JJ/MM/AAAA" → "AAAA-MM-JJ"
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            return outputFormatter.format(localDate);
        } catch (DateTimeParseException e) {
            System.out.println("⚠️ Erreur : Format de date invalide - " + date);
            return date;
        }
    }
}
