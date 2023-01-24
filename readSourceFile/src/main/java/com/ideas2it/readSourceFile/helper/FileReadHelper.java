package com.ideas2it.readXlsxFile.helper;

import com.ideas2it.readXlsxFile.model.ExcelRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

public class FileReadHelper {

    //check if file is of Excel type or not
    public static boolean checkFileFormat(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    //converts excel to list of Patients
    public static List<ExcelRecord> convertExcelToEhrFormat(MultipartFile file) {
        List<ExcelRecord> records = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        Map<String, Object> patientAttributes = new HashMap<>();
        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            int numberOfSheets = workbook.getNumberOfSheets();
            System.out.println(numberOfSheets);
            XSSFSheet sheet = workbook.getSheet("sheet1");
            int rowNumber = 0;
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.iterator();
                if (rowNumber == 0) {
                    while (cellIterator.hasNext()) {
                        headers.add(String.valueOf(cellIterator.next()));
                    }
                    rowNumber++;
                    continue;
                }
                for (String header : headers) {
                    Cell cell = cellIterator.next();
                    patientAttributes.put(header, String.valueOf(cell));
                }
                ExcelRecord excelRecord = new ExcelRecord();
                excelRecord.setFileName(file.getOriginalFilename());
                excelRecord.setRowNumber(row.getRowNum());
                excelRecord.setSheetName(sheet.getSheetName());
                excelRecord.setPatientAttributes(patientAttributes);
                records.add(excelRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;

    }
}
