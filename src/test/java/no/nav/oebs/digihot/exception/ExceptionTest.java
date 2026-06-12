package no.nav.oebs.digihot.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    @Nested
    class JsonMappingExceptionTests {

        @Test
        void constructor_withCause_storesCause() {
            Exception cause = new RuntimeException("original error");

            JsonMappingException ex = new JsonMappingException(cause);

            assertNotNull(ex);
            assertEquals(cause, ex.getCause());
        }

        @Test
        void constructor_withMessageAndCause_storesMessageAndCause() {
            Exception cause = new RuntimeException("original error");
            String message = "JSON mapping failed";

            JsonMappingException ex = new JsonMappingException(message, cause);

            assertEquals(message, ex.getMessage());
            assertEquals(cause, ex.getCause());
        }

        @Test
        void isInstanceOfRuntimeException() {
            JsonMappingException ex = new JsonMappingException(new RuntimeException());

            assertInstanceOf(RuntimeException.class, ex);
        }
    }

    @Nested
    class TechnicalPlsqlExceptionTests {

        @Test
        void constructor_withMessage_storesMessage() {
            TechnicalPlsqlException ex = new TechnicalPlsqlException("Something failed");

            assertNotNull(ex.getMessage());
            assertInstanceOf(PlsqlException.class, ex);
        }

        @Test
        void constructor_withMessageNumberAndMessage_formatsMessageCorrectly() {
            TechnicalPlsqlException ex = new TechnicalPlsqlException(1234, "Something failed");

            assertTrue(ex.getMessage().contains("1234"));
            assertTrue(ex.getMessage().contains("Something failed"));
        }

        @Test
        void isInstanceOfRuntimeException() {
            TechnicalPlsqlException ex = new TechnicalPlsqlException("error");

            assertInstanceOf(RuntimeException.class, ex);
        }
    }

    @Nested
    class UgyldigInputExceptionTests {

        @Test
        void constructor_withMessage_storesMessage() {
            UgyldigInputException ex = new UgyldigInputException("Invalid input");

            assertNotNull(ex.getMessage());
            assertInstanceOf(PlsqlException.class, ex);
        }

        @Test
        void isInstanceOfRuntimeException() {
            UgyldigInputException ex = new UgyldigInputException("error");

            assertInstanceOf(RuntimeException.class, ex);
        }
    }
}

