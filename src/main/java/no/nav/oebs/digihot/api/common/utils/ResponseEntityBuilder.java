package no.nav.oebs.digihot.api.common.utils;


import org.springframework.http.ResponseEntity;
import no.nav.oebs.digihot.db.entity.ApiError;

public class ResponseEntityBuilder {
    private ResponseEntityBuilder() {
        /* This utility class should not be instantiated */
    }

    public static ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

