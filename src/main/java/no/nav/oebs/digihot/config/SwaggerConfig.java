package no.nav.oebs.digihot.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

	public static final String DIGIHOT = "Digihot API";

	@Value("${oebs.environment}")
	String env;

	@Value("${oebs.date}")
	String dato;

	@Value("${oebs.version}")
	String versjon;

	public static final String BEARER_TOKEN_AUTH = "BearerToken";

@Bean
	public OpenAPI apiInfo() {
		return new OpenAPI()
				.info(new Info()
						.title(env + " - (NAIS)")
						.description("""
								<p>REST API'er som er tilbudt av Oebs.</p>
								<p>Sikkerhet:</p>
								<ul>
								<li>API'et støtter aksesstoken utstedt av Azure AD</li>""")
						.version(versjon + " " + "("+dato+")"))
				.components(new Components()
						.addSecuritySchemes(BEARER_TOKEN_AUTH,
								new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")
										.description(
												"Lim inn aksesstoken utstedt av azure AD uten \"Bearer\" foran."
										))
				);
	}
}
