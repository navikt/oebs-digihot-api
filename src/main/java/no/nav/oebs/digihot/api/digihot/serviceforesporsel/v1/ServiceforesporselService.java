package no.nav.oebs.digihot.api.digihot.serviceforesporsel.v1;

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
public class ServiceforesporselService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_digihot_api_pkg.xxrtv_serviceforesporsel";

	private PlsqlProcedureRepository plsqlProcedureRepository;

	public ServiceforesporselService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnServiceforesporselTransaksjoner(String fodsels_nummer) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(fodsels_nummer));
		    return result.getData();
	}

	private JsonRequest buildRequest(String fodsels_nummer) {
		return JsonRequest.builder() //
				.fodsels_nummer(fodsels_nummer) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(JsonRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
