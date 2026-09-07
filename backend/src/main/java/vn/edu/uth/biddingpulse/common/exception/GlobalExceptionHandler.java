package vn.edu.uth.biddingpulse.common.exception;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.edu.uth.biddingpulse.auction.exception.AuctionRuleException;
import vn.edu.uth.biddingpulse.common.response.ApiErrorResponse;
import vn.edu.uth.biddingpulse.exceptions.RegistrationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		List<String> errors = exception.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage)
				.toList();
		return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed", errors, request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException exception,
			HttpServletRequest request) {
		return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", exception.getMessage(), List.of(), request);
	}

	@ExceptionHandler({AuctionRuleException.class, RegistrationException.class})
	public ResponseEntity<ApiErrorResponse> handleBusinessRule(RuntimeException exception, HttpServletRequest request) {
		String message = exception instanceof RegistrationException registration
				? registration.getErrorMessage()
				: exception.getMessage();
		return error(HttpStatus.CONFLICT, "BUSINESS_RULE_VIOLATION", message, List.of(), request);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException exception,
			HttpServletRequest request) {
		return error(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Username or password is invalid", List.of(), request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception exception, HttpServletRequest request) {
		log.error("Unexpected API error", exception);
		return error(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "An unexpected error occurred", List.of(), request);
	}

	private ResponseEntity<ApiErrorResponse> error(HttpStatus status, String code, String message, List<String> errors,
			HttpServletRequest request) {
		ApiErrorResponse response = new ApiErrorResponse(status.value(), code, message, request.getRequestURI(),
				LocalDateTime.now(), errors);
		return ResponseEntity.status(status).body(response);
	}
}