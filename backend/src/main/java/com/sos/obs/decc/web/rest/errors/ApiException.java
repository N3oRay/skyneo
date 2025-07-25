package com.sos.obs.decc.web.rest.errors;

public class ApiException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

	public ApiException(String string) {
        super(ErrorConstants.ERR_VALIDATION_TYPE, string, "services", "not-found");
    }
}
