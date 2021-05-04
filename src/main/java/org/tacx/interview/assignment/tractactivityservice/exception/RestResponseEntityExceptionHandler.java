package org.tacx.interview.assignment.tractactivityservice.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.tacx.interview.assignment.tractactivityservice.api.error.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(value = {ActivityNotFoundException.class})
	protected ResponseEntity<Object> handleGameIsOverException(
			ActivityNotFoundException ex, WebRequest request) {
		String error = "Activity is not found: " + ex.getMessage();
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
	}

	@ExceptionHandler(value = {ActivityAlreadyExistsException.class})
	protected ResponseEntity<Object> handleGameIsOverException(
			ActivityAlreadyExistsException ex, WebRequest request) {
		String error = "Activity is already exist:" + ex.getMessage();
		return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, error, ex));
	}
}
