package panek.szymon.swiftcodeintern;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import panek.szymon.swiftcodeintern.service.SwiftCodeExcelImporter;

@SpringBootApplication
public class SwiftCodeInternApplication {
    private final SwiftCodeExcelImporter importer;

    public SwiftCodeInternApplication(SwiftCodeExcelImporter importer) {
        this.importer = importer;
    }

    @Bean
    CommandLineRunner runOnStartup() {
        return args -> {
            String filePath = "src/main/resources/Interns_2025_SWIFT_CODES.xlsx"; // Ścieżka do pliku Excel
            importer.importFromExcel(filePath);
            System.out.println("Dane z pliku Excel zostały zaimportowane do bazy.");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SwiftCodeInternApplication.class, args);
    }


}
