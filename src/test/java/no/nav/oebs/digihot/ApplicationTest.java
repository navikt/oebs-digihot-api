package no.nav.oebs.digihot;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.context.ApplicationContext;

/**
 * Enhetstest for loading av applikasjonskonteksten.
 */
@SpringBootTest
@AllArgsConstructor
class ApplicationTest {

	private ApplicationContext applicationContext;

	@Test
	void applicationContextShouldLoad() {
		assertThat(applicationContext).isNotNull();

	}
}