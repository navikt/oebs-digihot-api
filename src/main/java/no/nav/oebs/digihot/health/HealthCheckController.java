package no.nav.oebs.digihot.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.nav.security.token.support.core.api.Unprotected;

@RestController
@RequestMapping(path = "/internal")
public class HealthCheckController {

	private final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

	private HealthCheckDbProbe healthCheckDbProbe;

	HealthCheckController(HealthCheckDbProbe healthCheckDbProbe) {
		this.healthCheckDbProbe = healthCheckDbProbe;
	}

	@Unprotected
	@GetMapping(path = "/isready")
	public void isReady() {
		healthCheckDbProbe.pingDatabase();

		logger.debug("/isready");
	}

	@Unprotected
	@GetMapping(path = "/isalive")
	public void isalive() {
		healthCheckDbProbe.pingDatabase();

		logger.debug("/isalive");
	}
}
