//package utils;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//import org.apache.poi.ss.usermodel.*;
//
//public class ExcelUtils {
//    private static Workbook workbook;
//    private static Sheet sheet;
//
//    // Charger le fichier Excel
//    public static void setExcelFile(String filePath, String sheetName) throws IOException {
//        FileInputStream file = new FileInputStream(filePath);
//        workbook = WorkbookFactory.create(file);
//        sheet = workbook.getSheet(sheetName);
//    }
//
//    // Lire une valeur d'une cellule (ligne et colonne spécifiques)
//    public static String getCellData(int rowNum, int colNum) {
//        Cell cell = sheet.getRow(rowNum).getCell(colNum);
//        if (cell.getCellType() == CellType.STRING) {
//            return cell.getStringCellValue();
//        } else if (cell.getCellType() == CellType.NUMERIC) {
//            return String.valueOf((int) cell.getNumericCellValue());
//        } else {
//            return "";
//        }
//    }
//
//    // Fermer le fichier
//    public static void closeExcelFile() throws IOException {
//        workbook.close();
//    }
//    public static int getRowCount() throws IOException {
//        return sheet.getLastRowNum() + 1; // Nombre de lignes (ajout de 1 car indexé à 0)
//    }
//}
//
