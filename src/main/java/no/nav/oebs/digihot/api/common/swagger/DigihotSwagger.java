package no.nav.oebs.digihot.api.common.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.nav.oebs.digihot.config.SwaggerConfig;

import java.lang.annotation.*;


@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(operationId = "finnTransaksjoner", //
		tags = { SwaggerConfig.DIGIHOT }, //
		summary = "...", //
		security = { @SecurityRequirement(name = SwaggerConfig.BEARER_TOKEN_AUTH) }, //

		responses = { //
				@ApiResponse(responseCode = "200", description = "OK - Returnerer transaksjonene som ble funnet; en tom liste dersom ingen ble funnet."), //
				@ApiResponse(responseCode = "400", description = "- Ugyldig eller manglende input i forespørselen.\n"
						, content = @Content), //
				@ApiResponse(responseCode = "401", description = "- Ugyldig eller manglende aksesstoken.\n"
						+ "- Applikasjonen er ikke preautorisert til å kalle tjenesten.", content = @Content), //
				@ApiResponse(responseCode = "403", description = "Konsumenten har ikke tilgang til ressursen.", content = @Content), //
				@ApiResponse(responseCode = "500", description = "Teknisk feil.", content = @Content) //
		})

public @interface DigihotSwagger {
}
