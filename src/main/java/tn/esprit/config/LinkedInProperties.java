package tn.esprit.config;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "linkedin")
@PropertySource("classpath:linkedin.properties")
public class LinkedInProperties {

	@NotBlank
	private String loginUrl;

	@NotBlank
	private String checkpointLoginSubmit;

	@NotBlank
	private String emailFieldXpath;

	@NotBlank
	private String passwordFieldXpath;

	@NotBlank
	private String signingButtonXpath;

	@NotBlank
	private String logoutUrl;

	@NotBlank
	private String profileUrl;

	@NotBlank
	private String profileCardCssSelector;

	@NotBlank
	private String profileNameXpath;

	@NotBlank
	private String profileBioXpath;

	@NotBlank
	private String profileCurrentCompanyXpath;

	@NotBlank
	private String profileEducationXpath;

	@NotBlank
	private String profileLocationXpath;

	@NotBlank
	private String profileImageXpath;

}
