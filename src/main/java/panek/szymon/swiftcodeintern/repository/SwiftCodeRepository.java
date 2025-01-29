package panek.szymon.swiftcodeintern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panek.szymon.swiftcodeintern.model.SwiftCode;

public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {
}
