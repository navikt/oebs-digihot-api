package no.nav.oebs.digihot.common.logging;

import no.nav.oebs.digihot.config.common.logging.LoggingUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggingUtilsTest {

    @Nested
    class FormatExceptionAsStringTests {

        @Test
        void formatExceptionAsString_withNullException_returnsNull() {
            String result = LoggingUtils.formatExceptionAsString(null);

            assertNull(result, "formatExceptionAsString should return null for null input");
        }

        @Test
        void formatExceptionAsString_withException_returnsStackTrace() {
            Exception exception = new IllegalArgumentException("Test error message");

            String result = LoggingUtils.formatExceptionAsString(exception);

            assertNotNull(result);
            assertTrue(result.contains("java.lang.IllegalArgumentException: Test error message"),
                    "Stack trace should contain exception type and message");
        }

        @Test
        void formatExceptionAsString_withNestedExceptions_returnsCompleteStackTrace() {
            try {
                try {
                    throw new IllegalStateException("Inner exception");
                } catch (IllegalStateException e) {
                    throw new RuntimeException("Outer exception", e);
                }
            } catch (RuntimeException e) {
                String result = LoggingUtils.formatExceptionAsString(e);

                assertNotNull(result);
                assertTrue(result.contains("java.lang.RuntimeException: Outer exception"));
                assertTrue(result.contains("java.lang.IllegalStateException: Inner exception"),
                        "Nested exception should appear in stack trace");
            }
        }

        @Test
        void formatExceptionAsString_withRuntimeException_containsExceptionType() {
            RuntimeException exception = new RuntimeException("Runtime error");

            String result = LoggingUtils.formatExceptionAsString(exception);

            assertTrue(result.contains("java.lang.RuntimeException"),
                    "Result should contain exception class name");
        }
    }

    @Nested
    class MaskIfFnrTests {

        @Test
        void maskIfFnr_withNullText_returns_null_string() {
            String result = LoggingUtils.maskIfFnr(null);

            assertEquals("(null)", result);
        }

        @Test
        void maskIfFnr_with11DigitFnr_masksMid9Digits() {
            String result = LoggingUtils.maskIfFnr("01234567890");

            assertEquals("01*******90", result);
        }

        @Test
        void maskIfFnr_withFnrSurroundedByText_masksFnrOnly() {
            String result = LoggingUtils.maskIfFnr("Person with FNR 01234567890 in database");

            assertEquals("Person with FNR 01*******90 in database", result);
        }

        @Test
        void maskIfFnr_withoutFnrPattern_returnsUnchanged() {
            String text = "This is just regular text without FNR";

            String result = LoggingUtils.maskIfFnr(text);

            assertEquals(text, result);
        }

        @Test
        void maskIfFnr_withSpecialCharactersBefore_masksFnr() {
            String result = LoggingUtils.maskIfFnr("[01234567890]");

            assertEquals("[01*******90]", result);
        }

        @Test
        void maskIfFnr_withSpecialCharactersAfter_masksFnr() {
            String result = LoggingUtils.maskIfFnr("(01234567890)");

            assertEquals("(01*******90)", result);
        }

        @Test
        void maskIfFnr_withSpacesBefore_masksFnr() {
            String result = LoggingUtils.maskIfFnr("   01234567890");

            assertEquals("   01*******90", result);
        }

        @Test
        void maskIfFnr_withSpacesAfter_masksFnr() {
            String result = LoggingUtils.maskIfFnr("01234567890   ");

            assertEquals("01*******90   ", result);
        }

        @Test
        void maskIfFnr_withLessThan11Digits_returnsUnchanged() {
            String result = LoggingUtils.maskIfFnr("0123456789");

            assertEquals("0123456789", result);
        }

        @Test
        void maskIfFnr_withMoreThan11Digits_returnsMasksFirstMatch() {
            String result = LoggingUtils.maskIfFnr("123456789012345");

            // Should not match since pattern looks for 2-7-2 digit groups
            assertEquals("123456789012345", result);
        }

        @Test
        void maskIfFnr_withJsonFormat_masksFnr() {
            String json = "{\"fnr\":\"01234567890\",\"name\":\"John\"}";

            String result = LoggingUtils.maskIfFnr(json);

            assertEquals("{\"fnr\":\"01*******90\",\"name\":\"John\"}", result);
        }


        @Test
        void maskIfFnr_withEmptyString_returnsEmptyString() {
            String result = LoggingUtils.maskIfFnr("");

            assertEquals("", result);
        }
    }
}
