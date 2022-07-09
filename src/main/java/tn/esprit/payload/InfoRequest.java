package tn.esprit.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
public class InfoRequest {

	@NotBlank
	private String street;

	@NotBlank
	private String city;

	@NotBlank
	private String zipcode;

	private String phone;

	private String lat;

	private String lng;
}