package no.nav.oebs.digihot.config.common.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggingUtils {

	private LoggingUtils() {

	}

	public static String formatExceptionAsString(Throwable exception) {
		if (exception == null) {
			return null;
		}
		var stringWriter = new StringWriter();
		exception.printStackTrace(new PrintWriter(stringWriter));

		return stringWriter.toString();
	}

	public static String maskIfFnr(String text) {
		return text != null //
				? text.replaceAll("([^0-9]+|^)([0-9]{2})[0-9]{7}([0-9]{2})([^0-9]+|$)", "$1$2" + "*******" + "$3$4") //
				: "(null)";
	}
}
