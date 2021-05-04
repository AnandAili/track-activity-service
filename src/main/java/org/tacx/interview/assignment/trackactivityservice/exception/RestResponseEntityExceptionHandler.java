package org.tacx.interview.assignment.trackactivityservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.tacx.interview.assignment.trackactivityservice.api.error.ApiError;
import org.tacx.interview.assignment.trackactivityservice.exception.activity.ActivityAlreadyExistsException;
import org.tacx.interview.assignment.trackactivityservice.exception.activity.ActivityNotFoundException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.EmptyFileException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.MultipleFileExtensionException;
import org.tacx.interview.assignment.trackactivityservice.exception.file.WrongFileExtensionException;
import org.tacx.interview.assignment.trackactivityservice.exception.storage.StorageException;

/**
 * Central exception handler, catches all different types of exceptions and maps it to a
 * HTTP related status and message.
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(value = { ActivityNotFoundException.class })
	protected ResponseEntity<Object> handleGameIsOverException(
			ActivityNotFoundException ex, WebRequest request) {
		String error = "Activity is not found: " + ex.getMessage();
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
	}

	@ExceptionHandler(value = { ActivityAlreadyExistsException.class })
	protected ResponseEntity<Object> handleGameIsOverException(
			ActivityAlreadyExistsException ex, WebRequest request) {
		String error = "Activity is already exist:" + ex.getMessage();
		return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, error, ex));
	}

	@ExceptionHandler(value = { StorageException.class, EmptyFileException.class,
			MultipleFileExtensionException.class, WrongFileExtensionException.class })
	protected ResponseEntity<Object> handleGameIsOverException(RuntimeException ex,
			WebRequest request) {
		return buildResponseEntity(
				new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
	}

}
