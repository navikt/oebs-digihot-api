package no.nav.oebs.digihot.config;

import org.springframework.context.annotation.Configuration;
import no.nav.security.token.support.spring.api.EnableJwtTokenValidation;

@Configuration
@EnableJwtTokenValidation(ignore = { "org.springframework", "org.springdoc" })
public class SecurityConfig {

}