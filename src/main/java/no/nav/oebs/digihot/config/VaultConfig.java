package no.nav.oebs.digihot.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Konfigurasjonsklasse for applikasjonskonfigurasjonen som ligger i Vault.
 */
// @Profile("nais")
@Configuration
@PropertySource("file:/var/run/secrets/nais.io/vault/" + VaultConfig.PROPERTY_FILE)
public class VaultConfig {

    static final String PROPERTY_FILE = "secrets.properties";
/*
    @Autowired
    private PropertySourceLogger propertySourceLogger;

    /**
     * Eventlistener som logger konfigurasjonspropertyene som ligger i Vault.
     *
     * @param event
     *            Spring-eventen som trigger listener-metoden.
     *
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        propertySourceLogger.log(PROPERTY_FILE);
    }
    */
}