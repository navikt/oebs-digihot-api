package no.nav.oebs.digihot.api.digihot.brukernr.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.digihot.api.common.model.JsonRequest;
import no.nav.oebs.digihot.api.common.utils.ObjektMaps;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;


@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BrukerNrService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_digihot_api_pkg.xxrtv_brukernummer";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public BrukerNrService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBrukerNrTransaksjoner(String fodselsNummer) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(fodselsNummer));
		return result.getData();
	}

	/**
	 * Bygger et requestobjekt som skal konverteres til JSON.
	 */
	private JsonRequest buildRequest(String fodselsNummer) {
		return JsonRequest.builder() //
				.fodselsNummer(fodselsNummer) //
				.build();
	}

	/**
	 * Kaller PL/SQL-prosedyren som utfører forretningslogikken til operasjonen.
	 * @param request
	 */
	private PlsqlProcedureResult executePlsqlProcedure(JsonRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}

}
