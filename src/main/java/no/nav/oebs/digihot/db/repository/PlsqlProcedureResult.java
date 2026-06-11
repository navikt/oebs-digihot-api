package no.nav.oebs.digihot.db.repository;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;

import org.springframework.dao.DataRetrievalFailureException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PlsqlProcedureResult {

	private String data;
	private Integer messageNumber;
	private String message;

	public static final int OK = 0;

	public PlsqlProcedureResult(String data, Integer messageNumber, String message) {
		this.data = data;
		this.messageNumber = messageNumber != null ? messageNumber : OK;
		this.message = message;
	}

	public PlsqlProcedureResult(Clob clob, BigDecimal messageNumber, String message) {
		try {
			this.data = clob != null ? clob.getSubString(1, (int) clob.length()) : null;
			this.messageNumber = messageNumber != null ? messageNumber.intValue() : OK;
			this.message = message;
		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Feil ved lesing av clob-verdi", e);
		}
	}

}
