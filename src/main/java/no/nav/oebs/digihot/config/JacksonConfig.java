package no.nav.oebs.digihot.config;

import java.util.TimeZone;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;


@Configuration
public class JacksonConfig {

	@Bean
	public JsonMapperBuilderCustomizer jacksonCustomizer() {
		return jsonMapperBuilder -> jsonMapperBuilder
				.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
				.defaultTimeZone(TimeZone.getDefault());
	}
}
