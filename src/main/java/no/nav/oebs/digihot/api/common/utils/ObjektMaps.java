package no.nav.oebs.digihot.api.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.oebs.digihot.exception.JsonMappingException;

/**
 * Superklasse med felles funksjonalitet for implementasjon av tjenestespesifikke Service-klasser.
 */
public class ObjektMaps {

	private ObjectMapper objectMapper;

	protected ObjektMaps(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Mapper fra Java- til JSON-objekt.
	 */
	protected <T> String toJson(T object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new JsonMappingException(e);
		}
	}

}
