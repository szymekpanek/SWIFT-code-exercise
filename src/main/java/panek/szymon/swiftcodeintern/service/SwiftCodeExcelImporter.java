package panek.szymon.swiftcodeintern.service;

import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import panek.szymon.swiftcodeintern.model.SwiftCode;
import panek.szymon.swiftcodeintern.repository.SwiftCodeRepository;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SwiftCodeExcelImporter {
    private final SwiftCodeRepository repository;

    public SwiftCodeExcelImporter(SwiftCodeRepository repository) {
        this.repository = repository;
    }

    public void importFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

//            if (!validateHeaders(sheet)) {
//                throw new RuntimeException("Nagłówki w pliku Excel są niepoprawne.");
//            }

            List<SwiftCode> swiftCodes = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String countryISO2 = getCellValue(row, ExcelColumn.COUNTRY_ISO2_CODE).toUpperCase();
                String swiftCode = getCellValue(row, ExcelColumn.SWIFT_CODE);
                String bankName = getCellValue(row, ExcelColumn.NAME);
                String address = getCellValue(row, ExcelColumn.ADDRESS);
                String countryName = getCellValue(row, ExcelColumn.COUNTRY_NAME).toUpperCase();

                boolean isHeadquarter = swiftCode.endsWith("XXX");

                SwiftCode code = new SwiftCode(swiftCode, bankName, countryName, countryISO2, isHeadquarter, address);
                swiftCodes.add(code);
            }
            repository.saveAll(swiftCodes);
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas odczytu pliku Excel: " + e.getMessage(), e);
        }
    }

//    private boolean validateHeaders(Sheet sheet) {
//        Row headerRow = sheet.getRow(0);
//        if (headerRow == null) {
//            return false;
//        }
//
//        for (ExcelColumn column : ExcelColumn.values()) {
//            Cell cell = headerRow.getCell(column.getIndex());
//            String header = (cell != null) ? cell.getStringCellValue().trim() : "";
//
//            if (!header.equalsIgnoreCase(column.name())) {
//                return false;
//            }
//        }
//
//        return true;
//    }

    private String getCellValue(Row row, ExcelColumn column) {
        Cell cell = row.getCell(column.getIndex());
        return cell != null ? cell.getStringCellValue().trim() : "";
    }
}


enum ExcelColumn {
    COUNTRY_ISO2_CODE(0),
    SWIFT_CODE(1),
    CODE_TYPE(2),
    NAME(3),
    ADDRESS(4),
    TOWN_NAME(5),
    COUNTRY_NAME(6),
    TIME_ZONE(7);

    private final int index;

    public int getIndex() {
        return index;
    }

    ExcelColumn(int index) {
        this.index = index;
    }

}