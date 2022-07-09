package tn.esprit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import tn.esprit.payload.ApiResponse;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 7544349601900106564L;

	private ApiResponse apiResponse;

	public BadRequestException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}
}