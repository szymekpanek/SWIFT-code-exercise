package panek.szymon.swiftcodeintern.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import panek.szymon.swiftcodeintern.model.SwiftCode;
import panek.szymon.swiftcodeintern.repository.SwiftCodeRepository;

import java.util.List;

class SwiftCodeServiceTest {
    private AutoCloseable closeable;

    @Mock
    private SwiftCodeRepository repository;

    @InjectMocks
    private SwiftService service;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetSwiftCodesByCountry() {
        SwiftCode swiftCode1 = new SwiftCode("TESTBANK001", "Test Bank", "United States", "US", false, "123 Main St");
        SwiftCode swiftCode2 = new SwiftCode("TESTBANK002", "Test Bank", "Albania", "AL", false, "456 Main St");

        when(repository.findByCountryISO2("US")).thenReturn(List.of(swiftCode1));

        List<SwiftCode> result = service.getSwiftCodesByCountry("US");

        assertEquals(1, result.size());
        verify(repository, times(1)).findByCountryISO2("US");
    }

    @Test
    void testGetSwiftCode() {
        SwiftCode swiftCode = new SwiftCode("TESTBANKXXX", "Test Bank HQ", "United States", "US", true, "HQ Address");

        when(repository.findBySwiftCode("TESTBANKXXX")).thenReturn(swiftCode);

        SwiftCode result = service.getSwiftCode("TESTBANKXXX");

        assertNotNull(result);
        assertEquals("TESTBANKXXX", result.getSwiftCode());
        assertTrue(result.isHeadquarter());
    }

    @Test
    void testSaveSwiftCode() {
        SwiftCode swiftCode = new SwiftCode("TESTBANKXXX", "Test Bank HQ", "United States", "US", false, "HQ Address");

        service.saveSwiftCode(swiftCode);

        assertTrue(swiftCode.isHeadquarter());
        verify(repository, times(1)).save(swiftCode);
    }
}
