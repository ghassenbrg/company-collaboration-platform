package tn.esprit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import tn.esprit.payload.ApiResponse;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = -4706649222201909737L;

	private ApiResponse apiResponse;

	private String message;

	public AccessDeniedException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public AccessDeniedException(String message) {
		super(message);
		this.message = message;
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(ApiResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}