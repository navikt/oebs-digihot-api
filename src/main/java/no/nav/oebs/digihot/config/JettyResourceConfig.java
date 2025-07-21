package no.nav.oebs.digihot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.JettyResourceFactory;

@Configuration
public class JettyResourceConfig {

	@Bean
	public JettyResourceFactory jettyResourceFactory() {
		return new JettyResourceFactory();
	}
}
