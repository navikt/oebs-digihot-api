package no.nav.oebs.digihot.api.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.nav.oebs.digihot.db.repository.PlsqlMessageCodes;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureResult;
import no.nav.oebs.digihot.exception.JsonMappingException;
import no.nav.oebs.digihot.exception.TechnicalPlsqlException;
import no.nav.oebs.digihot.exception.UgyldigInputException;

/**
 * Superklasse med felles funksjonalitet for implementasjon av tjenestespesifikke Service-klasser.
 */
public class ObjektMaps {

	private ObjectMapper objectMapper;

	protected ObjektMaps() {
	}

	protected ObjektMaps(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Kaster exception iht. feilkoden returnert fra PL/SQL-prosedyren.
	 */
	protected void throwPlsqlException(PlsqlProcedureResult result) {
		switch (result.getMessageNumber()) {
		case PlsqlMessageCodes.FEIL_I_INPUT:
			throw new UgyldigInputException(result.getMessage());
		default:
			throw new TechnicalPlsqlException(result.getMessageNumber(), result.getMessage());
		}
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

	/**
	 * Mapper fra JSON- til Java-objekt.
	 */
	protected <T> T toObject(String json, Class<T> valueType) {
		try {
			return objectMapper.readValue(json, valueType);
		} catch (JsonProcessingException e) {
			throw new JsonMappingException(e);
		}
	}

	/**
	 * Mapper fra JSON- til Java-objekt der generisk typeinformasjon må brukes under mappingen. Dette gjelder typisk for List-
	 * og Map-objekter.
	 */
	protected <T> T toObject(String json, TypeReference<T> objectTypeRef) {
		try {
			return objectMapper.readValue(json, objectTypeRef);
		} catch (JsonProcessingException e) {
			throw new JsonMappingException(e);
		}
	}
}
