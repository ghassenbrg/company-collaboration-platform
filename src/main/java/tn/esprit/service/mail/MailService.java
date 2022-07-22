package tn.esprit.service.mail;

import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.MailRequest;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface MailService {

	ApiResponse sendMail(MailRequest mailRequest);
}
