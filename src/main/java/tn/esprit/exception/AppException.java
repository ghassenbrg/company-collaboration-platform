package tn.esprit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 6631866231946573779L;

	public AppException(String message) {
		super(message);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
}