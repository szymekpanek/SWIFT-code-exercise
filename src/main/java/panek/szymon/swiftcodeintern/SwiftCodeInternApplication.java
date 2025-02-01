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
            String filePath;

            if (System.getenv("RUNNING_IN_DOCKER") != null) {
                filePath = "/app/data/Interns_2025_SWIFT_CODES.xlsx";
            } else {
                filePath = "src/main/resources/data/Interns_2025_SWIFT_CODES.xlsx";
            }

            importer.importFromExcel(filePath);
            System.out.println("Data from Excel file was successfully added to the database");
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(SwiftCodeInternApplication.class, args);
    }


}
