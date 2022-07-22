package tn.esprit.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
public class MailRequest {

	@NotBlank
	private String to;

	@NotBlank
	private String subject;

	@NotBlank
	private String content;
	
	public MailRequest(String to, String subject, String content) {
		this.to = to;
		this.subject = subject;
		this.content = content;
	}
}
