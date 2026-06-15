package no.nav.oebs.digihot.exception;

import no.nav.oebs.digihot.config.common.logging.LoggingUtils;

public abstract class PlsqlException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected PlsqlException(String message) {
		super(LoggingUtils.maskIfFnr(message));
	}
}
