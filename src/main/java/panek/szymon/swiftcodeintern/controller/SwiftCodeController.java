package panek.szymon.swiftcodeintern.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import panek.szymon.swiftcodeintern.model.SwiftCode;
import panek.szymon.swiftcodeintern.repository.SwiftCodeRepository;
import panek.szymon.swiftcodeintern.service.SwiftService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    private final SwiftCodeRepository swiftCodeRepository;
    private final SwiftService service;

    public SwiftCodeController(SwiftCodeRepository swiftCodeRepository, SwiftService service) {
        this.swiftCodeRepository = swiftCodeRepository;
        this.service = service;
    }

    @GetMapping("/getAll")// pomocniczy endpoint
    public List<SwiftCode> getAllSwiftCodes() {
        return swiftCodeRepository.findAll();
    }


    // napraw aby pokazywało oddziały
    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<?> getSwiftCodesByCountry(@PathVariable String countryISO2) {
        List<SwiftCode> codes = service.getSwiftCodesByCountry(countryISO2);
        return ResponseEntity.ok(Map.of("countryISO2", countryISO2, "swiftCodes", codes));
    }

    @GetMapping("/{swiftCode}")
    public ResponseEntity<?> getSwiftCode(@PathVariable String swiftCode) {
        SwiftCode code = service.getSwiftCode(swiftCode);
        if (code.isHeadquarter()) {
            List<SwiftCode> branches = service.getBranchesForHeadquarter(swiftCode);
            return ResponseEntity.ok(Map.of("headquarter", code, "branches", branches));
        }
        return ResponseEntity.ok(code);
    }

    @PostMapping
    public ResponseEntity<?> addSwiftCode(@RequestBody SwiftCode swiftCode) {
        service.saveSwiftCode(swiftCode);
        return ResponseEntity.ok(Map.of("message", "SWIFT code added successfully"));
    }
    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<?> deleteSwiftCode(@PathVariable String swiftCode) {
        service.deleteSwiftCode(swiftCode);
        return ResponseEntity.ok(Map.of("message", "SWIFT code deleted successfully"));
    }


}

