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

	public PlsqlProcedureResult(String data, Integer messageNumber, String message) {
		this.data = data;
		this.messageNumber = messageNumber != null ? messageNumber : Integer.valueOf(PlsqlMessageCodes.OK);
		this.message = message;
	}

	public PlsqlProcedureResult(Clob clob, BigDecimal messageNumber, String message) {
		try {
			this.data = clob != null ? clob.getSubString(1, (int) clob.length()) : null;
			this.messageNumber = messageNumber != null ? messageNumber.intValue() : Integer.valueOf(PlsqlMessageCodes.OK);
			this.message = message;
		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Feil ved lesing av clob-verdi", e);
		}
	}

	public static Integer getMessageNumber(PlsqlProcedureResult result) {
		return result != null ? result.getMessageNumber() : Integer.valueOf(PlsqlMessageCodes.OK);
	}

	public static String getMessage(PlsqlProcedureResult result) {
		return result != null ? result.getMessage() : null;
	}
}
