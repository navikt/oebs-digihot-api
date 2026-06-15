package no.nav.oebs.digihot.api.digihot.brukerpass.v1;

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
public class BrukerPassService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_digihot_api_pkg.xxrtv_brukerpass";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public BrukerPassService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBrukerPassTransaksjoner(String fodselsNummer) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(fodselsNummer));
		    return result.getData();

	}

	private JsonRequest buildRequest(String fodselsNummer) {
		return JsonRequest.builder() //
				.fodselsNummer(fodselsNummer) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(JsonRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
