package no.nav.oebs.digihot.health;

import no.nav.oebs.digihot.db.repository.KallLoggRepository;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckDbProbe {

	private KallLoggRepository kallLoggRepository;

	HealthCheckDbProbe(KallLoggRepository kallLoggRepository) {
		this.kallLoggRepository = kallLoggRepository;
	}

	public void pingDatabase() {
		kallLoggRepository.pingKallLogg();
	}
}
