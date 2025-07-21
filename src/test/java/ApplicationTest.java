import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Enhetstest for loading av applikasjonskonteksten.
 */
@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {

	@Test
	void applicationContextShouldLoad() {

	}
}