package com.sos.obs.decc.web.rest.util;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.polls.payload.ApiResponse;

import java.net.URI;
import java.util.Optional;

/**
 * Utility class for ResponseEntity creation.
 */
public interface ResponseUtil {

    /**
     * Wrap the optional into a {@link ResponseEntity} with an {@link HttpStatus#OK} status, or if it's empty, it
     * returns a {@link ResponseEntity} with {@link HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @return response containing {@code maybeResponse} if present or {@link HttpStatus#NOT_FOUND}
     */
    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    /**
     * Wrap the optional into a {@link ResponseEntity} with an {@link HttpStatus#OK} status with the headers, or if it's
     * empty, it returns a {@link ResponseEntity} with {@link HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @param header        headers to be added to the response
     * @return response containing {@code maybeResponse} if present or {@link HttpStatus#NOT_FOUND}
     */
    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /*
    public static <X> ResponseEntity<String> wrapOrNotFoundString(Optional<X> maybeResponse, HttpHeaders header) {
        return (ResponseEntity<String>) maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    */
    public static <X> ResponseEntity<ApiResponse> wrapFound(String maybeResponse, String path, Object result, Boolean reponse) {
		URI obj = ServletUriComponentsBuilder
                .fromCurrentContextPath().path(path)
                .buildAndExpand(result).toUri();
    	
    	return ResponseEntity.created(obj).body(new ApiResponse(reponse, maybeResponse));
    }

}
