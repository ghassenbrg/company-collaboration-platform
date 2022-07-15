package tn.esprit.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@ConfigurationProperties(prefix = "selenium")
@PropertySource("classpath:selenium.properties")
public class SeleniumProperties {

	@NotNull
	private WebDriverType webDriverType;

	@NotBlank
	private String webDriverPath;
}
