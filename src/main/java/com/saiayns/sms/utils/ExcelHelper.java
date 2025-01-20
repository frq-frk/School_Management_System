package com.saiayns.sms.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;

public class ExcelHelper {
	public static List<Student> parseStudentExcelFile(InputStream is) {
        List<Student> studentList = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Student studentDetail = new Student();
                studentDetail.setName(row.getCell(0).getStringCellValue());
                studentDetail.setStudentClass(StudentClass.valueOf(row.getCell(1).getStringCellValue()));
                studentDetail.setGuardianName(row.getCell(2).getStringCellValue());
                studentDetail.setGuardianPhone(String.valueOf(row.getCell(3).getNumericCellValue()));
                studentList.add(studentDetail);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
        return studentList;
    }
}
