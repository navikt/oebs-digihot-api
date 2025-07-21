package no.nav.oebs.digihot.api.digihot.brukernr.v1;

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
public class BrukerNrService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_digihot_api_pkg.xxrtv_brukernr";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public BrukerNrService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBrukerNrTransaksjoner(String fodsels_nummer) {


		//public List<Leverandortransaksjon> finnKonteringstransaksjoner(String companycode, String segmentname,
		//															LocalDate lastupdatedate) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(fodsels_nummer));
		/* if (result.getMessageNumber() < 0) {
			 throwPlsqlException(result);
		/} */

		return result.getData();

		// return getApiResponse(result.getData());
	}

	/**
	 * Bygger et requestobjekt som skal konverteres til JSON.
	 */
	private JsonRequest buildRequest(String fodsels_nummer) {
		return JsonRequest.builder() //
				.fodsels_nummer(fodsels_nummer) //
				.build();
	}

	/**
	 * Kaller PL/SQL-prosedyren som utfører forretningslogikken til operasjonen.
	 * @param request
	 */
	private PlsqlProcedureResult executePlsqlProcedure(JsonRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}

	/**
	 * Konverterer en respons-JSON til et responsobjekt som API'et skal returnere.
	 */
	/*private List<Leverandortransaksjon> getApiResponse(String json) {
		if (json == null) {
			throw new TechnicalPlsqlException("Uventet null-verdi istedenfor JSON-objekt fra " + PLSQL_PROCEDURE);
		}

		return toObject(json, new TypeReference<List<Leverandortransaksjon>>() {
		});
	}*/
}
