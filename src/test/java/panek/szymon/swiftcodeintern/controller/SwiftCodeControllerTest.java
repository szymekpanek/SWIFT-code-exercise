package panek.szymon.swiftcodeintern.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import panek.szymon.swiftcodeintern.model.SwiftCode;

import panek.szymon.swiftcodeintern.service.SwiftService;

import java.util.List;


class SwiftCodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SwiftService service;

    @InjectMocks
    private SwiftCodeController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetSwiftCodesByCountry() throws Exception {
        SwiftCode swiftCode1 = new SwiftCode("TESTBANK001", "Test Bank", "United States", "US", false, "123 Main St");
        SwiftCode swiftCode2 = new SwiftCode("TESTBANK002", "Test Bank", "United States", "US", false, "456 Main St");

        when(service.getSwiftCodesByCountry("US")).thenReturn(List.of(swiftCode1, swiftCode2));

        mockMvc.perform(get("/v1/swift-codes/country/US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value("US"))
                .andExpect(jsonPath("$.swiftCodes.length()").value(2));
    }

    @Test
    void testGetSwiftCode() throws Exception {
        SwiftCode swiftCode = new SwiftCode("TESTBANKXXX", "Test Bank HQ", "United States", "US", true, "HQ Address");

        when(service.getSwiftCode("TESTBANKXXX")).thenReturn(swiftCode);

        mockMvc.perform(get("/v1/swift-codes/TESTBANKXXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.headquarter.swiftCode").value("TESTBANKXXX"))
                .andExpect(jsonPath("$.headquarter.headquarter").value(true));
    }

    @Test
    void testGetSwiftCode_Branch() throws Exception {
        SwiftCode swiftCode = new SwiftCode("TESTBANK001", "Test Bank Branch 1", "United States", "US", false, "Branch Address");

        when(service.getSwiftCode("TESTBANK001")).thenReturn(swiftCode);

        mockMvc.perform(get("/v1/swift-codes/TESTBANK001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("TESTBANK001"))
                .andExpect(jsonPath("$.headquarter").value(false));


    }

    @Test
    void testAddSwiftCode() throws Exception {
        SwiftCode swiftCode = new SwiftCode("TESTBANKXXX", "Test Bank HQ", "United States", "US", false, "HQ Address");

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(swiftCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code added successfully"));

        verify(service, times(1)).saveSwiftCode(any(SwiftCode.class));
    }
}
