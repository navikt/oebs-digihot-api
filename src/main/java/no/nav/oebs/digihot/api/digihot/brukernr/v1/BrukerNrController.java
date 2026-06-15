package no.nav.oebs.digihot.api.digihot.brukernr.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.digihot.Application;
import no.nav.oebs.digihot.api.common.swagger.DigihotSwagger;
import no.nav.oebs.digihot.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Protected;
import no.nav.security.token.support.core.api.Unprotected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BrukerNrController {

	private BrukerNrService service;

	public BrukerNrController(BrukerNrService service) { //,
		this.service = service;
	}

	//@Protected
	@Unprotected
	@GetMapping(path = "/brukernr")
	@DigihotSwagger
	public String finnBrukerNrTransaksjoner(
			@RequestParam(name = "fodsels_nummer") @Parameter(description = "11 siffer") String fodsels_nummer)
	{

		return service.finnBrukerNrTransaksjoner(fodsels_nummer);
	}
}
