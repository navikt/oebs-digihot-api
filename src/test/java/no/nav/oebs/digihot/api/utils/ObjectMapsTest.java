package no.nav.oebs.digihot.api.utils;

import no.nav.oebs.digihot.exception.JsonMappingException;
import no.nav.oebs.digihot.api.common.utils.ObjektMaps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMapsTest {

    // Concrete subclass to expose protected methods for testing
    private static class TestObjektMaps extends ObjektMaps {
        TestObjektMaps(JsonMapper ObjectMapper) {
            super(ObjectMapper);
        }

        @Override
        public <T> String toJson(T object) {
            return super.toJson(object);
        }

    }

    private TestObjektMaps objektMaps;

    @BeforeEach
    void setUp() {
        objektMaps = new TestObjektMaps(new JsonMapper());
    }

    @Nested
    class ToJsonTests {

        @Test
        void toJson_withSimpleObject_returnsJsonString() {
            Map<String, String> object = Map.of("key", "value");

            String json = objektMaps.toJson(object);

            assertNotNull(json);
            assertTrue(json.contains("key"));
            assertTrue(json.contains("value"));
        }

        @Test
        void toJson_withNull_returnsNullJson() {
            String json = objektMaps.toJson(null);

            assertEquals("null", json);
        }

        @Test
        void toJson_withInvalidObject_throwsJsonMappingException() {
            Object unserializable = new Object() {
                public String getField() { throw new RuntimeException("cannot serialize"); }
            };

            assertThrows(JsonMappingException.class, () ->
                    objektMaps.toJson(unserializable));
        }
    }

}
