package no.nav.oebs.digihot.api.digihot.serviceordre.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.digihot.api.common.model.JsonRequest;
import no.nav.oebs.digihot.api.common.utils.ObjektMaps;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class ServiceOrdreService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_digihot_api_pkg.xxrtv_serviceordre";

    private static final Logger logger = LoggerFactory.getLogger(ServiceOrdreService.class);

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public ServiceOrdreService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnServiceOrdreTransaksjoner(String fodselsNummer) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(fodselsNummer));

        logger.info(result.getData());

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
