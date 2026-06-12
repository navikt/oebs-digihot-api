package no.nav.oebs.digihot.config.common.logging;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import no.nav.oebs.digihot.config.common.mdc.MdcOperations;
import no.nav.oebs.digihot.db.entity.KallLogg;
import no.nav.oebs.digihot.db.repository.KallLoggRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {

	private final KallLoggRepository kallLoggRepository;

	public HttpLoggingFilter(KallLoggRepository kallLoggRepository) {
		this.kallLoggRepository = kallLoggRepository;
	}

	@Override
	public void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
			throws ServletException, IOException {

		long startTime = System.currentTimeMillis();

		HttpServletRequest requestToUse = request;
		if (!(request instanceof ContentCachingRequestWrapper)) {
			requestToUse = new ContentCachingRequestWrapper(request);
		}

		HttpServletResponse responseToUse = response;
		if (!(response instanceof ContentCachingResponseWrapper)) {
			responseToUse = new ContentCachingResponseWrapper(response);
		}

		try {
			filterChain.doFilter(requestToUse, responseToUse);
		} finally {
			String formattedRequest = formatRequest(requestToUse);
			String formattedResponse = formatResponse(responseToUse);

			long endTime = System.currentTimeMillis();

			KallLogg kallLogg = KallLogg.builder() //
					.korrelasjonId(MdcOperations.get(MdcOperations.MDC_CORRELATION_ID)) //
					.tidspunkt(LocalDateTime.now()) //
					.type(KallLogg.TYPE_REST) //
					.kallRetning(requestToUse.getMethod().equals("POST") ? KallLogg.RETNING_UT: KallLogg.RETNING_INN) //
					.method(requestToUse.getMethod()) //
					.operation(requestToUse.getRequestURI()) //
					.status(responseToUse.getStatus()) //
					.kalltid(endTime - startTime) //
					.request(formattedRequest) //
					.response(formattedResponse) //
					.logginfo(requestToUse.getParameter("system"))
					.build();

			saveKallLogg(kallLogg);
		}
	}

	//
	// Format request
	//

	private String formatRequest(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		formatMethodAndRequestURI(builder, request);
		formatHeaders(builder, getHeaders(request));
		formatBody(builder, request);

		return builder.toString();
	}

	private void formatMethodAndRequestURI(StringBuilder builder, HttpServletRequest request) {
		builder.append(request.getMethod()).append(' ').append(request.getRequestURI());

		String queryString = request.getQueryString();
		if (queryString != null) {
			builder.append('?').append(queryString);
		}

		builder.append('\n');
	}

	private HttpHeaders getHeaders(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();

		for (String headerName : Collections.list(request.getHeaderNames())) {
			headers.addAll(headerName, Collections.list(request.getHeaders(headerName)));
		}
		return headers;
	}

	private void formatBody(StringBuilder builder, HttpServletRequest request) {
		ContentCachingRequestWrapper wrappedRequest = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrappedRequest == null) {
			return;
		}

		byte[] buf = wrappedRequest.getContentAsByteArray();
		if (buf.length > 0) {
			String payload;

			try {
				payload = new String(buf, wrappedRequest.getCharacterEncoding());
			} catch (IOException e) {
				payload = "[unknown]";
			}

			builder.append(payload);
		}
	}

	private String formatResponse(HttpServletResponse response) {
		StringBuilder builder = new StringBuilder();
		formatStatus(builder, response);
		formatHeaders(builder, getHeaders(response));
		formatBody(builder, response);

		return builder.toString();
	}

	private void formatStatus(StringBuilder builder, HttpServletResponse response) {
		HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
		builder.append("HTTP ").append(httpStatus.value()).append(' ').append(httpStatus.getReasonPhrase()).append('\n');
	}

	private HttpHeaders getHeaders(HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();

		for (String headerName : response.getHeaderNames()) {
			headers.addAll(headerName, new ArrayList<>(response.getHeaders(headerName)));
		}
		return headers;
	}

	private void formatBody(StringBuilder builder, HttpServletResponse response) {
		ContentCachingResponseWrapper wrappedResponse = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		if (wrappedResponse == null) {
			return;
		}

		byte[] buf = wrappedResponse.getContentAsByteArray();
		if (buf.length > 0) {
			String payload;

			try {
				payload = new String(buf, wrappedResponse.getCharacterEncoding());

				// Viktig! Ellers blir det ingen responsdata igjen å returnere til konsumenten...
				wrappedResponse.copyBodyToResponse();
			} catch (IOException e) {
				payload = "[unknown]";
			}

			builder.append(payload);
		}
	}

	private void formatHeaders(StringBuilder builder, HttpHeaders headers) {
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			builder.append(entry.getKey()).append(": ");

			List<String> values = entry.getValue();

			for (int i = 0; i < values.size(); i++) {
				if (i > 0) {
					builder.append(", ");
				}
				builder.append(values.get(i));
			}
			builder.append('\n');
		}
	}

	private void saveKallLogg(KallLogg kallLogg) {
		try {
			kallLoggRepository.save(kallLogg);
		} catch (Exception e) {
			log.error("Feil ved logging av API-kalloggdata til databasen; feilmelding=" + e.getMessage(), e);
		}
	}
}
