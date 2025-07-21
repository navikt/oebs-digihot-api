package no.nav.oebs.digihot.exception;

public class TechnicalPlsqlException extends PlsqlException {

	private static final long serialVersionUID = 1L;

	public TechnicalPlsqlException(String message) {
		super(message);
	}

	public TechnicalPlsqlException(Integer messageNumber, String message) {
		this(formatMessage(messageNumber, message));
	}

	private static String formatMessage(Integer messageNumber, String message) {
		return String.format("PL/SQL-feil %d (%s)", messageNumber, message);
	}
}
