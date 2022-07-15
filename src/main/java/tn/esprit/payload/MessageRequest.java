package tn.esprit.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
public class MessageRequest {

	@NotBlank
	private String message;

	@NotBlank
	private Long receiverId;
}
