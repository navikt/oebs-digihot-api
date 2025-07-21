package no.nav.oebs.digihot.config.common.mdc;

import java.security.SecureRandom;

import org.slf4j.MDC;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MdcOperations {

	public static final String MDC_CORRELATION_ID = "correlationId";

	private static final SecureRandom RANDOM = new SecureRandom();

	private MdcOperations() {
	}

	public static String get(String key) {
		return MDC.get(key);
	}

	public static void put(String key, String value) {
		MDC.put(key, value);

		log.debug("Setter MDC-verdi {}={}", key, value);
	}

	public static void remove(String key) {
		MDC.remove(key);
	}

	public static String generateCorrelationId() {
		long systemTime = getSystemTime();
		int randomNumber = getRandomNumber();

		StringBuilder correlationId = new StringBuilder();
		correlationId.append(systemTime);
		correlationId.append("-");
		correlationId.append(randomNumber);

		return correlationId.toString();
	}

	private static int getRandomNumber() {
		return RANDOM.nextInt(Integer.MAX_VALUE);
	}

	private static long getSystemTime() {
		return System.currentTimeMillis();
	}
}
