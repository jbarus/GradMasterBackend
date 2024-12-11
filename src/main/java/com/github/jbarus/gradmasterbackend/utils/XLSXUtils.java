package com.github.jbarus.gradmasterbackend.utils;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XLSXUtils {
    public static List<List<String>> convertXSLXToList(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        List<List<String>> workbookData = new ArrayList<>();
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(formatter.formatCellValue(cell));
            }
            workbookData.add(rowData);
        }
        return workbookData;
    }

    public static List<UniversityEmployee> convertRawDataToUniversityEmployee(List<List<String>> workbookData) {
        List<UniversityEmployee> universityEmployees = new ArrayList<>();
        for (List<String> rowData : workbookData) {
            universityEmployees.add(new UniversityEmployee(
                    rowData.get(1),
                    rowData.get(0),
                    convertStringToBoolean(rowData.get(2)),
                    LocalTime.parse(rowData.get(3), DateTimeFormatter.ofPattern("h:mm:ss a")),
                    LocalTime.parse(rowData.get(4), DateTimeFormatter.ofPattern("h:mm:ss a")),
                    Integer.parseInt(rowData.get(5))
            ));
        }
        return universityEmployees;
    }

    public static HashMap<String, List<Student>> getStudentReviewerMapping(List<List<String>> workbookData) {
        HashMap<String, List<Student>> studentsByReviewer = new HashMap<>();

        for(List<String> student : workbookData) {
            studentsByReviewer.computeIfAbsent(student.get(3), k -> new ArrayList<>()).add(new Student(student.get(1),student.get(0)));
        }

        return studentsByReviewer;
    }

    public static HashMap<LocalDate, List<List<String>>> splitByDates(List<List<String>> workbookData, int dateIndex){
        HashMap<LocalDate, List<List<String>>> dates = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (List<String> rowData : workbookData) {
            LocalDate date = LocalDate.parse(rowData.get(dateIndex), formatter);
            dates.computeIfAbsent(date, k -> new ArrayList<>()).add(rowData);
        }
        return dates;
    }

    private static boolean convertStringToBoolean(String booleanValue) {
        return booleanValue.equalsIgnoreCase("Tak");
    }

    public static HashMap<String, List<Student>> convertRawDataToStudentHashMapByUniversityEmployee(List<List<String>> value) {
        HashMap<String, List<Student>> studentHashMap = new HashMap<>();
        for (List<String> rowData : value) {
            studentHashMap.computeIfAbsent(rowData.get(3), k -> new ArrayList<>()).add(new Student(rowData.get(1),rowData.get(0)));
        }
        return studentHashMap;
    }
}
