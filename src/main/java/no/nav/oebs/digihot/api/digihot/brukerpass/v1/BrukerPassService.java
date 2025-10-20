package no.nav.oebs.digihot.api.digihot.brukerpass.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.digihot.api.common.model.JsonRequest;
import no.nav.oebs.digihot.api.common.utils.ObjektMaps;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BrukerPassService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_digihot_api_pkg.xxrtv_brukerpass";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public BrukerPassService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBrukerPassTransaksjoner(String fodsels_nummer) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(fodsels_nummer));
        if (result.getMessageNumber() < 0) {
            throwPlsqlException(result);
        }
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
