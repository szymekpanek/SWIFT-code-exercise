package panek.szymon.swiftcodeintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panek.szymon.swiftcodeintern.model.SwiftCode;

import java.util.List;

public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {
    List<SwiftCode> findByCountryISO2(String upperCase);

    SwiftCode findBySwiftCode(String swiftCode);

    List<SwiftCode> findBySwiftCodeStartingWith(String swiftCodePrefix);
}
