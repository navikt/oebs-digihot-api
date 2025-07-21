package no.nav.oebs.digihot.config;

import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
		return builder -> builder.featuresToDisable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY) //
				.timeZone(TimeZone.getDefault()); // Bruk plattform default som default, ikke UTC.
	}
}
