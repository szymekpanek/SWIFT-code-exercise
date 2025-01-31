package panek.szymon.swiftcodeintern.service;

import org.springframework.stereotype.Service;
import panek.szymon.swiftcodeintern.model.SwiftCode;
import panek.szymon.swiftcodeintern.repository.SwiftCodeRepository;

import java.util.List;

@Service
public class SwiftService {
    private final SwiftCodeRepository repository;

    public SwiftService(SwiftCodeRepository repository) {
        this.repository = repository;
    }

    public List<SwiftCode> getSwiftCodesByCountry(String countryISO2) {
        if (countryISO2 == null) {
            throw new IllegalArgumentException("Country ISO code must not be null");
        }
        return repository.findByCountryISO2(countryISO2.toUpperCase());
    }

    public SwiftCode getSwiftCode(String swiftCode) {
        return repository.findBySwiftCode(swiftCode);
    }

    public List<SwiftCode> getBranchesForHeadquarter(String swiftCode) {
        String headquarterPrefix = swiftCode.substring(0, 8);
        return repository.findBySwiftCodeStartingWithAndIsHeadquarter(headquarterPrefix, false);
    }

    public void saveSwiftCode(SwiftCode swiftCode) {
        swiftCode.setHeadquarter(swiftCode.getSwiftCode().endsWith("XXX"));
        repository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        repository.deleteById(swiftCode);
    }
}
