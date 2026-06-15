package no.nav.oebs.digihot.config.common.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

public class LoggingUtils {

	private static final Pattern FNR_PATTERN = Pattern.compile("(\\D+|^)(\\d{2})\\d{7}(\\d{2})(\\D+|$)");

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
		if (text == null) {
			return "(null)";
		}
		return FNR_PATTERN.matcher(text).replaceAll("$1$2" + "*******" + "$3$4");
	}
}
