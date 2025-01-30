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
        return repository.findByCountryISO2(countryISO2.toUpperCase());
    }

    public SwiftCode getSwiftCode(String swiftCode) {
        return repository.findBySwiftCode(swiftCode);
    }

    public List<SwiftCode> getBranchesForHeadquarter(String swiftCode) {
        String headquarterPrefix = swiftCode.substring(0, 8);
        System.out.println("Headquarter Prefix: " + headquarterPrefix);
        List<SwiftCode> branches = repository.findBySwiftCodeStartingWith(headquarterPrefix);
        System.out.println("Branches: " + branches);
        return branches;
    }

    public void saveSwiftCode(SwiftCode swiftCode) {
        repository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        repository.deleteById(swiftCode);
    }
}
