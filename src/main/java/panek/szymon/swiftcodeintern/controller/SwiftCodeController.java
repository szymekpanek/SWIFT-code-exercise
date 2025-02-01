package panek.szymon.swiftcodeintern.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import panek.szymon.swiftcodeintern.model.SwiftCode;
import panek.szymon.swiftcodeintern.service.SwiftService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    private final SwiftService service;

    public SwiftCodeController(SwiftService service) {
        this.service = service;
    }

    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<?> getSwiftCodesByCountry(@PathVariable String countryISO2) {
        List<SwiftCode> codes = service.getSwiftCodesByCountry(countryISO2);
        if (codes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String countryName = codes.get(0).getCountryName();

        return ResponseEntity.ok(Map.of(
                "countryISO2", countryISO2,
                "countryName", countryName,
                "swiftCodes", codes
        ));
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
    public ResponseEntity<?> addSwiftCode(@Validated @RequestBody SwiftCode swiftCode) {
        service.saveSwiftCode(swiftCode);
        return ResponseEntity.ok(Map.of("message", "SWIFT code added successfully"));
    }

    @PostMapping("/{swiftCode}")
    public ResponseEntity<?> deleteSwiftCode(@PathVariable String swiftCode) {
        SwiftCode code = service.getSwiftCode(swiftCode);

        if (code == null) {
            return ResponseEntity.status(404).body(Map.of("error", "SWIFT code not found"));
        }

        service.deleteSwiftCode(swiftCode);
        return ResponseEntity.ok(Map.of("message", "SWIFT code deleted successfully"));
    }



}

