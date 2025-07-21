package no.nav.oebs.digihot.config.common.logging;


import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.stereotype.Component;

/**
 * Klasse for logging av alle properties i en propertyfil. Nyttig for logging av Vault-konfigurasjonen. Merk at passordverdier
 * blir maskert.
 */
@Component
public class PropertySourceLogger {

    private final Logger logger = LoggerFactory.getLogger(PropertySourceLogger.class);

    private Environment environment;

    public PropertySourceLogger(Environment environment) {
        this.environment = environment;
    }

    /**
     * Logger propertyene som ligger i propertysourcen med angitt navn, sortert etter nøkkel. Støtter kun propertysourcer av
     * typen {@link PropertiesPropertySource}, mao. basert på et {@link java.util.Properties}-objekt.
     * <p>
     * Alle propertyer med navn som inneholder "password", "passord" eller "secret" blir maskert.
     *
     * @param propertySourceName
     *            navn på propertysourcen.
     */
    public void log(String propertySourceName) {
        PropertySources propertySources = ((AbstractEnvironment) environment).getPropertySources();

        PropertySource<?> propertySource = findPropertiesPropertySource(propertySources, propertySourceName);
        if (propertySource == null) {
            logger.warn("Ingen PropertiesPropertySource med navn '{}' er funnet i {}", propertySourceName, propertySources);
            return;
        }

        Properties properties = (Properties) propertySource.getSource();
        TreeMap<String, String> sortedProperties = sortPropertiesByKey(properties);

        StringBuilder builder = new StringBuilder("Gjeldende verdier i propertyfil: " + propertySourceName);

        for (Map.Entry<String, String> entry : sortedProperties.entrySet()) {
            builder.append('\n');
            builder.append(entry.getKey() + "=" + maskIfPassword(entry.getKey(), entry.getValue()));
        }

        logger.info("{}", builder);
    }

    private PropertySource<?> findPropertiesPropertySource(PropertySources propertySources, String sourceName) {
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof PropertiesPropertySource && propertySource.getName().contains(sourceName)) {
                return propertySource;
            }
        }
        return null;
    }

    /**
     * Sorterer Properties-innholdet etter nøkkel ved å dytte propertyene inn i en TreeMap (som er implisitt sortert).
     */
    private TreeMap<String, String> sortPropertiesByKey(Properties properties) {
        TreeMap<String, String> sortedProperties = new TreeMap<>();

        for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            sortedProperties.put(key, properties.getProperty(key));
        }

        return sortedProperties;
    }

    private String maskIfPassword(String key, String value) {
        return (key.toLowerCase().matches(".*(pass[w]?ord|secret|ocp).*")) ? "********" : value;
    }
}