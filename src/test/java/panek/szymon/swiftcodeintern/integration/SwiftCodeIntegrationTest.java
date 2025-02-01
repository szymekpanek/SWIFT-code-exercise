package panek.szymon.swiftcodeintern.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import panek.szymon.swiftcodeintern.model.SwiftCode;
import panek.szymon.swiftcodeintern.repository.SwiftCodeRepository;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SwiftCodeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SwiftCodeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddAndGetSwiftCode() throws Exception {
        repository.deleteAll();

        SwiftCode swiftCode = new SwiftCode("TESTBANKXXX", "Test Bank HQ", "United States", "US", false, "HQ Address");


        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(swiftCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code added successfully"));


        mockMvc.perform(get("/v1/swift-codes/TESTBANKXXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.headquarter.swiftCode").value("TESTBANKXXX"))
                .andExpect(jsonPath("$.headquarter.headquarter").value(true))
                .andExpect(jsonPath("$.branches", hasSize(0)));
    }

    @Test
    void testGetSwiftCodesByCountry() throws Exception {
        repository.deleteAll();

        SwiftCode swiftCode1 = new SwiftCode("TESTBANK001", "Test Bank 1", "United States", "US", false, "Address 1");
        SwiftCode swiftCode2 = new SwiftCode("TESTBANK002", "Test Bank 2", "United States", "US", false, "Address 2");
        repository.saveAll(List.of(swiftCode1, swiftCode2));

        mockMvc.perform(get("/v1/swift-codes/country/US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value("US"))
                .andExpect(jsonPath("$.swiftCodes", hasSize(2)));
    }

    @Test
    void testDeleteSwiftCode() throws Exception {
        repository.deleteAll();

        repository.save(new SwiftCode("DELETEBANKXXX", "Delete Bank", "United States", "US", true, "Delete Address"));

        mockMvc.perform(post("/v1/swift-codes/DELETEBANKXXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code deleted successfully"));
    }

    @Test
    void testDeleteNonExistentSwiftCode() throws Exception {
        repository.deleteAll();

        mockMvc.perform(post("/v1/swift-codes/NONEXISTENT"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("SWIFT code not found"));
    }
}
