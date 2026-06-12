package no.nav.oebs.digihot.common.mcd;

import no.nav.oebs.digihot.config.common.mdc.MdcOperations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;

class McdOperationsTest {

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void put_shouldSetValueInMdc() {
        MdcOperations.put(MdcOperations.MDC_CORRELATION_ID, "test-id");

        assertEquals("test-id", MDC.get(MdcOperations.MDC_CORRELATION_ID));
    }

    @Test
    void get_shouldReturnValueFromMdc() {
        MDC.put(MdcOperations.MDC_CORRELATION_ID, "test-id");

        assertEquals("test-id", MdcOperations.get(MdcOperations.MDC_CORRELATION_ID));
    }

    @Test
    void get_shouldReturnNullWhenKeyNotSet() {
        assertNull(MdcOperations.get(MdcOperations.MDC_CORRELATION_ID));
    }

    @Test
    void remove_shouldRemoveValueFromMdc() {
        MDC.put(MdcOperations.MDC_CORRELATION_ID, "test-id");

        MdcOperations.remove(MdcOperations.MDC_CORRELATION_ID);

        assertNull(MDC.get(MdcOperations.MDC_CORRELATION_ID));
    }

    @Test
    void generateCorrelationId_shouldReturnNonNullId() {
        String correlationId = MdcOperations.generateCorrelationId();

        assertNotNull(correlationId);
    }

    @Test
    void generateCorrelationId_shouldContainDash() {
        String correlationId = MdcOperations.generateCorrelationId();

        assertTrue(correlationId.contains("-"));
    }

    @Test
    void generateCorrelationId_shouldGenerateUniqueIds() {
        String first = MdcOperations.generateCorrelationId();
        String second = MdcOperations.generateCorrelationId();

        assertNotEquals(first, second);
    }
}
