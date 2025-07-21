package no.nav.oebs.digihot.api.digihot.brukerpass.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.digihot.Application;
import no.nav.oebs.digihot.api.common.swagger.DigihotSwagger;
import no.nav.oebs.digihot.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Protected;
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
public class BrukerPassController {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private BrukerPassService service;
	// private LocalDate lastupdatedate;

	public BrukerPassController(BrukerPassService service) { //,
		this.service = service;
	}

	@Protected
	@GetMapping(path = "/brukerpass")
	@DigihotSwagger
	public String finnBrukerNrTransaksjoner(
			@RequestParam(name = "fodsels_nummer") @Parameter(description = "11 siffer") String fodsels_nummer)
	{

		return service.finnBrukerPassTransaksjoner(fodsels_nummer);
	}
}
