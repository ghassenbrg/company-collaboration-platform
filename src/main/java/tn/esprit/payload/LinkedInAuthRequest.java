package tn.esprit.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@NoArgsConstructor
@Getter
@Setter
@ApiModel
public class LinkedInAuthRequest {

	@ApiModelProperty
	@NotNull
	@NotBlank
	private String username;

	@ApiModelProperty
	@NotNull
	@NotBlank
	private String password;
}
