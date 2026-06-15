package no.nav.oebs.digihot.api.common.utils;

import no.nav.oebs.digihot.exception.JsonMappingException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Superklasse med felles funksjonalitet for implementasjon av tjenestespesifikke Service-klasser.
 */
public class ObjektMaps {

	private JsonMapper objectMapper;

	protected ObjektMaps(JsonMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Mapper fra Java- til JSON-objekt.
	 */
	protected <T> String toJson(T object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JacksonException e) {
			throw new JsonMappingException(e);
		}
	}

}
