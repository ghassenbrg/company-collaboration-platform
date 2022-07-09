package tn.esprit.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 2648041284865721691L;
	
	private final HttpStatus status;
	private final String message;

	public ApiException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ApiException(HttpStatus status, String message, Throwable exception) {
		super(exception);
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}