package tn.esprit.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
public class LoginRequest {
	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;
}