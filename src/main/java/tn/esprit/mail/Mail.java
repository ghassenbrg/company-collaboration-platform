package tn.esprit.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Getter
@Setter
@ToString
public class Mail {

	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String mailSubject;
	private String mailContent;
	private String templateName;
	private String contentType;

	public Mail() {
		this.contentType = "text/html";
	}
}
