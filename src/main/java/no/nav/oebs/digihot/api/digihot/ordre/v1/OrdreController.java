package no.nav.oebs.digihot.api.digihot.ordre.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.digihot.api.common.swagger.DigihotSwagger;
import no.nav.oebs.digihot.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Protected;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.DIGIHOT, description = "Digihot")
public class OrdreController {

	private OrdreService service;

	public OrdreController(OrdreService service) { //,
		this.service = service;
	}

	@Protected
	@GetMapping(path = "/ordre")
	@DigihotSwagger
	public String finnBrukerNrTransaksjoner(
			@RequestParam(name = "fodsels_nummer") @Parameter(description = "11 siffer") String fodsels_nummer)
	{

		return service.finnOrdreTransaksjoner(fodsels_nummer);
	}
}
