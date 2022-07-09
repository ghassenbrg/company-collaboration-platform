package tn.esprit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import tn.esprit.payload.ApiResponse;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1876358805501533051L;

	private transient ApiResponse apiResponse;

	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super();
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}
}
